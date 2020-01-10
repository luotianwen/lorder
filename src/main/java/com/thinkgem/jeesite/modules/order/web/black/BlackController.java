/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.web.black;

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
import com.thinkgem.jeesite.modules.order.entity.black.Black;
import com.thinkgem.jeesite.modules.order.service.black.BlackService;

/**
 * 测试黑名单Controller
 * @author 测试黑名单
 * @version 2020-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/order/black/black")
public class BlackController extends BaseController {

	@Autowired
	private BlackService blackService;
	
	@ModelAttribute
	public Black get(@RequestParam(required=false) String id) {
		Black entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = blackService.get(id);
		}
		if (entity == null){
			entity = new Black();
		}
		return entity;
	}
	
	@RequiresPermissions("order:black:black:view")
	@RequestMapping(value = {"list", ""})
	public String list(Black black, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Black> page = blackService.findPage(new Page<Black>(request, response), black); 
		model.addAttribute("page", page);
		return "modules/order/black/blackList";
	}

	@RequiresPermissions("order:black:black:view")
	@RequestMapping(value = "form")
	public String form(Black black, Model model) {
		model.addAttribute("black", black);
		return "modules/order/black/blackForm";
	}

	@RequiresPermissions("order:black:black:edit")
	@RequestMapping(value = "save")
	public String save(Black black, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, black)){
			return form(black, model);
		}
		blackService.save(black);
		addMessage(redirectAttributes, "保存测试黑名单成功");
		return "redirect:"+Global.getAdminPath()+"/order/black/black/?repage";
	}
	
	@RequiresPermissions("order:black:black:edit")
	@RequestMapping(value = "delete")
	public String delete(Black black, RedirectAttributes redirectAttributes) {
		blackService.delete(black);
		addMessage(redirectAttributes, "删除测试黑名单成功");
		return "redirect:"+Global.getAdminPath()+"/order/black/black/?repage";
	}

}