package com.ai.przychodnia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.przychodnia.dao.RegNotificationDao;
import com.ai.przychodnia.dao.UserDao;
import com.ai.przychodnia.model.Reg_notification;

@Service
@Transactional
public class RegNotificationServiceImpl implements RegNotificationService
{
	@Autowired
	private RegNotificationDao dao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public Reg_notification findById(int id) {
		return dao.findById(id);
	}

	@Override
	public void newNotification(Reg_notification notification) {
		dao.newNotification(notification);
	}

	@Override
	public void deleteNotificationById(int id, String pesel) {
		userDao.deleteUserByPesel(pesel);
		dao.deleteNotificationById(id);
	}

	@Override
	public Reg_notification findVisitById(int id) {
		return dao.findById(id);
	}

	@Override
	public void deleteNotification(Reg_notification notification) {
		dao.deleteNotification(notification);
	}

	@Override
	public List<Reg_notification> findNewNotifications() {
		return dao.findNewNotifications();
	}

	@Override
	public List<Reg_notification> findAllNotifications() {
		return dao.findAllNotifications();
	}

	@Override
	public long countNewNotifications() {
		return dao.countNewNotifications();
	}

	@Override
	public void setNotificationState(int id, boolean state) {
		Reg_notification notification = dao.findById(id);
		if (notification != null){
			notification.setRead(state);	
			notification.getUser_id().setIs_enabled(state);
		}
	}
}
