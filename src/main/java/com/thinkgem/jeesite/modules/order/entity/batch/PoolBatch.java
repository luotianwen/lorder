/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.batch;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 订单集成Entity
 * @author 罗天文
 * @version 2019-10-26
 */
public class PoolBatch extends DataEntity<PoolBatch> {
	
	private static final long serialVersionUID = 1L;
	private String batchNum;		// 批次号
	private Date batchGenDatetime;		// 生成时间
	private Double sumAmt;		// 总金额
	private String batchCreator;		// 批次创建人
	private String erpNo;		// SAP交货单号
	private String cardcode;		// CardCode
	private String isok;		// 是否交货
	private String orderclass;		// 订单类型
	private String agenttype;		// 代理类型
	private String sapsupplierid;		// sap供应商
	private String shipperid;		// 发货方
	private Date beginBatchGenDatetime;		// 开始 生成时间
	private Date endBatchGenDatetime;		// 结束 生成时间
	private List<PoolBatchLine> poolBatchLineList = Lists.newArrayList();		// 子表列表
	
	public PoolBatch() {
		super();
	}

	public PoolBatch(String id){
		super(id);
	}

	@Length(min=1, max=32, message="批次号长度必须介于 1 和 32 之间")
	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="生成时间不能为空")
	public Date getBatchGenDatetime() {
		return batchGenDatetime;
	}

	public void setBatchGenDatetime(Date batchGenDatetime) {
		this.batchGenDatetime = batchGenDatetime;
	}
	
	@NotNull(message="总金额不能为空")
	public Double getSumAmt() {
		return sumAmt;
	}

	public void setSumAmt(Double sumAmt) {
		this.sumAmt = sumAmt;
	}
	
	@Length(min=1, max=40, message="批次创建人长度必须介于 1 和 40 之间")
	public String getBatchCreator() {
		return batchCreator;
	}

	public void setBatchCreator(String batchCreator) {
		this.batchCreator = batchCreator;
	}
	
	@Length(min=1, max=40, message="SAP交货单号长度必须介于 1 和 40 之间")
	public String getErpNo() {
		return erpNo;
	}

	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}
	
	@Length(min=0, max=255, message="CardCode长度必须介于 0 和 255 之间")
	public String getCardcode() {
		return cardcode;
	}

	public void setCardcode(String cardcode) {
		this.cardcode = cardcode;
	}
	
	@Length(min=0, max=255, message="是否交货长度必须介于 0 和 255 之间")
	public String getIsok() {
		return isok;
	}

	public void setIsok(String isok) {
		this.isok = isok;
	}
	
	@Length(min=0, max=50, message="订单类型长度必须介于 0 和 50 之间")
	public String getOrderclass() {
		return orderclass;
	}

	public void setOrderclass(String orderclass) {
		this.orderclass = orderclass;
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
	
	@Length(min=0, max=50, message="发货方长度必须介于 0 和 50 之间")
	public String getShipperid() {
		return shipperid;
	}

	public void setShipperid(String shipperid) {
		this.shipperid = shipperid;
	}
	
	public Date getBeginBatchGenDatetime() {
		return beginBatchGenDatetime;
	}

	public void setBeginBatchGenDatetime(Date beginBatchGenDatetime) {
		this.beginBatchGenDatetime = beginBatchGenDatetime;
	}
	
	public Date getEndBatchGenDatetime() {
		return endBatchGenDatetime;
	}

	public void setEndBatchGenDatetime(Date endBatchGenDatetime) {
		this.endBatchGenDatetime = endBatchGenDatetime;
	}
		
	public List<PoolBatchLine> getPoolBatchLineList() {
		return poolBatchLineList;
	}

	public void setPoolBatchLineList(List<PoolBatchLine> poolBatchLineList) {
		this.poolBatchLineList = poolBatchLineList;
	}
}