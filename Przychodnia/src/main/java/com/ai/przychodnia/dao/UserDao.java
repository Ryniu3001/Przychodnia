package com.ai.przychodnia.dao;

import java.util.List;

import com.ai.przychodnia.model.User;

public interface UserDao
{
    User findById(int id);
    
    void saveUser(User user);
     
    void deleteUserByPesel(String pesel);
     
    List<User> findAllUsers(int type);
 
    User findUserByPesel(String pesel);
    
    User findUserByUsername(String username);
    
}
