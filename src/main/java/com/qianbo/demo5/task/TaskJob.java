package com.qianbo.demo5.task;

import com.qianbo.demo5.entity.Department;
import com.qianbo.demo5.entity.User;
import com.qianbo.demo5.service.UserService;
import net.sf.jsqlparser.parser.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Configurable
@EnableScheduling

public class TaskJob {
    @Autowired
    UserService userService;

//    在每天凌晨一点执行
    @Scheduled(cron = "0 0 1 * * ?")
    public void reportCurrentByCron() throws ParseException {

        String url="http://localhost:8080/findEmployee";
        Map<String,Object> map = new HashMap<>();
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-1);
        String startTime = new java.sql.Date(calendar.getTime().getTime()).toString();
        String lastTime = new java.sql.Date(new Date().getTime()).toString();
        map.put("startTime",startTime);
        map.put("lastTime",lastTime);
        //请求调用接口
        String content=PureNetUtil.post(url,map);
        //解析返回过来的json值；
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<User> employeeList = new ArrayList<>();
        JSONArray jsonArray = JSONArray.fromObject(content);
        for(int i=0;i<jsonArray.size();i++){

            JSONObject json = jsonArray.getJSONObject(i);
            User user= new User();
            Department department = new Department();
            Date date=sdf.parse(json.getString("hiredate"));
            user.setUsername(json.getString("username"));
            user.setUsercode(Integer.parseInt(json.getString("usercode")));
            department.setDepartmentname(json.getString("department"));
//            employee.setDepartment(json.getString("department"));
            user.setHiredate(new java.sql.Date(date.getTime()));
            userList.add(user);

            int departmentid = userService.selectIdByName(user.getDepartment().getDepartmentname());

            department.setId(departmentid);
            user.setDepartment(department);
            userService.InsertUser(user);
        }



    }
}
