package org.DABB.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.DABB.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author DABB
 * @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Mapper
 * @createDate 2023-04-06 11:55:14
 * @Entity org.DABB.domain.SetmealDish
 */
@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

}




