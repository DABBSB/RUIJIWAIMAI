package org.DABB.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.DABB.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author DABB
 * @description 针对表【address_book(地址管理)】的数据库操作Mapper
 * @createDate 2023-04-12 10:16:00
 * @Entity org.DABB.domain.AddressBook
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}




