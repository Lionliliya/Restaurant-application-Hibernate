package com.gmail.liliyayalovchenko.DAOs;


import com.gmail.liliyayalovchenko.domainModel.Dish;
import com.gmail.liliyayalovchenko.domainModel.Menu;

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
}
