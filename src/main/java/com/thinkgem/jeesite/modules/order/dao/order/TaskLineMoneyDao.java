/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.dao.order;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.order.TaskLineMoney;

/**
 * 分润DAO接口
 * @author 罗天文
 * @version 2019-11-27
 */
@MyBatisDao
public interface TaskLineMoneyDao extends CrudDao<TaskLineMoney> {
	
}