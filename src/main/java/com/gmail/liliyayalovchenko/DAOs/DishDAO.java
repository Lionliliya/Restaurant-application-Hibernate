package com.gmail.liliyayalovchenko.DAOs;

import com.gmail.liliyayalovchenko.domainModel.Dish;

import java.util.List;

public interface DishDAO {

    public void save(Dish dish);

    public void removeDish(Dish dish);

    public List<Dish> findAll();

    public Dish getDishByName(String dishName);

    public Object getDishById(int dishId);
}
