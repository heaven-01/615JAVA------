package com.petstore;

import com.petstore.service.GoldTimer;
import com.petstore.view.MainMenu;

/**
 * 主程序入口
 */
public class Main {
    public static void main(String[] args) {
        GoldTimer goldTimer = new GoldTimer();
        goldTimer.start();

        MainMenu mainMenu = new MainMenu();
        mainMenu.show();

        goldTimer.stop();
    }
}