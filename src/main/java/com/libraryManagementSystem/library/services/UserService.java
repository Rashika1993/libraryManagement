package com.libraryManagementSystem.library.services;

import com.libraryManagementSystem.library.entities.User;
import com.libraryManagementSystem.library.enums.UserType;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User user) ;
    void deleteById(Long id);
    List<User> fetchUsersBasedOnUserType(UserType userType);
}
