package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.dao.IngredientDAO;
import com.gmail.liliyayalovchenko.domain.Ingredient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class IngredientController {

    private IngredientDAO ingredientDAO;

    @Transactional
    public Ingredient getIngredientById(int id) {
        return ingredientDAO.getIngredientById(id);
    }

    @Transactional
    public List<Ingredient> getAllIngredients() {
        return ingredientDAO.getAllIngredients();
    }

    @Transactional
    public Ingredient getIngredientByName(String name) {
        return ingredientDAO.getIngredientByName(name);
    }

    @Transactional
    public void createIngredient(Ingredient ingredient) {
        ingredientDAO.addIngredient(ingredient);
    }

    public void setIngredientDAO(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }
}
