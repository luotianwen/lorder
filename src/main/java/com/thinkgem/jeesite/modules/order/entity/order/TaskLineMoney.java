/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.order;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 分润Entity
 * @author 罗天文
 * @version 2019-09-21
 */
public class TaskLineMoney extends DataEntity<TaskLineMoney> {
	
	private static final long serialVersionUID = 1L;
	private String lineId;		// 订单行数据
	private String usertype;		// 类型
	private String typename;		// 类型名称
	private String amount;		// 分账金额
	private String proportion;		// 分账比例
	private String rate;		// 税率
	private String shippertype;		// 发货类型
	private String shipperid;		// 发货方id
	private String shippername;		// 发货方名称
	private String isok;		// sap接口结果
	
	public TaskLineMoney() {
		super();
	}

	public TaskLineMoney(String id){
		super(id);
	}

	@Length(min=0, max=32, message="订单行数据长度必须介于 0 和 32 之间")
	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	
	@Length(min=0, max=11, message="类型长度必须介于 0 和 11 之间")
	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	@Length(min=0, max=50, message="类型名称长度必须介于 0 和 50 之间")
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
	
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
	
	@Length(min=0, max=11, message="发货类型长度必须介于 0 和 11 之间")
	public String getShippertype() {
		return shippertype;
	}

	public void setShippertype(String shippertype) {
		this.shippertype = shippertype;
	}
	
	@Length(min=0, max=11, message="发货方id长度必须介于 0 和 11 之间")
	public String getShipperid() {
		return shipperid;
	}

	public void setShipperid(String shipperid) {
		this.shipperid = shipperid;
	}
	
	@Length(min=0, max=50, message="发货方名称长度必须介于 0 和 50 之间")
	public String getShippername() {
		return shippername;
	}

	public void setShippername(String shippername) {
		this.shippername = shippername;
	}
	
	@Length(min=0, max=2, message="sap接口结果长度必须介于 0 和 2 之间")
	public String getIsok() {
		return isok;
	}

	public void setIsok(String isok) {
		this.isok = isok;
	}
	
}