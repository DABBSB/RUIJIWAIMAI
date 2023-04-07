package org.DABB.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.DABB.Mapper.CategoryMapper;
import org.DABB.commons.CustomException;
import org.DABB.entity.Category;
import org.DABB.entity.Dish;
import org.DABB.entity.Setmeal;
import org.DABB.service.DishService;
import org.DABB.service.CategoryService;
import org.DABB.service.SetmealService;
import org.springframework.stereotype.Service;



@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    private final DishService dishService;
    private final SetmealService setmealService;

    public CategoryServiceImpl(DishService dishService, SetmealService setmealService) {
        this.dishService = dishService;
        this.setmealService = setmealService;
    }

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> lqwD = new LambdaQueryWrapper<>();
        lqwD.eq(Dish::getCategoryId, id);
        int count = dishService.count(lqwD);
        if (count > 0) {
            throw new CustomException("当前分类关联了菜品，不能删除");
        }
        LambdaQueryWrapper<Setmeal> lqwS = new LambdaQueryWrapper<>();
        lqwS.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(lqwS);
        if (count2 > 0) {
            throw new CustomException("当前分类关联了套餐，不能删除");
        }
        super.removeById(id);
    }
}
