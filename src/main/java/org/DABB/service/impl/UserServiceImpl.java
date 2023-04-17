package org.DABB.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.DABB.entity.User;
import org.DABB.service.UserService;
import org.DABB.Mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author DABB
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2023-04-10 10:42:55
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




