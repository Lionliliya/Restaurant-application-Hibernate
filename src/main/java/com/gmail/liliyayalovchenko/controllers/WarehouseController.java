package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.dao.WarehouseDAO;
import com.gmail.liliyayalovchenko.domain.Ingredient;
import com.gmail.liliyayalovchenko.domain.Warehouse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class WarehouseController {

    private WarehouseDAO warehouseDAO;

    @Transactional
    public void addIngredient(Ingredient ingredient, int amount) {
        warehouseDAO.addIngredient(ingredient, amount);
    }

    @Transactional
    public void removeIngredient(String ingredientName) {
        warehouseDAO.removeIngredient(ingredientName);
    }

    @Transactional
    public void changeAmount(Ingredient ingredient, int delta, boolean increase) {
        warehouseDAO.changeAmount(ingredient, delta, increase);
    }

    @Transactional
    public Warehouse findByName(String ingredientName) {
        return warehouseDAO.findByName(ingredientName);
    }

    @Transactional
    public List<Warehouse> getAllIngredients() {
        return warehouseDAO.getAllIngredients();
    }

    @Transactional
    public List<Warehouse> getEndingIngredients() {
        return warehouseDAO.getEndingIngredients();
    }

    public void setWarehouseDAO(WarehouseDAO warehouseDAO) {
        this.warehouseDAO = warehouseDAO;
    }
}
