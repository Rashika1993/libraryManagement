package com.libraryManagementSystem.library.serviceImpl;

import com.libraryManagementSystem.library.entities.User;
import com.libraryManagementSystem.library.enums.UserType;
import com.libraryManagementSystem.library.exceptions.UnauthorizedAccessException;
import com.libraryManagementSystem.library.services.UserAccessService;
import com.libraryManagementSystem.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userAccessService")
public class UserAccessServiceImpl implements UserAccessService {
        @Autowired
        private UserService userService;

    public boolean checkIfUserAuthorised(Long currentUserId) {
        User user=userService.findById(currentUserId);
        if(user!=null && user.getUserType()== UserType.ADMIN){
            return true;
        }
        List<User> userList=userService.fetchUsersBasedOnUserType(UserType.ADMIN);
        if(!userList.isEmpty()){
            return true;
        }
        throw new UnauthorizedAccessException("User isn't authorised");
    }
}
