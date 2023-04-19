package org.DABB.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.DABB.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author DABB
 * @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
 * @createDate 2023-04-19 19:10:10
 * @Entity org.DABB.domain.OrderDetail
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




