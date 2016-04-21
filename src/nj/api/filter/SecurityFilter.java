package nj.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nj.common.utils.APIUtil;
import nj.common.utils.SysUtil;


public class SecurityFilter implements Filter {


	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response  =(HttpServletResponse)res; 
		String url=request.getRequestURI();
		System.out.println("url = "+url);	 
		String contextPath = request.getContextPath();
		System.out.println("contextPath = "+contextPath);
		String clienttag = (String) request.getHeader(SysUtil.CLIENT_TAG);
		System.out.println("当前登录终端： "+clienttag);//登陆超时跳转
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods","POST,GET");
		response.setHeader("Access-Control-Allow-Headers","token,client_tag"); 
		if(url.equals(contextPath+"/") || url.equals(contextPath)) {
			request.getRequestDispatcher("/static/html/index.html").forward(request,response);
			return ;
		}
	
		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("---------------------- SecurityServlet init ----------------------");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}	
	
	
	
	
	

}
