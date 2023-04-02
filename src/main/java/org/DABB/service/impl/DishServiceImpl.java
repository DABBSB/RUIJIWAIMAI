package org.DABB.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.DABB.Mapper.DishMapper;
import org.DABB.dto.DishDto;
import org.DABB.entity.Dish;
import org.DABB.entity.DishFlavor;
import org.DABB.service.DishFlavorService;
import org.DABB.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DABB
 * @description 针对表【dish(菜品管理)】的数据库操作Service实现
 * @createDate 2023-03-29 10:40:19
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

        flavors = flavors.stream().map((M) -> {
            M.setDishId(id);
            return M;
        }).collect(Collectors.toList());
        //添加菜品口味到口味表
        dishFlavorService.saveBatch(flavors);
    }

}




