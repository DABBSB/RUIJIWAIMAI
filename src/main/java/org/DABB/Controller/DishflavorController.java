package org.DABB.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.DABB.commons.R;
import org.DABB.dto.DishDto;
import org.DABB.entity.Dish;
import org.DABB.service.DishFlavorService;
import org.DABB.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishflavorController {

    final
    DishFlavorService dishFlavorService;
    final
    DishService dishService;

    public DishflavorController(DishFlavorService dishFlavorService, DishService dishService) {
        this.dishFlavorService = dishFlavorService;
        this.dishService = dishService;
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

        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();

        lqw.like(name != null, Dish::getName, name);

        lqw.orderByDesc(Dish::getUpdateTime);

        dishService.page(page1);
        
        return R.success(page1);
    }
}
