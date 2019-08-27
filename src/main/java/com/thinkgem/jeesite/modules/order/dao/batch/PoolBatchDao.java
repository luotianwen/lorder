/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.dao.batch;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.batch.PoolBatch;

/**
 * 订单集成DAO接口
 * @author 罗天文
 * @version 2019-08-27
 */
@MyBatisDao
public interface PoolBatchDao extends CrudDao<PoolBatch> {
	
}