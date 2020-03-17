package com.thinkgem.jeesite.modules.order.web.order;

import com.thinkgem.jeesite.common.OrderStatic;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.entity.order.OrderDetail;
import com.thinkgem.jeesite.modules.order.service.express.PoolExpressService;
import com.thinkgem.jeesite.modules.order.service.order.OrderService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    @Autowired
    private static PoolExpressService poolExpressService;
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("OrderID", "LD202003061423331646643");
        map.put("LogisticsNum", "772005219910391");
        map.put("LogisticsCode", "shentong");

        String json = OrderStatic.lxdpost(OrderStatic.SendGoods, map);
        System.out.println(json);
    }
    @Autowired
    private OrderService orderService;
    public List<Order> loadOrderData(String dsName, String datasetName, Map<String,Object> parameters){
        System.out.println(parameters);
        Order  order=new Order();

        order.setTaskStatus("3");
        List<Order> list=orderService.findList(order);
        List<Dict> dl= DictUtils.getDictList("P_TASK_TYPE");
        Map<String, String> ds = new HashMap<String, String>();
        for (Dict d:dl
                ) {
            ds.put(d.getValue(),d.getLabel());
        }

        for (Order o:list
                ) {
           /* List<OrderDetail> od2s = orderService.get(o.getId()).getOrderDetailList();
            o.setOrderDetailList(od2s);*/
            o.setTaskType("莲香岛-"+ds.get(o.getTaskType()));
           /* if(od2s.size()<6){
                int la=6-od2s.size();
                for (int i = 0; i <la ; i++) {
                    od2s.add(new OrderDetail());
                }

            }
            o.getPage().setPageNo(new BigDecimal(od2s.size()/6.0).setScale(0, BigDecimal.ROUND_UP).intValue());*/
        }
        return list;
    }
    public List<OrderDetail> loadOrderDetailData(String dsName, String datasetName, Map<String,Object> parameters){
        System.out.println(parameters);
        String id=parameters.get("id").toString();
        List<OrderDetail> list=orderService.get(id).getOrderDetailList();

        return list;
    }
}
