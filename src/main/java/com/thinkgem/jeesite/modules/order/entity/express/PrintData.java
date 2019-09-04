package com.thinkgem.jeesite.modules.order.entity.express;

public class PrintData {

    private String requestData;
    private String ebusinessID;
    private String dataSign;
    private String isPreview;

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
