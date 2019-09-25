/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.dao.order;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.order.UserShipper;

/**
 * 发货方DAO接口
 * @author 罗天文
 * @version 2019-09-21
 */
@MyBatisDao
public interface UserShipperDao extends CrudDao<UserShipper> {
	
}