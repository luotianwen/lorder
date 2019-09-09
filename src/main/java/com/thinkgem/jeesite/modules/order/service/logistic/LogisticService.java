/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service.logistic;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.order.entity.logistic.Logistic;
import com.thinkgem.jeesite.modules.order.dao.logistic.LogisticDao;

/**
 * 物流信息Service
 * @author 罗天文
 * @version 2019-09-09
 */
@Service
@Transactional(readOnly = true)
public class LogisticService extends CrudService<LogisticDao, Logistic> {

	public Logistic get(String id) {
		return super.get(id);
	}
	
	public List<Logistic> findList(Logistic logistic) {
		return super.findList(logistic);
	}
	
	public Page<Logistic> findPage(Page<Logistic> page, Logistic logistic) {
		return super.findPage(page, logistic);
	}
	
	@Transactional(readOnly = false)
	public void save(Logistic logistic) {
		super.save(logistic);
	}
	
	@Transactional(readOnly = false)
	public void delete(Logistic logistic) {
		super.delete(logistic);
	}
	
}