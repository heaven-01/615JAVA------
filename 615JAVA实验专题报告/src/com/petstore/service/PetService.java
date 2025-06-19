package com.petstore.service;

import com.petstore.dao.ItemDAO;
import com.petstore.dao.PetDAO;
import com.petstore.entity.Item;
import com.petstore.entity.Pet;
import com.petstore.entity.User;

import java.util.List;
import java.util.Random;

/**
 * 宠物服务类
 */
public class PetService {
    private PetDAO petDAO = new PetDAO();
    private ItemDAO itemDAO = new ItemDAO();
    private Random random = new Random();

    /**
     * 获取所有可领养的宠物
     * @return 可领养的宠物列表
     */
    public List<Pet> getAdoptablePets() {
        return petDAO.getAdoptablePets();
    }

    /**
     * 获取用户拥有的宠物
     * @param userId 用户ID
     * @return 用户拥有的宠物列表
     */
    public List<Pet> getUserPets(int userId) {
        return petDAO.getUserPets(userId);
    }

    /**
     * 领养宠物
     * @param petId 宠物ID
     * @param userId 用户ID
     * @return 领养成功返回true，失败返回false
     */
    public boolean adoptPet(int petId, int userId) {
        return petDAO.adoptPet(petId, userId);
    }

    /**
     * 与宠物互动（玩耍）
     * @param petId 宠物ID
     * @return 互动结果描述
     */
    public String playWithPet(int petId) {
        Pet pet = getPetById(petId);
        if (pet == null) {
            return "宠物不存在！";
        }

        // 检查宠物活力值
        if (pet.getEnergy() < 30) {
            return pet.getName() + "太累了，不想玩！";
        }

        // 减少活力值
        pet.setEnergy(pet.getEnergy() - 20);

        // 增加健康值（随机5-15）
        int healthIncrease = random.nextInt(11) + 5;
        pet.setHealth(pet.getHealth() + healthIncrease);

        // 更新宠物信息
        if (petDAO.updatePet(pet)) {
            return pet.getName() + "玩得很开心！健康值增加了" + healthIncrease + "，活力值减少了20。";
        } else {
            return "与宠物互动失败！";
        }
    }

    /**
     * 使用道具
     * @param itemId 道具ID
     * @param petId 宠物ID
     * @param userId 用户ID
     * @return 使用结果描述
     */
    public String useItemOnPet(int itemId, int petId, int userId) {
        Item item = getItemById(itemId);
        Pet pet = getPetById(petId);

        if (item == null) {
            return "道具不存在！";
        }

        if (pet == null) {
            return "宠物不存在！";
        }

        // 检查用户是否拥有该道具
        if (!itemDAO.useItem(itemId, userId)) {
            return "你没有这个道具！";
        }

        // 应用道具效果
        pet.setHealth(pet.getHealth() + item.getHealthEffect());
        pet.setSatiety(pet.getSatiety() + item.getSatietyEffect());
        pet.setEnergy(pet.getEnergy() + item.getEnergyEffect());

        // 更新宠物信息
        if (petDAO.updatePet(pet)) {
            return "使用" + item.getName() + "成功！\n" +
                    "健康值: " + (item.getHealthEffect() > 0 ? "+" : "") + item.getHealthEffect() + "\n" +
                    "饱腹值: " + (item.getSatietyEffect() > 0 ? "+" : "") + item.getSatietyEffect() + "\n" +
                    "活力值: " + (item.getEnergyEffect() > 0 ? "+" : "") + item.getEnergyEffect();
        } else {
            return "使用道具失败！";
        }
    }

    /**
     * 获取商店中所有可购买的宠物
     * @return 可购买的宠物列表
     */
    public List<Pet> getPetsForSale() {
        return petDAO.getPetsForSale();
    }

    /**
     * 购买宠物
     * @param petId 宠物ID
     * @param userId 用户ID
     * @return 购买成功返回true，失败返回false
     */
    public boolean buyPet(int petId, int userId) {
        return petDAO.buyPet(petId, userId);
    }

    /**
     * 获取所有道具信息
     * @return 道具列表
     */
    public List<Item> getAllItems() {
        return itemDAO.getAllItems();
    }

    /**
     * 购买道具
     * @param itemId 道具ID
     * @param userId 用户ID
     * @param quantity 购买数量
     * @return 购买成功返回true，失败返回false
     */
    public boolean buyItem(int itemId, int userId, int quantity) {
        return itemDAO.buyItem(itemId, userId, quantity);
    }

    /**
     * 获取用户拥有的道具
     * @param userId 用户ID
     * @return 用户拥有的道具列表
     */
    public List<Item> getUserItems(int userId) {
        return itemDAO.getUserItems(userId);
    }

    /**
     * 根据ID获取宠物
     * @param petId 宠物ID
     * @return 宠物对象，如果不存在则返回null
     */
    private Pet getPetById(int petId) {
        return petDAO.getPetById(petId);
    }

    /**
     * 根据ID获取道具
     * @param itemId 道具ID
     * @return 道具对象，如果不存在则返回null
     */
    private Item getItemById(int itemId) {
        List<Item> items = itemDAO.getAllItems();
        for (Item item : items) {
            if (item.getId() == itemId) {
                return item;
            }
        }
        return null;
    }
}