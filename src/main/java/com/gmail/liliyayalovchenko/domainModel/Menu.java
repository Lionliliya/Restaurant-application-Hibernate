package com.gmail.liliyayalovchenko.domainModel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MENU")
public class Menu {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="menuId")
    private List<Dish> dishList;

    public Menu(List<Dish> dishList, String name) {
        this.dishList = dishList;
        this.name = name;
    }

    public Menu() {}

    public void addDishToMenu(Dish dish) {
        dishList.add(dish);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dishList=" + dishList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;

        Menu menu = (Menu) o;

        if (name != null ? !name.equals(menu.name) : menu.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void removeDishFromMenu(Dish dish) {
        if (dishList.contains(dish)) {
            dishList.remove(dish);
        } else {
            System.out.println("Dish is not present in this menu");
        }
    }
}
