package nj.api.dao;

import java.util.List;
import java.util.Map;

public interface PushDAO {
    
    //修改cid
    public void updateCid(Map<String,Object> map)throws Exception;
    
    //查找信息列表
    public List<Map<String,Object>> pushList(Map<String,Object> map)throws Exception;
    
    //查找信息列表
    public List<Map<String,Object>> getMsgList(Map<String,Object> pageMap)throws Exception;
    
    //求和
    public Long MsgListCount()throws Exception;
    
    public Long pushListCount(Map<String,Object> map)throws Exception;
    
    //添加通知消息
    public int insertMsg(Map<String,Object> map)throws Exception;
    
    //添加用户消息记录
    public void insertMsgLog(Map<String,Object> map)throws Exception;
    
    //通知消息已读状态
    public void updateReadMsg(Long msgId)throws Exception;
}
