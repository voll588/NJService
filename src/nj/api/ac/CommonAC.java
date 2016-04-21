package nj.api.ac;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import nj.api.bs.BgAdminBS;
import nj.api.bs.CommonBS;
import nj.api.bs.MsgBS;
import nj.common.Constants;
import nj.common.utils.SysUtil;

@Controller
@RequestMapping("/api")
public class CommonAC {

	final Log logger = LogFactory.getLog(CommonAC.class);
	
	@Autowired
	private CommonBS commonBS;
	
	@Autowired
	private BgAdminBS adminBS;
	
	
	@RequestMapping(value = "/admin/upload", method = {RequestMethod.POST})
	@ResponseBody
	public void uploadFile(HttpServletRequest request,
			HttpServletResponse response,@RequestParam String type)throws Exception {	
		request.getInputStream();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(commonBS.uploadFile(request,type));	
		return;
	}
	
	
	
	@RequestMapping(value = "/admin/reportList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response,@RequestParam String adminId,String year,
			@RequestParam(required = false, defaultValue = "") String month,
			@RequestParam String token)throws Exception {

		if(adminBS.checkAdmin(adminId, token)){
			HSSFWorkbook wb = commonBS.toExcel(year,month);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=njexcel.xls"); 
			OutputStream ouputStream = response.getOutputStream();

			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		}else{
			return ;
		}
	}
	
}
