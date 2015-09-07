package com.ai.przychodnia.dao;

import java.util.List;

import com.ai.przychodnia.model.Reg_notification;
import com.ai.przychodnia.model.Visit;

public interface RegNotificationDao
{
	Reg_notification findById(int id);

	void newNotification(Reg_notification notification);

	void deleteNotificationById(int id);
	
	void deleteNotification(Reg_notification notification);
}
