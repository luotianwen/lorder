/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.web.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.order.entity.express.PoolExpress;
import com.thinkgem.jeesite.modules.order.entity.express.PrintData;
import com.thinkgem.jeesite.modules.order.service.express.PoolExpressService;
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
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.service.order.OrderService;

import java.io.IOException;

/**
 * 订单管理Controller
 * @author 罗天文
 * @version 2019-08-23
 */
@Controller
@RequestMapping(value = "${adminPath}/order/order/order")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private PoolExpressService poolExpressService;
	@ModelAttribute
	public Order get(@RequestParam(required=false) String id) {
		Order entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderService.get(id);
		}
		if (entity == null){
			entity = new Order();
		}
		return entity;
	}
	
	@RequiresPermissions("order:order:order:view")
	@RequestMapping(value = {"list", ""})
	public String list(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Order> page = orderService.findPage(new Page<Order>(request, response), order); 
		model.addAttribute("page", page);
		return "modules/order/order/orderList";
	}

	@RequiresPermissions("order:order:order:view")
	@RequestMapping(value = "form")
	public String form(Order order, Model model) {
		model.addAttribute("order", order);
		return "modules/order/order/orderForm";
	}
	@RequiresPermissions("order:order:order:view")
	@RequestMapping(value = "express")
	public String express(Order order, Model model) {
		model.addAttribute("expresss",poolExpressService.findList(new PoolExpress()));
		model.addAttribute("order", order);
		return "modules/order/order/orderExpress";
	}
	@RequiresPermissions("order:order:order:view")
	@RequestMapping(value = "print")
	public String print(Order order, Model model,HttpServletRequest request) throws Exception {
//String ip=getIpAddress(request);
String ip="219.237.112.6";
		PrintData pd=poolExpressService.print(order,ip);
        model.addAttribute("printData",pd);
		return "modules/order/order/orderPrint";
	}

	@RequiresPermissions("order:order:order:edit")
	@RequestMapping(value = "save")
	public String save(Order order, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, order)){
			return form(order, model);
		}
		orderService.save(order);
		addMessage(redirectAttributes, "保存订单管理成功");
		return "redirect:"+Global.getAdminPath()+"/order/order/order/?repage";
	}
	@RequiresPermissions("order:order:order:edit")
	@RequestMapping(value = "saveExpress")
	public String saveExpress(Order order, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, order)){
			return form(order, model);
		}
		orderService.saveExpress(order);
		addMessage(redirectAttributes, "订单发货成功");
		return "redirect:"+Global.getAdminPath()+"/order/order/order/?repage";
	}

	@RequiresPermissions("order:order:order:edit")
	@RequestMapping(value = "delete")
	public String delete(Order order, RedirectAttributes redirectAttributes) {
		orderService.delete(order);
		addMessage(redirectAttributes, "删除订单管理成功");
		return "redirect:"+Global.getAdminPath()+"/order/order/order/?repage";
	}

}