package com.ai.przychodnia.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ai.przychodnia.model.User;
import com.ai.przychodnia.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController
{

	@Autowired
	UserService service;

	@Autowired
	MessageSource messageSource;
	
	@RequestMapping(value = { "/new-{type}" }, method = RequestMethod.GET)
	public String newPatient(@PathVariable int type, ModelMap model) {
		User user = new User();
		if (type >=0 && type<=2)
			user.setType(type);
		else
			return null;
		user.setType(0);
		user.setIs_enabled(false);
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		return "userRegistration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	//@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/new-{type}" }, method = RequestMethod.POST)
	public String saveUser(@PathVariable int type, @Valid User user, BindingResult result,
			/*ModelMap model,*/ final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			return "userRegistration";
		}
		/* Check if pesel is unique */
		if (!service.isUserPeselUnique(user.getId(), user.getPesel())) {
			result.rejectValue("pesel", "non.unique.pesel",
					new Object[] { user.getPesel() }, "Undefined message");
			return "userRegistration";
		}

		/* Check if username is unique */
		if (!service.isUsernameUnique(user.getId(), user.getUsername())) {
			result.rejectValue("username", "non.unique.username",
					new Object[] { user.getUsername() }, "Undefined message");
			return "userRegistration";
		}
		
		try {
			service.saveUser(user);
		} catch (DataIntegrityViolationException e) {
			/*
			 * Na wypadek gdyby dwie sejse stweirdzily ze nie naruszaja
			 * unikalnosci
			 */
			ObjectError err = new ObjectError("user", messageSource.getMessage(
					"unique.user.nuerror", null, Locale.getDefault()));
			result.addError(err);
			user.setId(0);
			return "userRegistration";
		}

		/*model.addAttribute("success",
				"User " + user.getName() + " " + user.getSurname()
						+ " registered successfully");*/
		
		/* Automatyczne logowanie po rejestracji */ 
		UserDetails userDetails = service.loadUserByUsername(user.getUsername());
		Authentication auth = new UsernamePasswordAuthenticationToken(
				userDetails.getUsername (),userDetails.getPassword (),userDetails.getAuthorities ());
		SecurityContextHolder.getContext().setAuthentication(auth);
		redirectAttributes.addAttribute("success", "You have successfully registered and logged in.");
		
		return("redirect:/user/");
		
	}

	/*
	 * This method will provide the medium to update an existing user.
	 */
	@RequestMapping(value = { "/edit-{username}-user" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String username, ModelMap model) {
		User user = service.findUserByUsername(username);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		return "userRegistration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-{username}-user" }, method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "userRegistration";
		}

		service.updateUser(user);

		model.addAttribute("success",
				"User " + user.getName() + " " + user.getSurname()
						+ " updated successfully");
		return "success";
	}

	/*
	 * This method will delete an user by it's pesel value.
	 */
	@RequestMapping(value = { "/delete-{pesel}-user" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String pesel, RedirectAttributes redirectAttributes) {
		service.deleteUserByPesel(pesel);
		redirectAttributes.addAttribute("success", "The item was deleted.");
		return "redirect:/admin/patients";
	}

}