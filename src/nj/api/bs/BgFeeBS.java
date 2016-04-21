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

import nj.api.dao.BgAdminDAO;
import nj.api.dao.BgFeeDAO;
import nj.api.entity.FeeEntity;
import nj.common.Constants;
import nj.common.utils.StringUtils;

@Service
public class BgFeeBS {
	
	@Autowired
	private BgFeeDAO feeDAO;
	
	
	/**
	 * 查询单价列表
	 * @param adminId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getFeeList(String psId,int cursor,int offset)throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();
				
		Map<String,Object> pageMap = new HashMap<String,Object>();
		pageMap.put("cursor", cursor);
		pageMap.put("offset", offset);
		
		List<Map<String,Object>> feeList = new ArrayList<Map<String,Object>>();
		
		//查所有
		if(StringUtils.isNull(psId)){
			pageMap.put("psId", null);
			feeList = feeDAO.selectFeeList(pageMap);
			Long count = feeDAO.getFeeCount();
			resMap.put("count", count);
		}else{
			//按条件查询
			pageMap.put("psId", psId);
			feeList = feeDAO.selectFeeList(pageMap);
			resMap.put("count", 1);
		}
		

		resMap.put("list", feeList);
		resMap.put("code", Constants.APICODE_OK);
		return resMap;
		
	}
	
	/**
	 * 单价管理
	 * @param adminId
	 * @param psEntity
	 * @param opType
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> FeeMge(String psEntity,String opType)throws Exception{
		Map<String,Object> resMap = new HashMap<String,Object>();	
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		FeeEntity feeEntity = gson.fromJson(psEntity, FeeEntity.class);
		
		//添加单价
		if("add".equals(opType)){
			
			feeEntity.setPsTime(new Date());
			feeEntity.setPsType("other");
			feeEntity.setPsState(1);
			feeDAO.addFeeInfo(feeEntity);
			
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		
		//修改单价
		if("update".equals(opType)){
			
			feeDAO.updateFeeInfo(feeEntity);
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		
		//删除单价
		if("del".equals(opType)){
			
			feeDAO.delFeeInfo(feeEntity.getPsId());
		
			resMap.put("code", Constants.APICODE_OK);
			return resMap;
		}
		
		resMap.put("code", Constants.APICODE_ERROR);
		return resMap;
		
	}
	

}
