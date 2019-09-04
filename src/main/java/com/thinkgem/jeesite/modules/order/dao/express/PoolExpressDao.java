/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.dao.express;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.express.PoolExpress;

/**
 * 快递配置DAO接口
 * @author 罗天文
 * @version 2019-09-04
 */
@MyBatisDao
public interface PoolExpressDao extends CrudDao<PoolExpress> {
	
}