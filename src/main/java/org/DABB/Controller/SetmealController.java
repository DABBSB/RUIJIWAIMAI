package org.DABB.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.DABB.commons.R;
import org.DABB.dto.SetmealDto;
import org.DABB.entity.Category;
import org.DABB.entity.Dish;
import org.DABB.entity.Setmeal;
import org.DABB.service.CategoryService;
import org.DABB.service.DishService;
import org.DABB.service.SetmealDishService;
import org.DABB.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    final
    SetmealService setmealService;
    final
    SetmealDishService setmealDishService;
    final
    CategoryService categoryService;
    final
    DishService dishService;

    public SetmealController(SetmealService setmealService, SetmealDishService setmealDishService, CategoryService categoryService, DishService dishService) {
        this.setmealService = setmealService;
        this.setmealDishService = setmealDishService;
        this.categoryService = categoryService;
        this.dishService = dishService;
    }

    /***
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    @GetMapping("/page")
    public R<Page> pageR(int page, int pageSize, String name) {
        Page<Setmeal> page1 = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();

        lqw.like(name != null, Setmeal::getName, name);

        lqw.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(page1);

//        拷贝
        BeanUtils.copyProperties(page1, setmealDtoPage, "records");
//       手动拷贝
        List<Setmeal> records = page1.getRecords();

        List<SetmealDto> setmealDtos = records.stream().map(M -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(M, setmealDto);

            Long categoryId = M.getCategoryId();

            Category categoryServiceById = categoryService.getById(categoryId);
            if (categoryServiceById != null) {
                String name1 = categoryServiceById.getName();
                setmealDto.setCategoryName(name1);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(setmealDtos);
        return R.success(setmealDtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info(ids.toString());
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id) {
        return R.success(setmealService.getByIdWithSetmealDish(id));
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        setmealService.updateWithFlavor(setmealDto);
        return R.success("修改成功");
    }

    @PostMapping("/status/{status}")
//这个参数这里一定记得加注解才能获取到参数，否则这里非常容易出问题
    public R<String> status(@PathVariable("status") Integer status, @RequestParam List<Long> ids) {
        log.info("status:{}", status);
        log.info("ids:{}", ids);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ids != null, Setmeal::getId, ids);
        queryWrapper.orderByDesc(Setmeal::getPrice);
        //根据数据进行批量查询
        List<Setmeal> list = setmealService.list(queryWrapper);

        for (Setmeal setmeal : list) {
            if (setmeal != null) {
                setmeal.setStatus(status);
                setmealService.updateById(setmeal);
            }
        }
        return R.success("售卖状态修改成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> listR(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();

        lqw.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        lqw.eq(setmeal.getStatus() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        lqw.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(lqw);

        return R.success(list);
    }


}
