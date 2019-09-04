/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service.express;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.order.entity.express.OrderReturn;
import com.thinkgem.jeesite.modules.order.entity.express.PrintData;
import com.thinkgem.jeesite.modules.order.entity.order.Order;
import com.thinkgem.jeesite.modules.order.entity.order.OrderDetail;
import com.thinkgem.jeesite.modules.order.service.order.OrderService;
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
    public OrderReturn   express(Order order) throws Exception {
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
		String code=order.getTaskNo();


		String details="";

		List<OrderDetail> ods=order.getOrderDetailList();
		for (OrderDetail od:ods
			 ) {
			details+="{ 'GoodsName':'"+od.getName()+"','Goodsquantity':1,'GoodsWeight':1.0}";
		}
		String requestData= "{'OrderCode':'"+code+"'," +
				"'ShipperCode':'"+ShipperCode+"'," +
				"'CustomerName':'"+CustomerName+"'," +
				"'CustomerPwd':'"+CustomerPwd+"'," +
				"'SendSite':'"+SendSite+"'," +
				"'MonthCode':'"+MonthCode+"'," +

				"'PayType':1," +
				"'ExpType':1," +
				"'Cost':1.0," +
				"'OtherCost':1.0," +
				"'Sender':" +
				"{" +
				"'Company':'LV','Name':'Taylor','Mobile':'15018442396','ProvinceName':'上海','CityName':'上海','ExpAreaName':'青浦区','Address':'明珠路73号'}," +
				"'Receiver':" +
				"{" +
				"'Company':'','Name':'"+order.getConsigneeName()+"','Mobile':'"+order.getConsigneePhone()+"','ProvinceName':'"+order.getProvince().getName()+"'," +
				"'CityName':'"+order.getCity().getName()+"','ExpAreaName':'"+order.getCounty().getName()+"','Address':'"+order.getAddressDetail()+"'}," +
				"'Commodity':" +
				"[" +
				details +
				"]," +
				"'Weight':1.0," +
				"'Quantity':1," +
				"'Volume':0.0," +
				"'TemplateSize':"+TemplateSize+"," +

				"'Remark':'小心轻放'," +
				"'IsReturnPrintTemplate':1}";
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
			return orderReturn;
		}
	   else{
			throw new Exception(orderReturn.getReason());
		}

    }

	public static void main(String[] args) {
		String result="{  \"Order\" : {    \"MarkDestination\" : \"113 C107 M07 \",    \"LogisticCode\" : \"772002014605245\",    \"ShipperCode\" : \"STO\",    \"PackageName\" : \"北京顺义集散包 \",    \"OrderCode\" : \"LD201908291008041459474\",    \"KDNOrderCode\" : \"KDN1909042250000548\"  },  \"PrintTemplate\" : \"<html> <head>   <meta http-equiv=\\\"Content-Type\\\" content=\\\"text/html; charset=UTF-8\\\" />   <style>.item {        position: absolute;    }    .small-font{        font-size:12px;        -webkit-transform-origin-x: 0;        -webkit-transform: scale(0.70);    }\\t.font28{\\t  font-size:28.6667px;\\t}\\t.font30{\\t  font-size:30px;\\t}\\t.font21{\\t  font-size:21.3333px;\\t}    .smallsize-font {        　　　　font-size:8.4px;    }    </style>  </head>  <body>   <div class=\\\"item vline\\\" style=\\\"left:1px;top:1px;height:676px;width:0;border-left:1px solid #000;\\\"></div>   <div class=\\\"item hline\\\" style=\\\"left:1px;top:1px;width:373px;height:0;border-top:1px solid #000\\\"></div>   <div class=\\\"item hline\\\" style=\\\"left:1px;top:677px;width:373px;height:0;border-top:1px solid #000\\\"></div>   <div class=\\\"item vline\\\" style=\\\"left:373px;top:1px;height:676px;width:0;border-left:1px solid #000;\\\"></div>   <div class=\\\"item hline\\\" style=\\\"left:1px;top:34px;width:372px;height:0;border-top:1px solid #000\\\"></div>   <div class=\\\"item hline\\\" style=\\\"left:1px;top:91px;width:372px;height:0;border-top:1px solid #000\\\"></div>   <div class=\\\"item hline\\\" style=\\\"left:1px;top:128px;width:373px;height:0;border-top:1px solid #000\\\"></div>   <div class=\\\"item hline\\\" style=\\\"left:-2px;top:191px;width:287px;height:0;border-top:1px solid #000\\\"></div>   <div class=\\\"item hline\\\" style=\\\"left:1px;top:242px;width:374px;height:0;border-top:1px solid #000\\\"></div>   <div class=\\\"item hline\\\" style=\\\"left:1px;top:336px;width:371px;height:0;border-top:1px solid #000\\\"></div>   <div class=\\\"item hline\\\" style=\\\"left:2px;top:408px;width:372px;height:0;border-top:1px solid #000\\\"></div>   <div class=\\\"item hline\\\" style=\\\"left:1px;top:468px;width:372px;height:0;border-top:1px solid #000\\\"></div>   <div class=\\\"item hline\\\" style=\\\"left:1px;top:506px;width:372px;height:0;border-top:1px solid #000\\\"></div>   <div class=\\\"item hline\\\" style=\\\"left:1px;top:551px;width:372px;height:0;border-top:1px solid #000\\\"></div>   <div class=\\\"item vline\\\" style=\\\"left:28px;top:128px;height:114px;width:0;border-left:1px solid #000;\\\"></div>   <div class=\\\"item vline\\\" style=\\\"left:287px;top:128px;height:114px;width:0;border-left:1px solid #000;\\\"></div>   <div class=\\\"item hline\\\" style=\\\"left:287.5px;top:147px;width:85px;height:0;border-top:1px solid #000\\\"></div>   <div class=\\\"item vline\\\" style=\\\"left:179px;top:336px;height:72px;width:0;border-left:1px solid #000;\\\"></div>   <div class=\\\"item vline\\\" style=\\\"left:36px;top:468px;height:83px;width:0;border-left:1px solid #000;\\\"></div>   <img class=\\\"item image\\\" style=\\\"left:3.5px;top:2px;width:108px;height:32px;\\\" src=\\\"data:image/jpeg;base64,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\\\" />   <div class=\\\"item text font28\\\" style=\\\"left:2px;top:39px;width:368px;height:47px;font-family:'微软雅黑';font-size:28.6667px;font-weight:400;overflow:hidden;\\\">113 C107 M07 </div>   <div class=\\\"item text font21\\\" style=\\\"left:3px;top:94px;width:283px;height:32px;font-family:'微软雅黑';font-size:21.3333px;font-weight:400;overflow:hidden;\\\">北京顺义集散包 </div>   <div class=\\\"item text\\\" style=\\\"left:6.5px;top:200px;width:15px;height:37px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:visible;\\\">    寄件   </div>   <img class=\\\"item image\\\" style=\\\"left:4.5px;top:418px;width:117px;height:34px;\\\" src=\\\"data:image/jpeg;base64,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\\\" />   <div class=\\\"item text\\\" style=\\\"left:118.5px;top:307px;width:143px;height:20px;font-family:'微软雅黑';font-size:13.3333px;font-weight:400;overflow:visible;\\\">    772002014605245   </div>   <div class=\\\"item text\\\" style=\\\"left:201.5px;top:451px;width:143px;height:20px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:visible;\\\">    772002014605245   </div>   <div class=\\\"item text\\\" style=\\\"left:12.5px;top:469px;width:14px;height:35px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:visible;\\\">    寄件   </div>   <div class=\\\"item text\\\" style=\\\"left: 31px;top:132px;width:258px;height:22px;font-family:'微软雅黑';font-size:14px;font-weight:400;overflow:hidden;\\\">     张帅伟 &nbsp;&nbsp; 13020831303 &nbsp;&nbsp;    </div>   <img class=\\\"item image\\\" style=\\\"left: 65.5px;top: 253px;width: 230px;height: 55px;\\\" src=\\\"data:image/gif;base64,R0lGODlh+gA3APAAAAAAAP///ywAAAAA+gA3AEAI/wADCBxIsKDBgwgTKhwIAEAAhw8bCmwokeLDiBMnVoQoEWPHjRktaryYEWNEhxw5nkQJUmTKkR8hnrzYcaHNmzhz6typs6LGlBtRmgQ69GXMoTNhMhw58yVNi1BZqixK9edTnlizat3aU2hSilKvkozKFKRHlSI9Li3q9ePKpmHHGnXa1CTXu3jzdpULNCzLkmSpHnWb1idgok6l+o2b+K9dqHb1Sp482bDLqELdwi3r2GxctYetJn5L1iXnqnVrUl7NOqvlmEHFIm4sGG1Nw3JFlyXdUvNgupBVtx5OXOHrvqmnBv4N07ZM3I3bOl/89fRR3cKLa99+/KdfpJtrN/8Pedtr7rpIwW42Lf6xz+zb47fuzjuzcs9zxz99bj76ecXr+Zafe/bJZyBx9KkX3H2dDehZUqD99xp5scUmIWoLHqjhagliJptotDG3H0P9cSYdhdSxJ2J5BW7oYl4dVgjecg52BiF0Jv5X31n36ceiWC8GqVWM3wnYoI82FlYiWzoq2FuPZzGVnJBUYkXklHxVhV965ZEk4Yn7pSggkiS2WOWZN12ZYZbXbUlYl8/lOJqTf6lYY5lAoqknQmq+x6CWR77Jn5f+zemhhSEC5+eejB7UZ4uzWefgjUvC1uShjEkq5ZqNdgraZTIaCWh7n+HIpKEVZkoqf2Z6yuijH4b/t2KScK5lKapiQrkldjK5uiesNsp6p6AkEirnbnTyGFqUBObpK5rAzrirm84VG+epyGJa3ap4OvasntGK2mag1Sq1LGRcprrtrN1G9m2V4f45LqmUGotturmeeySv74IrHXJrRsotseZ+eam6dpLJb79nxnvYvtRSaK2tiGWL8JjM/ugtw0I6zGaiPtZ77a0W53vhdVhy3PG/3mEpMLsER1hoyQHqum/KKr/o8cvDlivzsfjWrC+Gi+Yc5M4gapqxyBTrFnRpGO+Ks9EaIi0smTGbSvLTTw6NMqdUV83yjsHSiLXPWlfMdZ1R3wx22AZabXbGtA46stqCmgxysxvD/y2f3NOSK3HBM6+t7MmKtup3fIBDLPiIhAOdt9CIb1r04n+PnWzZgdOrpL1bTw61zUQrjnlxjY+6ItP6ggng6F4n7uzpCGp+qLSOe15r6wfrrbTG7tI+XOrzrv753U6L3nXlfAcvPGvE7/3g8U2jpzzbpH99+fPD2x6qvNLXPTHvuFIefrvwcV+Z90WC/7v4kd97/eHnL6z+fOy7nPTAaFeK93Tmex/6enU/ykRPgFnzX/IACDvmAS99BcTLAfk3uJ/Jj4HLq9/UIqiXCcKsf6D7H4oCyC37cVAyHuxZBdO2wBE2UINvO6EE8xew/X1whQq0HgaxFzvLmU6GXEnh2YJwGMIWhomE7DIhEO8iRLqla3wGK98LBajEJW6liRGDnAVDt0P6UXGDVnQNDYvGsyFqkYU6dGEGvxjDMFppjJCyoQrPmEN0zQ9RbNyeG8WYJbLhTnWToh75aDbFEoJxj3shih/Fdb4EFjGNRyxkEg+JSJxg8XGlquOEIrlGQ7bxTAEBADs=\\\" />   <img class=\\\"item image\\\" style=\\\"left: 168.5px;top: 422px;width: 150px;height: 30px;\\\" src=\\\"data:image/gif;base64,R0lGODlhlgAeAPAAAAAAAP///ywAAAAAlgAeAEAI/wADCBwoEACAAAcRGixo8GBDhAoLMnSYcGFEiwsxKkx4cWDFjw4ZSmxIkeJGiREvfvSYMSXBly9bntxYEqLGkxhX5kxZUyTHlThF2nxItCfQjECDWoTJtONMkkorTuSps+rIpTIhUg3JkyZOo0KRCo3atKnMh16Jdv0a1qTKqz+5as3JVSPUok/bUmUZcmlZgmfp1nQLcu/bwz2dzi18NK1YtEcJx+3L8S/LxSAHr8W7d6ddrHIj15V69/FNz0nV+rXsFHJRyig5o/ZJGrTWxVMblzYJWe/O3C5ZtxZMFrNhsYhnKhaNeyhS3qetulRdmXXgzMUZR+5cO+5t5rpfq/+Uyn0sdeGXCzuGbfw38vfKs4IPu358bOmg2aO/PlFzdMm0Behceh61NRpcg4FV3nQtrWYZf/URltt2sw3oU4FbNVdSgnktmN9Q6F34lHgSsuWhhGgtZ6CGESpY4Ydu7Tcadue1RyF+8YW2YnjP2achgMA5+BeEu4Foo284pijfjvQV2eOPhhUno3ERbvYefija9hOTXTnpIpZ8GTmlek5aCeSVXWqJIV0setnhi5OJKRyRJJp5HICfebdlhjxy2NuJYcY454z9ZTchkgImtiSfTZL4ZaJxCmodoVX+d6eHOX7HZZ5+RgdpoMFNSmWZlrqHZ3cE3gdcmj0+eaN5DVan9yClpJInG5h5pvrjgc51aiuYQco6JK11lvpqckrqyCirvqqKWKShzjpqsb+eeSpcus7HLFt/wgmqkGXR2aqdpmKarKbLcsqtp89+K2y4xI5rLKLIqumsWtvi1S2wUg46rbzVXvpipnuy2ee6v34abIjD0Rjrfdaaa++ubTr6Jr81jjlVpQGXO/C5BTPWaKuPtrtwiOL61/Gx8IG8psj5mpawyf0SFBAAOw==\\\" />   <img class=\\\"item image\\\" style=\\\"left: 287px;top: 100px;width:82px;height: 18px;\\\" src=\\\"data:image/gif;base64,R0lGODlhhwASAPAAAAAAAP///ywAAAAAhwASAEAI/wABAAgwkKDAAAYPChzIsKBDhQ8RQpTYUGJChAYJYox4kWNDiBUXWsyY8SDJjiUZUnwIUqNJliRfVkxpUSRFjSNlqoyZ8GNKhzdpnhQZcqfNhT5nwpR5k+nLi003BiU6tWdHnzidckQ5EaZVqk+vTlWqFGpJqTEVVkUq9ujYtBvVdl25MilPsXC11jR5NufStUS7htU7VG5Iul8N+yXbNOJTm32z/uXJNjBUj4zpFpXM0u7giXkdA4Ws1q9mwC1bSm4clOvhy4FVfs4MurbZ0qtRUo7907RQvmBfH+VdFi9h26R35qa6OzVWzHBPz4Wd+q7l0KxdAleO2eho4s/fMp6VLlynZ6DGRWfHGrn78sRF0R8v6Lrv8Oqzo9cumxzn8tKPgXeZeFsFZ5959XGmH2MB0oebex7B19t/0dUHIIIGKjjefs3F5R+EqCUVHnaFyVZeZwkuBd1xt3E3lnd7OTcgiduZeCCKGapI4Hotfvjie5UJJp96JWp3Y10parYikew9+GOEQcbnG4fkHSlhcdfN12FUU14Yo4gzalllQAA7\\\" />       <div class=\\\"item text\\\" style=\\\"left: 31px;top:152px;width:258px;height:35px;font-family:'微软雅黑';font-size:14px;font-weight:400;overflow:hidden;\\\">    北京,北京 市辖区,市辖区 朝阳区,朝阳区 中铁国际城12号楼2单元1703室  </div>   <div  class=\\\"item text\\\"  style=\\\"left: 31px;top:191px;width:258px;height:20px;font-family:'微软雅黑';font-size:14px;font-weight:400;overflow:hidden;\\\">    Taylor&nbsp;&nbsp; 15018442396&nbsp;&nbsp;   </div>   <div class=\\\"item text\\\"  style=\\\"left:31px;top:209px;width:258px;height:35px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:hidden;\\\">    上海 上海 青浦区 明珠路73号   </div>      <div class=\\\"item text\\\" style=\\\"left: 37.5px;top:472px;width:332px;height:35px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:hidden;\\\">    Taylor&nbsp;&nbsp; 15018442396&nbsp;&nbsp;  &nbsp;&nbsp;上海 上海 青浦区 明珠路73号   </div>     <div class=\\\"item text\\\" style=\\\"left:37.5px;top:521px;width:332px;height:30px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:hidden;\\\">    北京,北京 市辖区,市辖区 朝阳区,朝阳区 中铁国际城12号楼2单元1703室  </div>       <div class=\\\"item text\\\" style=\\\"left: 37.5px;top:506px;width:332px;height:15px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:hidden;\\\">    张帅伟 &nbsp;&nbsp;13020831303&nbsp;&nbsp;    </div>   <div class=\\\"item text\\\" style=\\\"left:327.5px;top:656px;width:44px;height:18px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:visible;\\\">    已验视   </div>   <div class=\\\"item text small-font smallsize-font\\\" style=\\\"left:21px;top:330px;width:169px;height:62px;font-family:'微软雅黑';font-size:8px;font-weight:400;overflow:visible;\\\">    快件送达收件人地址，经收件人或收件人（寄件人）允许的代收人签字，视为送达。您的签字代表您已验收此包裹，并已确认商品信息无误，包装完好，没有划痕、破损等表面质量问题。   </div>   <div class=\\\"item text\\\" style=\\\"left:184px;top:361px;width:57px;height:16px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:visible;\\\">    签收人：   </div>   <div class=\\\"item text\\\" style=\\\"left:184px;top:383px;width:41px;height:19px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:visible;\\\">    时间：   </div>   <div class=\\\"item text\\\" style=\\\"left:316.5px;top:130px;width:27px;height:13px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:visible;\\\">    服务   </div>   <div class=\\\"item text\\\" style=\\\"left:4.5px;top:553px;width:365px;height:105px;font-family:'微软雅黑';font-size:13.3333px;font-weight:400;overflow:hidden;\\\">    数量：1&nbsp;&nbsp;重量：1.0kg&nbsp;&nbsp;净牌-雪莲滋养贴100片   </div>   <div class=\\\"item text font30\\\" style=\\\"left:282.5px;top:-1px;width:85px;height:32px;font-family:'微软雅黑';font-size:30px;font-weight:400;overflow:visible;\\\">    95543   </div>   <img class=\\\"item image\\\" style=\\\"left:2.5px;top:151px;width:24px;height:29px;\\\" src=\\\"data:image/png;base64,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\\\" />   <img class=\\\"item image\\\" style=\\\"left:3.5px;top:517px;width:29px;height:29px;\\\" src=\\\"data:image/png;base64,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\\\" />   <div class=\\\"item text\\\" style=\\\"left:184px;top:342px;width:68px;height:13px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:visible;\\\">    打印时间：   </div>   <div class=\\\"item text\\\" style=\\\"left:240px;top:342px;width:135px;height:16px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:visible;\\\">    2019-09-04 22:07:35   </div>   <div class=\\\"item text\\\" style=\\\"left:289.167px;top:189px;width:59px;height:20px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:visible;\\\">    数量：1   </div>   <div class=\\\"item text\\\" style=\\\"left:289.167px;top:216px;width:54px;height:19px;font-family:'微软雅黑';font-size:12px;font-weight:400;overflow:visible;\\\">    重量：   </div>   </body></html>\",  \"EBusinessID\" : \"1568694\",  \"UniquerRequestNumber\" : \"e27a9e40-0bd5-4f4d-b304-51965adc8287\",  \"ResultCode\" : \"106\",  \"Reason\" : \"该订单号已下单成功\",  \"Success\" : true}";
		 //OrderReturn orderReturn=JsonMapper.getInstance().fromJson(result, OrderReturn.class);

		/*ObjectMapper objectMapper = new ObjectMapper();
		OrderReturn orderReturn = null;
		try {
			orderReturn = objectMapper.readValue(result, OrderReturn.class);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		OrderReturn orderReturn=JSON.parseObject(result, OrderReturn.class);
		System.out.println(orderReturn.isSuccess());
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
		PrintData pd=new PrintData();
		String data = "[{\"OrderCode\":"+order.getTaskNo()+",\"PortName\":\"电子面单打印机\"}]";
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
}