/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.batch;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 订单集成Entity
 * @author 罗天文
 * @version 2019-08-27
 */
public class PoolBatchLine extends DataEntity<PoolBatchLine> {
	
	private static final long serialVersionUID = 1L;
	private PoolBatch poolBatch;		// 订单集成id 父类
	private String productId;		// ERP物料编码
	private String name;		// 物料名称
	private Integer amount;		// 数量
	private Double sumPrice;		// 总价格
	
	public PoolBatchLine() {
		super();
	}

	public PoolBatchLine(String id){
		super(id);
	}

	public PoolBatchLine(PoolBatch poolBatch){
		this.poolBatch = poolBatch;
	}

	@Length(min=1, max=32, message="订单集成id长度必须介于 1 和 32 之间")
	public PoolBatch getPoolBatch() {
		return poolBatch;
	}

	public void setPoolBatch(PoolBatch poolBatch) {
		this.poolBatch = poolBatch;
	}
	
	@Length(min=1, max=80, message="ERP物料编码长度必须介于 1 和 80 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=1, max=52, message="物料名称长度必须介于 1 和 52 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="数量不能为空")
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@NotNull(message="总价格不能为空")
	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}
	
}