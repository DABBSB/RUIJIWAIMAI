package org.DABB.dto;


import lombok.Data;
import org.DABB.entity.OrderDetail;
import org.DABB.entity.Orders;

import java.util.List;

/**
 * @author LJM
 * @create 2022/5/3
 */
@Data
public class OrderDto extends Orders {

    private List<OrderDetail> orderDetails;
}