package nj.api.dao;

import java.util.List;
import java.util.Map;

import nj.api.entity.ServiceEntity;

public interface ServiceDAO {

	
	//查询服务列表
	public List<ServiceEntity> getServiceList(Map<String,Object> paraMap)throws Exception;
	
	//查询单价总数
	public Long getServiceCount(Map<String,Object> paraMap)throws Exception;
	
	//根据id查询服务
	public ServiceEntity getServiceById(Long serviceId)throws Exception;
	
	//根据兴趣班id查找服务
	public ServiceEntity getServiceByTer(Long terId,Long userId)throws Exception;
	
	//查询欠费信息
	public ServiceEntity selectArrearage(Long userId,Integer type )throws Exception;
	
	//查询缴费记录
	public List<Map<String,Object>> getPayList(Map<String,Object> paraMap)throws Exception;
	
	//查询费用单价
	public Map<String,Object> selectServiceFee(Long psId)throws Exception;
	
	//添加教学服务项
	public void addNewService(Map<String,Object> paraMap)throws Exception;
	
	//更新教学服务
	public void updateService(Map<String,Object> paramMap)throws Exception;
	
	//删除教学服务
	public void delService(Long stuId,Integer state)throws Exception;
	
	//添加基础教学用户id
	public void updateRegService(Long stuId,Long userId)throws Exception;
	
	//查询欠费任务
	public List<ServiceEntity> selectArrearageTask()throws Exception;
	
	
}
