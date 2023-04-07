package org.DABB.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.DABB.Mapper.EmployeeMapper;
import org.DABB.entity.Employee;
import org.DABB.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
