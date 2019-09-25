/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.order.entity.order.UserShipper;
import com.thinkgem.jeesite.modules.order.dao.order.UserShipperDao;

/**
 * 发货方Service
 * @author 罗天文
 * @version 2019-09-21
 */
@Service
@Transactional(readOnly = true)
public class UserShipperService extends CrudService<UserShipperDao, UserShipper> {

	public UserShipper get(String id) {
		return super.get(id);
	}
	
	public List<UserShipper> findList(UserShipper userShipper) {
		return super.findList(userShipper);
	}
	
	public Page<UserShipper> findPage(Page<UserShipper> page, UserShipper userShipper) {
		return super.findPage(page, userShipper);
	}
	
	@Transactional(readOnly = false)
	public void save(UserShipper userShipper) {
		super.save(userShipper);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserShipper userShipper) {
		super.delete(userShipper);
	}
	
}