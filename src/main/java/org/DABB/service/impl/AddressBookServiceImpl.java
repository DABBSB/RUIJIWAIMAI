package org.DABB.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.DABB.entity.AddressBook;
import org.DABB.service.AddressBookService;
import org.DABB.Mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
 * @author DABB
 * @description 针对表【address_book(地址管理)】的数据库操作Service实现
 * @createDate 2023-04-12 10:16:00
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
        implements AddressBookService {

}




