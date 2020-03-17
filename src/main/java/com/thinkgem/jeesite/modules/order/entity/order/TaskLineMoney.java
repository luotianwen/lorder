/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.order;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 分润Entity
 * @author 罗天文
 * @version 2019-11-27
 */
public class TaskLineMoney extends DataEntity<TaskLineMoney> {
	
	private static final long serialVersionUID = 1L;
	private int userid;		// 分账方ID
	private String name;		// 分账方名称
	private String lineId;		// 订单行数据
	private   OrderDetail od;
	private int usertype;		// 类型
	private String typename;		// 类型名称
	private float amount;		// 分账金额
	private double proportion;		// 分账比例
	private String rate;		// 税率
	private String shippertype;		// 发货类型
	private String shipperid;		// 发货方id
	private String shippername;		// 发货方名称
	private String isok;		// 是否同步sap
	private String accountNumber;
	private String bankName;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

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
	private Date beginTaskGenDatetime;		// 开始 订单时间
	private Date endTaskGenDatetime;		// 结束 订单时间

	public Date getBeginTaskGenDatetime() {
		return beginTaskGenDatetime;
	}

	public void setBeginTaskGenDatetime(Date beginTaskGenDatetime) {
		this.beginTaskGenDatetime = beginTaskGenDatetime;
	}

	public Date getEndTaskGenDatetime() {
		return endTaskGenDatetime;
	}

	public void setEndTaskGenDatetime(Date endTaskGenDatetime) {
		this.endTaskGenDatetime = endTaskGenDatetime;
	}

	public TaskLineMoney() {
		super();
	}

	public TaskLineMoney(String id){
		super(id);
	}

	@ExcelField(title="分账方id", align=2, sort=4)
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
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
	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
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
	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	@ExcelField(title="分账比例", align=2, sort=25)
	public double getProportion() {
		return proportion;
	}

	public void setProportion(double proportion) {
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