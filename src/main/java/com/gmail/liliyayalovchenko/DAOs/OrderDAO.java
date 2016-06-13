package com.gmail.liliyayalovchenko.DAOs;

import com.gmail.liliyayalovchenko.domainModel.Dish;
import com.gmail.liliyayalovchenko.domainModel.Order;
import com.gmail.liliyayalovchenko.domainModel.OrderStatus;

import java.util.List;

public interface OrderDAO {

    public void save(Order order);

    public List<Order> findAll();

    public void addDishToOpenOrder(Dish dish, int orderNumber);

    public void deleteOrder(int orderNumber);

    public void changeOrderStatus(int orderNumber);

    public List<Order> getOpenOrClosedOrder(OrderStatus orderStatus);

    public Order getOrderById(int i);
}
