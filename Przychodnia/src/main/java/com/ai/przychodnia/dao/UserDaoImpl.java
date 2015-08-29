package com.ai.przychodnia.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.ai.przychodnia.model.User;

public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao
{

	public User findById(int id)
	{
		return getByKey(id);
	}

	public void saveUser(User user)
	{
		persist(user);
	}

	public void deleteUserByPesel(String pesel)
	{
		Query hql = getSession().createSQLQuery(
				"delete from User where pesel = :pesel");
		hql.setString("pesel", pesel);
		hql.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers()
	{
		Criteria criteria = createEntityCriteria();
		return (List<User>) criteria.list();
	}

	public User findUserByPesel(String pesel)
	{
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("pesel", pesel));
		return (User) criteria.uniqueResult();
	}

}
