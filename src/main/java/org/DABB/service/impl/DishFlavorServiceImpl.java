package org.DABB.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.DABB.entity.DishFlavor;
import org.DABB.service.DishFlavorService;
import org.DABB.Mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author DABB
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2023-04-01 13:28:21
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




