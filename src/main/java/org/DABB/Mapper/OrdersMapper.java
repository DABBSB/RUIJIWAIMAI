package org.DABB.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.DABB.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DABB
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2023-04-19 19:10:10
* @Entity org.DABB.domain.Orders
*/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}




