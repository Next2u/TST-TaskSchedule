package com.qianbo.demo5.mapper;

import com.qianbo.demo5.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import utils.MyMapper;

import java.util.List;
@Mapper
@Repository
public interface UserMapper extends MyMapper<User> {

    int deleteByPrimaryKey(Integer id);

     int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

     User selectByUsercode(Integer usercode);

    @Select("select id from department where departmentname")
     int selectidByName(String departmentname);

    //    @Select("select * from employee where hiredate >= #{startTime} and hiredate < #{lastTime}")
    List<User> findEmployeesByDate(String startTime, String lastTime);
}