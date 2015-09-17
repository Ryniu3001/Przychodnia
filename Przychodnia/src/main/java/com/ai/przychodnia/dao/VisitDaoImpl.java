package com.ai.przychodnia.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ai.przychodnia.model.Visit;
@Repository("visitDao")
public class VisitDaoImpl extends AbstractDao<Integer, Visit> implements VisitDao
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
	
	@SuppressWarnings("unchecked")
	public List<Date> takenTerms(int cid, int did){
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("doctor.id", did));
		criteria.add(Restrictions.eq("clinic.id", cid));
		List<Visit> visits = criteria.list();
		List<Date> dates = new ArrayList<Date>();
		for (Iterator<Visit> it = visits.iterator(); it.hasNext(); )
		{
			dates.add(it.next().getDatee());
		}
		return dates;
	}
	
	@SuppressWarnings("unchecked")
	public List<Date> takenTerms(int cid){
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("clinic.id", cid));
		List<Visit> visits = criteria.list();
		List<Date> dates = new ArrayList<Date>();
		for (Iterator<Visit> it = visits.iterator(); it.hasNext(); )
		{
			dates.add(it.next().getDatee());
		}
		return dates;
	}
	
	@SuppressWarnings("unchecked")
	public List<Date> doctorTerms(int did){
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("doctor.id", did));
		List<Visit> visits = criteria.list();
		List<Date> dates = new ArrayList<Date>();
		for (Iterator<Visit> it = visits.iterator(); it.hasNext(); )
		{
			dates.add(it.next().getDatee());
		}
		return dates;
	}
	
	@SuppressWarnings("unchecked")
	public List<Visit> userVisits(int uid){
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("patient.id", uid));
		List <Visit> visits = new ArrayList<Visit>();
		visits = criteria.list();
		return visits;
	}
	
	@SuppressWarnings("unchecked")
	public List<Visit> doctorVisits(int did){
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("doctor.id", did));
		criteria.add(Restrictions.eq("comfirmed", true));
		List <Visit> visits = new ArrayList<Visit>();
		visits = criteria.list();
		return visits;
	}
	

}
