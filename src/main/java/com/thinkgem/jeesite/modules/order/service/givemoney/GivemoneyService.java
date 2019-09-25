/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service.givemoney;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.order.entity.givemoney.Givemoney;
import com.thinkgem.jeesite.modules.order.dao.givemoney.GivemoneyDao;
import com.thinkgem.jeesite.modules.order.entity.givemoney.GivemoneyOrder;
import com.thinkgem.jeesite.modules.order.dao.givemoney.GivemoneyOrderDao;

/**
 * 打款信息Service
 * @author 罗天文
 * @version 2019-09-20
 */
@Service
@Transactional(readOnly = true)
public class GivemoneyService extends CrudService<GivemoneyDao, Givemoney> {

	@Autowired
	private GivemoneyOrderDao givemoneyOrderDao;
	
	public Givemoney get(String id) {
		Givemoney givemoney = super.get(id);
		givemoney.setGivemoneyOrderList(givemoneyOrderDao.findList(new GivemoneyOrder(givemoney)));
		return givemoney;
	}
	
	public List<Givemoney> findList(Givemoney givemoney) {
		return super.findList(givemoney);
	}
	
	public Page<Givemoney> findPage(Page<Givemoney> page, Givemoney givemoney) {
		return super.findPage(page, givemoney);
	}
	
	@Transactional(readOnly = false)
	public void save(Givemoney givemoney) {
		super.save(givemoney);
		for (GivemoneyOrder givemoneyOrder : givemoney.getGivemoneyOrderList()){
			if (givemoneyOrder.getId() == null){
				continue;
			}
			if (GivemoneyOrder.DEL_FLAG_NORMAL.equals(givemoneyOrder.getDelFlag())){
				if (StringUtils.isBlank(givemoneyOrder.getId())){
					givemoneyOrder.setMoney(givemoney);
					givemoneyOrder.preInsert();
					givemoneyOrderDao.insert(givemoneyOrder);
				}else{
					givemoneyOrder.preUpdate();
					givemoneyOrderDao.update(givemoneyOrder);
				}
			}else{
				givemoneyOrderDao.delete(givemoneyOrder);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Givemoney givemoney) {
		super.delete(givemoney);
		givemoneyOrderDao.delete(new GivemoneyOrder(givemoney));
	}
	
}