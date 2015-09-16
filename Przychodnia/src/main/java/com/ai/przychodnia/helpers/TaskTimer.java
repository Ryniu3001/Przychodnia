package com.ai.przychodnia.helpers;

import java.util.Timer;

import com.ai.przychodnia.service.VisitService;

public class TaskTimer {

	public TaskTimer() {}		
	
	public void addRemoveVisitTask(int vid, long delayMilis, VisitService service){
		Timer timer = new Timer();
		Task task = new Task(vid, timer, service);
		timer.schedule(task, delayMilis);
	}
	


}
