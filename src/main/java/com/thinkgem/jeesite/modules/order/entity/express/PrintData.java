package com.thinkgem.jeesite.modules.order.entity.express;

public class PrintData {

    private String requestData;
    private String ebusinessID;
    private String dataSign;
    private String isPreview;
    private String requestData2;

    public String getRequestData2() {
        return requestData2;
    }

    public void setRequestData2(String requestData2) {
        this.requestData2 = requestData2;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getEbusinessID() {
        return ebusinessID;
    }

    public void setEbusinessID(String ebusinessID) {
        this.ebusinessID = ebusinessID;
    }

    public String getDataSign() {
        return dataSign;
    }

    public void setDataSign(String dataSign) {
        this.dataSign = dataSign;
    }

    public String getIsPreview() {
        return isPreview;
    }

    public void setIsPreview(String isPreview) {
        this.isPreview = isPreview;
    }
}
