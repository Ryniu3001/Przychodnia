package com.ai.przychodnia.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ai.przychodnia.helpers.Type;
import com.ai.przychodnia.model.Clinic;
import com.ai.przychodnia.model.Doctor_Clinic;
import com.ai.przychodnia.model.Reg_notification;
import com.ai.przychodnia.model.User;
import com.ai.przychodnia.service.ClinicService;
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
	ClinicService clinicService;

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
	
	/*******************************************************************************/
	/*********************************** CLINICS ***********************************/
	/*******************************************************************************/
	
	@RequestMapping(value = {"/clinics" }, method = RequestMethod.GET)
	public String clinicList(ModelMap model) {
		List<Clinic> clinics = clinicService.findAllClinics();
		model.addAttribute("clinics", clinics);
		return "adminClinics";
	}
	
	@RequestMapping(value = {"/clinics/new-clinic" }, method = RequestMethod.GET)
	public String newClinic(ModelMap model) {
		Clinic clinic = new Clinic();
		model.addAttribute("clinic", clinic);
		model.addAttribute("edit", false);
		return "adminNewClinic";
	}
	
	@RequestMapping(value = {"/clinics/new-clinic" }, method = RequestMethod.POST)
	public String saveClinic(@Valid Clinic clinic, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return "adminNewClinic";
		}
		
		clinicService.newClinic(clinic);
		model.addAttribute("success", "Clinic " + clinic.getName() + " was added.");
		return "redirect:/admin/clinics/";
	}
	
	@RequestMapping(value = {"/clinics/delete-{id}-{name}-clinic" }, method = RequestMethod.GET)
	public String deleteClinic(@PathVariable int id, @PathVariable String name, ModelMap model) {
				
		clinicService.deleteClinicById(id);
		model.addAttribute("success", "Clinic " + name + " was deleted.");
		return "redirect:/admin/clinics/";
	}
	
	@RequestMapping(value = {"/clinics/edit-{id}-{name}-clinic" }, method = RequestMethod.GET)
	public String editClinic(@PathVariable int id, @PathVariable String name, ModelMap model) {
		Clinic clinic = clinicService.findById(id);
		model.addAttribute("clinic", clinic);
		model.addAttribute("edit", true);
		return "adminEditClinic";
	}
	
	@RequestMapping(value = {"/clinics/edit-{id}-{name}-clinic" }, method = RequestMethod.POST)
	public String updateClinic(@Valid Clinic clinic, User doctor, BindingResult result, ModelMap model, HttpServletRequest request) {
		if (result.hasErrors()) {
			return "userRegistration";
		}
		System.out.println(request.getAttribute("doctor_no"));
		clinicService.updateClinic(clinic);
		model.addAttribute("success", "Clinic " + clinic.getName() + " was updated.");
		return "redirect:/admin/clinics";
	}
	
	/*******************************************************************************/
	/******************************* DOCTOR - CLINIC *******************************/
	/*******************************************************************************/
	
/*	@ModelAttribute("asd")
	public Doctor_Clinic getDoc(){
		Doctor_Clinic dc = new Doctor_Clinic();
		return dc;
	}*/
	
	@RequestMapping(value = {"/clinics/assign" }, method = RequestMethod.GET)
	public String assign(@Valid Doctor_Clinic assigns, BindingResult result,ModelMap model) {
		Doctor_Clinic dc = new Doctor_Clinic();
		dc.setHourTo(new Date());
		List<Clinic> clinics = clinicService.findAllClinics();
		List<User> doctors = service.findAllUsers(1);
		model.addAttribute("dc",dc);
		model.addAttribute("clinics", clinics);
		model.addAttribute("doctors", doctors);
		return "adminDoctorsClinic";
	}
	
	@RequestMapping(value = {"/clinics/assign" }, method = RequestMethod.POST)
	public String saveAssign(@Valid Doctor_Clinic assigns, BindingResult result,ModelMap model) {
		List<Clinic> clinics = clinicService.findAllClinics();
		List<User> doctors = service.findAllUsers(1);
		model.addAttribute("clinics", clinics);
		model.addAttribute("doctors", doctors);
		return "adminDoctorsClinic";
	}
	
}
