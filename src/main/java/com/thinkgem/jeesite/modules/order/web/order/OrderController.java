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
import com.thinkgem.jeesite.modules.order.entity.address.Address;
import com.thinkgem.jeesite.modules.order.entity.express.PoolExpress;
import com.thinkgem.jeesite.modules.order.entity.express.PrintData;
import com.thinkgem.jeesite.modules.order.entity.express.SearchData;
import com.thinkgem.jeesite.modules.order.entity.order.OrderDetail;
import com.thinkgem.jeesite.modules.order.entity.order.UserShipper;
import com.thinkgem.jeesite.modules.order.service.address.AddressService;
import com.thinkgem.jeesite.modules.order.service.express.PoolExpressService;
import com.thinkgem.jeesite.modules.order.service.order.UserShipperService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
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
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.service.order.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private UserShipperService userShipperService;


    @RequiresPermissions("order:order:order:shipperedit")
    @RequestMapping(value = "shipper")
    public String shipper(Order order, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        UserShipper us=new UserShipper();
        us.setUser(UserUtils.getUser());
        List<UserShipper>uss=userShipperService.findList(us);
        if(null==uss||uss.size()==0){
            throw new Exception("没有权限");
        }
        order.setShipperid(uss.get(0).getShipperid());
        Page<Order> page = orderService.findPage(new Page<Order>(request, response), order);
        model.addAttribute("page", page);
        return "modules/order/order/ordershipperList";
    }



	@RequiresPermissions("order:order:order:view")
	@RequestMapping(value = {"list", ""})
	public String list(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Order> page = orderService.findPage(new Page<Order>(request, response), order); 
		model.addAttribute("page", page);
		model.addAttribute("expresss",poolExpressService.findList(new PoolExpress()));
		model.addAttribute("addresss",addressService.findList(new Address()));
		return "modules/order/order/orderList";
	}

	@RequiresPermissions("order:order:order:shipper")
	@RequestMapping(value = "form")
	public String form(Order order, Model model) {
		model.addAttribute("order", order);
		return "modules/order/order/orderForm";
	}
	@Autowired
	private AddressService addressService;
	@RequiresPermissions("order:order:order:shipper")
	@RequestMapping(value = "express")
	public String express(Order order, Model model) {
		model.addAttribute("expresss",poolExpressService.findList(new PoolExpress()));
		model.addAttribute("addresss",addressService.findList(new Address()));
		model.addAttribute("order", order);
		return "modules/order/order/orderExpress";
	}
	@RequiresPermissions("order:order:order:shipper")
	@RequestMapping(value = "print")
	public String print(Order order, Model model,HttpServletRequest request) throws Exception {
		String ip=getIpAddress(request);
		PrintData pd=poolExpressService.print(order,ip);
        model.addAttribute("printData",pd);
		return "modules/order/order/orderPrint";
	}
	@RequiresPermissions("order:order:order:view")
	@RequestMapping(value = "search")
	public String search(Order order, Model model,HttpServletRequest request) throws Exception {
		String[] cs=order.getCarriers().split("\\s+");
		PoolExpress pe=new PoolExpress();
		pe.setName(cs[0]);
		PoolExpress sp= poolExpressService.findList(pe).get(0);
		SearchData pd=poolExpressService.getOrderTracesByJson(sp.getAbbr(),cs[1]);
		model.addAttribute("searchData",pd);
		model.addAttribute("carriers",order.getCarriers());
		return "modules/order/order/orderSearch";
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
		Address ad=addressService.get(order.getPreSendAddress());
		//张三 ，13888888888 ，0518-88888888，江苏省 连云港市 新浦区 朝阳中路77号二楼 ，222000
		String sendAddress=ad.getName()+"，"+ad.getPhone()+"，"+ ad.getProvice().getName()+"，"+ad.getCity().getName()+"，"+ad.getCounty().getName()+"，"+ad.getAddressDetail();
		order.setPreSendAddress(sendAddress);
		orderService.saveExpress(order);
		//订阅物流
		poolExpressService.orderTracesSubByJson(order);
		addMessage(redirectAttributes, "订单发货成功");
		return "redirect:"+Global.getAdminPath()+"/order/order/order/?repage";
	}

	@RequiresPermissions("order:order:order:view")
	@RequestMapping(value = "orderListByBatchNum")
	public String orderListByBatchNum(OrderDetail order, Model model) {
		List<OrderDetail> os=orderService.getOrderDetailList(order);
		 model.addAttribute("os", os);
		return "modules/order/order/orderListByBatchNum";
	}
	@RequiresPermissions("order:order:order:edit")
	@RequestMapping(value = "delete")
	public String delete(Order order, RedirectAttributes redirectAttributes) {
		orderService.delete(order);
		addMessage(redirectAttributes, "删除订单管理成功");
		return "redirect:"+Global.getAdminPath()+"/order/order/order/?repage";
	}
	@RequiresPermissions("order:order:order:view")
	@RequestMapping(value = "wbexpress")
	public String wbexpress(Order order, Model model) {
		model.addAttribute("order", order);
		return "modules/order/order/orderWBExpress";
	}
	@RequiresPermissions("order:order:order:edit")
	@RequestMapping(value = "saveWBExpress")
	public String saveWBExpress(Order order, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, order)){
			return form(order, model);
		}
		//张三 ，13888888888 ，0518-88888888，江苏省 连云港市 新浦区 朝阳中路77号二楼 ，222000
		//String sendAddress=ad.getName()+"，"+ad.getPhone()+"，"+ ad.getProvice().getName()+"，"+ad.getCity().getName()+"，"+ad.getCounty().getName()+"，"+ad.getAddressDetail();
		//order.setPreSendAddress(sendAddress);
		orderService.saveWBExpress(order);
		addMessage(redirectAttributes, "订单发货成功");
		return "redirect:"+Global.getAdminPath()+"/order/order/order/?repage";
	}



	@RequiresPermissions("order:order:order:view")
	@RequestMapping(value = "allDeliver")
	public String allDeliver(String ids, String type,String preSendAddress ,String carriers, Model model,RedirectAttributes redirectAttributes) throws Exception {
		List<Order> os=new ArrayList<Order>();
		String ors[]=ids.split(",");
		String taskno="";
		for(String o:ors){
			Order order=orderService.get(o);

			if(!("1").equals(order.getHaveAmount())){
				taskno+="订单号"+order.getTaskNo()+"订单号没有库存不能发货";
				break;
			}

			if (StringUtils.isEmpty(order.getCarriers())) {
				order.setCarriers(carriers);
				Address ad = addressService.get(preSendAddress);
				String sendAddress = ad.getName() + "，" + ad.getPhone() + "，" + ad.getProvice().getName() + "，" + ad.getCity().getName() + "，" + ad.getCounty().getName() + "，" + ad.getAddressDetail();
				order.setPreSendAddress(sendAddress);
				os.add(order);
			} else {
				taskno += "失败 订单号" + order.getTaskNo() + "订单号有单号不能重新发货";
				break;
			}

		}
		if(StringUtils.isEmpty(taskno)) {
			orderService.allDeliver(os);
			addMessage(redirectAttributes, "订单重新发货成功");
			return "redirect:"+Global.getAdminPath()+"/order/order/order/?repage";
		}else{
			addMessage(redirectAttributes, taskno);
			return "redirect:"+Global.getAdminPath()+"/order/order/order/?repage";
		}

	}


	@RequiresPermissions("order:order:order:view")
	@RequestMapping(value = "tDeliver")
	public String tDeliver(String id, String remarks ,String signName ) throws Exception {

		Order order=orderService.get(id);
		order.setCarriers(signName+" "+remarks);
		orderService.saveWBExpress(order);
		return "redirect:"+Global.getAdminPath()+"/order/order/order/?repage";
	}



	@RequiresPermissions("order:order:order:shipper")
	@RequestMapping(value = "allprint")
	public String allprint(String ids, Model model,HttpServletRequest request) throws Exception {
		String ip=getIpAddress(request);
		List<Order> os=new ArrayList<Order>();
		String ors[]=ids.split(",");
		for(String o:ors){
			os.add(orderService.get(o));
		}
		PrintData pd=poolExpressService.allprint(os,ip);
		model.addAttribute("printData",pd);
		return "modules/order/order/orderPrint";
	}
	@RequiresPermissions("order:order:order:edit")
	@RequestMapping(value = "export", method= RequestMethod.POST)
	public String exportFile(Order order, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "订单数据"+ DateUtils.getDate("yyyyMMdd")+".xls";

			if(null==order){
				order=new Order();
			}
			List<Order> list=orderService.findList(order);
			ServletOutputStream out = null;
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, String> ds = new HashMap<String, String>();
			List<Dict> dl=DictUtils.getDictList("P_TASK_STATUS");
			for (Dict d:dl
				 ) {
				ds.put(d.getValue(),d.getLabel());
			}
			params.put("items", list);
			params.put("ds", ds);
			try {
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
				response.setHeader("Pragma", "public");
				response.setContentType("application/x-excel;charset=UTF-8");
				out = response.getOutputStream();
				JxlsTemplate.processTemplate("/order_list_export.xls", out, params);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//new ExportExcel("订单数据"+ DateUtils.getDate("yyyyMMddHHmmss"), Order.class).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/order/order/order/?repage";
	}
	@RequiresPermissions("order:order:order:edit")
	@RequestMapping(value = "export2", method= RequestMethod.POST)
	public void exportFile2(Order order, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "交货单"+ DateUtils.getDate("yyyyMMdd")+".xls";
			ServletOutputStream out = null;
			if(null==order){
				order=new Order();
			}
			order.setTaskStatus("3");
			List<Order> list=orderService.findList(order);
			List<Dict> dl=DictUtils.getDictList("P_TASK_TYPE");
			Map<String, String> ds = new HashMap<String, String>();

			for (Dict d:dl
			) {
				ds.put(d.getValue(),d.getLabel());
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("items", list);
			params.put("ds", ds);
			for (Order o:list
			) {
				List<OrderDetail> od2s = orderService.get(o.getId()).getOrderDetailList();
				o.setOrderDetailList(od2s);
				if(od2s.size()<8){
					int la=8-od2s.size();
					for (int i = 0; i <la ; i++) {
						od2s.add(new OrderDetail());
					}

				}
				o.getPage().setPageNo(new BigDecimal(od2s.size()/6.0).setScale(0, BigDecimal.ROUND_UP).intValue());
			}



			try {
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
				response.setHeader("Pragma", "public");
				response.setContentType("application/x-excel;charset=UTF-8");
				out = response.getOutputStream();
				JxlsTemplate.processTemplate("/product_jhd_export.xls", out, params);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}

	}
	@RequiresPermissions("order:order:order:edit")
	@RequestMapping(value = "exportProduct", method= RequestMethod.POST)
	public String exportProduct(Order order, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "备货单"+ DateUtils.getDate("yyyyMMdd")+".xls";

			if(null==order){
				order=new Order();
			}
			List<Order> list=orderService.findList(order);
			List<OrderDetail> ods=new ArrayList<OrderDetail>();
			for (Order o:list
				 ) {
				List<OrderDetail> od2s=orderService.get(o.getId()).getOrderDetailList();
				for (OrderDetail od2:od2s
					 ) {
					if(ods.size()==0){
						ods.add(od2);
					}
					else{
                        boolean f=false;
						for (OrderDetail od:ods
						) {
							if(od.getProductNo().equals(od2.getProductNo())){
								f=true;
								od.setAmount(od.getAmount()+od2.getAmount());
								break;
							}

						}
                   if(f){

				   }
				   else{
					   ods.add(od2);
				   }


					}



				}

			}
			ServletOutputStream out = null;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("items", ods);
			try {
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
				response.setHeader("Pragma", "public");
				response.setContentType("application/x-excel;charset=UTF-8");
				out = response.getOutputStream();
				JxlsTemplate.processTemplate("/order_bhd_export.xls", out, params);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		 		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/order/order/order/?repage";
	}
    @RequiresPermissions("order:order:order:shipper")
    @RequestMapping(value = "shipperexport", method= RequestMethod.POST)
    public String exportShipperFile(Order order, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "订单数据"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";

            if(null==order){
                order=new Order();
            }
            UserShipper us=new UserShipper();
            us.setUser(UserUtils.getUser());
            List<UserShipper>uss=userShipperService.findList(us);
            if(null==uss){
                throw new Exception("没有权限");
            }
            order.setShipperid(uss.get(0).getShipperid());

            List<Order> list=orderService.findList(order);
            new ExportExcel("订单数据"+ DateUtils.getDate("yyyyMMddHHmmss"), Order.class).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
        }
        return "redirect:"+Global.getAdminPath()+"/order/order/order/shipper?repage";
    }
}