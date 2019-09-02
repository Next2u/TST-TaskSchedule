package com.qianbo.demo5.service;

import com.qianbo.demo5.entity.User;
import com.qianbo.demo5.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    @Autowired

    private Object User;

    public void InsertUser(User user){
        UserMapper.insert(User);
    }
    public User  GetUserByUsercode(Integer usercode){
        User user  = new User();
        user  = UserMapper.selectByUsercode(usercode);
        return user;
    }

    public List<User> findUserByDate(String startTime, String lastTime){

        List<User> user= new ArrayList<>();
        user = UserMapper.findUserByDate(startTime,lastTime);
        return  user;
    }

    public int selectIdByName(String Name){
        return UserMapper.selectidByName(Name);
    }

}
