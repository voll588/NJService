package nj.api.dao;

import java.util.List;
import java.util.Map;

import nj.api.entity.Version;

public interface VersionDAO {
	
    List<Version> selectVersion(Map<String,String> map);
}
