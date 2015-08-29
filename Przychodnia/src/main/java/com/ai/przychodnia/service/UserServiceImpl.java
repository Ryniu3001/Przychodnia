package com.ai.przychodnia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.przychodnia.dao.UserDao;
import com.ai.przychodnia.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDao dao; // TODO Usunac autowired i zobaczyc co sie stanie

	public User findById(int id) {
		return dao.findById(id);
	}

	public void saveUser(User user) {
		dao.saveUser(user);
	}

	// TODO: Uzuzpelnic
	public void updateUser(User user) {
		User entity = dao.findById(user.getId());
		if (entity != null) {
			entity.setName(user.getName());
			entity.setSurname(user.getSurname());
			entity.setPesel(user.getPesel());
			entity.setCity(user.getCity());
		}
	}

	public void deleteUserByPesel(String pesel) {
		dao.deleteUserByPesel(pesel);
	}

	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

	public User findUserByPesel(String pesel) {
		return dao.findUserByPesel(pesel);
	}

	//TODO Usunac. Unikalnosc zapewnia baza danych
	public boolean isUserPeselUnique(Integer id, String pesel) {
		User user = findUserByPesel(pesel);
		return (user == null || ((id != null) && (user.getId() == id)));
	}

}
