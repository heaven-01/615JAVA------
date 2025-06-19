package com.petstore.service;

import com.petstore.dao.UserDAO;
import com.petstore.entity.User;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 金币自动增长服务：所有用户（包括管理员）金币随时间增长
 */
public class GoldTimer {
    private static final int GOLD_INCREMENT = 100;       // 每次增加的金币
    private static final long INTERVAL = 60 * 1000;     // 间隔时间（1分钟）

    private UserDAO userDAO = new UserDAO();
    private Timer timer;

    // 启动定时任务
    public void start() {
        timer = new Timer("GoldTimer", true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    updateAllUsersGold();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, INTERVAL); // 立即执行，之后每隔INTERVAL执行
    }

    // 停止定时任务
    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

    // 给所有用户增加金币（移除管理员过滤）
    private void updateAllUsersGold() {
        List<User> users = userDAO.getAllUsers(); // 需要在UserDAO新增getAllUsers方法
        for (User user : users) {
            user.setGold(user.getGold() + GOLD_INCREMENT);
            userDAO.updateUser(user);
        }
    }
}