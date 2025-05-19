package com.example.tubespboo.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orderDetails")

public class OrderDetails {
    private int orderId;
    private int payId;
    private int deliverId;
    private String payMethod;
    private Date payDate;
    private String deliveryAddress;
    private String trackingNumber;
    private String status;
    private String deliveryStatus;

    public OrderDetails() {}

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public int getDeliverId() {
        return deliverId;
    }

     public void setDeliverId(int deliverId) {
        this.deliverId = deliverId;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

     public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public void showOrderDetail() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Pay Method: " + payMethod);
        System.out.println("Pay Date: " + payDate);
        System.out.println("Delivery Address: " + deliveryAddress);
        System.out.println("Tracking Number: " + trackingNumber);
        System.out.println("Status: " + status);
        System.out.println("Delivery Status: " + deliveryStatus);
    }
    

}