package com.gmail.liliyayalovchenko.controllers;

import com.gmail.liliyayalovchenko.DAOs.OrderDAO;
import com.gmail.liliyayalovchenko.domainModel.Dish;
import com.gmail.liliyayalovchenko.domainModel.Order;
import com.gmail.liliyayalovchenko.domainModel.OrderStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderController {

    private OrderDAO orderDAO;

    @Transactional
    public void saveOrder(Order order) {
        Set<Order> allOrders = new HashSet<>(orderDAO.findAll());
        if (!allOrders.contains(order)) {
            orderDAO.save(order);
        } else {
            System.out.println("Order already exist!");
        }
    }

    @Transactional
    public List<Order> getAllorders() {
        return orderDAO.findAll();
    }

    @Transactional
    public void addDishToOpenOrder(Dish dish, int orderNumber) {
        orderDAO.addDishToOpenOrder(dish, orderNumber);
    }

    @Transactional
    public void deleteOrder(int orderNumber) {
        orderDAO.deleteOrder(orderNumber);
    }

    @Transactional
    public void changeOrderStatus(int orderNumber) {
        orderDAO.changeOrderStatus(orderNumber);
    }

    @Transactional
    public List<Order> getOpenOrClosedOrder(OrderStatus orderStatus) {
        return orderDAO.getOpenOrClosedOrder(orderStatus);
    }

    @Transactional
    public Order getOrderById(int i) {
        return orderDAO.getOrderById(i);
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }


}