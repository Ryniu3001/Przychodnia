package com.ai.przychodnia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ai.przychodnia.helpers.Type;
import com.ai.przychodnia.model.Reg_notification;
import com.ai.przychodnia.model.User;
import com.ai.przychodnia.service.RegNotificationService;
import com.ai.przychodnia.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController
{
	@Autowired
	UserService service;
	
	@Autowired
	RegNotificationService notifyService;

	@Autowired
	MessageSource messageSource;
	
	/*
	 * This method will list all existing users.
	 */
	@RequestMapping(value = {"/patients" }, method = RequestMethod.GET)
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
		model.addAttribute("type", new String("doctor"));
		return "allusers";
	}
	
	@RequestMapping(value = {"/notifications" }, method = RequestMethod.GET)
	public String notificationList(ModelMap model) {
		List<Reg_notification> notifications = notifyService.findAllNotifications();
		model.addAttribute("notifications", notifications);
		return "adminNotifications";
	}
	
	@RequestMapping(value = {"/delete-{id}-notification" }, method = RequestMethod.GET)
	public String deleteNotification(@PathVariable int id, ModelMap model) {
		notifyService.deleteNotificationById(id);
		model.addAttribute("success", "The item was deleted.");
		return "redirect:/admin/notifications";
	}
}
