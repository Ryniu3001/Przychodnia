package com.ai.przychodnia.controller;

import java.util.List;

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
import com.ai.przychodnia.service.UserService;

@Controller
public class MainController
{
	@Autowired
	UserService userService;
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String userMain(ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 				//get logged in username
	    User user = userService.findUserByUsername(name);
	    
			if (user.getType() == 2)
				return "redirect:/admin";
			else if (user.getType() == 0)
				return "redirect:/";
			else if (user.getType() == 1)
				return"redirect:/doctor";
	    
	    model.addAttribute("username", user.getUsername());
		return "userMainView";
	}
	
	@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
	public String adminMain(ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 				//get logged in username
	    User user = userService.findUserByUsername(name);
	    model.addAttribute("username", user.getUsername());
		return "adminMainView";
	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin");

		return model;

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
