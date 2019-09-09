/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.web.logistic;

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
import com.thinkgem.jeesite.modules.order.entity.logistic.Logistic;
import com.thinkgem.jeesite.modules.order.service.logistic.LogisticService;

/**
 * 物流信息Controller
 * @author 罗天文
 * @version 2019-09-09
 */
@Controller
@RequestMapping(value = "${adminPath}/order/logistic/logistic")
public class LogisticController extends BaseController {

	@Autowired
	private LogisticService logisticService;
	
	@ModelAttribute
	public Logistic get(@RequestParam(required=false) String id) {
		Logistic entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = logisticService.get(id);
		}
		if (entity == null){
			entity = new Logistic();
		}
		return entity;
	}
	
	@RequiresPermissions("order:logistic:logistic:view")
	@RequestMapping(value = {"list", ""})
	public String list(Logistic logistic, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Logistic> page = logisticService.findPage(new Page<Logistic>(request, response), logistic); 
		model.addAttribute("page", page);
		return "modules/order/logistic/logisticList";
	}

	@RequiresPermissions("order:logistic:logistic:view")
	@RequestMapping(value = "form")
	public String form(Logistic logistic, Model model) {
		model.addAttribute("logistic", logistic);
		return "modules/order/logistic/logisticForm";
	}

	@RequiresPermissions("order:logistic:logistic:edit")
	@RequestMapping(value = "save")
	public String save(Logistic logistic, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, logistic)){
			return form(logistic, model);
		}
		logisticService.save(logistic);
		addMessage(redirectAttributes, "保存物流信息成功");
		return "redirect:"+Global.getAdminPath()+"/order/logistic/logistic/?repage";
	}
	
	@RequiresPermissions("order:logistic:logistic:edit")
	@RequestMapping(value = "delete")
	public String delete(Logistic logistic, RedirectAttributes redirectAttributes) {
		logisticService.delete(logistic);
		addMessage(redirectAttributes, "删除物流信息成功");
		return "redirect:"+Global.getAdminPath()+"/order/logistic/logistic/?repage";
	}

}