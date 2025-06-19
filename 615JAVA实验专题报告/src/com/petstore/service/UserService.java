package com.petstore.service;

import com.petstore.dao.UserDAO;
import com.petstore.entity.User;

/**
 * 用户服务类
 */
public class UserService {
    private UserDAO userDAO = new UserDAO();

    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @return 注册成功返回true，失败返回false
     */
    public boolean register(String username, String password) {
        // 检查用户名和密码是否合法
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            System.out.println("用户名和密码不能为空！");
            return false;
        }

        // 创建用户对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setGold(500); // 普通用户初始金币500
        user.setAdmin(false); // 普通用户不是管理员

        return userDAO.register(user);
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户对象，失败返回null
     */
    public User login(String username, String password) {
        return userDAO.login(username, password);
    }

    /**
     * 更新用户信息
     * @param user 用户对象
     * @return 更新成功返回true，失败返回false
     */
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }
}