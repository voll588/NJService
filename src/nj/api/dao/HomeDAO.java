package nj.api.dao;

import java.util.List;
import java.util.Map;

import nj.api.entity.FoodWeekEntity;

public interface HomeDAO {

	
    //查询广告信息
    public List<Map<String,Object>> selectAdvertising()throws Exception;
    
    //查询学校信息
    public Map<String,Object> selectCommonConfig(String key)throws Exception;
    
    //本周餐表
    public Map<String,String> selectFoodList()throws Exception;
    
    //本周餐表
    public void updateFoodList(FoodWeekEntity foodEn)throws Exception;
    
}
