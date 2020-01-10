/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.dao.black;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.black.Black;

/**
 * 测试黑名单DAO接口
 * @author 测试黑名单
 * @version 2020-01-10
 */
@MyBatisDao
public interface BlackDao extends CrudDao<Black> {
	
}