package org.DABB.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.DABB.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface MapperCategory extends BaseMapper<Category> {

}
