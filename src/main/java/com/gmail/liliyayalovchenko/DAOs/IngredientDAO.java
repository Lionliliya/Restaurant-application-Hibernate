package com.gmail.liliyayalovchenko.DAOs;


import com.gmail.liliyayalovchenko.domainModel.Ingredient;

import java.util.List;

public interface IngredientDAO {

    public Ingredient getIngredientById(int id);

    public List<Ingredient> getAllIngredients();

    public Ingredient getIngredientByName(String name);

    public boolean exist(Ingredient ingredient);

    public void addIngredient(Ingredient ingredient);
}
