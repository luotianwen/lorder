package com.thinkgem.jeesite.modules.order.entity;


public class SkuNumber {

    private String itemCode;
    private String name;
    private int stock;
    private int order;
    private int product;
    private int collocation;
    private int promotion;
    private int productSKU;
    private int promotionSKU;
    private int colloPromotion;
    private int omsstock;
    private int omssapstock;
    private int laststock;

    public int getOmssapstock() {
        return omssapstock;
    }

    public void setOmssapstock(int omssapstock) {
        this.omssapstock = omssapstock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getCollocation() {
        return collocation;
    }

    public void setCollocation(int collocation) {
        this.collocation = collocation;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public int getProductSKU() {
        return productSKU;
    }

    public void setProductSKU(int productSKU) {
        this.productSKU = productSKU;
    }

    public int getPromotionSKU() {
        return promotionSKU;
    }

    public void setPromotionSKU(int promotionSKU) {
        this.promotionSKU = promotionSKU;
    }

    public int getColloPromotion() {
        return colloPromotion;
    }

    public void setColloPromotion(int colloPromotion) {
        this.colloPromotion = colloPromotion;
    }

    public int getOmsstock() {
        return omsstock;
    }

    public void setOmsstock(int omsstock) {
        this.omsstock = omsstock;
    }

    public int getLaststock() {
        return laststock;
    }

    public void setLaststock(int laststock) {
        this.laststock = laststock;
    }
}
