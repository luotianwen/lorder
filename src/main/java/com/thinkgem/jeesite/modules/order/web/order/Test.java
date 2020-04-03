package com.thinkgem.jeesite.modules.order.web.order;

import com.google.common.collect.Lists;
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
        List<String> od= Lists.newArrayList();

        List<String> od2= Lists.newArrayList();
        od.add("LD202003231138590304895");
        od.add("LD202003231242012494566");
        od.add("LD202003242215569215270");
        od.add("LD202003261903331401798");

        od2.add("shentong 772005436695642");
        od2.add("shentong 772005436568765");
        od2.add("shentong 772005445717234");
        od2.add("shentong 772005486066713");



        od.add("LD202003222343184053776");
        od.add("LD202003270705349521676");
        od.add("LD202003201251064524878");
        od.add("LD202003251328501407755");

        od2.add("shentong 772005455803105");
        od2.add("shentong 772005519624944");
        od2.add("shentong 772005453588070");
        od2.add("shentong 772005460180943");


        od.add("LD202003201819411407723");

        od2.add("shentong 772005455204276");
         for (int i = 0; i <od.size() ; i++) {
            Map map = new HashMap();
            map.put("OrderID", od.get(i));
            map.put("LogisticsNum", od2.get(i).split(" ")[1]);
            map.put("LogisticsCode", od2.get(i).split(" ")[0]);

            String json = OrderStatic.lxdpost(OrderStatic.SendGoods, map);
            System.out.println(json);
        }


       /* String json2="{\"LogisticCode\":\"1234561\",\"ShipperCode\":\"SF\",\"Traces\":[{\"AcceptStation\":\"shunfeng速运已收取快件\",\"AcceptTime\":\"2020-03-18 14:25:45\",\"Remark\":\"\"},{\"AcceptStation\":\"货物已经到达深圳\",\"AcceptTime\":\"2020-03-18 14:25:452\",\"Remark\":\"\"},{\"AcceptStation\":\"货物到达福田保税区网点\",\"AcceptTime\":\"2020-03-18 14:25:453\",\"Remark\":\"\"},{\"AcceptStation\":\"货物已经被张三签收了\",\"AcceptTime\":\"2020-03-18 14:25:454\",\"Remark\":\"\"}],\"State\":\"3\",\"EBusinessID\":\"test1568694\",\"Success\":true,\"Reason\":\"\",\"CallBack\":\"\",\"EstimatedDeliveryTime\":\"2020-03-18 14:25:45\"}";

        String json = OrderStatic.lxdpost("http://testinterface.lxisland.cn/api/Interface/GetOrderLogisticsInfo", json2);
        System.out.println(json);*/
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
