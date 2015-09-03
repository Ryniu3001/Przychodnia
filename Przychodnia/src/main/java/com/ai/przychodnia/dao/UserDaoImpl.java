package com.ai.przychodnia.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ai.przychodnia.helpers.Type;
import com.ai.przychodnia.model.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao
{

	public User findById(int id) {
		return getByKey(id);
	}

	public void saveUser(User user) {
		persist(user);
	}

	public void deleteUserByPesel(String pesel) {
		Query hql = getSession().createSQLQuery(
				"delete from User_data where pesel = :pesel");
		hql.setString("pesel", pesel);
		hql.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers(int type) {
		Criteria criteria = createEntityCriteria();
		if ((type == Type.patients.getValue())
				|| (type == Type.doctors.getValue())
				|| (type == Type.admins.getValue()))
			criteria.add(Restrictions.eq("type", type));
		return (List<User>) criteria.list();
	}

	public User findUserByPesel(String pesel) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("pesel", pesel));
		return (User) criteria.uniqueResult();
	}

	public User findUserByUsername(String username) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("username", username));
		return (User) criteria.uniqueResult();
	}

}
