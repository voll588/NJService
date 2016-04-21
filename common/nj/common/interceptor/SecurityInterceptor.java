package nj.common.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;  
import org.springframework.web.servlet.ModelAndView;  

/**
* 
*/
@SuppressWarnings("unused")
public class SecurityInterceptor implements HandlerInterceptor {  
	
	Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);  
	 
    public SecurityInterceptor() {  
    }
    @Override
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {
    	//System.out.println("*********************************preHandle****************************");
		return true;
    }  
  
    @Override  
    public void postHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler,  
            ModelAndView modelAndView) throws Exception {
    	//System.out.println("*********************************postHandle****************************");
    }  
  
    @Override  
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex)  
            throws Exception {
    	//System.out.println("*********************************afterCompletion****************************");
    }  
  
}  