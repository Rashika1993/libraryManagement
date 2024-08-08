package com.library.serviceImpl;

import com.library.entities.User;
import com.library.enums.UserType;
import com.library.services.UserService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service("userService")
public class UserServiceImpl implements UserService {
    AtomicLong userId=new AtomicLong(0l);
    Map<Long,User> userMap=new HashMap<>();
    Map<UserType,Map<Long,User>> userTypeListMap=new HashMap<>();
    public List<User> findAll() {
        return userMap.values().stream().toList();
    }
    public User findById(Long id) {
        return userMap.get(id);
    }
    public User save(User user) {
        user.setUserId(userId.incrementAndGet());
        userMap.put(user.getUserId(),user);
        userTypeListMap.getOrDefault(user.getUserType(),new HashMap<>()).put(user.getUserId(),user);
        return  user;
    }
    public void deleteById(Long id) {
        User user=userMap.get(id);
        userMap.remove(id);
        userTypeListMap.getOrDefault(user.getUserType(),new HashMap<>()).remove(id);
    }
    public List<User> fetchUsersBasedOnUserType(UserType userType){
        if(userTypeListMap.get(userType)!=null) {
            return userTypeListMap.get(userType).values().stream().toList();
        }else{
            return new ArrayList<>();
        }
    }
}
