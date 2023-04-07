package org.DABB.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.DABB.commons.R;
import org.DABB.entity.Employee;
import org.DABB.service.EmployeeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 员工登录
     *
     * @param employee 员工
     * @param request  *
     * @return *
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {
//        密码进行加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

//        根据网页输入的用户名来查询数据库
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(lqw);
//        如果没有查询到用户名，返回失败结果
        if (emp == null) {
            return R.error("登录失败");
        }
//        密码比对，没有返回失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }
//        查询用户状态
        if (emp.getStatus() == 0) {
            return R.error("用户已禁用");
        }
//        返回用户ID转入Session,并显示成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    //注销
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("删除成功");
    }

    /**
     * 添加员工
     *
     * @param employee *
     * @return *
     */
    @PostMapping
    public R<String> Add(HttpServletRequest request, @RequestBody Employee employee) {
        //设置初始密码并加密
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(password);

        employee.setCreate_time(LocalDateTime.now());
        employee.setUpdate_time(LocalDateTime.now());
        //填入创建员工的员工ID
        Long UserId = (Long) request.getSession().getAttribute("employee");
        employee.setCreate_user(UserId);
        employee.setUpdate_user(UserId);
        //添加数据
        employeeService.save(employee);
//        log.info(String.valueOf(request),employee);
        return R.success("添加成功");
    }

    /**
     * 分页
     *
     * @return R
     */
    @GetMapping("/page")
    public R<Page> pageR(int page, int pageSize, String name) {
//        分页制造器
        Page page1 = new Page<>(page, pageSize);
//        构建条件构造器
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
//        添加过滤条件
        lqw.like(Strings.isNotEmpty(name), Employee::getName, name);
//        添加排序条件
        lqw.orderByDesc(Employee::getUpdate_time);
//        执行查询
        employeeService.page(page1, lqw);

        return R.success(page1);
    }

    /**
     * 修改用户
     *
     * @param request *
     * @param employee *
     * @return R
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
        Object employee1 = request.getSession().getAttribute("employee");
//        更改时间
        employee.setUpdate_time(LocalDateTime.now());
//        更改更新用户
        employee.setUpdate_user((Long) employee1);
//        修改数据
        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
//        复用
    }

    //    回显
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee byId = employeeService.getById(id);
        if (byId != null) {
            return R.success(byId);
        }
        return R.error("没有该员工的信息");
    }
}

