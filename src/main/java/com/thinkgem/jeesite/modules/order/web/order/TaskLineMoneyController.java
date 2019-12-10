/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.web.order;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.JxlsTemplate;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.order.entity.order.TaskLineMoney;
import com.thinkgem.jeesite.modules.order.service.order.TaskLineMoneyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分润Controller
 * @author 罗天文
 * @version 2019-11-27
 */
@Controller
@RequestMapping(value = "${adminPath}/order/order/taskLineMoney")
public class TaskLineMoneyController extends BaseController {

	@Autowired
	private TaskLineMoneyService taskLineMoneyService;
	
	@ModelAttribute
	public TaskLineMoney get(@RequestParam(required=false) String id) {
		TaskLineMoney entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = taskLineMoneyService.get(id);
		}
		if (entity == null){
			entity = new TaskLineMoney();
		}
		return entity;
	}
	
	@RequiresPermissions("order:order:taskLineMoney:view")
	@RequestMapping(value = {"list", ""})
	public String list(TaskLineMoney taskLineMoney, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TaskLineMoney> page = taskLineMoneyService.findPage(new Page<TaskLineMoney>(request, response), taskLineMoney); 
		model.addAttribute("page", page);
		return "modules/order/order/taskLineMoneyList";
	}

	@RequiresPermissions("order:order:taskLineMoney:view")
	@RequestMapping(value = "form")
	public String form(TaskLineMoney taskLineMoney, Model model) {
		model.addAttribute("taskLineMoney", taskLineMoney);
		return "modules/order/order/taskLineMoneyForm";
	}

	@RequiresPermissions("order:order:taskLineMoney:edit")
	@RequestMapping(value = "save")
	public String save(TaskLineMoney taskLineMoney, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, taskLineMoney)){
			return form(taskLineMoney, model);
		}
		taskLineMoneyService.save(taskLineMoney);
		addMessage(redirectAttributes, "保存分润成功");
		return "redirect:"+Global.getAdminPath()+"/order/order/taskLineMoney/?repage";
	}
	
	@RequiresPermissions("order:order:taskLineMoney:edit")
	@RequestMapping(value = "delete")
	public String delete(TaskLineMoney taskLineMoney, RedirectAttributes redirectAttributes) {
		taskLineMoneyService.delete(taskLineMoney);
		addMessage(redirectAttributes, "删除分润成功");
		return "redirect:"+Global.getAdminPath()+"/order/order/taskLineMoney/?repage";
	}
	@RequiresPermissions("order:order:taskLineMoney:edit")
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportFile(TaskLineMoney taskLineMoney,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "订单分润数据"+ DateUtils.getDate("yyyyMMdd")+".xls";

			if(null==taskLineMoney){
				taskLineMoney=new TaskLineMoney();
			}
			List<TaskLineMoney> list=taskLineMoneyService.findList(taskLineMoney);
			Map<String, Object> params = new HashMap<String, Object>();
			ServletOutputStream out = null;
			params.put("items", list);

				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
				response.setHeader("Pragma", "public");
				response.setContentType("application/x-excel;charset=UTF-8");
				out = response.getOutputStream();
				JxlsTemplate.processTemplate("/profit_export.xls", out, params);
				out.flush();
				out.close();			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/order/order/taskLineMoney/?repage";
	}
}