package com.libraryManagementSystem.library.entities;

import com.libraryManagementSystem.library.enums.UserType;
import lombok.Data;
@Data
public class User {
    long userId;
    String userName;
    UserType userType;
    public User() {}
}
