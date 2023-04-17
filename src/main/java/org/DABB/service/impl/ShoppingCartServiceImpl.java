package org.DABB.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.DABB.entity.ShoppingCart;
import org.DABB.service.ShoppingCartService;
import org.DABB.Mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
 * @author DABB
 * @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
 * @createDate 2023-04-17 11:02:54
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
        implements ShoppingCartService {

}




