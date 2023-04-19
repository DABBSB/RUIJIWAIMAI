package org.DABB.Controller;

import lombok.extern.slf4j.Slf4j;
import org.DABB.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/orderDeta")
public class OrderDateController {
    @Autowired
    public OrderDetailService orderDetailService;
}
