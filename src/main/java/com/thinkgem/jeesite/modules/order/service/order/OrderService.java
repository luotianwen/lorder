/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service.order;

import java.util.*;

import com.thinkgem.jeesite.modules.order.entity.express.OrderReturn;
import com.thinkgem.jeesite.modules.order.entity.express.PoolExpress;
import com.thinkgem.jeesite.modules.order.service.express.PoolExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.dao.order.OrderDao;
import com.thinkgem.jeesite.modules.order.entity.order.OrderDetail;
import com.thinkgem.jeesite.modules.order.dao.order.OrderDetailDao;

/**
 * 订单管理Service
 * @author 罗天文
 * @version 2019-08-23
 */
@Service
@Transactional(readOnly = true)
public class OrderService extends CrudService<OrderDao, Order> {

	@Autowired
	private OrderDetailDao orderDetailDao;
	
	public Order get(String id) {
		Order order = super.get(id);
		order.setOrderDetailList(orderDetailDao.findList(new OrderDetail(order)));
		return order;
	}
	public List<OrderDetail> getOrderDetailList(OrderDetail order) {
		return orderDetailDao.findAllList(order);
	}
	public List<Order> findList(Order order) {
		return super.findList(order);
	}
	
	public Page<Order> findPage(Page<Order> page, Order order) {
		return super.findPage(page, order);
	}
	
	@Transactional(readOnly = false)
	public void save(Order order) {
		super.save(order);
		for (OrderDetail orderDetail : order.getOrderDetailList()){
			if (orderDetail.getId() == null){
				continue;
			}
			if (OrderDetail.DEL_FLAG_NORMAL.equals(orderDetail.getDelFlag())){
				if (StringUtils.isBlank(orderDetail.getId())){
					orderDetail.setPoolTask(order);
					orderDetail.preInsert();
					orderDetailDao.insert(orderDetail);
				}else{
					orderDetail.preUpdate();
					orderDetailDao.update(orderDetail);
				}
			}else{
				orderDetailDao.delete(orderDetail);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Order order) {
		super.delete(order);
		orderDetailDao.delete(new OrderDetail(order));
	}
	@Autowired
	private PoolExpressService poolExpressService;
	@Transactional(readOnly = false)
    public void saveExpress(Order order) throws Exception {
		OrderReturn or=poolExpressService.express(order,2);
		PoolExpress pe=poolExpressService.get(order.getCarriers());
		order.setRemark(order.getCarriers());
		order.setCarriers(pe.getName()+" "+or.getOrder().getLogisticCode());
		order.setSendWay("2");
		order.setSendStoreDatetime(new Date());
		dao.saveExpress(order);
    }
	@Transactional(readOnly = false)
	public void saveWBExpress(Order order) {
		order.setSendWay("2");
		order.setSendStoreDatetime(new Date());
		dao.saveWBExpress(order);
	}

	@Transactional(readOnly = false)
	public void allDeliver(List<Order> os,String type) throws Exception{

        Map<String,Order> map=new HashMap<String, Order>();
		for (Order order:os
		) {
			String key=order.getConsigneeName()+order.getConsigneePhone()+order.getProvince().getId()+order.getCity().getId()+order.getCounty().getId()+order.getAddressDetail();
			map.put(key,order);
		}
		Set<Map.Entry<String, Order>> set= map.entrySet();
		for (Map.Entry<String, Order> entry : set) {
			Order order = entry.getValue();
			PoolExpress pe=null;
			OrderReturn or=null;
			String key=order.getConsigneeName()+order.getConsigneePhone()+order.getProvince().getId()+order.getCity().getId()+order.getCounty().getId()+order.getAddressDetail();
			for (Order o:os){
				String okey=o.getConsigneeName()+o.getConsigneePhone()+o.getProvince().getId()+o.getCity().getId()+o.getCounty().getId()+o.getAddressDetail();
				  if(key.equals(okey)) {
				  	if(pe==null) {
						or = poolExpressService.express(order,1);
						pe = poolExpressService.get(order.getCarriers());
					}
					  o.setRemark(order.getCarriers());
					  o.setCarriers(pe.getName()+" "+or.getOrder().getLogisticCode());
					  o.setSendWay("2");
					  o.setSendStoreDatetime(new Date());
					  //if(type.equals("1")) {
						//  dao.saveExpress(o);
					 // }
					 /* else {
						  dao.updateExpress(o);
					  }*/
					  dao.updateExpress(o);
					  //订阅物流
					  poolExpressService.orderTracesSubByJson(o,1);
				  }

			}

		}

		/*for (Order order:os
				) {
			OrderReturn or=poolExpressService.express(order);
			PoolExpress pe=poolExpressService.get(order.getCarriers());
			order.setRemark(order.getCarriers());
			order.setCarriers(pe.getName()+" "+or.getOrder().getLogisticCode());
			order.setSendWay("2");
			order.setSendStoreDatetime(new Date());
			dao.saveExpress(order);
			//订阅物流
			poolExpressService.orderTracesSubByJson(order);
		}*/
	}
}