package org.DABB.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.DABB.Mapper.SetmealMapper;
import org.DABB.commons.CustomException;
import org.DABB.dto.DishDto;
import org.DABB.dto.SetmealDto;
import org.DABB.entity.Dish;
import org.DABB.entity.DishFlavor;
import org.DABB.entity.Setmeal;
import org.DABB.entity.SetmealDish;
import org.DABB.service.SetmealDishService;
import org.DABB.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DABB
 * @description 针对表【setmeal(套餐)】的数据库操作Service实现
 * @createDate 2023-03-29 10:40:19
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    SetmealDishService setmealDishService;

    @Override
    public void saveWithDish(SetmealDto setmealDto) {
//        保存基本信息
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes.stream().map(M -> {
            M.setSetmealId(setmealDto.getId());
            return M;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
//        查询套餐状态
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.in(Setmeal::getId, ids);
        lqw.eq(Setmeal::getStatus, 1);
        int count = this.count(lqw);
        if (count > 0) {
            throw new CustomException("套餐出售中不能删除");
        }
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> lqw1 = new LambdaQueryWrapper<>();
        lqw1.in(SetmealDish::getSetmealId, ids);

        setmealDishService.remove(lqw1);
    }

    @Override
    public void updateWithFlavor(SetmealDto setmealDto) {
        //更新dish表的信息
        this.updateById(setmealDto);
        //清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishService.remove(queryWrapper);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map(M -> {
            M.setSetmealId(setmealDto.getId());
            return M;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public SetmealDto getByIdWithSetmealDish(Long id) {
        //        据id,查询套餐信息
        Setmeal setmeal = this.getById(id);
//        创建一个返回对象
        SetmealDto setmealDto = new SetmealDto();
//        拷贝
        BeanUtils.copyProperties(setmeal, setmealDto);

        //据id查找套餐分类
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, setmeal.getId());
        List<SetmealDish> list = setmealDishService.list(lqw);
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }
}






