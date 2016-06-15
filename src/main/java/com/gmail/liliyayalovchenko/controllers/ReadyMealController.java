package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.dao.ReadyMealDAO;
import com.gmail.liliyayalovchenko.domain.ReadyMeal;
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

    @Transactional
    public void removeReadyMeal(int orderNumber1) {
        readyMealDAO.removeReadyMeal(orderNumber1);
    }

    public void setReadyMealDAO(ReadyMealDAO readyMealDAO) {
        this.readyMealDAO = readyMealDAO;
    }


}
