package com.jorge.prueba2.repository;

import org.springframework.stereotype.Repository;

@Repository
public class InventoryRepository {
    
    public static final String INSERT_ITEM = "INSERT INTO inventario (name, cantidad, marca, modelo) values (?,?,?,?)";
    public static final String GET_ALL_ITEMS = "SELECT * FROM inventario";
    public static final String GET_ITEM_ID = "SELECT * FROM inventario WHERE id = ?";
    public static final String DELETE_ITEM = "DELETE FROM inventario WHERE id = ?";
    public static final String UPDATE_ITEM = "UPDATE inventario SET name = ?, cantidad = ?, marca = ?, modelo = ? WHERE id = ?";
    public static final String BUY_ITEM = "UPDATE inventario SET cantidad = cantidad - ? WHERE id = ?"; 
}
