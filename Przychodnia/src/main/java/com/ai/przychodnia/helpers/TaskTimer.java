package com.ai.przychodnia.helpers;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import com.ai.przychodnia.model.Visit;
import com.ai.przychodnia.service.VisitService;

public class TaskTimer {

	private int visitId;
	
	@Autowired
	VisitService service;
	
	public TaskTimer() {}	
	
	
	public void addRemoveVisitTask(int vid, long delayMilis){
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				service.deleteVisitById(vid);
				timer.cancel();
				timer.purge();
			}
		};
		timer.schedule(task, delayMilis);
	}
	


}
