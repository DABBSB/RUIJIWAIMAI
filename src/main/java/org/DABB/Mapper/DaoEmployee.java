package org.DABB.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.DABB.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DaoEmployee extends BaseMapper<Employee> {
}
