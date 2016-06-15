package com.gmail.liliyayalovchenko.dao;

import com.gmail.liliyayalovchenko.domain.ReadyMeal;

import java.util.List;

public interface ReadyMealDAO {

    public List<ReadyMeal> getAllReadyMeals();

    public void addReadyMeal(ReadyMeal meal);

    public void removeReadyMeal(int orderNumber);
}
