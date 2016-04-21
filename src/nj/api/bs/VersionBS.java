package nj.api.bs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nj.api.dao.VersionDAO;
import nj.api.entity.Version;

import nj.common.utils.SysUtil;

@Service
public class VersionBS {

	@Autowired
	private VersionDAO versionDAO;
	
	public Map<String,Object> getVersion(String version,String clientType) {
		Map<String,Object> resMap = new HashMap<String,Object>();
		try {
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("version", version);
			paramMap.put("vtype", clientType);
			List<Version> vl = versionDAO.selectVersion(paramMap);
			if(vl.size()>=1){
				resMap.put(SysUtil.CODE, 0);
				resMap.put("vnum",vl.get(0).getVnum());
				resMap.put("vname",vl.get(0).getVname());
				resMap.put("vdate",vl.get(0).getVdate());
				resMap.put("vcontext",vl.get(0).getVcontext());
				resMap.put("vaddress",vl.get(0).getVaddress());
				resMap.put("isupdate",vl.get(0).getIsupdate());
				resMap.put("vsize",vl.get(0).getVsize());
				resMap.put("vmd5",vl.get(0).getVmd5());
			}else{
				resMap.put("vnum", version);
				resMap.put("code", 0);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return resMap;
		}
		resMap.put("code", 0);
		return resMap;
	}
}
