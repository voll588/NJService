package nj.api.dao;

import java.util.List;
import java.util.Map;

import nj.api.entity.Inkey;
import nj.api.entity.UserInfo;
import nj.api.entity.VerificationCode;

public interface UserDAO {
	
	//----------------查询-------------------
	
	//根据id查找用户-
    public UserInfo selectUserById(Long userId)throws Exception;
    
    //根据账户名查找用户-
    public UserInfo selectUserByName(String phone)throws Exception;
    
    //根据学号查找用户-
    public List<UserInfo> selectUserByStu(Long stuId)throws Exception;

    //根据账户名和id查找用户-
    public UserInfo selectUserByPU(Map<String,Object> map)throws Exception;
    
    //查找手机验证码-
    public List<VerificationCode> selectPhoneCode(Map<String,String> map)throws Exception;
    
    //查找邀请验证码-
    public List<Inkey> selectInKey(String secret)throws Exception;
    
    //全部用户
    public List<UserInfo> selectAllUser()throws Exception;
    
	//根据班级查找学生
    public List<UserInfo> selectstudentByClass(Long classId)throws Exception;
    
    //----------------添加-------------------
    
	//添加一条用户信息-
	public void insertUser(UserInfo user)throws Exception;
	
    //添加手机验证码-
    public void insertPhoneCode(VerificationCode verificationCode)throws Exception;
    
    //添加用户token-
    public void insertUserToekn(Map<String,Object> map)throws Exception;
    
    //----------------更新-------------------
    
    //修改密码-
    public void updatePwd(Map<String,String> map)throws Exception;
    
    //修改cid
    public void updateCid(Map map)throws Exception;
    
    //修改用户信息
    public void updateUserInfo(UserInfo user)throws Exception;
    
    //修改用户信息
    public void delUser(Long sutId)throws Exception;
    
    //修改用户金额
    public void updateBalance(Map map)throws Exception;
    
    //设置邀请码无效-
    public void setInKey(Long keyId)throws Exception;
    
}