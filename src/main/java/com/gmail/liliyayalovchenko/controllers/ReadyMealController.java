package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.DAOs.ReadyMealDAO;
import com.gmail.liliyayalovchenko.domainModel.ReadyMeal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ReadyMealController {

    private ReadyMealDAO readyMealDAO;

    @Transactional
    public List<ReadyMeal> getAllReadyMeals() {
        return readyMealDAO.getAllReadyMeals();
    }

    @Transactional
    public void addReadyMeal(ReadyMeal meal) {
        readyMealDAO.addReadyMeal(meal);
    }

    public void setReadyMealDAO(ReadyMealDAO readyMealDAO) {
        this.readyMealDAO = readyMealDAO;
    }
}
