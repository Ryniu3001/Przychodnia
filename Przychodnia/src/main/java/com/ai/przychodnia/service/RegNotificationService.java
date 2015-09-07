package com.ai.przychodnia.service;

import com.ai.przychodnia.model.Reg_notification;

public interface RegNotificationService
{
	Reg_notification findById(int id);

	void newNotification(Reg_notification notification);

	void deleteNotificationById(int id);

	Reg_notification findVisitById(int id);
	
	void deleteNotification(Reg_notification notification);
}
