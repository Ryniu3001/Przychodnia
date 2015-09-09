package com.ai.przychodnia.helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;

public class SessionHelper
{
	private static SessionHelper instance;
	
	private SessionHelper(){}	
	public static SessionHelper getInstance(){
		if (instance == null)
			instance = new SessionHelper();
		return instance;
	}
	
	/**
	 * Zapisuje dane autoryzacyjne uzytkownika do sesji
	 * @param request
	 */
	public void putUserDetailInSession(HttpServletRequest request) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		HttpSession session = request.getSession(true);
		session.setAttribute("userDetails", principal);
	}
}
