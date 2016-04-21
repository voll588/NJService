package nj.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import nj.api.entity.Inkey;
import nj.api.entity.StuInfo;


public interface StuDAO {
	
	//根据id查找幼儿
    public StuInfo selectStuByKey(Long stuId)throws Exception;
    
	//根据用户id查找幼儿
    public StuInfo selectStuByUserId(Long userId)throws Exception;
    
	//根据条件查找幼儿
    public List<Map<String,Object>> selectStuByMap(Map<String,Object> pageMap)throws Exception;
    
	//根据班级查找幼儿
    public List<StuInfo> selectStuByClass(Long classId)throws Exception;
    
    //根据stuId查找邀请码
    public List<Inkey>  selectInkeyBystuId(Long stuId)throws Exception;
    
	//根据用户id查找幼儿
    public Long getStuCount()throws Exception;
    
	//添加学生
    public int addStudent(StuInfo student)throws Exception;
    
	//注销学生
    public void delStudent(Long stuId)throws Exception;
    
	//修改学生
    public void updaeStudent(StuInfo student)throws Exception;
    
	//关联用户和学生
    public void updateUserOfStu(Long stuid,Long userId)throws Exception;
    
	//生成邀请码
    public void addInKey(Inkey inkey)throws Exception;
    
	//根据学生id查找inkey
    public List<Map<String,Object>> getInkey(Long StuId)throws Exception;
    
	
}
