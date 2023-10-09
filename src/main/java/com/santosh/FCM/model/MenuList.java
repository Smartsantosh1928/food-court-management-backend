package com.santosh.FCM.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "menuList")
public class MenuList {
    @Id
    private String id;
    private String name;
    private List<FoodItem> foodItems;
    private String createdBy;
    private boolean isActiveToday;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isActiveToday() {
        return isActiveToday;
    }

    public void setActiveToday(boolean activeToday) {
        isActiveToday = activeToday;
    }
}
