package com.ai.przychodnia.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.przychodnia.dao.RegNotificationDao;
import com.ai.przychodnia.dao.UserDao;
import com.ai.przychodnia.model.Reg_notification;
import com.ai.przychodnia.model.User;

/**
 * @author Marcin
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDao dao;
	@Autowired
	private RegNotificationDao notifyDao;

	public User findById(int id) {
		return dao.findById(id);
	}

	public void saveUser(User user) {
		dao.saveUser(user);
		notifyDao.newNotification(new Reg_notification(false,user));
	}

	public void updateUser(User user) {
		User entity = dao.findById(user.getId());
		if (entity != null) {
			entity.setName(user.getName());
			entity.setSurname(user.getSurname());
			entity.setPesel(user.getPesel());
			entity.setCity(user.getCity());
			entity.setZip_code(user.getZip_code());
			entity.setType(entity.getType());
			entity.setUsername(user.getUsername());
			entity.setPassword(user.getPassword());
			entity.setHouse_nr(user.getHouse_nr());
			entity.setPwz(user.getPwz());
		}
	}

	public void deleteUserByPesel(String pesel) {
		dao.deleteUserByPesel(pesel);
	}

	public List<User> findAllUsers(int type) {
		return dao.findAllUsers(type);
	}
	

	public User findUserByPesel(String pesel) {
		return dao.findUserByPesel(pesel);
	}

	public User findUserByUsername(String username) {
		return dao.findUserByUsername(username);
	}

	public boolean isUserPeselUnique(Integer id, String pesel) {
		User user = findUserByPesel(pesel);
		return (user == null || ((id != null) && (user.getId() == id)));
	}

	public boolean isUsernameUnique(Integer id, String username) {
		User user = findUserByUsername(username);
		return (user == null || ((id != null) && (user.getId() == id)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		User user = dao.findUserByUsername(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getType());
		return buildUserForAuthentication(user, authorities);

	}

	/**
	 * Konwertuje com.ai.przyhcodnia.mode.User na
	 * org.springframework.security.core.userdetails.User na potrzeby
	 * autoryzacji.
	 * 
	 * @param user
	 * @param authorities
	 *            Lista r�l uzytkownika
	 * @return
	 */
	private org.springframework.security.core.userdetails.User buildUserForAuthentication(
			User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), user.getIs_enabled(),
				true, true, true, authorities);
	}

	/**
	 * Tworzy liste r�l uzytkownika na potrzebu autoryzacji W przypadku tej
	 * aplikacji moze byc tylko jedna rola dla uzytkownika. Zobaczymy czy to
	 * zadziala :)
	 * 
	 * @param userRole
	 * @return
	 */
	private List<GrantedAuthority> buildUserAuthority(int userRole) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		setAuths.add(new SimpleGrantedAuthority("ROLE_"+Integer.toString(userRole)));

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(
				setAuths);

		return Result;
	}
}
