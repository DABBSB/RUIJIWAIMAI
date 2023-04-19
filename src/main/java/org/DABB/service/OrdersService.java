package org.DABB.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.DABB.entity.Orders;

/**
* @author DABB
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2023-04-19 19:10:10
*/
public interface OrdersService extends IService<Orders> {

    void submit(Orders order);
}
