package com.library.entities;

import com.library.enums.UserType;
import lombok.Data;
@Data
public class User {
    long userId;
    String userName;
    UserType userType;
    public User() {}
}
