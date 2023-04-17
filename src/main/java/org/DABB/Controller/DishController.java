package org.DABB.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.DABB.commons.R;
import org.DABB.dto.DishDto;
import org.DABB.entity.Category;
import org.DABB.entity.Dish;
import org.DABB.entity.DishFlavor;
import org.DABB.service.CategoryService;
import org.DABB.service.DishFlavorService;
import org.DABB.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    final
    DishFlavorService dishFlavorService;
    final
    DishService dishService;
    final
    CategoryService categoryService;


    public DishController(DishFlavorService dishFlavorService, DishService dishService, CategoryService categoryService) {
        this.dishFlavorService = dishFlavorService;
        this.dishService = dishService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);

        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page> pageR(int page, int pageSize, String name) {
        Page<Dish> page1 = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();

        lqw.like(name != null, Dish::getName, name);

        lqw.orderByDesc(Dish::getUpdateTime);

        dishService.page(page1);
//        拷贝数据
        BeanUtils.copyProperties(page1, dishDtoPage, "records");
//        手动拷贝数据
        List<Dish> records = page1.getRecords();

        List<DishDto> dishDtolist = records.stream().map((M) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(M, dishDto);

            Long id = M.getCategoryId();

            Category categoryServiceById = categoryService.getById(id);
            if (categoryServiceById != null) {
                String categoryname = categoryServiceById.getName();
                dishDto.setCategoryName(categoryname);
            }
            //当前菜品的id
//            Long dishId = M.getId();
//            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
//            //SQL:select * from dish_flavor where dish_id = ?
//            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
//            dishDto.setFlavors(dishFlavorList);

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(dishDtolist);

        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        return R.success(dishService.getByIdWithFlavor(id));
    }

    /**
     * 修改菜品
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

//    @GetMapping("/list")
//    public R<List<Dish>> listR(Dish dish) {
////        添加查询
//        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(Dish::getStatus, 1);
////        菜品分类
//        lqw.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
////        排序
//        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//
//        List<Dish> list = dishService.list(lqw);
//
//        return R.success(list);
//    }

    @GetMapping("/list")
    public R<List<DishDto>> listR(Dish dish) {
//        添加查询
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Dish::getStatus, 1);
//        菜品分类
        lqw.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
//        排序
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list1 = dishService.list(lqw);

        List<DishDto> DishDtolist = list1.stream().map((M) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(M, dishDto);
            //当前菜品的id
            Long Dishid = M.getId();

            LambdaQueryWrapper<DishFlavor> lqwDish = new LambdaQueryWrapper<>();

            lqwDish.eq(DishFlavor::getDishId, Dishid);

            List<DishFlavor> list = dishFlavorService.list(lqwDish);

            dishDto.setFlavors(list);

            return dishDto;
        }).collect(Collectors.toList());

        return R.success(DishDtolist);
    }

}
