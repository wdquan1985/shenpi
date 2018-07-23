package com.arisen.shenpi.util;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SpringSecuritySession {
	/**shiro管理的session
	 * @return
	 */
	public static HttpSession getSession(){
		//Subject currentUser = SecurityUtils.getSubject();  
		ServletRequestAttributes attr = (ServletRequestAttributes) 
			    RequestContextHolder.currentRequestAttributes();
			HttpSession session= attr.getRequest().getSession(true); // true == allow create
			return session;
	}
	
	/**获取当前登录的用户名
	 * @return
	 */
	public static String getUsername(){
		return getSession().getAttribute(Const.SESSION_USERNAME).toString();
	}
	
	/**获取当前登录的用户姓名
	 * @return
	 */
	public static String getU_name(){
		return getSession().getAttribute(Const.SESSION_U_NAME).toString();
	}
}
