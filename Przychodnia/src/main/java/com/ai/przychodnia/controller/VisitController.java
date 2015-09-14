package com.ai.przychodnia.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ai.przychodnia.helpers.DaysDecoder;
import com.ai.przychodnia.model.Clinic;
import com.ai.przychodnia.model.Doctor_Clinic;
import com.ai.przychodnia.model.User;
import com.ai.przychodnia.model.Visit;
import com.ai.przychodnia.service.ClinicService;
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
	ClinicService clinicService;

	@Autowired
	MessageSource messageSource;

	/*
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public ModelAndView newVisit(ModelMap model) {
		Visit visit = new Visit();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 
	    
	    User patient = userService.findUserByUsername(name);
	    
	    model.addAttribute("patient", patient);
		model.put("clinics", clinicService.findAllClinics());
		model.addAttribute("visit", visit);
		model.addAttribute("edit", false);
		//return "newVisit";
		return new ModelAndView("newVisit", model); 
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
//	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
//	public String saveVisist(@Valid Visit visit, BindingResult result,
//			ModelMap model) {
//
//		if (result.hasErrors()) {
//			return "newVisit";
//		}
//
//		try {
//			service.saveVisit(visit);
//		} catch (DataIntegrityViolationException e) {
//			/*
//			 * Na wypadek gdyby dwie sejse stweirdzily ze nie naruszaja
//			 * unikalnosci
//			 */
//			ObjectError err = new ObjectError("visit",
//					messageSource.getMessage("unique.visit.nuerror", null,
//							Locale.getDefault()));
//			result.addError(err);
//			visit.setId(0);
//			return "redirect:/visits/new";   
//		}
//
//		model.addAttribute("success", visit + " registered successfully");
//		return "success";
//	}
	@Transactional
	@RequestMapping(value = { "/new/choose-doctor" }, method = RequestMethod.POST)
	public String pickVisitDoctor(@ModelAttribute Visit visit, ModelMap model) {
		User patient = userService.findById(visit.getPatient().getId());
		Clinic clinic = clinicService.findById(visit.getClinic().getId());
		visit.setClinic(clinic);	
		visit.setPatient(patient);
		List<Doctor_Clinic> doctor_clinic = new ArrayList<Doctor_Clinic>();
		Hibernate.initialize(clinic.getDoctorsInClinic());
		doctor_clinic.addAll(clinic.getDoctorsInClinic());
		Set<User> doctors = new HashSet<User>();
		for (Iterator<Doctor_Clinic> it = doctor_clinic.iterator(); it.hasNext(); ){
			doctors.add(it.next().getDoctor());
		}
		
		model.addAttribute("doctors", doctors);
		return "newVisitDoctor";
	}
	
	@Transactional
	@RequestMapping(value = { "/new/choose-date" }, method = RequestMethod.POST)
	public String pickVisitDate(@ModelAttribute Visit visit, ModelMap model) {
		User doctor = userService.findById(visit.getDoctor().getId());
		visit.setDoctor(doctor);
		Clinic clinic = clinicService.findById(visit.getClinic().getId());
		Hibernate.initialize(clinic.getDoctorsInClinic());
		try {
			getFreeDates(visit.getPatient().getId(), clinic, visit.getDoctor().getId());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//model.addAttribute("doctors", null);
		return "newVisitDoctor";
	}
	
	private List<Date> getFreeDates(int pid, Clinic clinic, int did) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 6);
		calendar.set(Calendar.MINUTE, 0);
		Date t1 = new Date(calendar.getTimeInMillis());
		
		calendar.add(Calendar.DATE, 14);
		calendar.set(Calendar.HOUR_OF_DAY, 18);
		Date t2 = new Date(calendar.getTimeInMillis());

		calendar.setTimeInMillis(t1.getTime());
		
		List<Doctor_Clinic> doc_clin = new ArrayList<Doctor_Clinic>();
		Hibernate.initialize(clinic.getDoctorsInClinic());
		doc_clin.addAll(clinic.getDoctorsInClinic());
		
		Map<Integer, String[]> daysOfWork = new HashMap<Integer, String[]>();
		//Zapisuje dni i godziny pracy doktora do wygodnej postaci mapy
		for (Iterator<Doctor_Clinic> it = doc_clin.iterator(); it.hasNext(); ){
			Doctor_Clinic dc = it.next();
			if (dc.getDoctor().getId() == did){
				DaysDecoder.getInstance().decode(dc, daysOfWork);
			}
		}
		
		List<Date> list = new ArrayList<Date>();
		/** Z przedzialu 2 tygodni wybiera tylko te dni i godziny w ktorych pracuje doktor */
		for (calendar.getTime(); calendar.getTime().before(t2); calendar.add(Calendar.MINUTE, 30)){
//			// Nie uwzglednia godzin przed 6 i po 18
//			if (calendar.get(Calendar.HOUR_OF_DAY) == 18 || calendar.get(Calendar.HOUR_OF_DAY) < 6 )
//				continue;
			// Nie uwzglednia weekendow
			String u = new SimpleDateFormat("u").format(calendar.getTime());
			if ((u.equals("6")) || (u.equals("7")))
				continue;
			
			//Nie uwzglednia godzin i dni tygodnia innych niz te w ktorych pracuje doktor
			if (daysOfWork.containsKey(Integer.parseInt(u))){
				String [] s = daysOfWork.get(Integer.parseInt(u));
				DateFormat format = new SimpleDateFormat("HH:mm");
				String cal = format.format(calendar.getTime());
				
				if ((s != null) && ((format.parse(s[0]).before(format.parse(cal))) 
						&& ((format.parse(s[1]).after(format.parse(cal))))) 
						|| (format.parse(s[0]).equals(format.parse(cal)) || format.parse(s[1]).equals(format.parse(cal)))){					
					System.out.println(dateFormat.format(calendar.getTime()));
					list.add(calendar.getTime());
				}

			}else
				continue;			
		}
		
		return list;
	}
}
