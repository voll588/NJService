package nj.api.bs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import nj.api.dao.ClassDAO;
import nj.api.dao.ServiceDAO;
import nj.api.dao.StuDAO;
import nj.api.dao.UserDAO;

import nj.api.entity.Inkey;
import nj.api.entity.StuInfo;
import nj.api.entity.UserInfo;
import nj.common.Constants;
import nj.common.utils.APIUtil;


@Service
public class StudentBS {
	
	@Autowired
	private StuDAO stuDAO;
	
	@Autowired
	private ServiceDAO serviceDAO;
	
	@Autowired
	private ClassDAO classDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	/**
	 * 学生信息查询
	 * @param adminId
	 * @param stuId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getstuList(long stuId,int cursor,int offset)throws Exception{
		
		Map<String,Object> resMap = new HashMap<String,Object>();

		Map<String,Object> pageMap = new HashMap<String,Object>();
		pageMap.put("cursor", cursor);
		pageMap.put("offset", offset);
		
		List<Map<String,Object>> stuList = new ArrayList<Map<String,Object>>();
		
		//查所有
		if(stuId==0){
			pageMap.put("stuId", 0);
			stuList = stuDAO.selectStuByMap(pageMap);
			Long count = stuDAO.getStuCount();
			resMap.put("count", count);
		}else{
			//按条件查询
			pageMap.put("stuId", stuId);
			stuList = stuDAO.selectStuByMap(pageMap);
			resMap.put("count", 1);
		}

		resMap.put("list", stuList);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
		
	}
	
	
	/**
	 * 学生管理
	 * @param adminId
	 * @param studentEntity
	 * @param opType
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> stuMge(String studentEntity,String opType)throws Exception{
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		StuInfo student = gson.fromJson(studentEntity, StuInfo.class);
		
		//添加学生
		if("add".equals(opType)){
			//添加学生信息
			student.setcTime(new Date());
			student.setStuState(1);
			stuDAO.addStudent(student);
			//添加基础套餐信息
			Map<String,Object> paraMapFee = new HashMap<String,Object>();
			Map<String,Object> paraMapFood = new HashMap<String,Object>();
			paraMapFee.put("stuId", student.getStuId());
			paraMapFee.put("psId", 1);
			paraMapFee.put("sTime", new Date());
			paraMapFee.put("sType", 01);
			paraMapFee.put("terId", null);
			serviceDAO.addNewService(paraMapFee);
			
			paraMapFood.put("stuId", student.getStuId());
			paraMapFood.put("psId", 2);
			paraMapFood.put("sTime", new Date());
			paraMapFood.put("sType", 02);
			paraMapFood.put("terId", null);
			
			serviceDAO.addNewService(paraMapFood);	
			
			//更新班级人数
			classDAO.updateClassNum(student.getClassId());
			
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		
		//修改学生
		if("update".equals(opType)){
		
			stuDAO.updaeStudent(student);	
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		
		//注销学生
		if("del".equals(opType)){
			//更新学生状态
			stuDAO.delStudent(student.getStuId());
			//更新用户状态
			userDAO.delUser(student.getStuId());;
			//更新服务状态 
			serviceDAO.delService(student.getStuId(),0);
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		
		resMap.put("code", Constants.APICODE_ERROR);
		return resMap;
		
	}
	
	
	/**
	 * 生成邀请码
	 * @param adminId
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getInKey(String studentId)throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();

		//查找此号可用的邀请码
		List<Map<String,Object>>  sList = stuDAO.getInkey(Long.parseLong(studentId));

		if(sList.isEmpty()){
			//生成新的邀请码
			Inkey inkey = new Inkey();
			//产生邀请码
			inkey.setSecret(APIUtil.toSerialCode(Long.parseLong(studentId)));
			inkey.setStuId(Long.parseLong(studentId));
			inkey.setState(1);
			inkey.setKeyTime(new Date());
			stuDAO.addInKey(inkey);
			resMap.put("inkey", inkey.getSecret());
		}else{
			String secret = sList.get(0).get("secret").toString();
			resMap.put("inkey", secret);
		}

		resMap.put("code", Constants.APICODE_OK);
		return resMap;
	}
	
	public Map<String,Object> getUserInfo(String studentId)throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		List<UserInfo> userList = userDAO.selectUserByStu(Long.parseLong(studentId));
				
		resMap.put("list", userList);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
		
	}
	
}
