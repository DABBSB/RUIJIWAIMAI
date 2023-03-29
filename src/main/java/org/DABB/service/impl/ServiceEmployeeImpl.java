package org.DABB.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.DABB.Mapper.DaoEmployee;
import org.DABB.entity.Employee;
import org.DABB.service.ServiceEmployee;
import org.springframework.stereotype.Service;

@Service
public class ServiceEmployeeImpl extends ServiceImpl<DaoEmployee, Employee> implements ServiceEmployee {
}
