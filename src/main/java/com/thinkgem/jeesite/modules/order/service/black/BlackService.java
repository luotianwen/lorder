/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service.black;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.order.entity.black.Black;
import com.thinkgem.jeesite.modules.order.dao.black.BlackDao;

/**
 * 测试黑名单Service
 * @author 测试黑名单
 * @version 2020-01-10
 */
@Service
@Transactional(readOnly = true)
public class BlackService extends CrudService<BlackDao, Black> {

	public Black get(String id) {
		return super.get(id);
	}
	
	public List<Black> findList(Black black) {
		return super.findList(black);
	}
	
	public Page<Black> findPage(Page<Black> page, Black black) {
		return super.findPage(page, black);
	}
	
	@Transactional(readOnly = false)
	public void save(Black black) {
		super.save(black);
	}
	
	@Transactional(readOnly = false)
	public void delete(Black black) {
		super.delete(black);
	}
	
}