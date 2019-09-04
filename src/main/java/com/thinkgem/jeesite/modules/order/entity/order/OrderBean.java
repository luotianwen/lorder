package com.thinkgem.jeesite.modules.order.entity.order;

public class OrderBean {
      /* OrderCode : 012657700387
            * ShipperCode : HTKY
         * LogisticCode : 50002498503427
            * MarkDestination : 京-朝阳(京-1)
         * OriginCode : 200000
            * OriginName : 上海分拨中心
         * PackageCode : 北京
         */

    private String orderCode;
    private String shipperCode;
    private String logisticCode;
    private String markDestination;
    private String originCode;
    private String originName;
    private String packageCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }

    public String getLogisticCode() {
        return logisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        this.logisticCode = logisticCode;
    }

    public String getMarkDestination() {
        return markDestination;
    }

    public void setMarkDestination(String markDestination) {
        this.markDestination = markDestination;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }
}
