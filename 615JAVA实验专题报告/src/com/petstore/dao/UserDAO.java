package com.petstore.dao;

import com.petstore.DBManager;
import com.petstore.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据访问对象
 */
public class UserDAO {

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getConnection();
            String sql = "SELECT * FROM users";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setGold(rs.getInt("gold"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, stmt, rs);
        }
        return users;
    }

    /**
     * 用户注册
     * @param user 用户对象
     * @return 注册成功返回true，失败返回false
     */
    public boolean register(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getConnection();

            // 检查用户名是否已存在
            String checkSql = "SELECT id FROM users WHERE username = ?";
            stmt = conn.prepareStatement(checkSql);
            stmt.setString(1, user.getUsername());
            rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("用户名已存在！");
                return false;
            }

            // 注册新用户
            String insertSql = "INSERT INTO users (username, password, gold, is_admin) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getGold());
            stmt.setBoolean(4, user.isAdmin());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("注册成功！");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, stmt, rs);
        }

        return false;
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 用户对象，如果登录失败返回null
     */
    public User login(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DBManager.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setGold(rs.getInt("gold"));
                user.setAdmin(rs.getBoolean("is_admin"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, stmt, rs);
        }

        return user;
    }

    /**
     * 更新用户信息
     * @param user 用户对象
     * @return 更新成功返回true，失败返回false
     */
    public boolean updateUser(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBManager.getConnection();
            String sql = "UPDATE users SET gold = ?, is_admin = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getGold());
            stmt.setBoolean(2, user.isAdmin());
            stmt.setInt(3, user.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, stmt, null);
        }

        return false;
    }
}