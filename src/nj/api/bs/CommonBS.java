package nj.api.bs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nj.api.dao.PayOrderDAO;

import nj.common.utils.StringUtils;
import nj.common.utils.UploadUtil;

@Service
public class CommonBS {

	@Autowired
	private PayOrderDAO orderDAO;
	
	String[] excelHeader = {"学号","订单号", "幼儿名称", "班级","交款日","费用类型","支付金额","学生类型"}; 
	

	/**
	 * 根据日期生成交易报表
	 * @param year
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook toExcel(String year,String month)throws Exception{
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		List<Map<String,String>> billList = new ArrayList<Map<String,String>>();
		
		//获取报表数据
		
		if(!StringUtils.isNull(year)&&StringUtils.isNull(month)){
			paraMap.put("year", year);
			billList = orderDAO.selectBill(paraMap);
		}else if(!StringUtils.isNull(month)){
			paraMap.put("year", year);
			paraMap.put("month", month);
			billList = orderDAO.selectBill(paraMap);
		}else{
			billList = orderDAO.selectBill(paraMap);
		}
		
		//生成报表
		
		HSSFWorkbook wb = new HSSFWorkbook(); 
		HSSFSheet sheet = wb.createSheet("牛津国际幼儿园报表");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        for (int i = 0; i < excelHeader.length; i++) {    
            HSSFCell cell = row.createCell(i);    
            cell.setCellValue(excelHeader[i]);    
            cell.setCellStyle(style);    
            sheet.autoSizeColumn(i);    
        }    
    
        for (int i = 0; i < billList.size(); i++) {    
            row = sheet.createRow(i + 1);        
            row.createCell(0).setCellValue(String.valueOf(billList.get(i).get("stu_id")));    
            row.createCell(1).setCellValue(String.valueOf(billList.get(i).get("order_id")));    
            row.createCell(2).setCellValue(billList.get(i).get("stu_name"));
            row.createCell(3).setCellValue(billList.get(i).get("class_name"));    
            row.createCell(4).setCellValue(String.valueOf(billList.get(i).get("order_time")));    
            row.createCell(5).setCellValue(billList.get(i).get("order_subject")); 
            row.createCell(6).setCellValue(String.valueOf(billList.get(i).get("amount")));    
            row.createCell(7).setCellValue(billList.get(i).get("stu_type").equals("01")?"学生":"业主");
        } 
		
		return wb;
		
	}
	
	/**
	 * 上传接口
	 * @param request
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String uploadFile (HttpServletRequest request,String type)throws Exception{
		
		String outFile_path = "";
		
		int year = new DateTime().getYear();
		
		if("TT".equals(type)){
			outFile_path = "Resource/thumb/"+year+"/";
		}else if("TV".equals(type)){
			outFile_path = "Resource/video/"+year+"/";
		}else if("TVP".equals(type)){
			outFile_path = "Resource/vpic/"+year+"/";
		}else if("ICP".equals(type)){
			outFile_path = "Resource/vpic/"+year+"/";
		}else{
			return "{\"error\":\"true\",\"code\":\"1\"}";
		}
		
		
		
		Map<String,Object> map = new UploadUtil().fileUpload(request, outFile_path);
		if(null!=map.get("url")&&map.get("url").toString().length()>0){
			//Map<String,Object> themeMap = new HashMap<String,Object>();
			//themeMap.put("themePic",map.get("url").toString());
			//themeDAO.insertThemePic(themeMap);
			return "{\"success\":\"true\",\"code\":\"0\",\"picId\":\""+map.get("url")+"\"}";
		}else{
			return "{\"error\":\"true\",\"code\":\"1\"}";
		}
		
	}
}
