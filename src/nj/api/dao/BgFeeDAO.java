package nj.api.dao;

import java.util.List;
import java.util.Map;

import nj.api.entity.FeeEntity;

public interface BgFeeDAO {

	//查询单价费用列表
    public List<Map<String,Object>> selectFeeList(Map<String,Object> pageMap)throws Exception;
    
	//费用总数
    public Long getFeeCount()throws Exception;
    
	//添加费用单价
    public void addFeeInfo(FeeEntity feeEntity)throws Exception;
    
	//添加费用单价
    public void delFeeInfo(Long feeId)throws Exception;
    
	//添加费用单价
    public void updateFeeInfo(FeeEntity feeEntity)throws Exception;
}
