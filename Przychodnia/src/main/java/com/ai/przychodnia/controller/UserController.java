package com.ai.przychodnia.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ai.przychodnia.helpers.Type;
import com.ai.przychodnia.model.User;
import com.ai.przychodnia.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController
{

	@Autowired
	UserService service;

	@Autowired
	MessageSource messageSource;
	
	/*
	 * This method will list all existing users.
	 */
	@RequestMapping(value = {"/patients" }, method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_1')")
	public String listPatients(ModelMap model) {
		List<User> users = service.findAllUsers(Type.patients.getValue());
		model.addAttribute("users", users);
		model.addAttribute("type", new String("patient"));
		return "allusers";
	}

	@RequestMapping(value = {"/doctors" }, method = RequestMethod.GET)
	public String listDoctors(ModelMap model) {
		List<User> users = service.findAllUsers(Type.doctors.getValue());
		model.addAttribute("users", users);
		return "allusers";
	}
	/*
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/new-patient" }, method = RequestMethod.GET)
	public String newPatient(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		return "UserRegistration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/new-patient" }, method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "UserRegistration";
		}
		/* Check if pesel is unique */
		if (!service.isUserPeselUnique(user.getId(), user.getPesel())) {
			result.rejectValue("pesel", "non.unique.pesel",
					new Object[] { user.getPesel() }, "Undefined message");
			return "UserRegistration";
		}

		/* Check if username is unique */
		if (!service.isUsernameUnique(user.getId(), user.getUsername())) {
			result.rejectValue("username", "non.unique.username",
					new Object[] { user.getUsername() }, "Undefined message");
			return "UserRegistration";
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
			return "UserRegistration";
		}

		model.addAttribute("success",
				"User " + user.getName() + " " + user.getSurname()
						+ " registered successfully");
		return "success";
	}

	/*
	 * This method will provide the medium to update an existing user.
	 */
	@RequestMapping(value = { "/edit-{pesel}-user" }, method = RequestMethod.GET)
	public String editEmployee(@PathVariable String pesel, ModelMap model) {
		User user = service.findUserByPesel(pesel);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		return "UserRegistration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-{pesel}-user" }, method = RequestMethod.POST)
	public String updateEmployee(@Valid User user, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "UserRegistration";
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
	public String deleteUser(@PathVariable String pesel) {
		service.deleteUserByPesel(pesel);
		return "redirect:/patients";
	}

}