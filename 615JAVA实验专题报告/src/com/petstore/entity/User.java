package com.petstore.entity;

/**
 * 用户实体类
 */
public class User {
    private int id;
    private String username;
    private String password;
    private int gold;
    private boolean isAdmin;

    // 构造方法、Getter和Setter方法
    public User() {}

    public User(String username, String password, int gold, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.gold = gold;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "用户ID: " + id +
                ", 用户名: '" + username + '\'' +
                ", 金币: " + gold +
                ", 是否管理员: " + isAdmin;
    }
}