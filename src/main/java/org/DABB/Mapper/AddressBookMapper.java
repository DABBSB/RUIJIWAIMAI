package org.DABB.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.DABB.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}

