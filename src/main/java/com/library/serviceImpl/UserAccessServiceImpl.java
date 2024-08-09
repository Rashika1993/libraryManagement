package com.library.serviceImpl;

import com.library.entities.User;
import com.library.enums.UserType;
import com.library.exceptions.UnauthorizedAccessException;
import com.library.services.UserAccessService;
import com.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userAccessService")
public class UserAccessServiceImpl implements UserAccessService {

    private final UserService userService;

    @Autowired
    public UserAccessServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean checkIfUserAuthorised(Long currentUserId) {
        User user=userService.findById(currentUserId);
        if(user!=null && user.getUserType()== UserType.ADMIN){
            return true;
        }
        List<User> userList=userService.fetchUsersBasedOnUserType(UserType.ADMIN);
        if(userList==null || userList.isEmpty()){
            return true;
        }

        throw new UnauthorizedAccessException("User isn't authorised");
    }
}
