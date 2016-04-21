package nj.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sun.org.glassfish.gmbal.ParameterNames;

import nj.api.entity.ClassEntity;
import nj.api.entity.TeacherInfo;

public interface TeacherDAO {

	
	public List<Map<String,Object>> selectTeacherList();
	
	//根据条件查找老师 
    public List<TeacherInfo> selectTeacherByName(Map<String,Object> pageMap)throws Exception;
    
	//查找老师总数
    public Long getTeacherCount()throws Exception;
    
	//添加老师
    public void addTeacher(TeacherInfo teacher)throws Exception;
    
	//注销老师
    public void delTeacher(Long teacherId)throws Exception;
    
	//修改老师
    public void updateTeacher(TeacherInfo teacher)throws Exception;
    
}
