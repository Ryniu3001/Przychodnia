package com.ai.przychodnia.service;

import java.util.List;

import com.ai.przychodnia.model.User;

public interface UserService
{
	User findById(int id);

	void saveUser(User user);

	void updateUser(User user);

	void deleteUserByPesel(String pesel);

	List<User> findAllUsers();

	User findUserByPesel(String pesel);

	boolean isUserPeselUnique(Integer id, String pesel);
}
