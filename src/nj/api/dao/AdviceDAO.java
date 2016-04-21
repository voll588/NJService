package nj.api.dao;

import java.util.List;
import java.util.Map;

public interface AdviceDAO {

	
    //添加意见
    public void insertAdvice(Map<String,Object> map)throws Exception;
    
    //查询意见
    public List<Map<String,Object>> getAdviceList(Map<String,Object> map)throws Exception;
    
    //意见求和
    public Long getAdviceCount()throws Exception;

}
