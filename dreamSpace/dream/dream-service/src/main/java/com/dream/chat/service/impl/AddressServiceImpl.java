package com.dream.chat.service.impl;

import com.dream.chat.entity.Address;
import com.dream.chat.extension.SuperServiceImpl;
import com.dream.chat.mapper.AddressMapper;
import com.dream.chat.service.AddressService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangjing
 * @since 2019-04-24
 */
@Service
public class AddressServiceImpl extends SuperServiceImpl<AddressMapper, Address> implements AddressService {

}
