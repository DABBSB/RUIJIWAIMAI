package org.DABB.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.DABB.commons.R;
import org.DABB.entity.Employee;
import org.DABB.service.ServiceEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private ServiceEmployee serviceEmployee;

    /**
     * 员工登录
     *
     * @param employee
     * @param request
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {
//        密码进行加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

//        根据网页输入的用户名来查询数据库
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, employee.getUsername());
        Employee emp = serviceEmployee.getOne(lqw);
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

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("删除成功");
    }
}

