/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity.address;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 发货地址Entity
 * @author 罗天文
 * @version 2019-09-16
 */
public class Address extends DataEntity<Address> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 发件人名称
	private Area provice;		// 发件地址-省
	private Area city;		// 发件地址-市
	private Area county;		// 发件地址-区县
	private String addressDetail;		// 发件详细地址
	private String phone;		// 发件人电话
	
	public Address() {
		super();
	}

	public Address(String id){
		super(id);
	}

	@Length(min=1, max=50, message="发件人名称长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="发件地址-省不能为空")
	public Area getProvice() {
		return provice;
	}

	public void setProvice(Area provice) {
		this.provice = provice;
	}
	
	@NotNull(message="发件地址-市不能为空")
	public Area getCity() {
		return city;
	}

	public void setCity(Area city) {
		this.city = city;
	}
	
	@NotNull(message="发件地址-区县不能为空")
	public Area getCounty() {
		return county;
	}

	public void setCounty(Area county) {
		this.county = county;
	}
	
	@Length(min=1, max=512, message="发件详细地址长度必须介于 1 和 512 之间")
	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	
	@Length(min=1, max=50, message="发件人电话长度必须介于 1 和 50 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}