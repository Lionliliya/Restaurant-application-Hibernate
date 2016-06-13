package com.gmail.liliyayalovchenko.DAOs;

import com.gmail.liliyayalovchenko.domainModel.ReadyMeal;

import java.util.List;

public interface ReadyMealDAO {

    public List<ReadyMeal> getAllReadyMeals();

    public void addReadyMeal(ReadyMeal meal);
}
