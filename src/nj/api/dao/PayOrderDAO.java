package nj.api.dao;

import java.util.List;
import java.util.Map;

import nj.api.entity.OrderEntity;

public interface PayOrderDAO {

	//根据订单id查询订单信息
	public OrderEntity selectPayOrder(long orderId)throws Exception;
	
	//查询缴费中的订单
	public OrderEntity selectPayingOrder(Map<String,Object> paramMap)throws Exception;
	//插入订单
	public void insertPayOrder(OrderEntity order)throws Exception;
	
	//插入订单
	public void updateChargeId(OrderEntity order)throws Exception;
	
	//更新订单信息
	public void updatePayOrder(Map<String,Object> paramMap)throws Exception;
	//更新兴趣班
	public void updateClass(Map<String,Object> paramMap)throws Exception;
	
	public List<Map<String,Object>> payHistory(Map<String,Object> paraMap)throws Exception;
	
	public List<Map<String,Object>> selectHisMonth(Long userId)throws Exception;
	
	//查询费用账单
	public List<Map<String,String>> selectBill(Map<String,Object> paraMap)throws Exception;
	
}
