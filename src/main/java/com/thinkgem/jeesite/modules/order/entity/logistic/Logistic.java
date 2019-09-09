/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.logistic;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 物流信息Entity
 * @author 罗天文
 * @version 2019-09-09
 */
public class Logistic extends DataEntity<Logistic> {
	
	private static final long serialVersionUID = 1L;
	private String logisticcode;		// 单号
	private String content;		// 内容
	
	public Logistic() {
		super();
	}

	public Logistic(String id){
		super(id);
	}

	@Length(min=0, max=32, message="单号长度必须介于 0 和 32 之间")
	public String getLogisticcode() {
		return logisticcode;
	}

	public void setLogisticcode(String logisticcode) {
		this.logisticcode = logisticcode;
	}
	
	@Length(min=0, max=32, message="内容长度必须介于 0 和 32 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}