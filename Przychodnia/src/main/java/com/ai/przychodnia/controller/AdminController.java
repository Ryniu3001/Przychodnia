package com.ai.przychodnia.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ai.przychodnia.helpers.Type;
import com.ai.przychodnia.model.Clinic;
import com.ai.przychodnia.model.Doctor_Clinic;
import com.ai.przychodnia.model.Reg_notification;
import com.ai.przychodnia.model.User;
import com.ai.przychodnia.service.ClinicService;
import com.ai.przychodnia.service.DoctorClinicService;
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
	DoctorClinicService assignService;

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
	
	/*******************************************************************************/
	/********************************* NOTIFICATION ********************************/
	/*******************************************************************************/
	
	@RequestMapping(value = {"/notifications" }, method = RequestMethod.GET)
	public String notificationList(ModelMap model) {
		List<Reg_notification> notifications = notifyService.findNewNotifications();
		model.addAttribute("notifications", notifications);
		return "adminNotifications";
	}
	
	@RequestMapping(value = {"/delete-{id}-{pesel}-notification" }, method = RequestMethod.POST)
	public String deleteNotification(@PathVariable int id, @PathVariable String pesel, ModelMap model) {
		notifyService.deleteNotificationById(id,pesel);
		model.addAttribute("success", "The notification and user was deleted.");
		return "redirect:/admin/notifications";
	}
	
	@RequestMapping(value = {"/accept-{id}-{pesel}-notification" }, method = RequestMethod.POST)
	public String acceptNotification(@PathVariable int id, @PathVariable String pesel, ModelMap model) {
		notifyService.setNotificationState(id, true);
		model.addAttribute("success", "User was accepted.");
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
		
//		Hibernate.initialize(clinic.getDoctorsInClinic());
//		List<Doctor_Clinic> users = new ArrayList<Doctor_Clinic>();		
//		users.addAll(clinic.getDoctorsInClinic());
//
//		Map<Integer,String> map = new HashMap<Integer, String>();
//		for (int i=0; i<users.size();i++){
//			int uid = users.get(i).getDoctor().getId();
//			if (!map.containsKey(uid)){				
//				String workTime = "";
//				for (int j=0; j<users.size(); j++){
//					if (users.get(j).getDoctor().getId() == uid){
//						workTime += users.get(j).getDayOfWeek() + " " + users.get(j).getHourFrom() + " - " + users.get(j).getHourTo() + "\n";
//					}
//				}
//				map.put(uid, workTime);
//			}
//		}
//		
//		List<User> users2 = new ArrayList<User>();
//		for (Iterator<Doctor_Clinic> it = clinic.getDoctorsInClinic().iterator(); it.hasNext();){
//			users2.add(it.next().getDoctor());
//		}
//		
//		model.addAttribute("times", map);
//		model.addAttribute("users", users2);
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
	//TODO Doktorzy przypisani do klinik
	@RequestMapping(value = {"/clinics/assign" }, method = RequestMethod.GET)
	public String assign(ModelMap model) {
	
		Doctor_Clinic dc = new Doctor_Clinic();
		List<Clinic> clinics = clinicService.findAllClinics();
		List<User> doctors = service.findAllUsers(1);

		model.addAttribute("dc",dc);
		model.addAttribute("clinics", clinics);
		model.addAttribute("doctors", doctors);
		return "adminDoctorsClinic";
	}
	
	@RequestMapping(value = {"/clinics/assign" }, method = RequestMethod.POST)
	public String saveAssign(@ModelAttribute("dc") @Valid Doctor_Clinic assign, BindingResult result,ModelMap model, 
			HttpServletRequest request) {
		
		String[] days = request.getParameterValues("days");
		boolean pkOK = isEverythingOk(assign, result, days);
		if (result.hasErrors() || !pkOK) {
			model.addAttribute("clinics", clinicService.findAllClinics());
			model.addAttribute("doctors", service.findAllUsers(1));
			return "adminDoctorsClinic";
		}

		try {
			assignService.newAssignation(assign, days);
		} catch (DataIntegrityViolationException e) {
			ObjectError err = new ObjectError("dc", messageSource.getMessage(
					"unique.dc.assign", null, Locale.getDefault()));
			result.addError(err);
			model.addAttribute("clinics", clinicService.findAllClinics());
			model.addAttribute("doctors", service.findAllUsers(1));
			return "adminDoctorsClinic";
		}
		model.addAttribute("success", "Doctor-Clinic link is created.");
		
		return "redirect:/admin/";
	}
	
	private boolean isEverythingOk(Doctor_Clinic dc, BindingResult result, String[] days){
		boolean isOK = true;
		if (dc.getPk().getDoctor().getId() < 0) {
			result.rejectValue("doctor", "clinicpk.dc.empty", "Undefined message");
			isOK = false;
		}
		if (dc.getPk().getClinic().getId() < 0) {
			result.rejectValue("clinic", "clinicpk.dc.empty", "Undefined message");
			isOK = false;
		}
		if(days == null){
			result.rejectValue("dayOfWeek", "clinicpk.dc.empty", "Undefined message");
			isOK = false;
		}
		return isOK;
	}
	@Transactional
	@RequestMapping(value = {"/clinics/assignment-{cid}" }, method = RequestMethod.GET)
	public String assignment(@PathVariable int cid, ModelMap model) {
		List<Clinic> clinics = null;
		if (cid == 0){
			clinics = clinicService.findAllClinics();
		}else{
			clinics = new ArrayList<Clinic>(1);
			clinics.add(clinicService.findById(cid));
		}
		
		for (Iterator<Clinic> it = clinics.iterator(); it.hasNext(); )
			Hibernate.initialize(it.next().getDoctorsInClinic());
		
		model.addAttribute("clinics", clinics);
		return "adminDoctorsClinicAssignment";
	}
	
}
