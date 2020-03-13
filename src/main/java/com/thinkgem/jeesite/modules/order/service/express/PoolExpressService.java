/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service.express;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.thinkgem.jeesite.common.OrderStatic;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.order.dao.order.OrderDao;
import com.thinkgem.jeesite.modules.order.dao.order.OrderDetailDao;
import com.thinkgem.jeesite.modules.order.dao.order.TaskLineMoneyDao;
import com.thinkgem.jeesite.modules.order.entity.express.OrderReturn;
import com.thinkgem.jeesite.modules.order.entity.express.PrintData;
import com.thinkgem.jeesite.modules.order.entity.express.SearchData;
import com.thinkgem.jeesite.modules.order.entity.logistic.Logistic;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.entity.order.OrderDetail;
import com.thinkgem.jeesite.modules.order.entity.order.TaskLineMoney;
import com.thinkgem.jeesite.modules.order.service.logistic.LogisticService;
import com.thinkgem.jeesite.modules.order.service.order.OrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.order.entity.express.PoolExpress;
import com.thinkgem.jeesite.modules.order.dao.express.PoolExpressDao;

/**
 * 快递配置Service
 * @author 罗天文
 * @version 2019-09-04
 */
@Service
@Transactional(readOnly = true)
public class PoolExpressService extends CrudService<PoolExpressDao, PoolExpress> {
	Log log= LogFactory.getLog(PoolExpressService.class);
	public PoolExpress get(String id) {
		return super.get(id);
	}
	
	public List<PoolExpress> findList(PoolExpress poolExpress) {
		return super.findList(poolExpress);
	}
	
	public Page<PoolExpress> findPage(Page<PoolExpress> page, PoolExpress poolExpress) {
		return super.findPage(page, poolExpress);
	}
	
	@Transactional(readOnly = false)
	public void save(PoolExpress poolExpress) {
		super.save(poolExpress);
	}
	
	@Transactional(readOnly = false)
	public void delete(PoolExpress poolExpress) {
		super.delete(poolExpress);
	}

	/**
	 * MD5加密
	 * @param str 内容
	 * @param charset 编码方式
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private String MD5(String str, String charset) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes(charset));
		byte[] result = md.digest();
		StringBuffer sb = new StringBuffer(32);
		for (int i = 0; i < result.length; i++) {
			int val = result[i] & 0xff;
			if (val <= 0xf) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(val));
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * base64编码
	 * @param str 内容
	 * @param charset 编码方式
	 * @throws UnsupportedEncodingException
	 */
	private String base64(String str, String charset) throws UnsupportedEncodingException {
		String encoded = Base64.encode(str.getBytes(charset));
		return encoded;
	}

	@SuppressWarnings("unused")
	private String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
		String result = URLEncoder.encode(str, charset);
		return result;
	}

	/**
	 * 电商Sign签名生成
	 * @param content 内容
	 * @param keyValue Appkey
	 * @param charset 编码方式
	 * @throws UnsupportedEncodingException ,Exception
	 * @return DataSign签名
	 */
	@SuppressWarnings("unused")
	private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception
	{
		if (keyValue != null)
		{
			return base64(MD5(content + keyValue, charset), charset);
		}
		return base64(MD5(content, charset), charset);
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * @param url 发送请求的 URL
	 * @param params 请求的参数集合
	 * @return 远程资源的响应结果
	 */
	@SuppressWarnings("unused")
	private String sendPost(String url, Map<String, String> params) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// POST方法
			conn.setRequestMethod("POST");
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.connect();
			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			// 发送请求参数
			if (params != null) {
				StringBuilder param = new StringBuilder();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					if(param.length()>0){
						param.append("&");
					}
					param.append(entry.getKey());
					param.append("=");
					param.append(entry.getValue());
					System.out.println(entry.getKey()+":"+entry.getValue());
				}
				System.out.println("param:"+param.toString());
				out.write(param.toString());
			}
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result.toString();
	}

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderDetailDao orderDetailDao;
	@Autowired
	private TaskLineMoneyDao taskLineMoneyDao;

	/**
	 *
	 * @param order
	 * @param type 1发货或者重新发货 2多单号
	 * @return
	 * @throws Exception
	 */
    public OrderReturn   express(Order order,int type) throws Exception {

		String EBusinessID= Global.getConfig("express.EBusinessID");
		String AppKey= Global.getConfig("express.AppKey");
		String ReqURL= Global.getConfig("express.ReqURL");
        PoolExpress pe=get(order.getCarriers());
		String ShipperCode=pe.getAbbr();
		String CustomerName=pe.getAccount();
		String CustomerPwd=pe.getPassword();
		String MonthCode=pe.getMonthcode();
		String SendSite=pe.getSendsite();
		String TemplateSize=pe.getTemplatesize();
		Order order2=orderDao.get(order.getId());
		String code=order.getTaskNo();//.replace("LD20","");
		if(type==2){
			int csl=order2.getCarriers().split("\\|").length+1;
			code=code+"_"+csl;
		}
        String payType=pe.getPaytype();

		String details="";
		//张三 ，13888888888 ， 江苏省 连云港市 新浦区 朝阳中路77号二楼
		String addrsss[]=order.getPreSendAddress().split("，");

		List<OrderDetail> ods=order.getOrderDetailList();
		int q=0;
		 for (OrderDetail od:ods
			 ) { /*
			details+="{ 'GoodsName':'"+od.getName()+"','Goodsquantity':1,'GoodsWeight':''}";*/
				q=q+od.getAmount();
		 }
		details+="{ 'GoodsName':'护理用品','Goodsquantity':"+q+",'GoodsWeight':'"+order.getWeight()+"'}";
		String requestData= "{'OrderCode':'"+code+"'," +
				"'ShipperCode':'"+ShipperCode+"'," +
				"'CustomerName':'"+CustomerName+"'," +
				"'CustomerPwd':'"+CustomerPwd+"'," +
				"'SendSite':'"+SendSite+"'," +
				"'MonthCode':'"+MonthCode+"'," +

				"'PayType':"+payType+"," +
				"'ExpType':1," +
				"'Cost':1.0," +
				"'OtherCost':1.0," +
				"'Sender':" +
				"{" +
				"'Company':'','Name':'"+addrsss[0]+"','Mobile':'"+addrsss[1]+"','ProvinceName':'"+addrsss[2]+"','CityName':'"+addrsss[3]+"','ExpAreaName':'"+addrsss[4]+"','Address':'"+addrsss[5]+"'}," +
				"'Receiver':" +
				"{" +
				"'Company':'','Name':'"+order.getConsigneeName()+"','Mobile':'"+order.getConsigneePhone()+"','ProvinceName':'"+order.getProvince().getName()+"'," +
				"'CityName':'"+order.getCity().getName()+"','ExpAreaName':'"+order.getCounty().getName()+"','Address':'"+order.getAddressDetail()+"'}," +
				"'Commodity':" +
				"[" +
				details +
				"]," +
				"'Weight':'"+order.getWeight()+"'," +
				"'Quantity':1," +
				"'Volume':0.0," +
				"'TemplateSize':"+TemplateSize+"," +

				"'Remark':'小心轻放'," +
				"'IsReturnPrintTemplate':1}";
		System.out.println(requestData);
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1007");
		String dataSign=encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");

		String result=sendPost(ReqURL, params);

		OrderReturn orderReturn=JSON.parseObject(result, OrderReturn.class);

		if(orderReturn.isSuccess()){
			//通知平台发货
			sendPtFh(order.getTaskNo(),pe.getRemarks(),orderReturn.getOrder().getLogisticCode());
			return orderReturn;
		}
	   else{
			throw new Exception(orderReturn.getReason());
		}

    }
	/**
	 *通知平台发货
	 */
    public void  sendPtFh(String taskNo, String code, String num){
		 Map map = new HashMap();
		map.put("OrderID", taskNo);
		map.put("LogisticsNum", num);
		map.put("LogisticsCode", code);

		String json =OrderStatic.lxdpost(OrderStatic.SendGoods, map);
		log.error(map.toString()+"通知平台发货结果"+json);
		SendsGoodsData orderReturn=JSON.parseObject(json, SendsGoodsData.class);
		List<TransferData> orderReturns=orderReturn.getResult();
		for (TransferData transferData : orderReturns
				) {
			List<TransferData.ItemBean> ibs = transferData.getItem();
			OrderDetail p=new OrderDetail();
			p.setTaskNo(transferData.getOrderID());
			p.setProductNo(transferData.getItemCode());
			p.setPriceSum(transferData.getAmount());
			OrderDetail tl = orderDetailDao.findFirst(p);
			if (null == tl) {
				continue;
			}
			for (TransferData.ItemBean ib : ibs
					) {
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				TaskLineMoney tm = new TaskLineMoney();
				tm.setId(id);
				tm.setLineId(tl.getId());
				tm.setAmount(ib.getAmount());
				tm.setProportion(ib.getProportion());
				tm.setTypename(ib.getTypeName());
				tm.setUsertype(ib.getUserType());
				tm.setUserid(ib.getUserID());
				tm.setName(ib.getName());
				taskLineMoneyDao.insert(tm);

			}
		}

	}
	/**
	 * Json方式  物流信息订阅
	 * @throws Exception
	 */
	public void orderTracesSubByJson(Order order,int type) throws Exception{
		String EBusinessID= Global.getConfig("express.EBusinessID");
		String AppKey= Global.getConfig("express.AppKey");
		String ReqURL= Global.getConfig("express.ReqSubscribeURL");
		String[] cs=order.getCarriers().split("\\s+");
		PoolExpress pe=get(order.getRemark());
		String ShipperCode=pe.getAbbr();
		String CustomerName=pe.getAccount();
		String CustomerPwd=pe.getPassword();
		String MonthCode=pe.getMonthcode();
		String SendSite=pe.getSendsite();
		String TemplateSize=pe.getTemplatesize();
		Order order2=orderDao.get(order.getId());
		String code=order.getTaskNo();//.replace("LD20","");
		if(type==2){
			int csl=order2.getCarriers().split("\\|").length+1;
			code=code+"_"+csl;
		}
		//String code=order.getTaskNo().replace("LD20","");
		String payType=pe.getPaytype();




		String details="";
		String addrsss[]=order.getPreSendAddress().split("，");
		List<OrderDetail> ods=order.getOrderDetailList();
		int q=0;
		for (OrderDetail od:ods
		) { /*
			details+="{ 'GoodsName':'"+od.getName()+"','Goodsquantity':1,'GoodsWeight':''}";*/
			q=q+od.getAmount();
		}
		details+="{ 'GoodsName':'护理用品','Goodsquantity':"+q+",'GoodsWeight':'"+order.getWeight()+"'}";
		String requestData="{'OrderCode': '"+code+"'," +
				"'ShipperCode':'"+ShipperCode+"'," +
				"'CustomerName':'"+CustomerName+"'," +
				"'CustomerPwd':'"+CustomerPwd+"'," +
				"'SendSite':'"+SendSite+"'," +
				"'MonthCode':'"+MonthCode+"'," +
				"'PayType':"+payType+"," +
				"'LogisticCode':'"+cs[1]+"'," +
				"'ExpType':1," +
				"'IsNotice':0," +
				"'Cost':1.0," +
				"'OtherCost':1.0," +
				"'Sender':" +
				"{" +
				"'Company':'','Name':'"+addrsss[0]+"','Mobile':'"+addrsss[1]+"','ProvinceName':'"+addrsss[2]+"','CityName':'"+addrsss[3]+"','ExpAreaName':'"+addrsss[4]+"','Address':'"+addrsss[5]+"'}," +
				"'Receiver':" +
				"{" +
				"'Company':'','Name':'"+order.getConsigneeName()+"','Mobile':'"+order.getConsigneePhone()+"','ProvinceName':'"+order.getProvince().getName()+"'," +
				"'CityName':'"+order.getCity().getName()+"','ExpAreaName':'"+order.getCounty().getName()+"','Address':'"+order.getAddressDetail()+"'}," +
				"'Commodity':" +
				"[" +
				details +
				"]," +

				"'Weight':"+order.getWeight()+"," +
				"'Quantity':1," +
				"'Volume':0.0," +
				"'Remark':'小心轻放'}";

		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1008");
		String dataSign=encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");

		String result=sendPost(ReqURL, params);


	}

	private   String encrpy(String content, String key) throws UnsupportedEncodingException, Exception {
		String charset = "UTF-8";
		return new String(com.sun.xml.internal.messaging.saaj.util.Base64.encode(md5(content + key, charset).getBytes(charset)));
	}
	private String md5(String str, String charset) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes(charset));
		byte[] result = md.digest();
		StringBuffer sb = new StringBuffer(32);
		for (int i = 0; i < result.length; i++) {
			int val = result[i] & 0xff;
			if (val <= 0xf) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(val));
		}
		return sb.toString().toLowerCase();
	}
	public PrintData print(Order order,String ip) throws Exception {
		/*String ip=Global.getConfig("express.ip");
		String name=Global.getConfig("express.name");
		PrintData pd=new PrintData();
		String data = "[{\"OrderCode\":"+order.getTaskNo().replace("LD","")+",\"PortName\":\"电子面单打印机\"}]";*/
		//String ip=Global.getConfig("express.ip");
		String name=Global.getConfig("express.name");
		PrintData pd=new PrintData();
		String data = "[{\"OrderCode\":"+order.getTaskNo().replace("LD20","")+",\"PortName\":\""+name+"\"}]";
		String EBusinessID= Global.getConfig("express.EBusinessID");
		String AppKey= Global.getConfig("express.AppKey");
		pd.setEbusinessID(EBusinessID);
		pd.setRequestData(URLEncoder.encode(data, "UTF-8"));
		pd.setDataSign(encrpy(ip + data, AppKey));
		pd.setIsPreview("1");
		/*String result = "{\"RequestData\": \"" + URLEncoder.encode(data, "UTF-8") + "\", \"EBusinessID\":\"" + EBussinessID + "\", \"DataSign\":\"" + encrpy(ip + data, AppKey) + "\", \"IsPreview\":\""
				+ IsPreview + "\"}";*/

		return pd;
	}
	@Autowired
	private LogisticService logisticService;
	@Transactional(readOnly = false)
	public SearchData getOrderTracesByJson(String expCode, String expNo) throws Exception{
		Logistic lg=new Logistic();
		lg.setLogisticcode(expNo);
		List<Logistic> lgs=logisticService.findList(lg);
		if(null!=lgs&&lgs.size()>0){
			String result=lgs.get(0).getContent();
			//根据公司业务处理返回的信息......
			SearchData orderReturn= JSON.parseObject(result, SearchData.class);
			return orderReturn;
		}
		String EBusinessID= Global.getConfig("express.EBusinessID");
		String AppKey= Global.getConfig("express.AppKey");
		String requestData= "{'OrderCode':'','ShipperCode':'" + expCode + "','LogisticCode':'" + expNo + "'}";

		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1002");
		String dataSign=encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");
		String ReqURL= Global.getConfig("express.ReqSearchURL");
		String result=sendPost(ReqURL, params);
		lg.setContent(result);
		lg.setUpdateDate(new Date());
		logisticService.save(lg);
		//根据公司业务处理返回的信息......
		SearchData orderReturn= JSON.parseObject(result, SearchData.class);
		return orderReturn;
	}
@Transactional(readOnly = false)
	public PrintData allprint(List<Order> orders, String ip) throws Exception {
		String data="[";

		String name=Global.getConfig("express.name");
		PrintData pd=new PrintData();
		for (Order order:orders
				) {
     if(!"5".equals(order.getOmsstatus())) {
		 order.setOmsstatus("3");
		 orderDao.updateomsstatus(order);
	 }
			String code=order.getTaskNo();//.replace("LD20","");

				int csl=order.getCarriers().split("\\|").length;
			data += "{\"OrderCode\":\""+code+"\",\"PortName\":\""+name+"\"},";
				if(csl>1){
					for (int i = 2; i <=csl ; i++) {
						data += "{\"OrderCode\":\""+code+"_"+i+"\",\"PortName\":\""+name+"\"},";
					}
				}
		}
		data=data.substring(0,data.length()-1);
		data+="]";
		String EBusinessID= Global.getConfig("express.EBusinessID");
		String AppKey= Global.getConfig("express.AppKey");
		pd.setEbusinessID(EBusinessID);
		pd.setRequestData(URLEncoder.encode(data, "UTF-8"));
		pd.setDataSign(encrpy(ip + data, AppKey));
		pd.setIsPreview("1");
		pd.setRequestData2(data);
		/*String result = "{\"RequestData\": \"" + URLEncoder.encode(data, "UTF-8") + "\", \"EBusinessID\":\"" + EBussinessID + "\", \"DataSign\":\"" + encrpy(ip + data, AppKey) + "\", \"IsPreview\":\""
				+ IsPreview + "\"}";*/

		return pd;
	}
}