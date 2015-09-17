package com.ai.przychodnia.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ai.przychodnia.model.Clinic;
import com.ai.przychodnia.model.DoctorClinicId;
import com.ai.przychodnia.model.Doctor_Clinic;
import com.ai.przychodnia.model.User;
import com.ai.przychodnia.service.DoctorClinicService;
import com.ai.przychodnia.service.RegNotificationService;
import com.ai.przychodnia.service.UserService;

@Controller
public class MainController
{
	@Autowired
	UserService userService;
	
	@Autowired
	DoctorClinicService assignService;
	
	@Autowired
	RegNotificationService notifyService;
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String rootPage(ModelMap model, RedirectAttributes redirectAttr) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 				//get logged in username
	    User user = userService.findUserByUsername(name);
	    String success = (String)model.get("success");
	    redirectAttr.addAttribute("success", success);
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
	public String adminMain(ModelMap model, HttpServletRequest request) {
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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 
	    User user = userService.findUserByUsername(name);
	    model.addAttribute("doctor", user);
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
	
	@Transactional
	@RequestMapping(value = {"/clinics/schedule-{did}" }, method = RequestMethod.GET)
	public String assignment(@PathVariable int did, ModelMap model) {
		User doctor = null;
		doctor = userService.findById(did);
		List<Doctor_Clinic> dclinics = new ArrayList<Doctor_Clinic>();
		Hibernate.initialize(doctor.getDoctorsInClinic());
		dclinics.addAll(doctor.getDoctorsInClinic());
		
		List<Clinic> clinics = new ArrayList<Clinic>();
		for (Iterator<Doctor_Clinic> it = dclinics.iterator(); it.hasNext(); ){
			Clinic clinic = it.next().getClinic();
			Hibernate.initialize(clinic.getDoctorsInClinic());
			clinics.add(clinic);
		}
		Set<Clinic> set = new HashSet<Clinic>(clinics);
		clinics.clear();
		clinics.addAll(set);
		model.addAttribute("clinics", clinics);
		return "doctorsClinicAssignment";
	}
	
	@RequestMapping(value = {"/clinics/assignment/delete-{cid}-{uid}-{dayOfWeek}" }, method = RequestMethod.POST)
	public String removeAssignment(@PathVariable int cid, @PathVariable int uid,
			@PathVariable String dayOfWeek, RedirectAttributes redirectAttributes) {
		DoctorClinicId pk = new DoctorClinicId(cid, uid);
		pk.setDayOfWeek(dayOfWeek);
		assignService.deleteAssignationById(pk);
		
		redirectAttributes.addAttribute("success", "Doctor-Clinic link was deleted.");
		return "redirect:/doctor";
	}

}
