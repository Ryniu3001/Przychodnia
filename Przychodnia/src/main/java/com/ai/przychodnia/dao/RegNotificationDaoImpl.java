package com.ai.przychodnia.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ai.przychodnia.model.Reg_notification;
import com.ai.przychodnia.model.Visit;

@Repository("regNotificationDao")
public class RegNotificationDaoImpl extends AbstractDao<Integer, Reg_notification> implements RegNotificationDao
{

	@Override
	public Reg_notification findById(int id) {
		return getByKey(id);
	}

	@Override
	public void newNotification(Reg_notification notification) {
		persist(notification);		
	}

	@Override
	public void deleteNotificationById(int id) {
		Query hql = getSession().createSQLQuery(
				"delete from Reg_notification where id = :id");
		hql.setString("id", Integer.toString(id));
		hql.executeUpdate();		
	}

	@Override
	public void deleteNotification(Reg_notification notification) {
		this.delete(notification);		
	}

}
