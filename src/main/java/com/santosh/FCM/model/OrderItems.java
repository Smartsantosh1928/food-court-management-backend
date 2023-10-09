package com.santosh.FCM.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Document(collection = "orderItems")
public class OrderItems {
    @Id
    private String id;
    private Map<FoodItem,Integer> items;
    private String userId;
    private String menuListId;
    private OrderStatus status;
    private Date orderedAt;
    private Date paidAt;

    public OrderItems(Map<FoodItem, Integer> items, String userId, String menuListId, OrderStatus status, Date orderedAt) {
        this.items = items;
        this.userId = userId;
        this.menuListId = menuListId;
        this.status = status;
        this.orderedAt = orderedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<FoodItem, Integer> getItems() {
        return items;
    }

    public void setItems(Map<FoodItem, Integer> items) {
        this.items = items;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMenuListId() {
        return menuListId;
    }

    public void setMenuListId(String menuListId) {
        this.menuListId = menuListId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    public Date getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }
}
