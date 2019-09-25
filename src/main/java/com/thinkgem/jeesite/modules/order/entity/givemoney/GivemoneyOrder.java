/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.givemoney;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 打款信息Entity
 * @author 罗天文
 * @version 2019-09-20
 */
public class GivemoneyOrder extends DataEntity<GivemoneyOrder> {
	
	private static final long serialVersionUID = 1L;
	private String orderid;		// 订单id
	private Date orderdate;		// 订单日期
	private String amount;		// 金额
	private String productname;		// 商品名称
	private String productnumber;		// 数量
	private String itemcode;		// 物料id
	private Givemoney money;		// 打款信息id 父类
	
	public GivemoneyOrder() {
		super();
	}

	public GivemoneyOrder(String id){
		super(id);
	}

	public GivemoneyOrder(Givemoney money){
		this.money = money;
	}

	@Length(min=0, max=50, message="订单id长度必须介于 0 和 50 之间")
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=200, message="商品名称长度必须介于 0 和 200 之间")
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}
	
	@Length(min=0, max=11, message="数量长度必须介于 0 和 11 之间")
	public String getProductnumber() {
		return productnumber;
	}

	public void setProductnumber(String productnumber) {
		this.productnumber = productnumber;
	}
	
	@Length(min=0, max=200, message="物料id长度必须介于 0 和 200 之间")
	public String getItemcode() {
		return itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	
	@Length(min=0, max=32, message="打款信息id长度必须介于 0 和 32 之间")
	public Givemoney getMoney() {
		return money;
	}

	public void setMoney(Givemoney money) {
		this.money = money;
	}
	
}