package com.library.services;

import com.library.entities.User;
import com.library.enums.UserType;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User user) ;
    void deleteById(Long id);
    List<User> fetchUsersBasedOnUserType(UserType userType);
}
