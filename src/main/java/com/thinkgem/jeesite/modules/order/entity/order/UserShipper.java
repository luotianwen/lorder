/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.order;

import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 发货方Entity
 * @author 罗天文
 * @version 2019-09-21
 */
public class UserShipper extends DataEntity<UserShipper> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户
	private String shipperid;		// 发货方id
	
	public UserShipper() {
		super();
	}

	public UserShipper(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=255, message="发货方id长度必须介于 0 和 255 之间")
	public String getShipperid() {
		return shipperid;
	}

	public void setShipperid(String shipperid) {
		this.shipperid = shipperid;
	}
	
}