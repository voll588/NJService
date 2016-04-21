package nj.api.bs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nj.api.dao.ClassDAO;
import nj.api.dao.TeacherDAO;
import nj.api.entity.ClassEntity;
import nj.api.entity.TeacherInfo;
import nj.common.Constants;
import nj.common.utils.StringUtils;

/**
 * 老师管理服务类
 * @author shallen
 *
 */
@Service
public class TeacherBS {
	
	@Autowired
	private TeacherDAO teacherDAO;
	
	@Autowired
	private ClassDAO classDAO;

	/**
	 * 查询老师信息
	 * @param adminId
	 * @param teacherName
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getTeacherList(String teacherName,int cursor,int offset)throws Exception{
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		Map<String,Object> pageMap = new HashMap<String,Object>();
		pageMap.put("cursor", cursor);
		pageMap.put("offset", offset);
		
		List<TeacherInfo> teacherList = new ArrayList<TeacherInfo>();
		
		//查所有
		if(StringUtils.isNull(teacherName)){
			pageMap.put("teacherName", null);
			teacherList = teacherDAO.selectTeacherByName(pageMap);
			Long count = teacherDAO.getTeacherCount();
			resMap.put("count", count);
		}else{
			//按条件查询
			pageMap.put("teacherName", teacherName);
			teacherList = teacherDAO.selectTeacherByName(pageMap);
			resMap.put("count", 1);
		}

		resMap.put("list", teacherList);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
		
	}
	
	
	/**
	 * 管理老师信息
	 * @param adminId
	 * @param teacherEntity
	 * @param opType
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> TeacherMge(String teacherEntity,String opType)throws Exception{
		
		Map<String,Object> resMap = new HashMap<String,Object>();
				
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		TeacherInfo teacherInfo = gson.fromJson(teacherEntity, TeacherInfo.class);
		
		//新增老师
		
		if("add".equals(opType)){
			
			teacherInfo.setCreateTime(new Date());
			teacherInfo.setTeacherState(1);
			//上传视频地址
			//teacherInfo.setVideoUrl("");
			//上传图片地址
			teacherDAO.addTeacher(teacherInfo);
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}

		//修改老师信息
		
		if("update".equals(opType)){
			
			teacherDAO.updateTeacher(teacherInfo);
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
			
		}
		
		//删除老师
		
		if("del".equals(opType)){
			//判断是否带班
			
			List<ClassEntity> classList = classDAO.selectClassByTId(teacherInfo.getTeacherId());
			
			if(classList.size()<=0){
				resMap.put("errorMessage", Constants.BG_DELTEACHER_ERROR);
				resMap.put("code", Constants.APICODE_ERROR);
				return resMap;
			}
			
			teacherDAO.delTeacher(teacherInfo.getTeacherId());
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		
		resMap.put("code", Constants.APICODE_ERROR);
		return resMap;
		
	}
	
	
}
