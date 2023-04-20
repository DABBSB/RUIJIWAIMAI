package org.DABB.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.DABB.commons.R;
import org.DABB.entity.Orders;
import org.DABB.service.OrdersService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    public OrdersService ordersService;

    //    用户下单
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders order) {
        ordersService.submit(order);
        return R.success("下单成功");
    }

    @GetMapping("/page")
    public R<Page> pageR(@RequestParam int page, @RequestParam int pageSize, String number, String beginTime, String endTime) {
        Page<Orders> ordersPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        lqw.like(number != null, Orders::getNumber, number);
        lqw.gt(Strings.isNotEmpty(beginTime), Orders::getOrderTime, beginTime);
        lqw.lt(Strings.isNotEmpty(endTime), Orders::getOrderTime, endTime);

        ordersService.page(ordersPage, lqw);

        return R.success(ordersPage);
    }
}
