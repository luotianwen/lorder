/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.order;

import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 订单管理Entity
 * @author 罗天文
 * @version 2019-08-23
 */
public class OrderDetail extends DataEntity<OrderDetail> {
	
	private static final long serialVersionUID = 1L;
	private Order poolTask;		// 订单集成ID 父类
	private String poolTaskNo;		// 集成单号
	private String taskNo;		// 订单号
	private String productName;		// 商品名称
	@ExcelField(title="商品编号", align=1, sort=1 )
	private String productNo;		// 商品编号
	private String productClass;		// 产品线/产品分类
	@ExcelField(title="数量", align=1, sort=3 )
	private Integer amount;		// 数量
	private int stock;		// 库存

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	private String productId;		// ERP物料编码
	@ExcelField(title="商品名称", align=1, sort=2 )
	private String name;		// 物料名称
	private String isPresent;		// 赠品标识
	private String presentNotes;		// 赠品备注
	private String giftType;		// 赠品类型
	private String times;		// 分期数
	private String perTimes;		// 分期价格
	private String isOrig;		// 是否原始订单
	private String currency;		// 货币
	private String relievePrice;		// 分摊价格
	private String lxbAmount;		// 莲香币额
	private String lxbPrice;		// 莲香币分摊价格
	private String goldenAmount;		// 金币额
	private String goldenPrice;		// 金币分摊价格
	private String wentAmount;		// 积分额
	private String wentPrice;		// 积分分摊价格
	private String batchNum;		// 批次号
	private String lineMemo;		// 备注
	private String profitLsdtechAmount;		// 莲香岛科技分润金额
	private String profitLsdtechRates;		// 莲香岛科技分润税率
	private String profitLsdinfoAmount;		// 莲香岛信息技术分润金额
	private String profitLsdinfoRates;		// 莲香岛信息技术分润税率
	private String profitStoreAmount;		// 门店分润金额
	private String profitStoreRates;		// 门店分润税率
	private String profitSupplierAmount;		// 供应商分润金额
	private String profitSupplierRates;		// 供应商分润税率
	private String supplierid;		// 供应商id
	private String suppliername;		// 供应商名称


	private double payAmount;//商品实付单价
	private double reductionAmount;//减免金额
	private double priceSum;//商品应付总价
	private String agentType;//代理商标识
	private String sapSupplierID;//SAP供应商ID
	private double payAmountSum;//商品实付总价
	private double socre;

	public double getSocre() {
		return socre;
	}

	public void setSocre(double socre) {
		this.socre = socre;
	}
	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public double getReductionAmount() {
		return reductionAmount;
	}

	public void setReductionAmount(double reductionAmount) {
		this.reductionAmount = reductionAmount;
	}

	public double getPriceSum() {
		return priceSum;
	}

	public void setPriceSum(double priceSum) {
		this.priceSum = priceSum;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public String getSapSupplierID() {
		return sapSupplierID;
	}

	public void setSapSupplierID(String sapSupplierID) {
		this.sapSupplierID = sapSupplierID;
	}

	public double getPayAmountSum() {
		return payAmountSum;
	}

	public void setPayAmountSum(double payAmountSum) {
		this.payAmountSum = payAmountSum;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public OrderDetail() {
		super();
	}

	public OrderDetail(String id){
		super(id);
	}

	public OrderDetail(Order poolTask){
		this.poolTask = poolTask;
	}

	@Length(min=1, max=32, message="订单集成ID长度必须介于 1 和 32 之间")
	public Order getPoolTask() {
		return poolTask;
	}

	public void setPoolTask(Order poolTask) {
		this.poolTask = poolTask;
	}
	
	@Length(min=1, max=10, message="集成单号长度必须介于 1 和 10 之间")
	public String getPoolTaskNo() {
		return poolTaskNo;
	}

	public void setPoolTaskNo(String poolTaskNo) {
		this.poolTaskNo = poolTaskNo;
	}
	
	@Length(min=1, max=10, message="订单号长度必须介于 1 和 10 之间")
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
	@Length(min=1, max=512, message="商品名称长度必须介于 1 和 512 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=1, max=40, message="商品编号长度必须介于 1 和 40 之间")
	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	@Length(min=0, max=20, message="产品线/产品分类长度必须介于 0 和 20 之间")
	public String getProductClass() {
		return productClass;
	}

	public void setProductClass(String productClass) {
		this.productClass = productClass;
	}
	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@Length(min=1, max=80, message="ERP物料编码长度必须介于 1 和 80 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=1, max=512, message="物料名称长度必须介于 1 和 512 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1, message="赠品标识长度必须介于 0 和 1 之间")
	public String getIsPresent() {
		return isPresent;
	}

	public void setIsPresent(String isPresent) {
		this.isPresent = isPresent;
	}
	
	@Length(min=0, max=2000, message="赠品备注长度必须介于 0 和 2000 之间")
	public String getPresentNotes() {
		return presentNotes;
	}

	public void setPresentNotes(String presentNotes) {
		this.presentNotes = presentNotes;
	}
	
	@Length(min=0, max=6, message="赠品类型长度必须介于 0 和 6 之间")
	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}
	
	@Length(min=0, max=11, message="分期数长度必须介于 0 和 11 之间")
	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}
	
	public String getPerTimes() {
		return perTimes;
	}

	public void setPerTimes(String perTimes) {
		this.perTimes = perTimes;
	}
	
	@Length(min=0, max=1, message="是否原始订单长度必须介于 0 和 1 之间")
	public String getIsOrig() {
		return isOrig;
	}

	public void setIsOrig(String isOrig) {
		this.isOrig = isOrig;
	}
	
	@Length(min=0, max=6, message="货币长度必须介于 0 和 6 之间")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getRelievePrice() {
		return relievePrice;
	}

	public void setRelievePrice(String relievePrice) {
		this.relievePrice = relievePrice;
	}
	
	public String getLxbAmount() {
		return lxbAmount;
	}

	public void setLxbAmount(String lxbAmount) {
		this.lxbAmount = lxbAmount;
	}
	
	public String getLxbPrice() {
		return lxbPrice;
	}

	public void setLxbPrice(String lxbPrice) {
		this.lxbPrice = lxbPrice;
	}
	
	public String getGoldenAmount() {
		return goldenAmount;
	}

	public void setGoldenAmount(String goldenAmount) {
		this.goldenAmount = goldenAmount;
	}
	
	public String getGoldenPrice() {
		return goldenPrice;
	}

	public void setGoldenPrice(String goldenPrice) {
		this.goldenPrice = goldenPrice;
	}
	
	public String getWentAmount() {
		return wentAmount;
	}

	public void setWentAmount(String wentAmount) {
		this.wentAmount = wentAmount;
	}
	
	public String getWentPrice() {
		return wentPrice;
	}

	public void setWentPrice(String wentPrice) {
		this.wentPrice = wentPrice;
	}
	
	@Length(min=0, max=64, message="批次号长度必须介于 0 和 64 之间")
	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	
	@Length(min=0, max=512, message="备注长度必须介于 0 和 512 之间")
	public String getLineMemo() {
		return lineMemo;
	}

	public void setLineMemo(String lineMemo) {
		this.lineMemo = lineMemo;
	}
	
	public String getProfitLsdtechAmount() {
		return profitLsdtechAmount;
	}

	public void setProfitLsdtechAmount(String profitLsdtechAmount) {
		this.profitLsdtechAmount = profitLsdtechAmount;
	}
	
	public String getProfitLsdtechRates() {
		return profitLsdtechRates;
	}

	public void setProfitLsdtechRates(String profitLsdtechRates) {
		this.profitLsdtechRates = profitLsdtechRates;
	}
	
	public String getProfitLsdinfoAmount() {
		return profitLsdinfoAmount;
	}

	public void setProfitLsdinfoAmount(String profitLsdinfoAmount) {
		this.profitLsdinfoAmount = profitLsdinfoAmount;
	}
	
	public String getProfitLsdinfoRates() {
		return profitLsdinfoRates;
	}

	public void setProfitLsdinfoRates(String profitLsdinfoRates) {
		this.profitLsdinfoRates = profitLsdinfoRates;
	}
	
	public String getProfitStoreAmount() {
		return profitStoreAmount;
	}

	public void setProfitStoreAmount(String profitStoreAmount) {
		this.profitStoreAmount = profitStoreAmount;
	}
	
	public String getProfitStoreRates() {
		return profitStoreRates;
	}

	public void setProfitStoreRates(String profitStoreRates) {
		this.profitStoreRates = profitStoreRates;
	}
	
	public String getProfitSupplierAmount() {
		return profitSupplierAmount;
	}

	public void setProfitSupplierAmount(String profitSupplierAmount) {
		this.profitSupplierAmount = profitSupplierAmount;
	}
	
	public String getProfitSupplierRates() {
		return profitSupplierRates;
	}

	public void setProfitSupplierRates(String profitSupplierRates) {
		this.profitSupplierRates = profitSupplierRates;
	}
	
}