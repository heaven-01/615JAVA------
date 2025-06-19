package com.petstore.dao;

import com.petstore.DBManager;
import com.petstore.entity.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 道具数据访问对象
 */
public class ItemDAO {

    /**
     * 获取所有道具信息（包含分类名称）
     * @return 道具列表
     */
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getConnection();
            String sql = "SELECT i.*, c.name as category_name " +
                    "FROM items i " +
                    "JOIN item_categories c ON i.category_id = c.id";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setCategoryName(rs.getString("category_name"));
                item.setPrice(rs.getInt("price"));
                item.setHealthEffect(rs.getInt("health_effect"));
                item.setSatietyEffect(rs.getInt("satiety_effect"));
                item.setEnergyEffect(rs.getInt("energy_effect"));
                item.setDescription(rs.getString("description"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, stmt, rs);
        }

        return items;
    }

    /**
     * 购买道具
     * @param itemId 道具ID
     * @param userId 用户ID
     * @param quantity 购买数量
     * @return 购买成功返回true，失败返回false
     */
    public boolean buyItem(int itemId, int userId, int quantity) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getConnection();
            // 开始事务
            conn.setAutoCommit(false);

            // 获取道具信息
            String itemSql = "SELECT * FROM items WHERE id = ? FOR UPDATE";
            stmt = conn.prepareStatement(itemSql);
            stmt.setInt(1, itemId);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                conn.rollback();
                return false;
            }

            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setPrice(rs.getInt("price"));

            // 计算总价
            int totalPrice = item.getPrice() * quantity;

            // 获取用户信息
            String userSql = "SELECT gold FROM users WHERE id = ? FOR UPDATE";
            stmt = conn.prepareStatement(userSql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                conn.rollback();
                return false;
            }

            int userGold = rs.getInt("gold");
            if (userGold < totalPrice) {
                conn.rollback();
                return false;
            }

            // 更新用户金币
            String updateUserSql = "UPDATE users SET gold = gold - ? WHERE id = ?";
            stmt = conn.prepareStatement(updateUserSql);
            stmt.setInt(1, totalPrice);
            stmt.setInt(2, userId);
            int userRows = stmt.executeUpdate();

            // 更新用户道具数量
            String checkUserItemSql = "SELECT id, quantity FROM user_items WHERE user_id = ? AND item_id = ? FOR UPDATE";
            stmt = conn.prepareStatement(checkUserItemSql);
            stmt.setInt(1, userId);
            stmt.setInt(2, itemId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // 用户已有该道具，更新数量
                int currentQuantity = rs.getInt("quantity");
                String updateUserItemSql = "UPDATE user_items SET quantity = ? WHERE id = ?";
                stmt = conn.prepareStatement(updateUserItemSql);
                stmt.setInt(1, currentQuantity + quantity);
                stmt.setInt(2, rs.getInt("id"));
                stmt.executeUpdate();
            } else {
                // 用户没有该道具，插入新记录
                String insertUserItemSql = "INSERT INTO user_items (user_id, item_id, quantity) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(insertUserItemSql);
                stmt.setInt(1, userId);
                stmt.setInt(2, itemId);
                stmt.setInt(3, quantity);
                stmt.executeUpdate();
            }

            if (userRows > 0) {
                conn.commit();
                return true;
            }

            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.close(conn, stmt, rs);
        }

        return false;
    }

    /**
     * 获取用户拥有的道具
     * @param userId 用户ID
     * @return 用户拥有的道具列表
     */
    public List<Item> getUserItems(int userId) {
        List<Item> items = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getConnection();
            String sql = "SELECT i.*, c.name as category_name, ui.quantity " +
                    "FROM user_items ui " +
                    "JOIN items i ON ui.item_id = i.id " +
                    "JOIN item_categories c ON i.category_id = c.id " +
                    "WHERE ui.user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCategoryId(rs.getInt("category_id"));
                item.setCategoryName(rs.getString("category_name"));
                item.setPrice(rs.getInt("price"));
                item.setHealthEffect(rs.getInt("health_effect"));
                item.setSatietyEffect(rs.getInt("satiety_effect"));
                item.setEnergyEffect(rs.getInt("energy_effect"));
                item.setDescription(rs.getString("description"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, stmt, rs);
        }

        return items;
    }

    /**
     * 使用道具
     * @param itemId 道具ID
     * @param userId 用户ID
     * @return 使用成功返回true，失败返回false
     */
    public boolean useItem(int itemId, int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getConnection();
            // 开始事务
            conn.setAutoCommit(false);

            // 检查用户是否拥有该道具
            String checkSql = "SELECT id, quantity FROM user_items WHERE user_id = ? AND item_id = ? FOR UPDATE";
            stmt = conn.prepareStatement(checkSql);
            stmt.setInt(1, userId);
            stmt.setInt(2, itemId);
            rs = stmt.executeQuery();

            if (!rs.next() || rs.getInt("quantity") <= 0) {
                conn.rollback();
                return false;
            }

            int userItemId = rs.getInt("id");
            int quantity = rs.getInt("quantity");

            // 更新道具数量
            if (quantity > 1) {
                String updateSql = "UPDATE user_items SET quantity = quantity - 1 WHERE id = ?";
                stmt = conn.prepareStatement(updateSql);
                stmt.setInt(1, userItemId);
                stmt.executeUpdate();
            } else {
                String deleteSql = "DELETE FROM user_items WHERE id = ?";
                stmt = conn.prepareStatement(deleteSql);
                stmt.setInt(1, userItemId);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.close(conn, stmt, rs);
        }

        return false;
    }
}