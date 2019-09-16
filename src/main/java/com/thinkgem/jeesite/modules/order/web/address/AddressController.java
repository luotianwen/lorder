/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.web.address;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.order.entity.address.Address;
import com.thinkgem.jeesite.modules.order.service.address.AddressService;

/**
 * 发货地址Controller
 * @author 罗天文
 * @version 2019-09-16
 */
@Controller
@RequestMapping(value = "${adminPath}/order/address/address")
public class AddressController extends BaseController {

	@Autowired
	private AddressService addressService;
	
	@ModelAttribute
	public Address get(@RequestParam(required=false) String id) {
		Address entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = addressService.get(id);
		}
		if (entity == null){
			entity = new Address();
		}
		return entity;
	}
	
	@RequiresPermissions("order:address:address:view")
	@RequestMapping(value = {"list", ""})
	public String list(Address address, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Address> page = addressService.findPage(new Page<Address>(request, response), address); 
		model.addAttribute("page", page);
		return "modules/order/address/addressList";
	}

	@RequiresPermissions("order:address:address:view")
	@RequestMapping(value = "form")
	public String form(Address address, Model model) {
		model.addAttribute("address", address);
		return "modules/order/address/addressForm";
	}

	@RequiresPermissions("order:address:address:edit")
	@RequestMapping(value = "save")
	public String save(Address address, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, address)){
			return form(address, model);
		}
		addressService.save(address);
		addMessage(redirectAttributes, "保存发货地址成功");
		return "redirect:"+Global.getAdminPath()+"/order/address/address/?repage";
	}
	
	@RequiresPermissions("order:address:address:edit")
	@RequestMapping(value = "delete")
	public String delete(Address address, RedirectAttributes redirectAttributes) {
		addressService.delete(address);
		addMessage(redirectAttributes, "删除发货地址成功");
		return "redirect:"+Global.getAdminPath()+"/order/address/address/?repage";
	}

}