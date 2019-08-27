/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.web.batch;

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
import com.thinkgem.jeesite.modules.order.entity.batch.PoolBatch;
import com.thinkgem.jeesite.modules.order.service.batch.PoolBatchService;

/**
 * 订单集成Controller
 * @author 罗天文
 * @version 2019-08-27
 */
@Controller
@RequestMapping(value = "${adminPath}/order/batch/poolBatch")
public class PoolBatchController extends BaseController {

	@Autowired
	private PoolBatchService poolBatchService;
	
	@ModelAttribute
	public PoolBatch get(@RequestParam(required=false) String id) {
		PoolBatch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = poolBatchService.get(id);
		}
		if (entity == null){
			entity = new PoolBatch();
		}
		return entity;
	}
	
	@RequiresPermissions("order:batch:poolBatch:view")
	@RequestMapping(value = {"list", ""})
	public String list(PoolBatch poolBatch, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PoolBatch> page = poolBatchService.findPage(new Page<PoolBatch>(request, response), poolBatch); 
		model.addAttribute("page", page);
		return "modules/order/batch/poolBatchList";
	}

	@RequiresPermissions("order:batch:poolBatch:view")
	@RequestMapping(value = "form")
	public String form(PoolBatch poolBatch, Model model) {
		model.addAttribute("poolBatch", poolBatch);
		return "modules/order/batch/poolBatchForm";
	}

	@RequiresPermissions("order:batch:poolBatch:edit")
	@RequestMapping(value = "save")
	public String save(PoolBatch poolBatch, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, poolBatch)){
			return form(poolBatch, model);
		}
		poolBatchService.save(poolBatch);
		addMessage(redirectAttributes, "保存订单集成成功");
		return "redirect:"+Global.getAdminPath()+"/order/batch/poolBatch/?repage";
	}
	
	@RequiresPermissions("order:batch:poolBatch:edit")
	@RequestMapping(value = "delete")
	public String delete(PoolBatch poolBatch, RedirectAttributes redirectAttributes) {
		poolBatchService.delete(poolBatch);
		addMessage(redirectAttributes, "删除订单集成成功");
		return "redirect:"+Global.getAdminPath()+"/order/batch/poolBatch/?repage";
	}

}