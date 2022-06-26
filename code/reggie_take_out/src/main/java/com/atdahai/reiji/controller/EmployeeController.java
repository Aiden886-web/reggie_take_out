package com.atdahai.reiji.controller;

import com.atdahai.reiji.common.R;
import com.atdahai.reiji.entity.Employee;
import com.atdahai.reiji.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        //1、讲页面提交的密码password进行md5加密
//        String password = employee.getPassword();
//        password = DigestUtils.md5DigestAsHex(password.getBytes());
        String employeePassword = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername , employee.getUsername());
        Employee emp = employeeService.getOne(wrapper);
        //3、如果没有查询到则返回登录失败结果
        if(emp == null){
            return R.error("登录失败");
        }
        //4、密码对比，如果不一致返回登陆失败结果
        /**
         * 错误方式
         *         wrapper.eq(Employee::getPassword  ,employeePassword);
         *         boolean equals = employeeService.equals(wrapper);
         *         if(!equals){
         *             return R.error("密码不一致，请求登录失败");
         *         }
         *          ======  不是很清晰，上面用用户名查询的emp到底是什么，甚至对于如何去数据库拿数据，以及传入方法的参数是哪里的参数。=======
         */

        //正确方式
        if(!emp.getPassword().equals(employeePassword)){
            return R.error("登录失败");
        }

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用
        /**
         * if(emp.getStatus().equals(0)){
         *             return R.error("该员已经被禁用");
         *         }
         *         ===== 对于            == 和 equal 选择很模糊
         *         总结：
         *
         *  == 的作用：
         * 　　基本类型：比较值是否相等
         * 　　引用类型：比较内存地址值是否相等
         *
         * equals 的作用:
         * 　　引用类型：默认情况下，比较内存地址值是否相等。可以按照需求逻辑，重写对象的equals方法。
         */
        if(emp.getStatus() == 0){
            return R.error("该员工已经被禁用");
        }

        //6、登录成功，将员工id存入到Session并返回登录成功结果
        request.getSession().setAttribute("employee" , emp.getId());
        return R.success(emp);
    }

    //退出员工
    @PostMapping("logout")
    public R<String> logout(HttpServletRequest request){
        //清除session
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    //新增员工
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
       log.info("新增员工,员工信息：" ,employee.toString());
       //设置初始密码123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
        /**
         *  Long  id = (Long) request.getAttribute("employee.id");
         *  问题：这能拿到登录的id ， 对于请求什么都没有思考清楚
         */
        Long id = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(id);
        employee.setUpdateUser(id);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * 员工信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page , int pageSize , String name){
        //构造分页构造器
        Page pageInfo = new Page<>(page , pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        //添加条件过滤
        wrapper.like(StringUtils.isNotEmpty(name) , Employee::getName , name);
        //添加排序条件
        wrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo , wrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
        Long id = Thread.currentThread().getId();
        log.info("线程id为:{}" + id);
        //employee.setUpdateTime(LocalDateTime.now());
        //Long id = (Long) request.getSession().getAttribute("employee");
        //employee.setUpdateUser(id);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * 员工信息查询
     * @param id
     * @return
     */
//    @GetMapping("/{id}")
//    public R<Employee> getById(@PathVariable long id){
//        Employee employee = employeeService.getById(id);
//        return R.success(employee);
//    }
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable long id){
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.error("没有查询到对应的员工信息");
    }



}
