package com.gmail.liliyayalovchenko.dao.hibernate;

import com.gmail.liliyayalovchenko.dao.OrderDAO;
import com.gmail.liliyayalovchenko.domain.Dish;
import com.gmail.liliyayalovchenko.domain.Order;
import com.gmail.liliyayalovchenko.domain.OrderStatus;
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
        query.setParameter("orderNumber", orderNumber);
        Order order = (Order) query.getResultList().get(0);
        if (order == null) {
            throw new RuntimeException("Cant get order by this order number! Wrong order number");
        } else {
            order.addDishToOrder(dish);
            session.update(order);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteOrder(int orderNumber) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select o from Order o where o.orderNumber =:orderNumber");
        query.setParameter("orderNumber", orderNumber);
        Order order = (Order) query.getResultList().get(0);
        if (order == null) {
            throw new RuntimeException("Cant get order by this order number! Wrong order number");
        } else {
            session.delete(order);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void changeOrderStatus(int orderNumber) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select o from Order o where o.orderNumber like :orderNumber");
        query.setParameter("orderNumber", orderNumber);
        Order order = (Order) query.getResultList().get(0);
        if (order != null) {
            order.setStatus(OrderStatus.closed);
            session.update(order);

        } else {
            throw new RuntimeException("Cant get order by this order number! Wrong order number");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Order> getOpenOrClosedOrder(OrderStatus orderStatus) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select o from Order o where o.status =:status");
        query.setParameter("status", orderStatus);
        return  (List<Order>)query.getResultList();
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Order getOrderById(int i) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select o from Order o where o.id =:id");
        query.setParameter("id", i);
        Order order = (Order) query.getResultList().get(0);
        if (order != null) {
            return order;
        } else {
            throw new RuntimeException("Cant get order by this id! Error!");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public int getLastOrder() {
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.query.Query query = session.createQuery("select max(o.orderNumber) from Order o");
        return (int) query.list().get(0);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
