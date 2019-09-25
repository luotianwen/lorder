/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.order.entity.order.TaskLineMoney;
import com.thinkgem.jeesite.modules.order.dao.order.TaskLineMoneyDao;

/**
 * 分润Service
 * @author 罗天文
 * @version 2019-09-21
 */
@Service
@Transactional(readOnly = true)
public class TaskLineMoneyService extends CrudService<TaskLineMoneyDao, TaskLineMoney> {

	public TaskLineMoney get(String id) {
		return super.get(id);
	}
	
	public List<TaskLineMoney> findList(TaskLineMoney taskLineMoney) {
		return super.findList(taskLineMoney);
	}
	
	public Page<TaskLineMoney> findPage(Page<TaskLineMoney> page, TaskLineMoney taskLineMoney) {
		return super.findPage(page, taskLineMoney);
	}
	
	@Transactional(readOnly = false)
	public void save(TaskLineMoney taskLineMoney) {
		super.save(taskLineMoney);
	}
	
	@Transactional(readOnly = false)
	public void delete(TaskLineMoney taskLineMoney) {
		super.delete(taskLineMoney);
	}
	
}