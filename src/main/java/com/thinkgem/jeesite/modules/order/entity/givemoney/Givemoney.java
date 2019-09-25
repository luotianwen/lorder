/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.givemoney;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 打款信息Entity
 * @author 罗天文
 * @version 2019-09-20
 */
public class Givemoney extends DataEntity<Givemoney> {
	
	private static final long serialVersionUID = 1L;
	private String accountnumber;		// 银行账号
	private String accountname;		// 账户名
	private String amount;		// 打款金额
	private String bankname;		// 银行名称
	private String usertype;		// 分账用户类型
	private String userid;		// 分账用户ID
	private String typename;		// 分账用户类型名称
	private List<GivemoneyOrder> givemoneyOrderList = Lists.newArrayList();		// 子表列表
	
	public Givemoney() {
		super();
	}

	public Givemoney(String id){
		super(id);
	}

	@Length(min=0, max=50, message="银行账号长度必须介于 0 和 50 之间")
	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	
	@Length(min=0, max=200, message="账户名长度必须介于 0 和 200 之间")
	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=200, message="银行名称长度必须介于 0 和 200 之间")
	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	
	@Length(min=0, max=11, message="分账用户类型长度必须介于 0 和 11 之间")
	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	@Length(min=0, max=11, message="分账用户ID长度必须介于 0 和 11 之间")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Length(min=0, max=200, message="分账用户类型名称长度必须介于 0 和 200 之间")
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
	
	public List<GivemoneyOrder> getGivemoneyOrderList() {
		return givemoneyOrderList;
	}

	public void setGivemoneyOrderList(List<GivemoneyOrder> givemoneyOrderList) {
		this.givemoneyOrderList = givemoneyOrderList;
	}
}