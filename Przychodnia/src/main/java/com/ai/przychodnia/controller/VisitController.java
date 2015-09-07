package com.ai.przychodnia.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ai.przychodnia.model.Visit;
import com.ai.przychodnia.service.UserService;
import com.ai.przychodnia.service.VisitService;

@Controller
@RequestMapping("/visits")
public class VisitController
{
	@Autowired
	VisitService service;
	
	@Autowired
	UserService userService;

	@Autowired
	MessageSource messageSource;

	/*
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public ModelAndView newVisit(ModelMap model) {
		Visit visit = new Visit();
		model.put("users", userService.findAllUsers(-1));
		model.addAttribute("visit", visit);
		model.addAttribute("edit", false);
		//return "newVisit";
		return new ModelAndView("newVisit", model); 
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveVisist(@Valid Visit visit, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "newVisit";
		}

		try {
			service.saveVisit(visit);
		} catch (DataIntegrityViolationException e) {
			/*
			 * Na wypadek gdyby dwie sejse stweirdzily ze nie naruszaja
			 * unikalnosci
			 */
			ObjectError err = new ObjectError("visit",
					messageSource.getMessage("unique.visit.nuerror", null,
							Locale.getDefault()));
			result.addError(err);
			visit.setId(0);
			return "redirect:/visits/new";   
		}

		model.addAttribute("success", visit + " registered successfully");
		return "success";
	}
}
