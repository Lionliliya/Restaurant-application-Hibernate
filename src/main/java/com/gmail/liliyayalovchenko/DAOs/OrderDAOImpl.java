package com.gmail.liliyayalovchenko.DAOs;

import com.gmail.liliyayalovchenko.domainModel.Dish;
import com.gmail.liliyayalovchenko.domainModel.Order;
import com.gmail.liliyayalovchenko.domainModel.OrderStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(Order order) {
        sessionFactory.getCurrentSession().save(order);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> findAll() {
        return sessionFactory.getCurrentSession().createQuery("select o from Order o").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addDishToOpenOrder(Dish dish, int orderNumber) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select o from Order o where o.orderNumber =:orderNumber");
        Order order = (Order) query.getResultList().get(0);
        order.addDishToOrder(dish);
        session.update(order);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteOrder(int orderNumber) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select o from Order o where o.orderNumber like :orderNumber");
        Order order = (Order) query.getResultList().get(0);
        session.delete(order);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void changeOrderStatus(int orderNumber) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select o from Order o where o.orderNumber like :orderNumber");
        query.setParameter("orderNumber", orderNumber);
        Order order = (Order) query.getResultList().get(0);
        order.setStatus(OrderStatus.closed);
        session.update(order);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> getOpenOrClosedOrder(OrderStatus orderStatus) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select o from Order o where o.status like :status");
        query.setParameter("status", orderStatus);
        return  (List<Order>)query.getResultList();
    }

    @Override
    public Order getOrderById(int i) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select o from Order o where o.id =:id");
        query.setParameter("id", i);
        return (Order) query.getResultList().get(0);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
