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


od.add("LD202003070848403749806");
od.add("LD202003161604061243311");
od.add("LD202003190025419681244");
od.add("LD202003190746594055894");
od.add("LD202003190816057818272");
od.add("LD202003190824536555332");
od.add("LD202003190829498129939");
od.add("LD202003190925298744536");
od.add("LD202003190949490312970");
od.add("LD202003190953354563852");
od.add("LD202003191046420159338");
od.add("LD202003191048202181478");
od.add("LD202003191117579849700");
od.add("LD202003191126568902460");
od.add("LD202003191220576099211");
od.add("LD202003191308032185247");
od.add("LD202003191321277186600");
od.add("LD202003191322189062389");
od.add("LD202003191503566243149");
od.add("LD202003191511150624831");
od.add("LD202003191604263743810");
od.add("LD202003192122562027473");
od.add("LD202003192148079524196");
od.add("LD202003201436160936893");
od.add("LD202003201821593748633");
od.add("LD202003201822159052609");
od.add("LD202003201824025463767");
od.add("LD202003202103257961598");
od.add("LD202003202214423271231");
od.add("LD202003210716007346446");
od.add("LD202003211653083279941");
od.add("LD202003211654244528266");
od.add("LD202003212353439996890");
od.add("LD202003212358309061460");
od.add("LD202003220804249681024");
od.add("LD202003230511501247223");
od.add("LD202003231222500624861");
od.add("LD202003240734146092569");
od.add("LD202003241352081243888");
od.add("LD202003241643367491205");
od.add("LD202003250547497657720");
od.add("LD202003271526174379803");
od.add("LD132297692167694182");
od.add("LD202003271654151098269");
od.add("LD202003271655269213459");
od.add("LD202003272139072799456");
od.add("LD202003272145144687842");
od.add("LD202003280644435774982");
od.add("LD202003281353037021659");
od.add("LD202003281845078279724");
od.add("LD202003301517247816445");
od2.add("shentong 772005444961585");
od2.add("shentong 772005443075024");
od2.add("shentong 772005410410650");
od2.add("shentong 772005410596875");
od2.add("shentong 772005412156652");
od2.add("shentong 772005411940797");
od2.add("shentong 772005411940797");
od2.add("shentong 772005410918195");
od2.add("shentong 772005410736642");
od2.add("shentong 772005410736642");
od2.add("shentong 772005412307511");
od2.add("shentong 772005399514306");
od2.add("shentong 772005412273776");
od2.add("shentong 772005412273776");
od2.add("shentong 772005411993548");
od2.add("shentong 772005410859258");
od2.add("shentong 772005410859258");
od2.add("shentong 772005410859258");
od2.add("shentong 772005411969297");
od2.add("shentong 772005411969297");
od2.add("shentong 772005411460519");
od2.add("shentong 772005409909590");
od2.add("shentong 772005408348004");
od2.add("shentong 772005444961585");
od2.add("shentong 772005455226497");
od2.add("shentong 772005455226497");
od2.add("shentong 772005455226497");
od2.add("shentong 772005454129586");
od2.add("shentong 772005455722683");
od2.add("shentong 772005455181172");
od2.add("shentong 772005452553865");
od2.add("shentong 772005452553865");
od2.add("shentong 772005455584337");
od2.add("shentong 772005455584337");
od2.add("shentong 772005455114882");
od2.add("shentong 772005440668297");
od2.add("shentong 772005437601103");
od2.add("shentong 772005445803720");
od2.add("shentong 772005446063421");
od2.add("shentong 772005447976823");
od2.add("shentong 772005463210457");
od2.add("shentong 772005542134027");
od2.add("shentong 772005518784906");
od2.add("shentong 772005520436463");
od2.add("shentong 772005516266968");
od2.add("shentong 772005532820966");
od2.add("shentong 772005534703628");
od2.add("shentong 772005527896386");
od2.add("shentong 772005525633152");
od2.add("shentong 772005519892491");
od2.add("shentong 772005542293297");
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
