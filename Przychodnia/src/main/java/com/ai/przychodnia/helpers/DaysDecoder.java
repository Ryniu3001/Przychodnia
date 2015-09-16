package com.ai.przychodnia.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.ai.przychodnia.model.Doctor_Clinic;

enum Days{
	Monday(1),
	Tuesday(2),
	Wednesday(3),
	Thursday(4),
	Friday(5);
	
	private int value;
	Days(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	
	public static int getByKey(String key){
		for (Days d : Days.values()){
			if (d.name().equals(key))
				return d.value;
		}
		return 0;
	}
}

public class DaysDecoder {

	private static DaysDecoder instance;
	
	private DaysDecoder(){}
	
	public static DaysDecoder getInstance(){
		if (instance == null)
			instance = new DaysDecoder();
		return instance;
	}
	
	/**
	 * Zamienia String z dniami pracy lekarza na wygodna postac mapy z godzinami pracy
	 * @param days
	 * @param map
	 */
	public void decode(Doctor_Clinic days, Map<Integer, String[]> map){
		String[] tmp = days.getDayOfWeek().split("\\, ");
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		String[] hours = {dateFormat.format(days.getHourFrom()), dateFormat.format(days.getHourTo()) };

		if (tmp != null) {
			for (String s : tmp) {
				if (Days.getByKey(s) != 0){
					map.put(Days.getByKey(s), hours);
				}
			}
		}
	}
}
