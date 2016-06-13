package com.gmail.liliyayalovchenko.domainModel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "DISH")
public class Dish {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private DishCategory dishCategory;

    @Column(name = "price")
    private double price;

    @Column(name = "weight")
    private int weight;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "DISH_INGTREDIENTS",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "ingred_id"))
    private List<Ingredient> ingredients;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menuId;

    public Dish(String name, DishCategory dishCategory, double price, int weight, List<Ingredient> ingredients, Menu menuId) {
        this.name = name;
        this.dishCategory = dishCategory;
        this.price = price;
        this.weight = weight;
        this.ingredients = ingredients;
        this.menuId = menuId;
    }

    public Dish() {}



    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDishCategory(DishCategory dishCategory) {
        this.dishCategory = dishCategory;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;

        Dish dish = (Dish) o;

        if (Double.compare(dish.price, price) != 0) return false;
        if (weight != dish.weight) return false;
        if (dishCategory != dish.dishCategory) return false;
        if (ingredients != null ? !ingredients.equals(dish.ingredients) : dish.ingredients != null) return false;
        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (dishCategory != null ? dishCategory.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + weight;
        result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dishCategory=" + dishCategory +
                ", price=" + price +
                ", weight=" + weight +
                ", ingredients=" + ingredients +
                ", menuId=" + menuId +
                '}';
    }

    public void setMenuId(Menu menuId) {
        this.menuId = menuId;
    }
}
