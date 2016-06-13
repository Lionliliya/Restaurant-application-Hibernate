package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.DAOs.DishDAO;
import com.gmail.liliyayalovchenko.domainModel.Dish;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DishController {

    private DishDAO dishDAO;

    @Transactional
    public void createDish(Dish dish) {
        Set<Dish> allDishes = new HashSet<>(dishDAO.findAll());
        if (!allDishes.contains(dish)) {
            dishDAO.save(dish);
        } else {
            System.out.println("Dish already exist!");
        }
    }

    @Transactional
    public List<Dish> getAllDishes() {
        return dishDAO.findAll();
    }

    @Transactional
    public Dish getDishByName(String dishName) {
        return  dishDAO.getDishByName(dishName);
    }

    @Transactional
    public void removeDish(Dish dish) {
        dishDAO.removeDish(dish);
    }

    @Transactional
    public Dish getDishById(int dishId) {
       return (Dish) dishDAO.getDishById(dishId);
    }

    public void setDishDAO(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }
}
