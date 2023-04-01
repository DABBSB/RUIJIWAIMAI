package org.DABB.Controller;

import lombok.extern.slf4j.Slf4j;
import org.DABB.service.DishFlavorService;
import org.DABB.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/Dish")
public class DishflavorController {

    final
    DishFlavorService dishFlavorService;
    final
    DishService dishService;

    public DishflavorController(DishFlavorService dishFlavorService, DishService dishService) {
        this.dishFlavorService = dishFlavorService;
        this.dishService = dishService;
    }


}
