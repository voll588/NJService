package nj.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import nj.api.entity.InterestClassEntity;
import nj.api.entity.ServiceEntity;

public interface InterestClassDAO {	
	
	//查询兴趣班列表
	public List<InterestClassEntity> getInterestList()throws Exception;
	
	//根据兴趣班名称查询兴趣班
	public List<InterestClassEntity> getInterestListByName(Map<String,Object> pageMap)throws Exception;
	
	//查询兴趣班订购、续费状态
	public Map<String,Object> getInterestState(Map<String,Object> paraMap)throws Exception;
	
	//兴趣班总数
	public Long getInterestCount()throws Exception;
	
	//查询已经参加的兴趣班列表
	public List<InterestClassEntity> getJoinList(Long stuId)throws Exception;
	
	//根据订单ID查询兴趣班
	public InterestClassEntity getIntClassById(Long orderId)throws Exception;
	
	//根据ID查询兴趣班
	public InterestClassEntity getInterestClass(Long terId)throws Exception;
	
	//更新兴趣班人数
	public int updateInterestClass(Long terId)throws Exception;
	
	//减少兴趣班人数
	public int cancelInterestClass(Long terId)throws Exception;
	
	//添加兴趣班
	public void addInterestClass(InterestClassEntity intClassEn)throws Exception;
	
	//修改兴趣班
	public void updateInterClassInfo(InterestClassEntity intClassEn)throws Exception;
	
	//关闭兴趣班
	public int delInterestClass(Long terId)throws Exception;
	
	
}
