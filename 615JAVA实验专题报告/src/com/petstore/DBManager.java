package com.petstore;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * 数据库连接管理类，使用连接池管理数据库连接
 */
public class DBManager {
    private static String url;
    private static String username;
    private static String password;
    private static int initialSize;
    private static int maxActive;

    // 静态代码块，加载数据库配置
    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("database");
            url = bundle.getString("jdbc.url");
            username = bundle.getString("jdbc.username");
            password = bundle.getString("jdbc.password");
            initialSize = Integer.parseInt(bundle.getString("jdbc.initialSize"));
            maxActive = Integer.parseInt(bundle.getString("jdbc.maxActive"));
            Class.forName(bundle.getString("jdbc.driver"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据库初始化失败", e);
        }
    }

    /**
     * 获取数据库连接
     * @return 数据库连接对象
     * @throws SQLException 数据库操作异常
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 关闭数据库资源
     * @param conn 数据库连接
     * @param stmt Statement对象
     * @param rs 结果集
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}