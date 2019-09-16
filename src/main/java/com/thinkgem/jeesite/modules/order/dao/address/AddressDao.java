/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.dao.address;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.address.Address;

/**
 * 发货地址DAO接口
 * @author 罗天文
 * @version 2019-09-16
 */
@MyBatisDao
public interface AddressDao extends CrudDao<Address> {
	
}