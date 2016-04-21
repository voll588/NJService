package nj.api.bs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nj.api.dao.ClassDAO;
import nj.api.dao.ServiceDAO;
import nj.api.dao.StuDAO;
import nj.api.dao.UserDAO;

import nj.api.entity.ClassEntity;
import nj.api.entity.StuInfo;
import nj.common.Constants;
import nj.common.utils.StringUtils;

@Service
public class ClassBS {
	
	@Autowired
	private ClassDAO classDAO;

	@Autowired
	private StuDAO stuDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ServiceDAO serviceDAO;
	
	/**
	 * 查询班级
	 * @param adminId
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getClassList(String className,int cursor,int offset)throws Exception{
		
		Map<String,Object> resMap = new HashMap<String,Object>();
				
		List<ClassEntity> classList = new ArrayList<ClassEntity>();
		
		Map<String,Object> pageMap = new HashMap<String,Object>();
		pageMap.put("cursor", cursor);
		pageMap.put("offset", offset);
		
		//查所有
		if(StringUtils.isNull(className)){
			pageMap.put("className", null);
			classList = classDAO.selectClassByName(pageMap);
			Long count = classDAO.getClassCount();
			resMap.put("count", count);
		}else{
			//按条件查询
			pageMap.put("className", className);
			classList = classDAO.selectClassByName(pageMap);
			resMap.put("count", 1);
		}

		resMap.put("list", classList);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
		
	}
	
	
	/**
	 * 班级管理
	 * @param adminId
	 * @param classEntity
	 * @param opType
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> classMge(String classEntity,String opType)throws Exception{
		
		Map<String,Object> resMap = new HashMap<String,Object>();		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		ClassEntity classEn = gson.fromJson(classEntity, ClassEntity.class);
		
		//新增班级
		
		if("add".equals(opType)){
			
			classEn.setClassTime(new Date());
			classEn.setClassState(1);
			classDAO.addClass(classEn);
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}

		//修改班级
		
		if("update".equals(opType)){
			//ContextLoader.getCurrentWebApplicationContext();
			if(classEn.getClassState()==0){
				List<StuInfo> stuList = stuDAO.selectStuByClass(classEn.getClassId());
				if(stuList.size()>0){
					resMap.put("errorMessage", Constants.BG_DELCLASS_ERROR);
					resMap.put("code", Constants.APICODE_ERROR);
					return resMap;
				}
			}
			
			classDAO.updateClass(classEn);
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		
		//删除班级
		
		if("del".equals(opType)){
			
			List<StuInfo> stuList = stuDAO.selectStuByClass(classEn.getClassId());
			if(stuList.size()>0){
				resMap.put("errorMessage", Constants.BG_DELCLASS_ERROR);
				resMap.put("code", Constants.APICODE_ERROR);
				return resMap;
			}
			
			classDAO.closeClass(classEn.getClassId());
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;	
		}
		
		//毕业
		if("complete".equals(opType)){
			List<StuInfo> stuList = stuDAO.selectStuByClass(classEn.getClassId());
			
			for(StuInfo student:stuList){
				StuInfo stu = new StuInfo();
				stu.setStuId(student.getStuId());
				stu.setStuState(2);
				stuDAO.updaeStudent(stu);
				//更新用户状态
				userDAO.delUser(stu.getStuId());;
				//更新服务状态 
				serviceDAO.delService(stu.getStuId(),0);
			}
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		
		
		resMap.put("code", Constants.APICODE_ERROR);
		return resMap;
		
	}

	
}
