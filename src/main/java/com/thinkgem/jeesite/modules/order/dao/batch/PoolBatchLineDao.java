/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.dao.batch;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.batch.PoolBatchLine;

/**
 * 订单集成DAO接口
 * @author 罗天文
 * @version 2019-08-18
 */
@MyBatisDao
public interface PoolBatchLineDao extends CrudDao<PoolBatchLine> {
	
}