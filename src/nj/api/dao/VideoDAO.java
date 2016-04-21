package nj.api.dao;

import java.util.List;
import java.util.Map;
import nj.api.entity.VideoEntity;

public interface VideoDAO {

	
	//根据id查找直播列表
    public List<Map<String,Object>> getVideoList()throws Exception;
    
	//查找直播列表
    public List<Map<String,Object>> getLiveVideoList(Map<String,Object> paraMap)throws Exception;
    
	//查找直播列表
    public Long getLiveVideoListCount()throws Exception;
    
	//添加直播设备
    public void addLiveVideo(VideoEntity videoEn)throws Exception;
    
	//更新直播设备
    public void updateLiveVideo(VideoEntity videoEn)throws Exception;
    
	//删除直播设备
    public void delLiveVideo(Long videoId)throws Exception;
    
    
}
