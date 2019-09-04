package com.thinkgem.jeesite.modules.order.entity.express;

import com.thinkgem.jeesite.modules.order.entity.order.OrderBean;

public class OrderReturn {


    /**
     * EBusinessID : 1237100
     * Order : {"OrderCode":"012657700387","ShipperCode":"HTKY","LogisticCode":"50002498503427","MarkDestination":"京-朝阳(京-1)","OriginCode":"200000","OriginName":"上海分拨中心","PackageCode":"北京"}
     * PrintTemplate : 此处省略打印模板HTML内容
     * EstimatedDeliveryTime : 2016-03-06
     * Success : true
     * ResultCode : 100
     * Reason : 成功
     */

    private String ebusinessID;
    private OrderBean order;
    private String printTemplate;
    private String estimatedDeliveryTime;
    private boolean success;
    private String resultCode;
    private String reason;

    public String getEbusinessID() {
        return ebusinessID;
    }

    public void setEbusinessID(String ebusinessID) {
        this.ebusinessID = ebusinessID;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public String getPrintTemplate() {
        return printTemplate;
    }

    public void setPrintTemplate(String printTemplate) {
        this.printTemplate = printTemplate;
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
