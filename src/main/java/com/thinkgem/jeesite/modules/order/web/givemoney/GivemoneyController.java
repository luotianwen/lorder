/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.web.givemoney;

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
import com.thinkgem.jeesite.modules.order.entity.givemoney.Givemoney;
import com.thinkgem.jeesite.modules.order.service.givemoney.GivemoneyService;

/**
 * 打款信息Controller
 * @author 罗天文
 * @version 2019-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/order/givemoney/givemoney")
public class GivemoneyController extends BaseController {

	@Autowired
	private GivemoneyService givemoneyService;
	
	@ModelAttribute
	public Givemoney get(@RequestParam(required=false) String id) {
		Givemoney entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = givemoneyService.get(id);
		}
		if (entity == null){
			entity = new Givemoney();
		}
		return entity;
	}
	
	@RequiresPermissions("order:givemoney:givemoney:view")
	@RequestMapping(value = {"list", ""})
	public String list(Givemoney givemoney, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Givemoney> page = givemoneyService.findPage(new Page<Givemoney>(request, response), givemoney); 
		model.addAttribute("page", page);
		return "modules/order/givemoney/givemoneyList";
	}

	@RequiresPermissions("order:givemoney:givemoney:view")
	@RequestMapping(value = "form")
	public String form(Givemoney givemoney, Model model) {
		model.addAttribute("givemoney", givemoney);
		return "modules/order/givemoney/givemoneyForm";
	}

	@RequiresPermissions("order:givemoney:givemoney:edit")
	@RequestMapping(value = "save")
	public String save(Givemoney givemoney, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, givemoney)){
			return form(givemoney, model);
		}
		givemoneyService.save(givemoney);
		addMessage(redirectAttributes, "保存打款信息成功");
		return "redirect:"+Global.getAdminPath()+"/order/givemoney/givemoney/?repage";
	}
	
	@RequiresPermissions("order:givemoney:givemoney:edit")
	@RequestMapping(value = "delete")
	public String delete(Givemoney givemoney, RedirectAttributes redirectAttributes) {
		givemoneyService.delete(givemoney);
		addMessage(redirectAttributes, "删除打款信息成功");
		return "redirect:"+Global.getAdminPath()+"/order/givemoney/givemoney/?repage";
	}

}