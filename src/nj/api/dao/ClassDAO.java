package nj.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import nj.api.entity.ClassEntity;
import nj.api.entity.StuInfo;
import nj.api.entity.UserInfo;

public interface ClassDAO {

	//根据条件查找班级
    public List<ClassEntity> selectClassByName(Map<String,Object> pageMap)throws Exception;
    
	//班级总数
    public Long getClassCount()throws Exception;
    
	//根据老师id查找班级
    public List<ClassEntity> selectClassByTId(Long teacherId)throws Exception;
	
	//添加班级
    public void addClass(ClassEntity classEn)throws Exception;
    
	//更新班级信息
    public void updateClass(ClassEntity classEn)throws Exception;
    
	//关闭班级
    public void closeClass(Long classId)throws Exception;
    
	//更新班级人数
	public void updateClassNum(Long classId)throws Exception;
}
