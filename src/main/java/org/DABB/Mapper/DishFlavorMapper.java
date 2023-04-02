package org.DABB.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.DABB.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DABB
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2023-04-01 13:28:21
* @Entity org.DABB.domain.DishFlavor
*/
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

}




