package nj.api.ac;

import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nj.api.bs.PayBS;
import nj.api.bs.UserBS;
import nj.common.Constants;
import nj.common.utils.StringUtils;
import nj.common.utils.SysUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;

@Controller
@RequestMapping("api")
public class PayAC {

	final Log logger = LogFactory.getLog(PayAC.class);
	@Autowired
	private PayBS payBS;
	
	
	/**
	 * 费用支付
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/user/payFee", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getPayCharge(HttpServletRequest request,
			@RequestParam String userId,@RequestParam String type,
			@RequestParam String amount,@RequestParam String buyMonth,
			@RequestParam(required = false, defaultValue = "") String terId,
			@RequestParam String channel,@RequestParam String serviceId,
			@RequestParam String psId)throws Exception {
		//客户端传递标识0 android，1 ios
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		String token = (String) request.getHeader(SysUtil.TOKEN);
		String clientIp = (String) request.getLocalAddr();
		return payBS.getPayOrder(userId,amount,channel,clientIp,token,buyMonth,terId,type,serviceId,psId);
	}
	
	/**
	 * ping++支付回调
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/user/cpay", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> callbackPay(HttpServletRequest request)throws Exception {
		String token = (String) request.getHeader(SysUtil.TOKEN);
		//获取头部所有信息
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println(key+" "+value);
        }
		return payBS.callbackPay(request);
	}
	
	/**
	 * 支付历史
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/balance/payHistory", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> payHistory(HttpServletRequest request,
			@RequestParam String userId)throws Exception {
		//客户端传递标识0 android，1 ios
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		String token = (String) request.getHeader(SysUtil.TOKEN);

		return payBS.payHistory(userId, token);
	}
	
	/**
	 * 支付异常
	 * author liangpj
	 * @return
	 */
	@RequestMapping(value = "/user/payCallback", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> payException(HttpServletRequest request,
			@RequestParam String userId,@RequestParam String orderId,
			@RequestParam String serviceType,@RequestParam String payType)throws Exception {

		String token = (String) request.getHeader(SysUtil.TOKEN);
		Long userid = Long.parseLong(StringUtils.base64Decode(userId));
		return payBS.payException(userid, token,orderId,serviceType,payType);
	}
	
	
}
