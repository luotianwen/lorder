/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.web.order;

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
import com.thinkgem.jeesite.modules.order.entity.order.UserShipper;
import com.thinkgem.jeesite.modules.order.service.order.UserShipperService;

/**
 * 发货方Controller
 * @author 罗天文
 * @version 2019-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/order/order/userShipper")
public class UserShipperController extends BaseController {

	@Autowired
	private UserShipperService userShipperService;
	
	@ModelAttribute
	public UserShipper get(@RequestParam(required=false) String id) {
		UserShipper entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userShipperService.get(id);
		}
		if (entity == null){
			entity = new UserShipper();
		}
		return entity;
	}
	
	@RequiresPermissions("order:order:userShipper:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserShipper userShipper, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserShipper> page = userShipperService.findPage(new Page<UserShipper>(request, response), userShipper); 
		model.addAttribute("page", page);
		return "modules/order/order/userShipperList";
	}

	@RequiresPermissions("order:order:userShipper:view")
	@RequestMapping(value = "form")
	public String form(UserShipper userShipper, Model model) {
		model.addAttribute("userShipper", userShipper);
		return "modules/order/order/userShipperForm";
	}

	@RequiresPermissions("order:order:userShipper:edit")
	@RequestMapping(value = "save")
	public String save(UserShipper userShipper, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, userShipper)){
			return form(userShipper, model);
		}
		userShipperService.save(userShipper);
		addMessage(redirectAttributes, "保存发货方成功");
		return "redirect:"+Global.getAdminPath()+"/order/order/userShipper/?repage";
	}
	
	@RequiresPermissions("order:order:userShipper:edit")
	@RequestMapping(value = "delete")
	public String delete(UserShipper userShipper, RedirectAttributes redirectAttributes) {
		userShipperService.delete(userShipper);
		addMessage(redirectAttributes, "删除发货方成功");
		return "redirect:"+Global.getAdminPath()+"/order/order/userShipper/?repage";
	}

}