package com.ai.przychodnia.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ai.przychodnia.model.User;

public interface UserService extends UserDetailsService
{
	User findById(int id);

	void saveUser(User user);

	void updateUser(User user);

	void deleteUserByPesel(String pesel);

	/**
	 * Parametr to typ Enum Type lub null jesli chcemy zwrocic wszystkich userow
	 * @param type Enum Type
	 * @return Lista userow wedlug filtra
	 */
	List<User> findAllUsers(int type);

	User findUserByPesel(String pesel);
	
	User findUserByUsername(String username);

	boolean isUserPeselUnique(Integer id, String pesel);
	
	boolean isUsernameUnique(Integer id, String username);
	
	@Override
	 UserDetails loadUserByUsername(String username);
}
