package com.petstore.entity;

/**
 * 宠物实体类
 */
public class Pet {
    private int id;
    private String name;
    private String type;
    private int health;
    private int satiety;
    private int energy;
    private int userId;
    private boolean isFree;
    private int price;
    private int stock;
    private String description;

    // 构造方法、Getter和Setter方法
    public Pet() {}

    public Pet(String name, String type, int health, int satiety, int energy,
               int userId, boolean isFree, int price, int stock, String description) {
        this.name = name;
        this.type = type;
        this.health = health;
        this.satiety = satiety;
        this.energy = energy;
        this.userId = userId;
        this.isFree = isFree;
        this.price = price;
        this.stock = stock;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        // 健康值范围限制
        this.health = Math.max(0, Math.min(100, health));
    }

    public int getSatiety() {
        return satiety;
    }

    public void setSatiety(int satiety) {
        // 饱腹值范围限制
        this.satiety = Math.max(0, Math.min(100, satiety));
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        // 活力值范围限制
        this.energy = Math.max(0, Math.min(100, energy));
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "宠物ID: " + id +
                ", 名字: '" + name + '\'' +
                ", 类型: '" + type + '\'' +
                ", 健康值: " + health +
                ", 饱腹值: " + satiety +
                ", 活力值: " + energy +
                ", 所属用户ID: " + userId +
                ", 是否免费: " + isFree +
                ", 价格: " + price +
                ", 库存: " + stock +
                ", 描述: '" + description + '\'';
    }

    /**
     * 获取宠物状态描述
     * @return 宠物状态描述
     */
    public String getStatus() {
        StringBuilder status = new StringBuilder();
        status.append(name).append("的状态：\n");

        // 根据健康值判断状态
        if (health <= 20) {
            status.append("健康：很差（需要治疗）\n");
        } else if (health <= 50) {
            status.append("健康：一般（需要照顾）\n");
        } else if (health <= 80) {
            status.append("健康：良好\n");
        } else {
            status.append("健康：极佳\n");
        }

        // 根据饱腹值判断状态
        if (satiety <= 20) {
            status.append("饱腹：非常饿（急需食物）\n");
        } else if (satiety <= 50) {
            status.append("饱腹：有点饿\n");
        } else if (satiety <= 80) {
            status.append("饱腹：一般\n");
        } else {
            status.append("饱腹：很饱\n");
        }

        // 根据活力值判断状态
        if (energy <= 20) {
            status.append("活力：很低（需要休息）\n");
        } else if (energy <= 50) {
            status.append("活力：一般\n");
        } else if (energy <= 80) {
            status.append("活力：良好\n");
        } else {
            status.append("活力：充沛\n");
        }

        return status.toString();
    }
}