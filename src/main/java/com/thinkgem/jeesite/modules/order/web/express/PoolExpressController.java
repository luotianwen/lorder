/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.web.express;

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
import com.thinkgem.jeesite.modules.order.entity.express.PoolExpress;
import com.thinkgem.jeesite.modules.order.service.express.PoolExpressService;

/**
 * 快递配置Controller
 * @author 罗天文
 * @version 2019-09-08
 */
@Controller
@RequestMapping(value = "${adminPath}/order/express/poolExpress")
public class PoolExpressController extends BaseController {

	@Autowired
	private PoolExpressService poolExpressService;
	
	@ModelAttribute
	public PoolExpress get(@RequestParam(required=false) String id) {
		PoolExpress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = poolExpressService.get(id);
		}
		if (entity == null){
			entity = new PoolExpress();
		}
		return entity;
	}
	
	@RequiresPermissions("order:express:poolExpress:view")
	@RequestMapping(value = {"list", ""})
	public String list(PoolExpress poolExpress, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PoolExpress> page = poolExpressService.findPage(new Page<PoolExpress>(request, response), poolExpress); 
		model.addAttribute("page", page);
		return "modules/order/express/poolExpressList";
	}

	@RequiresPermissions("order:express:poolExpress:view")
	@RequestMapping(value = "form")
	public String form(PoolExpress poolExpress, Model model) {
		model.addAttribute("poolExpress", poolExpress);
		return "modules/order/express/poolExpressForm";
	}

	@RequiresPermissions("order:express:poolExpress:edit")
	@RequestMapping(value = "save")
	public String save(PoolExpress poolExpress, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, poolExpress)){
			return form(poolExpress, model);
		}
		poolExpressService.save(poolExpress);
		addMessage(redirectAttributes, "保存快递配置成功");
		return "redirect:"+Global.getAdminPath()+"/order/express/poolExpress/?repage";
	}
	
	@RequiresPermissions("order:express:poolExpress:edit")
	@RequestMapping(value = "delete")
	public String delete(PoolExpress poolExpress, RedirectAttributes redirectAttributes) {
		poolExpressService.delete(poolExpress);
		addMessage(redirectAttributes, "删除快递配置成功");
		return "redirect:"+Global.getAdminPath()+"/order/express/poolExpress/?repage";
	}

}