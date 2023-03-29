package org.DABB.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.select.KSQLWindow;
import org.DABB.commons.R;
import org.DABB.entity.Employee;
import org.DABB.service.ServiceEmployee;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.DateTimeAtCreation;
import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.time.LocalDateTime;

//@Slf4j
//@RestController
//@RequestMapping("/employee")
public class EmployeeController2 {
    @Autowired
    ServiceEmployee serviceEmployee;

    /**
     * 登录
     *
     * @param employee *
     * @param request  *
     * @return R
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {
//  密码加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
//        已用户名查询数据库的数据
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, employee.getUsername());

        Employee employee1 = serviceEmployee.getOne(lqw);
//        如果没有查询到用户名返回失败结果
        if (employee1 == null) {
            return R.error("用户不存在,登录失败");
        }
//        校对密码
        if (!employee1.getPassword().equals(password)) {
            return R.error("密码错误");
        }
//        查询用户状态
        if (employee1.getStatus() == 0) {
            return R.error("用户已禁用");
        }
//
        request.getSession().setAttribute("employee", employee1.getId());
        return R.success(employee1);
    }

    //用户注销
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("已注销");
    }

    //添加员工
    @PostMapping
    public R<String> Add(HttpServletRequest request, @RequestBody Employee employee) {
//        添加默认密码,并加密
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(password);
//添加创造人id
//        Object employee1 = request.getSession().getAttribute("employee");
//        employee.setCreate_user((Long) employee1);
//        employee.setUpdate_user((Long) employee1);
//        //添加创建时间
//        employee.setCreate_time(LocalDateTime.now());
//        employee.setUpdate_time(LocalDateTime.now());

        serviceEmployee.save(employee);
        return R.success("添加完成");

    }

    @GetMapping("/page")
    public R<Page> pageString(int page, int pageSize, String name) {
//        添加分页器
        Page page1 = new Page(page, pageSize);
//
        LambdaQueryWrapper<Employee> lpw = new LambdaQueryWrapper<>();

        lpw.like(Strings.isNotEmpty(name), Employee::getName, name);
        lpw.orderByAsc(Employee::getUsername);

        serviceEmployee.page(page1);
        return R.success(page1);
    }

    //修改
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
//        获取修改人id
//        Object employee1 = request.getSession().getAttribute("employee");
//
//        employee.setUpdate_time(LocalDateTime.now());
//        employee.setUpdate_user((Long) employee1);

        serviceEmployee.updateById(employee);

        return R.success("修改成功");
    }

    //    回显
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee byId = serviceEmployee.getById(id);
        if (byId != null) {
            return R.success(byId);
        }
        return R.error("没有员工");
    }

}

