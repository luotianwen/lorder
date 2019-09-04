/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.express;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 快递配置Entity
 * @author 罗天文
 * @version 2019-09-04
 */
public class PoolExpress extends DataEntity<PoolExpress> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String abbr;		// 简称
	private String account;		// 账号
	private String password;		// 密码
	private String monthcode;		// 月结账号
	private String sendsite;		// 公司
	private String templatesize;		// 模板
	
	public PoolExpress() {
		super();
	}

	public PoolExpress(String id){
		super(id);
	}

	@Length(min=0, max=50, message="名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=20, message="简称长度必须介于 0 和 20 之间")
	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	
	@Length(min=0, max=50, message="账号长度必须介于 0 和 50 之间")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	@Length(min=0, max=50, message="密码长度必须介于 0 和 50 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=200, message="月结账号长度必须介于 0 和 200 之间")
	public String getMonthcode() {
		return monthcode;
	}

	public void setMonthcode(String monthcode) {
		this.monthcode = monthcode;
	}
	
	@Length(min=0, max=200, message="公司长度必须介于 0 和 200 之间")
	public String getSendsite() {
		return sendsite;
	}

	public void setSendsite(String sendsite) {
		this.sendsite = sendsite;
	}
	
	@Length(min=0, max=20, message="模板长度必须介于 0 和 20 之间")
	public String getTemplatesize() {
		return templatesize;
	}

	public void setTemplatesize(String templatesize) {
		this.templatesize = templatesize;
	}
	
}