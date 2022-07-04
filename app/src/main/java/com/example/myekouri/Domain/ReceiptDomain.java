package com.example.myekouri.Domain;

import java.io.Serializable;

public class ReceiptDomain {

    String ProductName;
    String ProductPrice;
    String totalQuantity;
    String currentDate;
    String currentTime;
    double totalPrice;
    String documentId;

    public ReceiptDomain() {
    }

    public ReceiptDomain(String productName, String productPrice, String totalQuantity, String currentDate, String currentTime, double totalPrice, String documentId) {
        ProductName = productName;
        ProductPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.totalPrice = totalPrice;
        this.documentId = documentId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
