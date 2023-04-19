package org.DABB.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.DABB.entity.OrderDetail;
import org.DABB.service.OrderDetailService;
import org.DABB.Mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author DABB
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2023-04-19 19:10:10
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




