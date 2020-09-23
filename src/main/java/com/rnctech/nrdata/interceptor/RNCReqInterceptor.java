package com.rnctech.nrdata.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.rnctech.nrdata.services.AuthService;

/**
 * identify register user or anonymous user
 * for non-register user, row limit to 10,000 
 * and single table data generation with whole deviavtion
 * 
 * @Author Zilin Chen
 * @Date 2020/09/23
 */

@Controller
public class RNCReqInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	AuthService authService;
	
	private static final Logger logger = LoggerFactory.getLogger(RNCReqInterceptor.class);
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {			
		if(request.getRequestURI().endsWith("/error") || request.getRequestURI().endsWith("/ping"))
			return true;
		
		if(request.getRequestURI().endsWith("/logout")) {
			request.getSession(false).invalidate();
			return true;
		}
		
		String p = request.getContextPath()+"/";
		if(request.getRequestURI().equals(p) || request.getRequestURI().equals(p+"api/v1/")){	//for registered user
			try{
				String user = authService.validate(request);
			}catch(Throwable t) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, t.getMessage());
				logger.error(request.getRemoteUser()+" "+request.getRemoteHost()+ "\n"+request.toString());
				return false;
			}
		} else {
			return false;			
		}
		return true;		
	}
	
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		
	}
	
}
