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
 * @version 2019-10-26
 */
public class PoolBatchLine extends DataEntity<PoolBatchLine> {
	
	private static final long serialVersionUID = 1L;
	private PoolBatch poolBatch;		// 订单集成id 父类
	private String productId;		// ERP物料编码
	private String name;		// 物料名称
	private Integer amount;		// 数量
	private Double sumPrice;		// 总价格
	private String supplierid;		// 发货方ID
	private String suppliername;		// 发货方名称
	private String productClass;		// 类型
	private String wharehouse;		// 仓库
	private String agenttype;		// 代理类型
	private String sapsupplierid;		// sap供应商
	private Integer amount2;		// 数量

	public Integer getAmount2() {
		return amount2;
	}

	public void setAmount2(Integer amount2) {
		this.amount2 = amount2;
	}

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
	
	@Length(min=0, max=11, message="发货方ID长度必须介于 0 和 11 之间")
	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	
	@Length(min=0, max=200, message="发货方名称长度必须介于 0 和 200 之间")
	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	
	@Length(min=0, max=20, message="类型长度必须介于 0 和 20 之间")
	public String getProductClass() {
		return productClass;
	}

	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}
	
	@Length(min=0, max=50, message="仓库长度必须介于 0 和 50 之间")
	public String getWharehouse() {
		return wharehouse;
	}

	public void setWharehouse(String wharehouse) {
		this.wharehouse = wharehouse;
	}
	
	@Length(min=0, max=100, message="代理类型长度必须介于 0 和 100 之间")
	public String getAgenttype() {
		return agenttype;
	}

	public void setAgenttype(String agenttype) {
		this.agenttype = agenttype;
	}
	
	@Length(min=0, max=50, message="sap供应商长度必须介于 0 和 50 之间")
	public String getSapsupplierid() {
		return sapsupplierid;
	}

	public void setSapsupplierid(String sapsupplierid) {
		this.sapsupplierid = sapsupplierid;
	}
	
}