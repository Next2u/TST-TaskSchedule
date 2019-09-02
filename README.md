# 定时任务

## 核心业务代码



1.配置Springboot+Mybatis，本项目中使用了Mybatis Generator插件生成实体类，mapper文件。

2.请求前端数据并定时执行

```java
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
```

3.将请求的信息存入数据库

```java
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
```

## 未实现的功能

1.拓展练习

2.bug修改中...

