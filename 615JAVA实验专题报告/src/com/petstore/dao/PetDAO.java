package com.petstore.dao;

import com.petstore.DBManager;
import com.petstore.entity.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 宠物数据访问对象
 */
public class PetDAO {

    public Pet getPetById(int petId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pet pet = null;
        try {
            conn = DBManager.getConnection();
            String sql = "SELECT * FROM pets WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, petId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setName(rs.getString("name"));
                pet.setType(rs.getString("type"));
                pet.setHealth(rs.getInt("health"));
                pet.setSatiety(rs.getInt("satiety"));
                pet.setEnergy(rs.getInt("energy"));
                pet.setUserId(rs.getInt("user_id"));
                pet.setFree(rs.getBoolean("is_free"));
                pet.setPrice(rs.getInt("price"));
                pet.setStock(rs.getInt("stock"));
                pet.setDescription(rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, stmt, rs);
        }
        return pet;
    }

    /**
     * 获取所有可领养的宠物（免费且有库存）
     * @return 可领养的宠物列表
     */
    public List<Pet> getAdoptablePets() {
        List<Pet> pets = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getConnection();
            String sql = "SELECT * FROM pets WHERE is_free = true AND stock > 0";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setName(rs.getString("name"));
                pet.setType(rs.getString("type"));
                pet.setHealth(rs.getInt("health"));
                pet.setSatiety(rs.getInt("satiety"));
                pet.setEnergy(rs.getInt("energy"));
                pet.setUserId(rs.getInt("user_id"));
                pet.setFree(rs.getBoolean("is_free"));
                pet.setPrice(rs.getInt("price"));
                pet.setStock(rs.getInt("stock"));
                pet.setDescription(rs.getString("description"));
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, stmt, rs);
        }

        return pets;
    }

    /**
     * 获取用户拥有的宠物
     * @param userId 用户ID
     * @return 用户拥有的宠物列表
     */
    public List<Pet> getUserPets(int userId) {
        List<Pet> pets = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getConnection();
            String sql = "SELECT * FROM pets WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setName(rs.getString("name"));
                pet.setType(rs.getString("type"));
                pet.setHealth(rs.getInt("health"));
                pet.setSatiety(rs.getInt("satiety"));
                pet.setEnergy(rs.getInt("energy"));
                pet.setUserId(rs.getInt("user_id"));
                pet.setFree(rs.getBoolean("is_free"));
                pet.setPrice(rs.getInt("price"));
                pet.setStock(rs.getInt("stock"));
                pet.setDescription(rs.getString("description"));
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, stmt, rs);
        }

        return pets;
    }

    /**
     * 领养宠物
     * @param petId 宠物ID
     * @param userId 用户ID
     * @return 领养成功返回true，失败返回false
     */
    public boolean adoptPet(int petId, int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getConnection();
            // 开始事务
            conn.setAutoCommit(false);

            // 检查宠物是否可领养
            String checkSql = "SELECT * FROM pets WHERE id = ? AND is_free = true AND stock > 0 FOR UPDATE";
            stmt = conn.prepareStatement(checkSql);
            stmt.setInt(1, petId);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                conn.rollback();
                return false;
            }

            // 更新宠物信息（设置用户ID，减少库存）
            String updateSql = "UPDATE pets SET user_id = ?, stock = stock - 1 WHERE id = ?";
            stmt = conn.prepareStatement(updateSql);
            stmt.setInt(1, userId);
            stmt.setInt(2, petId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
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
     * 更新宠物信息
     * @param pet 宠物对象
     * @return 更新成功返回true，失败返回false
     */
    public boolean updatePet(Pet pet) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBManager.getConnection();
            String sql = "UPDATE pets SET health = ?, satiety = ?, energy = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pet.getHealth());
            stmt.setInt(2, pet.getSatiety());
            stmt.setInt(3, pet.getEnergy());
            stmt.setInt(4, pet.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, stmt, null);
        }

        return false;
    }

    /**
     * 获取商店中所有可购买的宠物
     * @return 可购买的宠物列表
     */
    public List<Pet> getPetsForSale() {
        List<Pet> pets = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getConnection();
            String sql = "SELECT * FROM pets WHERE is_free = false AND stock > 0";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setName(rs.getString("name"));
                pet.setType(rs.getString("type"));
                pet.setHealth(rs.getInt("health"));
                pet.setSatiety(rs.getInt("satiety"));
                pet.setEnergy(rs.getInt("energy"));
                pet.setUserId(rs.getInt("user_id"));
                pet.setFree(rs.getBoolean("is_free"));
                pet.setPrice(rs.getInt("price"));
                pet.setStock(rs.getInt("stock"));
                pet.setDescription(rs.getString("description"));
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(conn, stmt, rs);
        }

        return pets;
    }

    /**
     * 购买宠物
     * @param petId 宠物ID
     * @param userId 用户ID
     * @return 购买成功返回true，失败返回false
     */
    public boolean buyPet(int petId, int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getConnection();
            // 开始事务
            conn.setAutoCommit(false);

            // 获取宠物信息
            String petSql = "SELECT * FROM pets WHERE id = ? AND is_free = false AND stock > 0 FOR UPDATE";
            stmt = conn.prepareStatement(petSql);
            stmt.setInt(1, petId);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                conn.rollback();
                return false;
            }

            Pet pet = new Pet();
            pet.setId(rs.getInt("id"));
            pet.setPrice(rs.getInt("price"));

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
            if (userGold < pet.getPrice()) {
                conn.rollback();
                return false;
            }

            // 更新用户金币
            String updateUserSql = "UPDATE users SET gold = gold - ? WHERE id = ?";
            stmt = conn.prepareStatement(updateUserSql);
            stmt.setInt(1, pet.getPrice());
            stmt.setInt(2, userId);
            int userRows = stmt.executeUpdate();

            // 更新宠物信息
            String updatePetSql = "UPDATE pets SET user_id = ?, stock = stock - 1 WHERE id = ?";
            stmt = conn.prepareStatement(updatePetSql);
            stmt.setInt(1, userId);
            stmt.setInt(2, petId);
            int petRows = stmt.executeUpdate();

            if (userRows > 0 && petRows > 0) {
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
}