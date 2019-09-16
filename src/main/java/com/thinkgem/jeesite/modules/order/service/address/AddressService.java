/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service.address;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.order.entity.address.Address;
import com.thinkgem.jeesite.modules.order.dao.address.AddressDao;

/**
 * 发货地址Service
 * @author 罗天文
 * @version 2019-09-16
 */
@Service
@Transactional(readOnly = true)
public class AddressService extends CrudService<AddressDao, Address> {

	public Address get(String id) {
		return super.get(id);
	}
	
	public List<Address> findList(Address address) {
		return super.findList(address);
	}
	
	public Page<Address> findPage(Page<Address> page, Address address) {
		return super.findPage(page, address);
	}
	
	@Transactional(readOnly = false)
	public void save(Address address) {
		super.save(address);
	}
	
	@Transactional(readOnly = false)
	public void delete(Address address) {
		super.delete(address);
	}
	
}