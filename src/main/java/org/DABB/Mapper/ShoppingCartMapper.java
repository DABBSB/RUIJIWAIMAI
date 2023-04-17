package org.DABB.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.DABB.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DABB
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2023-04-17 11:02:54
* @Entity org.DABB.domain.ShoppingCart
*/
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




