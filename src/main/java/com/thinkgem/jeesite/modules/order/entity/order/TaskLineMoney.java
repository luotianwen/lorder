/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.order;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 分润Entity
 * @author 罗天文
 * @version 2019-11-27
 */
public class TaskLineMoney extends DataEntity<TaskLineMoney> {
	
	private static final long serialVersionUID = 1L;
	private String userid;		// 分账方ID
	private String name;		// 分账方名称
	private String lineId;		// 订单行数据
	private   OrderDetail od;
	private String usertype;		// 类型
	private String typename;		// 类型名称
	private String amount;		// 分账金额
	private String proportion;		// 分账比例
	private String rate;		// 税率
	private String shippertype;		// 发货类型
	private String shipperid;		// 发货方id
	private String shippername;		// 发货方名称
	private String isok;		// 是否同步sap
	@ExcelField(title="集成单号", align=2, value ="od.batchNum", sort=2)
	public OrderDetail getOd() {
		return od;
	}
	@ExcelField(title="平台单号", align=2, value ="od.taskNo", sort=1)
	public OrderDetail gettaskNo() {
		return od;
	}

	@ExcelField(title="物料号", align=2, value ="od.productNo", sort=6)
	public OrderDetail getproductNo() {
		return od;
	}

	@ExcelField(title="物料名称", align=2, value ="od.name", sort=7)
	public OrderDetail gettaskname() {
		return od;
	}

	@ExcelField(title="应付总价", align=2, value ="od.priceSum", sort=8)
	public OrderDetail getpriceSum() {
		return od;
	}

	@ExcelField(title="实付总价", align=2, value ="od.payAmountSum", sort=9)
	public OrderDetail getpayAmountSum() {
		return od;
	}
	public void setOd(OrderDetail od) {
		this.od = od;
	}

	public TaskLineMoney() {
		super();
	}

	public TaskLineMoney(String id){
		super(id);
	}

	@ExcelField(title="分账方id", align=2, sort=4)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	@ExcelField(title="分账方名称", align=2, sort=5)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	@ExcelField(title="类型", align=2, sort=16)
	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	@ExcelField(title="类型名称", align=2, sort=18)
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
	@ExcelField(title="分账金额", align=2, sort=20)
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	@ExcelField(title="分账比例", align=2, sort=25)
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
	
	@Length(min=0, max=2, message="是否同步sap长度必须介于 0 和 2 之间")
	public String getIsok() {
		return isok;
	}

	public void setIsok(String isok) {
		this.isok = isok;
	}
	
}