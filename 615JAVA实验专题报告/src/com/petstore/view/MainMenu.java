package com.petstore.view;

import com.petstore.entity.Item;
import com.petstore.entity.Pet;
import com.petstore.entity.User;
import com.petstore.service.PetService;
import com.petstore.service.UserService;

import java.util.List;
import java.util.Scanner;

/**
 * 主菜单视图类
 */
public class MainMenu {
    private Scanner scanner = new Scanner(System.in);
    private UserService userService = new UserService();
    private PetService petService = new PetService();
    private User currentUser;

    /**
     * 显示主菜单
     */
    public void show() {
        while (true) {
            if (currentUser == null) {
                // 用户未登录，显示登录注册菜单
                showLoginMenu();
            } else {
                // 用户已登录，显示主功能菜单
                showMainFunctionMenu();
            }
        }
    }

    /**
     * 显示登录注册菜单
     */
    private void showLoginMenu() {
        System.out.println("\n===== 宠物养成系统 =====");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        System.out.println("3. 退出");
        System.out.print("请选择操作: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                handleLogin();
                break;
            case 2:
                handleRegister();
                break;
            case 3:
                System.out.println("感谢使用，再见！");
                System.exit(0);
                break;
            default:
                System.out.println("无效的选择，请重新输入！");
        }
    }

    /**
     * 处理登录
     */
    private void handleLogin() {
        System.out.print("请输入用户名: ");
        String username = scanner.nextLine();
        System.out.print("请输入密码: ");
        String password = scanner.nextLine();

        currentUser = userService.login(username, password);
        if (currentUser != null) {
            System.out.println("登录成功，欢迎回来 " + currentUser.getUsername() + "！");
        } else {
            System.out.println("用户名或密码错误！");
        }
    }

    /**
     * 处理注册
     */
    private void handleRegister() {
        System.out.print("请输入用户名: ");
        String username = scanner.nextLine();
        System.out.print("请输入密码: ");
        String password = scanner.nextLine();

        if (userService.register(username, password)) {
            System.out.println("注册成功，请登录！");
        }
    }

    /**
     * 显示主功能菜单
     */
    private void showMainFunctionMenu() {
        System.out.println("\n===== 欢迎 " + currentUser.getUsername() + " =====");
        System.out.println("当前金币: " + currentUser.getGold());
        System.out.println("1. 领养宠物");
        System.out.println("2. 查看我的宠物");
        System.out.println("3. 宠物商店");
        System.out.println("4. 道具商店");
        System.out.println("5. 我的道具");
        System.out.println("6. 退出登录");
        System.out.print("请选择操作: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                handleAdoptPet();
                break;
            case 2:
                handleViewMyPets();
                break;
            case 3:
                handlePetStore();
                break;
            case 4:
                handleItemStore();
                break;
            case 5:
                handleMyItems();
                break;
            case 6:
                currentUser = null;
                System.out.println("已退出登录！");
                break;
            default:
                System.out.println("无效的选择，请重新输入！");
        }
    }

    /**
     * 处理领养宠物
     */
    private void handleAdoptPet() {
        List<Pet> adoptablePets = petService.getAdoptablePets();

        if (adoptablePets.isEmpty()) {
            System.out.println("暂无可领养的宠物！");
            return;
        }

        System.out.println("\n===== 可领养的宠物 =====");
        for (int i = 0; i < adoptablePets.size(); i++) {
            Pet pet = adoptablePets.get(i);
            System.out.println((i + 1) + ". " + pet.getName() + " (" + pet.getType() + ") - " + pet.getDescription());
        }

        System.out.print("请选择要领养的宠物编号(0返回): ");
        int choice = getIntInput();

        if (choice > 0 && choice <= adoptablePets.size()) {
            Pet selectedPet = adoptablePets.get(choice - 1);
            if (petService.adoptPet(selectedPet.getId(), currentUser.getId())) {
                System.out.println("成功领养了" + selectedPet.getName() + "！");
            } else {
                System.out.println("领养失败！");
            }
        }
    }

    /**
     * 处理查看我的宠物
     */
    private void handleViewMyPets() {
        List<Pet> myPets = petService.getUserPets(currentUser.getId());

        if (myPets.isEmpty()) {
            System.out.println("你还没有宠物，请先领养或购买！");
            return;
        }

        System.out.println("\n===== 我的宠物 =====");
        for (int i = 0; i < myPets.size(); i++) {
            Pet pet = myPets.get(i);
            System.out.println((i + 1) + ". " + pet.getName() + " (" + pet.getType() + ")");
            System.out.println(pet.getStatus());
        }

        System.out.println("请选择要操作的宠物编号(0返回): ");
        int petChoice = getIntInput();

        if (petChoice > 0 && petChoice <= myPets.size()) {
            Pet selectedPet = myPets.get(petChoice - 1);
            handlePetOperations(selectedPet);
        }
    }

    /**
     * 处理宠物操作
     * @param pet 选中的宠物
     */
    private void handlePetOperations(Pet pet) {
        while (true) {
            System.out.println("\n===== " + pet.getName() + " =====");
            System.out.println("1. 与宠物玩耍");
            System.out.println("2. 使用道具");
            System.out.println("3. 返回");
            System.out.print("请选择操作: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    System.out.println(petService.playWithPet(pet.getId()));
                    break;
                case 2:
                    handleUseItem(pet);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("无效的选择，请重新输入！");
            }
        }
    }

    /**
     * 处理使用道具
     * @param pet 目标宠物
     */
    private void handleUseItem(Pet pet) {
        List<Item> userItems = petService.getUserItems(currentUser.getId());

        if (userItems.isEmpty()) {
            System.out.println("你没有任何道具！");
            return;
        }

        System.out.println("\n===== 我的道具 =====");
        for (int i = 0; i < userItems.size(); i++) {
            Item item = userItems.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " (" + item.getCategoryName() + ") - " + item.getDescription());
        }

        System.out.print("请选择要使用的道具编号(0返回): ");
        int choice = getIntInput();

        if (choice > 0 && choice <= userItems.size()) {
            Item selectedItem = userItems.get(choice - 1);
            System.out.println(petService.useItemOnPet(selectedItem.getId(), pet.getId(), currentUser.getId()));
        }
    }

    /**
     * 处理宠物商店
     */
    private void handlePetStore() {
        List<Pet> petsForSale = petService.getPetsForSale();

        if (petsForSale.isEmpty()) {
            System.out.println("商店暂无宠物出售！");
            return;
        }

        System.out.println("\n===== 宠物商店 =====");
        for (int i = 0; i < petsForSale.size(); i++) {
            Pet pet = petsForSale.get(i);
            System.out.println((i + 1) + ". " + pet.getName() + " (" + pet.getType() + ") - " + pet.getPrice() + "金币 - " + pet.getDescription());
        }

        System.out.print("请选择要购买的宠物编号(0返回): ");
        int choice = getIntInput();

        if (choice > 0 && choice <= petsForSale.size()) {
            Pet selectedPet = petsForSale.get(choice - 1);
            if (petService.buyPet(selectedPet.getId(), currentUser.getId())) {
                System.out.println("成功购买了" + selectedPet.getName() + "！");
                // 更新用户信息
                currentUser = userService.login(currentUser.getUsername(), currentUser.getPassword());
            } else {
                System.out.println("购买失败！可能是金币不足或库存不足。");
            }
        }
    }

    /**
     * 处理道具商店
     */
    private void handleItemStore() {
        List<Item> allItems = petService.getAllItems();

        if (allItems.isEmpty()) {
            System.out.println("商店暂无道具出售！");
            return;
        }

        System.out.println("\n===== 道具商店 =====");
        for (int i = 0; i < allItems.size(); i++) {
            Item item = allItems.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " (" + item.getCategoryName() + ") - " + item.getPrice() + "金币 - " + item.getDescription());
        }

        System.out.print("请选择要购买的道具编号(0返回): ");
        int choice = getIntInput();

        if (choice > 0 && choice <= allItems.size()) {
            Item selectedItem = allItems.get(choice - 1);
            System.out.print("请输入购买数量: ");
            int quantity = getIntInput();

            if (quantity > 0) {
                if (petService.buyItem(selectedItem.getId(), currentUser.getId(), quantity)) {
                    System.out.println("成功购买了" + quantity + "个" + selectedItem.getName() + "！");
                    // 更新用户信息
                    currentUser = userService.login(currentUser.getUsername(), currentUser.getPassword());
                } else {
                    System.out.println("购买失败！可能是金币不足。");
                }
            }
        }
    }

    /**
     * 处理我的道具
     */
    private void handleMyItems() {
        List<Item> userItems = petService.getUserItems(currentUser.getId());

        if (userItems.isEmpty()) {
            System.out.println("你没有任何道具！");
            return;
        }

        System.out.println("\n===== 我的道具 =====");
        for (int i = 0; i < userItems.size(); i++) {
            Item item = userItems.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " (" + item.getCategoryName() + ") - " + item.getDescription());
        }

        System.out.println("按任意键返回...");
        scanner.nextLine();
    }

    /**
     * 获取整数输入
     * @return 输入的整数
     */
    private int getIntInput() {
        try {
            int input = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符
            return input;
        } catch (Exception e) {
            scanner.nextLine(); // 清除错误输入
            return -1;
        }
    }
}