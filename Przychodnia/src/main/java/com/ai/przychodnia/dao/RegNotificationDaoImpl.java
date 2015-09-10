package com.ai.przychodnia.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.TypedValue;
import org.springframework.stereotype.Repository;

import com.ai.przychodnia.model.Reg_notification;
import com.ai.przychodnia.model.User;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Reg_notification> findNewNotifications() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("read", false));
		return (List<Reg_notification>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reg_notification> findAllNotifications() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.desc("read"));
		return (List<Reg_notification>) criteria.list();
	}

	@Override
	public long countNewNotifications() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("read", false));
		criteria.setProjection(Projections.rowCount());
		return (long)criteria.uniqueResult();
	}

}
