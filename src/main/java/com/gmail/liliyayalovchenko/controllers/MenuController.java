package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.dao.MenuDAO;
import com.gmail.liliyayalovchenko.domain.Dish;
import com.gmail.liliyayalovchenko.domain.Menu;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class MenuController {

    private MenuDAO menuDAO;


    @Transactional
    public void addNewMenu(String menuName, List<Dish> dishList) {
        menuDAO.addNewMenu(menuName, dishList);
    }

    @Transactional
    public void createMenu(Menu menu) {
        menuDAO.createMenu(menu);
    }

    @Transactional
    public void removeMenu(Menu menu) {
        menuDAO.removeMenu(menu);
    }

    @Transactional
    public Menu getMenuByName(String name) {
        return menuDAO.getMenuByName(name);
    }

    @Transactional
    public void addDishToMenu(int menuId, Dish dish) {
        menuDAO.addDishToMenu(menuId, dish);
    }

    @Transactional
    public void removeDishFromMenu(int menuId, Dish dish) {
        menuDAO.removeDishFromMenu(menuId, dish);
    }

    @Transactional
    public void showAllMenus() {
        menuDAO.showAllMenus();
    }

    @Transactional
    public void printMenuNames() {
        menuDAO.showAllMenuNames();
    }


    @Transactional
    public List<Menu> getAllMenus() {
        return menuDAO.getAllMenu();
    }

    public void setMenuDAO(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }
}
