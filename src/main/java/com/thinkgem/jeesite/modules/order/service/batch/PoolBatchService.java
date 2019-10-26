/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service.batch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.order.entity.batch.PoolBatch;
import com.thinkgem.jeesite.modules.order.dao.batch.PoolBatchDao;
import com.thinkgem.jeesite.modules.order.entity.batch.PoolBatchLine;
import com.thinkgem.jeesite.modules.order.dao.batch.PoolBatchLineDao;

/**
 * 订单集成Service
 * @author 罗天文
 * @version 2019-10-26
 */
@Service
@Transactional(readOnly = true)
public class PoolBatchService extends CrudService<PoolBatchDao, PoolBatch> {

	@Autowired
	private PoolBatchLineDao poolBatchLineDao;
	
	public PoolBatch get(String id) {
		PoolBatch poolBatch = super.get(id);
		poolBatch.setPoolBatchLineList(poolBatchLineDao.findList(new PoolBatchLine(poolBatch)));
		return poolBatch;
	}
	
	public List<PoolBatch> findList(PoolBatch poolBatch) {
		return super.findList(poolBatch);
	}
	
	public Page<PoolBatch> findPage(Page<PoolBatch> page, PoolBatch poolBatch) {
		return super.findPage(page, poolBatch);
	}
	
	@Transactional(readOnly = false)
	public void save(PoolBatch poolBatch) {
		super.save(poolBatch);
		for (PoolBatchLine poolBatchLine : poolBatch.getPoolBatchLineList()){
			if (poolBatchLine.getId() == null){
				continue;
			}
			if (PoolBatchLine.DEL_FLAG_NORMAL.equals(poolBatchLine.getDelFlag())){
				if (StringUtils.isBlank(poolBatchLine.getId())){
					poolBatchLine.setPoolBatch(poolBatch);
					poolBatchLine.preInsert();
					poolBatchLineDao.insert(poolBatchLine);
				}else{
					poolBatchLine.preUpdate();
					poolBatchLineDao.update(poolBatchLine);
				}
			}else{
				poolBatchLineDao.delete(poolBatchLine);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(PoolBatch poolBatch) {
		super.delete(poolBatch);
		poolBatchLineDao.delete(new PoolBatchLine(poolBatch));
	}
	
}