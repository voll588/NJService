package nj.api.bs;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nj.common.sms.SMS;

import nj.common.utils.APIUtil;
import nj.common.utils.StringUtils;
import nj.api.dao.ServiceDAO;
import nj.api.dao.StuDAO;
import nj.api.dao.UserDAO;
import nj.api.entity.Inkey;
import nj.api.entity.StuInfo;
import nj.api.entity.UserInfo;
import nj.api.entity.VerificationCode;


import nj.common.Constants;
import nj.common.utils.SysUtil;

@Service
public class UserBS {
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private StuDAO stuDAO;
	
	@Autowired
	private ServiceDAO serviceDAO;	
	
	/**
	 * 用户注册
	 * author liangpj
	 * phone 注册电话
	 * password 密码
	 * code 手机验证码
	 * secret 邀请码
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> register(String phone,String password,String code,String secret,String clientTag)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();

		List<Inkey> inkey = userDAO.selectInKey(secret);
		
		if(inkey.isEmpty()){
			map.put("code", Constants.APICODE_INKEY_NULL);
			return map;
		}
		
		//查找学生信息
		StuInfo stu=stuDAO.selectStuByKey(inkey.get(0).getStuId());

		if(stu==null){
			map.put("code", Constants.APICODE_INKEY_NULL);
			return map;
		}
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put(phone, stu.getStuId());
		
		//查找用户名是否存在
		UserInfo olderUser = userDAO.selectUserByPU(paramap);
		if(olderUser!=null){
			map.put("code", Constants.APICODE_USER_REG);
			return map;
		}
		if(password.length()<6){
			map.put("code", Constants.APICODE_PWD_LENGTHE);
			return map;
		}
		
		//校验手机验证码是否合法
		Map<String,String> phoneMap = new HashMap<String,String>();
		phoneMap.put("phone", phone);
		phoneMap.put("type", "register");
		List<VerificationCode> verlist = userDAO.selectPhoneCode(phoneMap);
		if(verlist.size()==0){
			map.put("code", Constants.APICODE_CODE_ERROR);
			return map;
		}else{
			VerificationCode verificationCode = (VerificationCode)verlist.get(0);
			if(!verificationCode.getCode().equals(code)){
				map.put("code", Constants.APICODE_CODE_ERROR);
				return map;
			}
			Long d1 = verificationCode.getTime().getTime();
			Long d2 = new Date().getTime();
		    long diff = d1 - d2;
		    long days = diff / (1000 * 60);
			if(days>30){
				map.put("code", Constants.APICODE_CODE_ERROR);
				return map;
			}
			
		}
		//当终端号异常时，输入10号状态码
		if(StringUtils.isNull(clientTag)){
			clientTag="10"; 
		}
		
		UserInfo user = new UserInfo();
		
		//查找主账号是否存在
		List<UserInfo> accountUser = userDAO.selectUserByStu(stu.getStuId());
		
		if(accountUser.size() > 0){
			user.setIsAccount(1);  //子号
		}else{
			user.setIsAccount(0);  //主号
		}
		
		user.setPhone(phone);
		user.setStuId(stu.getStuId());
		user.setPassword(password);
		user.setNickName(stu.getStuName());
		user.setRegTime(new Date());
		user.setuState(1);    //状态为1表示正常
		user.setInKey(inkey.get(0).getKeyId());
		user.setClientTag(Integer.valueOf(clientTag));
		
		userDAO.insertUser(user);
		//更新邀请码为无效状态
		userDAO.setInKey(inkey.get(0).getKeyId());

		if(user.getIsAccount() == 0){
			//更新产品服务信息
			serviceDAO.updateRegService(stu.getStuId(),user.getUserId());
			//关联用户与学生
			stuDAO.updateUserOfStu(user.getStuId(),user.getUserId());
		}
		
		map.put("code", 0);
		return map;
	}
	
	/**
	 * 用户登录
	 * author liangpj
	 * phone 电话
	 * password 密码
	 * @return
	 */
	public Map<String,Object> login(String phone,String password,String clientTag)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();

		//查找用户名是否存在
		UserInfo olderUser = userDAO.selectUserByName(phone);
		if(olderUser==null||
				!olderUser.getPassword().equals(password)||
				1!=olderUser.getuState()){
			map.put("code", Constants.APICODE_LOGIN_ERROR);
			return map;
		}	
		UserInfo upUser = new UserInfo();
		upUser.setUserId(olderUser.getUserId());
		upUser.setClientTag(Integer.parseInt(clientTag));
		userDAO.updateUserInfo(upUser);
		Map<String,Object> pageMap = new HashMap<String,Object>();
		pageMap.put("stuId", olderUser.getStuId());
		pageMap.put("cursor", 0);
		pageMap.put("offset", 0);
		List<Map<String,Object>> stuList = stuDAO.selectStuByMap(pageMap);
		map.put("userId", StringUtils.base64Encode("" + olderUser.getUserId()));
		map.put("access", olderUser.getIsAccount());
		map.put("stuName", olderUser.getNickName());
		map.put("stuClass", stuList.get(0).get("className"));
		map.put("token", SysUtil.initMacKey());
		Map<String,Object> tokMap = new HashMap<String,Object>();
		tokMap.put("token", map.get("token"));
		tokMap.put("phone", olderUser.getPhone());
		userDAO.insertUserToekn(tokMap);//插入用户token
		map.put("code", 0);
		return map;
	}
	
	/**
	 * 用户退出
	 * author liangpj
	 * @return
	 */
	public Map<String,Object> loginout(String userId,String token,String clientTag)throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();
//用户验证		
		Long userid = Long.parseLong(StringUtils.base64Decode(userId));
		UserInfo olderUser = userDAO.selectUserById(userid);
		if(olderUser==null){
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
		if(!APIUtil.checkToken(token,olderUser.getuToken())){
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
//用户验证
		
		Map<String,Object> tokMap = new HashMap<String,Object>();
		tokMap.put("token", "");
		tokMap.put("phone", olderUser.getPhone());
		userDAO.insertUserToekn(tokMap);//删除token
		resMap.put("code", 0);
		return resMap;
	}
	
	/**
	 * 用户获取短信验证码
	 * author liangpj
	 * @return
	 */
	public Map<String,Object> getCode(String phone,String type)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		
		UserInfo olderUser = userDAO.selectUserByName(phone);

		SMS sms = new SMS();
		String code = sms.getIdentifyingcode();
		VerificationCode verificationCode = new VerificationCode();
		verificationCode.setPhone(phone);
		verificationCode.setCode(code);
		verificationCode.setTime(new Date());
		if("1".equals(type)){
			if(olderUser!=null){
				map.put("code", Constants.APICODE_USER_REG);
				return map;
			}
			verificationCode.setType("register");
		}else if("2".equals(type)){
			if(olderUser==null){
				map.put("code", Constants.APICODE_ERROR);
				return map;
			}
			verificationCode.setType("forgetPwd");
		}
		userDAO.insertPhoneCode(verificationCode);
			// sms.send(phone, content);
		String str = sms.sendMsg(code, SysUtil.getValue("msgid"), phone);

		map.put("code", 0);
		return map;
	}
	
	/**
	 * 重置密码
	 * author liangpj
	 * @return
	 */
	public Map<String,Object> newPwd(String phone,String code,String pwd)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		UserInfo olderUser = userDAO.selectUserByName(phone);
		if(olderUser==null){
			map.put("code", Constants.APICODE_ERROR);
			return map;
		}
		//校验手机验证码是否合法
		Map<String,String> phoneMap = new HashMap<String,String>();
		phoneMap.put("phone", phone);
		phoneMap.put("type", "forgetPwd");
		List<VerificationCode> verlist = userDAO.selectPhoneCode(phoneMap);
		if(verlist.size()==0){
			map.put("code", Constants.APICODE_CODE_ERROR);
			return map;
		}else{
			VerificationCode verificationCode = (VerificationCode)verlist.get(0);
			if(!verificationCode.getCode().equals(code)){
				map.put("code", Constants.APICODE_CODE_ERROR);
				return map;
			}
			Long d1 = verificationCode.getTime().getTime();
			Long d2 = new Date().getTime();
		    long diff = d1 - d2;
		    long days = diff / (1000 * 60);
			if(days>30){
				map.put("code", Constants.APICODE_CODE_ERROR);
				return map;
			}
			
		}
		if(pwd.length()<6){
			map.put("code", Constants.APICODE_PWD_LENGTHE);
			return map;
		}
		Map<String,String> pwdMap = new HashMap<String,String>();
		pwdMap.put("phone", phone);
		pwdMap.put("pwd", pwd);
		userDAO.updatePwd(pwdMap);
		map.put("code", 0);
		return map;
	}
	
	/**
	 * 修改密码
	 * author liangpj
	 * @return
	 */
	public Map<String,Object> updatePwd(String userId,String pwd,String newPwd,String token)throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();
		
//用户验证		
		Long userid = Long.parseLong(StringUtils.base64Decode(userId));
		UserInfo olderUser = userDAO.selectUserById(userid);
		if(olderUser==null){
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
		if(!APIUtil.checkToken(token,olderUser.getuToken())){
			resMap.put("code", Constants.APICODE_TOKEN_ERROR);
			return resMap;
		}
//用户验证
		
		if(!pwd.equals(olderUser.getPassword())){
			resMap.put("code", Constants.APICODE_PWD_ERROR);
			return resMap;
		}
		if(newPwd.length()<6){
			resMap.put("code", Constants.APICODE_PWD_LENGTHE);
			return resMap;
		}
		Map<String,String> pwdMap = new HashMap<String,String>();
		pwdMap.put("phone", olderUser.getPhone());
		pwdMap.put("pwd", newPwd);
		userDAO.updatePwd(pwdMap);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
	}
}
