package com.petstore.entity;

/**
 * 道具实体类
 */
public class Item {
    private int id;
    private String name;
    private int categoryId;
    private int price;
    private int healthEffect;
    private int satietyEffect;
    private int energyEffect;
    private String description;
    private String categoryName; // 道具分类名称

    // 构造方法、Getter和Setter方法
    public Item() {}

    public Item(String name, int categoryId, int price, int healthEffect,
                int satietyEffect, int energyEffect, String description) {
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.healthEffect = healthEffect;
        this.satietyEffect = satietyEffect;
        this.energyEffect = energyEffect;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHealthEffect() {
        return healthEffect;
    }

    public void setHealthEffect(int healthEffect) {
        this.healthEffect = healthEffect;
    }

    public int getSatietyEffect() {
        return satietyEffect;
    }

    public void setSatietyEffect(int satietyEffect) {
        this.satietyEffect = satietyEffect;
    }

    public int getEnergyEffect() {
        return energyEffect;
    }

    public void setEnergyEffect(int energyEffect) {
        this.energyEffect = energyEffect;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "道具ID: " + id +
                ", 名称: '" + name + '\'' +
                ", 分类: " + (categoryName != null ? categoryName : categoryId) +
                ", 价格: " + price +
                ", 健康效果: " + healthEffect +
                ", 饱腹效果: " + satietyEffect +
                ", 活力效果: " + energyEffect +
                ", 描述: '" + description + '\'';
    }
}