package nj.api.ac;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nj.api.bs.VersionBS;


@Controller
@RequestMapping("/api")
public class VersionAC {

	
	@Autowired
	private VersionBS versionBS;
	
	@RequestMapping(value = "/user/version", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getVersion(@RequestParam String curVersion,
			@RequestParam String vtype) {
		return versionBS.getVersion(curVersion, vtype);
	}
	
}
