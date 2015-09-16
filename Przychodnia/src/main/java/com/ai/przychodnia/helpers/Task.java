package com.ai.przychodnia.helpers;

import java.util.Timer;
import java.util.TimerTask;

import com.ai.przychodnia.model.Visit;
import com.ai.przychodnia.service.VisitService;

public class Task extends TimerTask
{
	VisitService service;	
	private int visitId;
	private Timer timer;

	public Task(int vid, Timer timer, VisitService service){
		this.visitId = vid;
		this.timer = timer;
		this.service = service;
	}
	
	@Override
	public void run() {
		Visit visit = service.findById(visitId);
		if (visit != null && !visit.isComfirmed()){
			service.deleteVisitById(visitId);
		}
		timer.cancel();
		timer.purge();
	}
}
