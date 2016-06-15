package com.gmail.liliyayalovchenko.dao;


import com.gmail.liliyayalovchenko.domain.Dish;
import com.gmail.liliyayalovchenko.domain.Menu;

import java.util.List;

public interface MenuDAO {

    public void addNewMenu(String menuName, List<Dish> dishList);

    public void createMenu(Menu menu);

    public void removeMenu(Menu menu);

    public Menu getMenuByName(String name);

    public void showAllMenus();

    public void addDishToMenu(int menuId, Dish dish);

    public void removeDishFromMenu(int menuId, Dish dish);

    public List<Menu> getAllMenu();

    public void showAllMenuNames();
}
