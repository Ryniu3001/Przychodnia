package com.ai.przychodnia.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ai.przychodnia.model.Visit;
@Repository("visitDao")
public class VisitDaoImpl<T> extends AbstractDao<Integer, Visit> implements
		VisitDao
{

	public Visit findById(int id) {
		return getByKey(id);
	}

	public void saveVisit(Visit visit) {
		persist(visit);
	}

	public void deleteVisitById(int id) {
		Query hql = getSession().createSQLQuery(
				"delete from Visit where id = :id");
		hql.setString("id", Integer.toString(id));
		hql.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Visit> findAllVisits() {
		Criteria criteria = createEntityCriteria();
		return (List<Visit>) criteria.list();
	}

	public void deleteVisit(Visit visit) {
		this.delete(visit);
	}
	

}
