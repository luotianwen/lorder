/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.order;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 订单管理Entity
 * @author 罗天文
 * @version 2019-08-23
 */
public class Order extends DataEntity<Order> {
	
	private static final long serialVersionUID = 1L;
	private String poolTaskNo;		// 集成单号
	private String taskNo;		// 平台单号
	private String supplierTaskNo;		// 供应商单号
	private Date taskGenDatetime;		// 订单时间
	private String payWay;		// 付款渠道
	private String taskStatus;		// 订单状态
	private Date statusChangeDatetime;		// 更新时间
	private Double taskAmount;		// 订单金额
	private String saleGroup;		// 发货组织
	private String taskType;		// 订单类型
	private String dmNo;		// 档期编码
	private String dmName;		// 档期名称
	private String source;		// 订单来源
	private String ebTaskNo;		// SAP单号
	private String erpNo;		// SAP交货单号
	private String emergentId;		// 紧急程度
	private String failreason;		// 失败原因
	private String taskCreator;		// 订单创建人
	private String customerNo;		// 客户编号
	private String customerName;		// 客户名称
	private String sex;		// 客户性别
	private String homePhone;		// 家庭电话
	private String companyPhone;		// 单位电话
	private String handPhone;		// 客户手机
	private String email;		// 电子邮件
	private String fax;		// 会员编号
	private String idCardName;		// 证件名称
	private String idCard;		// 证件号码
	private Area province;		// 收货地址-省
	private Area city;		// 收货地址-市
	private Area county;		// 收货地址-区县
	private String addressDetail;		// 收货详细地址
	private String postcode;		// 邮政编码
	private String consigneeName;		// 收货人名称
	private String consigneePhone;		// 收货人电话
	private String preSendAddress;		// 发货地址
	private String sendWay;		// 送货方式
	private String carriers;		// 承运商
	private String invoiceTitle;		// 发票抬头
	private String invoiceNo;		// 发票号
	private String invoiceType;		// 发票类型
	private String invoiceSendId;		// 发票发送方式
	private String invoiceSendAddress;		// 发票发送地址
	private Date sendStoreDatetime;		// 发货日期
	private String signStandard;		// 签收标准
	private String signResult;		// 是否签收
	private String signName;		// 签收人
	private Date signDate;		// 签收时间
	private String recallStatus;		// 签收状态
	private String remark;		// 备注
	private Date beginTaskGenDatetime;		// 开始 订单时间
	private Date endTaskGenDatetime;		// 结束 订单时间
	private Date beginSendStoreDatetime;		// 开始 发货日期
	private Date endSendStoreDatetime;		// 结束 发货日期
	private Date beginSignDate;		// 开始 签收时间
	private Date endSignDate;		// 结束 签收时间
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	private List<OrderDetail> orderDetailList = Lists.newArrayList();		// 子表列表
	
	public Order() {
		super();
	}

	public Order(String id){
		super(id);
	}

	@Length(min=1, max=10, message="集成单号长度必须介于 1 和 10 之间")
	public String getPoolTaskNo() {
		return poolTaskNo;
	}

	public void setPoolTaskNo(String poolTaskNo) {
		this.poolTaskNo = poolTaskNo;
	}
	
	@Length(min=1, max=10, message="平台单号长度必须介于 1 和 10 之间")
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
	@Length(min=0, max=40, message="供应商单号长度必须介于 0 和 40 之间")
	public String getSupplierTaskNo() {
		return supplierTaskNo;
	}

	public void setSupplierTaskNo(String supplierTaskNo) {
		this.supplierTaskNo = supplierTaskNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="订单时间不能为空")
	public Date getTaskGenDatetime() {
		return taskGenDatetime;
	}

	public void setTaskGenDatetime(Date taskGenDatetime) {
		this.taskGenDatetime = taskGenDatetime;
	}
	
	@Length(min=1, max=11, message="付款渠道长度必须介于 1 和 11 之间")
	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	
	@Length(min=1, max=11, message="订单状态长度必须介于 1 和 11 之间")
	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="更新时间不能为空")
	public Date getStatusChangeDatetime() {
		return statusChangeDatetime;
	}

	public void setStatusChangeDatetime(Date statusChangeDatetime) {
		this.statusChangeDatetime = statusChangeDatetime;
	}
	
	@NotNull(message="订单金额不能为空")
	public Double getTaskAmount() {
		return taskAmount;
	}

	public void setTaskAmount(Double taskAmount) {
		this.taskAmount = taskAmount;
	}
	
	@Length(min=1, max=16, message="发货组织长度必须介于 1 和 16 之间")
	public String getSaleGroup() {
		return saleGroup;
	}

	public void setSaleGroup(String saleGroup) {
		this.saleGroup = saleGroup;
	}
	
	@Length(min=1, max=16, message="订单类型长度必须介于 1 和 16 之间")
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	@Length(min=0, max=100, message="档期编码长度必须介于 0 和 100 之间")
	public String getDmNo() {
		return dmNo;
	}

	public void setDmNo(String dmNo) {
		this.dmNo = dmNo;
	}
	
	@Length(min=0, max=100, message="档期名称长度必须介于 0 和 100 之间")
	public String getDmName() {
		return dmName;
	}

	public void setDmName(String dmName) {
		this.dmName = dmName;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@Length(min=0, max=40, message="SAP单号长度必须介于 0 和 40 之间")
	public String getEbTaskNo() {
		return ebTaskNo;
	}

	public void setEbTaskNo(String ebTaskNo) {
		this.ebTaskNo = ebTaskNo;
	}
	
	@Length(min=0, max=40, message="SAP交货单号长度必须介于 0 和 40 之间")
	public String getErpNo() {
		return erpNo;
	}

	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}
	
	@Length(min=0, max=512, message="紧急程度长度必须介于 0 和 512 之间")
	public String getEmergentId() {
		return emergentId;
	}

	public void setEmergentId(String emergentId) {
		this.emergentId = emergentId;
	}
	
	@Length(min=0, max=512, message="失败原因长度必须介于 0 和 512 之间")
	public String getFailreason() {
		return failreason;
	}

	public void setFailreason(String failreason) {
		this.failreason = failreason;
	}
	
	@Length(min=0, max=40, message="订单创建人长度必须介于 0 和 40 之间")
	public String getTaskCreator() {
		return taskCreator;
	}

	public void setTaskCreator(String taskCreator) {
		this.taskCreator = taskCreator;
	}
	
	@Length(min=1, max=40, message="客户编号长度必须介于 1 和 40 之间")
	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	@Length(min=1, max=512, message="客户名称长度必须介于 1 和 512 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=2, message="客户性别长度必须介于 0 和 2 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=40, message="家庭电话长度必须介于 0 和 40 之间")
	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	
	@Length(min=0, max=40, message="单位电话长度必须介于 0 和 40 之间")
	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	
	@Length(min=0, max=40, message="客户手机长度必须介于 0 和 40 之间")
	public String getHandPhone() {
		return handPhone;
	}

	public void setHandPhone(String handPhone) {
		this.handPhone = handPhone;
	}
	
	@Length(min=0, max=40, message="电子邮件长度必须介于 0 和 40 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=40, message="会员编号长度必须介于 0 和 40 之间")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@Length(min=0, max=20, message="证件名称长度必须介于 0 和 20 之间")
	public String getIdCardName() {
		return idCardName;
	}

	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
	}
	
	@Length(min=0, max=20, message="证件号码长度必须介于 0 和 20 之间")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	@NotNull(message="收货地址-省不能为空")
	public Area getProvince() {
		return province;
	}

	public void setProvince(Area province) {
		this.province = province;
	}
	
	@NotNull(message="收货地址-市不能为空")
	public Area getCity() {
		return city;
	}

	public void setCity(Area city) {
		this.city = city;
	}
	
	@NotNull(message="收货地址-区县不能为空")
	public Area getCounty() {
		return county;
	}

	public void setCounty(Area county) {
		this.county = county;
	}
	
	@Length(min=1, max=512, message="收货详细地址长度必须介于 1 和 512 之间")
	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	
	@Length(min=0, max=10, message="邮政编码长度必须介于 0 和 10 之间")
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	@Length(min=0, max=100, message="收货人名称长度必须介于 0 和 100 之间")
	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	
	@Length(min=0, max=100, message="收货人电话长度必须介于 0 和 100 之间")
	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	
	@Length(min=0, max=512, message="发货地址长度必须介于 0 和 512 之间")
	public String getPreSendAddress() {
		return preSendAddress;
	}

	public void setPreSendAddress(String preSendAddress) {
		this.preSendAddress = preSendAddress;
	}
	
	@Length(min=0, max=2, message="送货方式长度必须介于 0 和 2 之间")
	public String getSendWay() {
		return sendWay;
	}

	public void setSendWay(String sendWay) {
		this.sendWay = sendWay;
	}
	
	@Length(min=0, max=50, message="承运商长度必须介于 0 和 50 之间")
	public String getCarriers() {
		return carriers;
	}

	public void setCarriers(String carriers) {
		this.carriers = carriers;
	}
	
	@Length(min=0, max=512, message="发票抬头长度必须介于 0 和 512 之间")
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	
	@Length(min=0, max=100, message="发票号长度必须介于 0 和 100 之间")
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
	@Length(min=0, max=16, message="发票类型长度必须介于 0 和 16 之间")
	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	public String getInvoiceSendId() {
		return invoiceSendId;
	}

	public void setInvoiceSendId(String invoiceSendId) {
		this.invoiceSendId = invoiceSendId;
	}
	
	@Length(min=0, max=512, message="发票发送地址长度必须介于 0 和 512 之间")
	public String getInvoiceSendAddress() {
		return invoiceSendAddress;
	}

	public void setInvoiceSendAddress(String invoiceSendAddress) {
		this.invoiceSendAddress = invoiceSendAddress;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendStoreDatetime() {
		return sendStoreDatetime;
	}

	public void setSendStoreDatetime(Date sendStoreDatetime) {
		this.sendStoreDatetime = sendStoreDatetime;
	}
	
	@Length(min=0, max=200, message="签收标准长度必须介于 0 和 200 之间")
	public String getSignStandard() {
		return signStandard;
	}

	public void setSignStandard(String signStandard) {
		this.signStandard = signStandard;
	}
	
	@Length(min=0, max=1, message="是否签收长度必须介于 0 和 1 之间")
	public String getSignResult() {
		return signResult;
	}

	public void setSignResult(String signResult) {
		this.signResult = signResult;
	}
	
	@Length(min=0, max=200, message="签收人长度必须介于 0 和 200 之间")
	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	@Length(min=0, max=1, message="签收状态长度必须介于 0 和 1 之间")
	public String getRecallStatus() {
		return recallStatus;
	}

	public void setRecallStatus(String recallStatus) {
		this.recallStatus = recallStatus;
	}
	
	@Length(min=0, max=200, message="备注长度必须介于 0 和 200 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
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
		
	public Date getBeginSendStoreDatetime() {
		return beginSendStoreDatetime;
	}

	public void setBeginSendStoreDatetime(Date beginSendStoreDatetime) {
		this.beginSendStoreDatetime = beginSendStoreDatetime;
	}
	
	public Date getEndSendStoreDatetime() {
		return endSendStoreDatetime;
	}

	public void setEndSendStoreDatetime(Date endSendStoreDatetime) {
		this.endSendStoreDatetime = endSendStoreDatetime;
	}
		
	public Date getBeginSignDate() {
		return beginSignDate;
	}

	public void setBeginSignDate(Date beginSignDate) {
		this.beginSignDate = beginSignDate;
	}
	
	public Date getEndSignDate() {
		return endSignDate;
	}

	public void setEndSignDate(Date endSignDate) {
		this.endSignDate = endSignDate;
	}
		
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
}