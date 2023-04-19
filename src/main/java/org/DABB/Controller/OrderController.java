package org.DABB.Controller;

import lombok.extern.slf4j.Slf4j;
import org.DABB.commons.R;
import org.DABB.entity.Orders;
import org.DABB.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
