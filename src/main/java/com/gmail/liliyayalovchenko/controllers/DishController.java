package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.dao.DishDAO;
import com.gmail.liliyayalovchenko.domain.Dish;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DishController {

    private DishDAO dishDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(DishController.class);


    @Transactional
    public void createDish(Dish dish) {
       try {
           dishDAO.save(dish);
       } catch (HibernateException ex) {
           LOGGER.error("Cant save dish to database! " + ex);
       }
    }

    @Transactional
    public List<Dish> getAllDishes() {
        List<Dish> allDish = null;
        try {
            allDish = dishDAO.findAll();
        } catch (HibernateException ex) {
            LOGGER.error("Cant get all dishes from database " + ex);
        }
        return allDish;
    }

    @Transactional
    public void printAllDishes() {
        LOGGER.info("Start to print all dishes");
        List<Dish> allDishes = getAllDishes();
        if (null != allDishes) {
            allDishes.forEach(System.out::println);
            LOGGER.info("All dishes are printed");
        } else {
            LOGGER.info("Could not get dish list from database.");
        }
    }

    @Transactional
    public Dish getDishByName(String dishName) {
       try {
           return  dishDAO.getDishByName(dishName);
       } catch (HibernateException ex) {
           LOGGER.error("Hibernate exception! " + ex);
       } catch (RuntimeException ex) {
           LOGGER.error("Error! " + ex);
       }
       return null;
    }

    @Transactional
    public void removeDish(Dish dish) {
        try {
            dishDAO.removeDish(dish);
        } catch (HibernateException ex) {
            LOGGER.error("Cannot remove dish! " + ex);
        }
    }

    @Transactional
    public Dish getDishById(int dishId) {
       try {
           return dishDAO.getDishById(dishId);
       } catch (HibernateException ex) {
           LOGGER.error("Cannot get dish from database " + ex);
       } catch (RuntimeException ex) {
           LOGGER.error("Error! " + ex);
       }
       return null;
    }

    public void setDishDAO(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }
}
