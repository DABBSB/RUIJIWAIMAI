package org.DABB.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.DABB.Mapper.AddressBookMapper;
import org.DABB.entity.AddressBook;
import org.DABB.service.AddressBookService;
import org.springframework.stereotype.Service;
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}