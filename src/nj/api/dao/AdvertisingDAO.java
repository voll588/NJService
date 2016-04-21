package nj.api.dao;

import java.util.List;
import java.util.Map;

public interface AdvertisingDAO {

    //查询广告信息
    public List<Map<String,Object>> selectAdvertising()throws Exception;
}
