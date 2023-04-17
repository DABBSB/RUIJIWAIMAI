package org.DABB.Controller;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.DABB.commons.BaseContext;
import org.DABB.commons.R;
import org.DABB.entity.ShoppingCart;
import org.DABB.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    public ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
//        获得用户id
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
//      获取菜品id
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();

        lqw.eq(ShoppingCart::getUserId, currentId);
        //  查询是否为套餐
        if (dishId != null) {
            lqw.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        } else {
            lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart cartServiceone = shoppingCartService.getOne(lqw);

        if (cartServiceone != null) {
            //如果已经存在,就在原来的数量基础上加一
            Integer number = cartServiceone.getNumber();
            cartServiceone.setNumber(number + 1);
            shoppingCartService.updateById(cartServiceone);
        } else {
            //如果不存在,则添加到购物车，数量默认是1
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            cartServiceone = shoppingCart;
        }

        return R.success(cartServiceone);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> listR() {
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();

        lqw.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        lqw.orderByDesc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(lqw);
        return R.success(list);
    }

    @DeleteMapping("/clean")
    public R<String> clean() {
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        shoppingCartService.remove(lqw);
        return R.success("已清空");
    }
}
