package org.DABB.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.DABB.dto.DishDto;
import org.DABB.dto.SetmealDto;
import org.DABB.entity.Setmeal;

import java.util.List;

/**
 * @author DABB
 * @description 针对表【setmeal(套餐)】的数据库操作Service
 * @createDate 2023-03-29 10:40:19
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     *
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);

    void updateWithFlavor(SetmealDto setmealDto);

    SetmealDto getByIdWithSetmealDish(Long id);
}
