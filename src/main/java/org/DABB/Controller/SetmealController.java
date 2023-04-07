package org.DABB.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.DABB.commons.R;
import org.DABB.dto.SetmealDto;
import org.DABB.entity.Category;
import org.DABB.entity.Setmeal;
import org.DABB.service.CategoryService;
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

    public SetmealController(SetmealService setmealService, SetmealDishService setmealDishService, CategoryService categoryService) {
        this.setmealService = setmealService;
        this.setmealDishService = setmealDishService;
        this.categoryService = categoryService;
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
}
