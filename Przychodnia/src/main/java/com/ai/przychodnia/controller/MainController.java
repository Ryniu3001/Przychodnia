package com.ai.przychodnia.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ai.przychodnia.model.User;
import com.ai.przychodnia.service.RegNotificationService;
import com.ai.przychodnia.service.UserService;

@Controller
public class MainController
{
	@Autowired
	UserService userService;
	
	@Autowired
	RegNotificationService notifyService;
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String rootPage(ModelMap model, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 				//get logged in username
	    User user = userService.findUserByUsername(name);
	    
			if (user.getType() == 2)
				return "redirect:/admin";
			else if (user.getType() == 0)
				return "redirect:/user";
			else if (user.getType() == 1)
				return"redirect:/doctor";
	    
	    model.addAttribute("username", user.getUsername());
		return "userMainView";
	}
	
	@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
	public String adminMain(ModelMap model) {
/*		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 				//get logged in username
	    User user = userService.findUserByUsername(name);
	    model.addAttribute("username", user.getUsername());*/
		long count = notifyService.countNewNotifications();
		model.addAttribute("count", count);
		return "adminMainView";
	}
	
	@RequestMapping(value = { "/user" }, method = RequestMethod.GET)
	public String userMain(ModelMap model) {
		return "userMainView";
	}
	
	@RequestMapping(value = { "/doctor" }, method = RequestMethod.GET)
	public String doctorMain(ModelMap model) {
		return "doctorMainView";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		
		ModelAndView model = new ModelAndView();
		
		//Jesli jakis user jest zalogowany to przekieruj do /
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			model.setViewName("redirect:/");
			return model;
		}
		
		if (error != null) {
			model.addObject("error", "Invalid username and/or password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}

}
