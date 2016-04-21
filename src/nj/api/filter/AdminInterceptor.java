package nj.api.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import nj.common.utils.APIUtil;
import nj.common.utils.SysUtil;

public class AdminInterceptor implements HandlerInterceptor  {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
//		String url=request.getRequestURI();
//		HttpSession session = request.getSession();
//		
//		if(url.indexOf("api/admin/login")==-1){
//			String token = (String)session.getAttribute("token");
//			String rtoken = (String) request.getHeader(SysUtil.TOKEN);
//			if(!APIUtil.checkToken(token,rtoken)){
//				response.sendError(400);
//				return false;
//			}	
//		}	
		return true;
	}
	
}
