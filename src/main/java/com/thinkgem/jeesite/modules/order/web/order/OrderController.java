/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.web.order;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.OrderStatic;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.ObjectUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.JxlsTemplate;
import com.thinkgem.jeesite.modules.order.entity.address.Address;
import com.thinkgem.jeesite.modules.order.entity.express.PoolExpress;
import com.thinkgem.jeesite.modules.order.entity.express.PrintData;
import com.thinkgem.jeesite.modules.order.entity.express.SearchData;
import com.thinkgem.jeesite.modules.order.entity.order.OrderDetail;
import com.thinkgem.jeesite.modules.order.entity.order.StockReData;
import com.thinkgem.jeesite.modules.order.entity.order.UserShipper;
import com.thinkgem.jeesite.modules.order.service.address.AddressService;
import com.thinkgem.jeesite.modules.order.service.express.PoolExpressService;
import com.thinkgem.jeesite.modules.order.service.order.UserShipperService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.service.order.OrderService;

import java.math.BigDecimal;
import java.util.*;

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
	@ResponseBody
	public StockReData allDeliver(String ids, String type,String preSendAddress ,String carriers, Model model,RedirectAttributes redirectAttributes) throws Exception {
		StockReData st=new StockReData();

		List<Order> os=new ArrayList<Order>();
		String ors[]=ids.split(",");
		String taskno="";
		for(String o:ors){
			Order order=orderService.get(o);

			if(!("1").equals(order.getHaveAmount())){
				taskno+="订单号"+order.getTaskNo()+"订单号没有库存不能发货";
				break;
			}


				order.setCarriers(carriers);
				Address ad = addressService.get(preSendAddress);
				String sendAddress = ad.getName() + "，" + ad.getPhone() + "，" + ad.getProvice().getName() + "，" + ad.getCity().getName() + "，" + ad.getCounty().getName() + "，" + ad.getAddressDetail();
				order.setPreSendAddress(sendAddress);
				os.add(order);


		}
		if(StringUtils.isEmpty(taskno)) {
			orderService.allDeliver(os,type);
			st.setCode("1");
			st.setMessage("订单重新发货成功");

		}else{
			st.setCode("0");
			st.setMessage(taskno);

		}
		return st;
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
	public void exportFile2(Order order,String ids, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "交货单"+ DateUtils.getDate("yyyyMMdd")+".xls";
			ServletOutputStream out = null;
			if(null==order){
				order=new Order();
			}
			order.setTaskStatus("3");
			List<Order> list=null;
			if(StringUtils.isEmpty(ids)){
				list=orderService.findList(order);}
			else{
				String[] id=ids.split(",");
				list=new ArrayList<Order>();
				for (String i:id
				) {
					list.add(orderService.get(i));
				}
			}
			List<Dict> dl=DictUtils.getDictList("P_TASK_TYPE");
			Map<String, String> ds = new HashMap<String, String>();

			for (Dict d:dl
			) {
				ds.put(d.getValue(),d.getLabel());
			}
			Map<String, Object> params = new HashMap<String, Object>();

			List<Order> alllist=new ArrayList<Order>();
			List<String> sheetNames=new ArrayList();

			for (Order o:list
			) {

				//ObjectUtils.annotationToObject(o,od);
				List<OrderDetail> od2s = orderService.get(o.getId()).getOrderDetailList();
				int page=new BigDecimal(od2s.size()/6.0).setScale(0, BigDecimal.ROUND_UP).intValue();
				if(od2s==null||od2s.size()==0){
					continue;
				}
				int all=od2s.size();
				for (int i = 0; i <page ; i++) {

					List<OrderDetail> od2s2=new ArrayList<OrderDetail>();
					int l=(i)*6;
					int k=(l+5)>all?all:(l+5);
					for (int j = l; j <k; j++) {
						od2s2.add(od2s.get(j));
					}

					if(od2s2.size()<6){
						int la=6-od2s2.size();
						for (int j = 0; j <la ; j++) {
							od2s2.add(new OrderDetail());
						}
					}
					Order od=new Order();
					BeanUtils.copyProperties(od,o);
					od.setOrderDetailList(od2s2);
					od.getPage().setPageNo(i+1);
					sheetNames.add(o.getTaskNo()+"_"+i+1);
					alllist.add(od);
				}



			}
			params.put("items", alllist);
			params.put("sheetNames", sheetNames);
			params.put("ds", ds);


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
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}

	}
	@RequiresPermissions("order:order:order:edit")
	@RequestMapping(value = "export6", method= RequestMethod.POST)
	public void exportFile6(Order order,String ids, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "所有交货单"+ DateUtils.getDate("yyyyMMdd")+".xls";
			ServletOutputStream out = null;
			if(null==order){
				order=new Order();
			}
			order.setTaskStatus("3");
			List<Order> list=null;
			if(StringUtils.isEmpty(ids)){
				list=orderService.findList(order);}
			else{
				String[] id=ids.split(",");
				list=new ArrayList<Order>();
				for (String i:id
				) {
					Order o=orderService.get(i);
					if(o.getTaskStatus().equals("3")) {
						list.add(o);
					}
				}
			}
			List<Dict> dl=DictUtils.getDictList("P_TASK_TYPE");
			Map<String, String> ds = new HashMap<String, String>();

			for (Dict d:dl
			) {
				ds.put(d.getValue(),d.getLabel());
			}
			Map<String, Object> params = new HashMap<String, Object>();

			Collections.sort(list, new Comparator<Order>() {
				@Override
				public int compare(Order o1, Order o2) {

					return o1.getConsigneeName().compareTo(o2.getConsigneeName());
				}
			});


			for (Order o:list
			) {
				List<OrderDetail> od2s = orderService.get(o.getId()).getOrderDetailList();
				o.setOrderDetailList(od2s);
			}
			params.put("items", list);

			params.put("ds", ds);


			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
			response.setHeader("Pragma", "public");
			response.setContentType("application/x-excel;charset=UTF-8");
			out = response.getOutputStream();
			JxlsTemplate.processTemplate("/product_jhd_export6.xls", out, params);
			out.flush();
			out.close();


		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}

	}
	@RequiresPermissions("order:order:order:edit")
	@RequestMapping(value = "export7", method= RequestMethod.POST)
	public void export7(Order order,String ids, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "交货单2"+ DateUtils.getDate("yyyyMMdd")+".xls";
			ServletOutputStream out = null;
			if(null==order){
				order=new Order();
			}
			order.setTaskStatus("3");
			List<Order> list=null;
			if(StringUtils.isEmpty(ids)){
				list=orderService.findList(order);}
			else{
				String[] id=ids.split(",");
				list=new ArrayList<Order>();
				for (String i:id
				) {
					Order o=orderService.get(i);
					if(o.getTaskStatus().equals("3")) {
						list.add(o);
					}
				}
			}
			List<Dict> dl=DictUtils.getDictList("P_TASK_TYPE");
			Map<String, String> ds = new HashMap<String, String>();

			for (Dict d:dl
			) {
				ds.put(d.getValue(),d.getLabel());
			}
			Map<String, Object> params = new HashMap<String, Object>();

			List<Order> alllist=new ArrayList<Order>();


			for (Order o:list
			) {

				//ObjectUtils.annotationToObject(o,od);
				List<OrderDetail> od2s = orderService.get(o.getId()).getOrderDetailList();
				int page=new BigDecimal(od2s.size()/6.0).setScale(0, BigDecimal.ROUND_UP).intValue();
               if(od2s==null||od2s.size()==0){
               	continue;
			   }
                int all=od2s.size();
				for (int i = 0; i <page ; i++) {

					List<OrderDetail> od2s2=new ArrayList<OrderDetail>();
					int l=(i)*6;
					int k=(l+5)>all?all:(l+5);
					for (int j = l; j <k; j++) {
						od2s2.add(od2s.get(j));
					}

					if(od2s2.size()<6){
						int la=6-od2s2.size();
						for (int j = 0; j <la ; j++) {
							od2s2.add(new OrderDetail());
						}
					}
					Order od=new Order();
					BeanUtils.copyProperties(od,o);
					od.setOrderDetailList(od2s2);
					od.getPage().setPageNo(i+1);
					String t=od.getTaskNo();
					od.setRemark(t.substring(t.length() -5,t.length()));
					alllist.add(od);
				}



			}
			Collections.sort(alllist, new Comparator<Order>() {
				@Override
				public int compare(Order o1, Order o2) {

					return o1.getConsigneeName().compareTo(o2.getConsigneeName());
				}
			});
			List<String> sheetNames=new ArrayList();

			//
			List<List<Order>> lastlist=new ArrayList<List<Order>>();
			int ll =new BigDecimal(alllist.size()/3.0).setScale(0, BigDecimal.ROUND_UP).intValue();
			int size=alllist.size();
			int sy=ll*3-size;
			if(sy==0) {
				for (int i = 0; i < ll; i++) {
					List<Order> lls = new ArrayList<Order>();
					lls.add(alllist.get(i * 3 + 0));
					lls.add(alllist.get(i * 3 + 1));
					lls.add(alllist.get(i * 3 + 2));
					lastlist.add(lls);
					sheetNames.add("第_" + (i + 1) + "页");
				}
			}
			else{
				for (int i = 0; i < ll-1; i++) {
					List<Order> lls = new ArrayList<Order>();
					lls.add(alllist.get(i * 3 + 0));
					lls.add(alllist.get(i * 3 + 1));
					lls.add(alllist.get(i * 3 + 2));
					lastlist.add(lls);
					sheetNames.add("第_" + (i + 1) + "页");
				}
				List<Order> lls=new ArrayList<Order>();
				for (int i = 1; i <=3-sy ; i++) {
					lls.add(alllist.get(size-i));
				}

				//lls.add(alllist.get(size-2));

				lastlist.add(lls);
				sheetNames.add("第_"+ll+"页");
			}
			params.put("items", lastlist);
			params.put("sheetNames", sheetNames);
			params.put("ds", ds);


				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
				response.setHeader("Pragma", "public");
				response.setContentType("application/x-excel;charset=UTF-8");
				out = response.getOutputStream();
				JxlsTemplate.processTemplate("/product_jhd_export3.xls", out, params);
				out.flush();
				out.close();


		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}

	}
	@RequiresPermissions("order:order:order:edit")
	@RequestMapping(value = "exportProduct2", method= RequestMethod.POST)
	public String exportProduct2(Order order,String ids, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {

		List<OrderDetail> ods=getOds(order,ids);
			model.addAttribute("items",ods);
		return "modules/order/order/exportProduct2";
	}

	private List<OrderDetail> getOds(Order order,String ids){
		if(null==order){
			order=new Order();
		}
		List<Order> list=null;
		if(StringUtils.isEmpty(ids)){
			list=orderService.findList(order);}
		else{
			String[] id=ids.split(",");
			list=new ArrayList<Order>();
			for (String i:id
			) {
				list.add(orderService.get(i));
			}
		}
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
		for (OrderDetail d:ods
			 ) {
			StockReData sd=getSapStockByItemCode(d.getProductNo());
			int stock=0;
			if(sd!=null&&sd.getData()!=null&&sd.getData().size()>0){
				List<StockReData.DataBean> dbs=sd.getData();
				for (StockReData.DataBean sd1:dbs
					 ) {
					stock+=sd1.getQuantity();
				}
			}
			d.setStock(stock);
		}
		Collections.sort(ods, new Comparator<OrderDetail>() {
			@Override
			public int compare(OrderDetail o1, OrderDetail o2) {
				return o2.getAmount().compareTo(o1.getAmount());
			}
		});
		return ods;
	}
	private StockReData getSapStockByItemCode(String itemCode){

		String json= OrderStatic.get(OrderStatic.salesstock+itemCode);
		StockReData sr= JSON.parseObject(json,StockReData.class);
		return sr;
	}
	@RequiresPermissions("order:order:order:edit")
	@RequestMapping(value = "exportProduct", method= RequestMethod.POST)
	public String exportProduct(Order order,String ids, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "备货单"+ DateUtils.getDate("yyyyMMdd")+".xls";
			List<OrderDetail> ods=getOds(order,ids);
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