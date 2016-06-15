package com.gmail.liliyayalovchenko.dao;


import com.gmail.liliyayalovchenko.domain.Ingredient;
import com.gmail.liliyayalovchenko.domain.Warehouse;

import java.util.List;

public interface WarehouseDAO {

    public void addIngredient(Ingredient ingredient, int amount);

    public void removeIngredient(String ingredientName);

    public void changeAmount(Ingredient ingredient, int delta, boolean increase);

    public Warehouse findByName(String ingredientName);

    public List<Warehouse> getAllIngredients();

    public List<Warehouse> getEndingIngredients();

    public boolean alreadyExist(Ingredient ingredient);
}
