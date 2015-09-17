package com.ai.przychodnia.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.przychodnia.dao.DoctorClinicDao;
import com.ai.przychodnia.dao.UserDao;
import com.ai.przychodnia.helpers.DaysDecoder;
import com.ai.przychodnia.model.DoctorClinicId;
import com.ai.przychodnia.model.Doctor_Clinic;

@Service
@Transactional
public class DoctorClinicServiceImpl implements DoctorClinicService {

	@Autowired
	private DoctorClinicDao dao;

	
	@Autowired
	UserDao userDao;
	
	@Override
	public Doctor_Clinic findAssignById(DoctorClinicId id) {
		return dao.findAssignById(id);
	}

	@Override
	public List<Doctor_Clinic> findAllAssignations() {
		return dao.findAllAssignations();
	}

	@Override
	public void newAssignation(Doctor_Clinic assign) {
		dao.newAssignation(assign);
	}

	public void newAssignation(Doctor_Clinic assign, String[] days) throws DataIntegrityViolationException, ParseException{
		if (days!=null){
			String value = "";
			for (int i=0; i<days.length;i++)
				value += days[i] + ", ";
			value = (String) value.subSequence(0, value.length()-2);
			assign.setDayOfWeek(value);
			
			//Sprawdza czy doktor juz pracuje w danej przychodni w dany dzien. jesli tak rzuca wyjatek
			DateFormat format = new SimpleDateFormat("HH:mm");
			Map<Integer, String[]> daysOfWorkToSave = new HashMap<Integer, String[]>();
			Map<Integer, String[]> daysOfWork = new HashMap<Integer, String[]>();
			int clinicIdToSave = assign.getClinic().getId();
			Set<Doctor_Clinic> set = userDao.findById(assign.getDoctor().getId()).getDoctorsInClinic();
			DaysDecoder.getInstance().decode(assign, daysOfWorkToSave);
			for (Iterator<Doctor_Clinic> it = set.iterator(); it.hasNext(); ){
				Doctor_Clinic dc = it.next();
				int clinicId = dc.getClinic().getId(); 
				DaysDecoder.getInstance().decode(dc, daysOfWork);
				for (Integer k : daysOfWork.keySet()){
					//W danej klinice doktor moze miec tylko jeden ciagly grafik w ciagu dnia
					if (clinicIdToSave == clinicId && daysOfWorkToSave.containsKey(k))
						throw new DataIntegrityViolationException("Doctor already worked in this clinic in this day");
					//Nie moze byc w dwoch miejscach jednoczesnie
					for (Map.Entry<Integer, String[]> entry : daysOfWorkToSave.entrySet()){
						Date from = format.parse(daysOfWork.get(k)[0]);
						Date to = format.parse(daysOfWork.get(k)[1]);
						
						//Jesli godzina rozpoczecia pracy jest w ramach godzin juz zapisanych w bazie
						if (entry.getKey() == k && (format.parse(entry.getValue()[0])
								.after(from) || format.parse(entry.getValue()[0]).equals(from))
								&& ((format.parse(entry.getValue()[0])
										.before(to) || format.parse(entry.getValue()[1]).equals(to))))
							throw new DataIntegrityViolationException("2 places");
						
						if (entry.getKey() == k && (format.parse(entry.getValue()[1])
								.before(to) || format.parse(entry.getValue()[1]).equals(to))
								&& ((format.parse(entry.getValue()[1])
										.after(from) || format.parse(entry.getValue()[1]).equals(from))))
							throw new DataIntegrityViolationException("2 places");
					}
					
				}
				daysOfWork.clear();
			}
			
			dao.newAssignation(assign);
		}
	}

	
	
	@Override
	public void deleteAssignationById(DoctorClinicId id) {
		dao.deleteAssignationById(id);
	}

	@Override
	public void deleteAssignation(Doctor_Clinic assign) {
		dao.deleteAssignation(assign);
	}

}
