package org.DABB.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.DABB.Mapper.DishMapper;
import org.DABB.dto.DishDto;
import org.DABB.entity.Dish;
import org.DABB.entity.DishFlavor;
import org.DABB.service.DishFlavorService;
import org.DABB.service.DishService;
import org.springframework.beans.BeanUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DABB
 * &#064;description  针对表【dish(菜品管理)】的数据库操作Service实现
 * &#064;createDate  2023-03-29 10:40:19
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    final
    DishFlavorService dishFlavorService;

    public DishServiceImpl(DishFlavorService dishFlavorService) {
        this.dishFlavorService = dishFlavorService;
    }

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息
        this.save(dishDto);
        Long id = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().peek((M) -> M.setDishId(id)).collect(Collectors.toList());
        //添加菜品口味到口味表
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    //新增一个业务功能，根据id查询菜品信息和对应的菜品类别信息
    public DishDto getByIdWithFlavor(Long id) {
//        据id,查询菜品信息
        Dish dish = this.getById(id);
//        创建一个返回对象
        DishDto dishDto = new DishDto();
//        拷贝
        BeanUtils.copyProperties(dish, dishDto);

        //据id查找菜品分类
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> list = dishFlavorService.list(lqw);
        dishDto.setFlavors(list);
        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表的信息
        this.updateById(dishDto);
        //清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);
//       添加提交过来的口味数据
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map(M -> {
            M.setDishId(dishDto.getId());
            return M;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }


}




