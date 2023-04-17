package org.DABB.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.DABB.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author DABB
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2023-04-10 10:42:55
* @Entity org.DABB.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




