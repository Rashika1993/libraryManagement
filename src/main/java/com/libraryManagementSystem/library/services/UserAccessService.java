package com.libraryManagementSystem.library.services;

import com.libraryManagementSystem.library.entities.User;
import com.libraryManagementSystem.library.enums.UserType;

import java.util.List;

public interface UserAccessService {
    boolean checkIfUserAuthorised(Long currentUserId);

}
