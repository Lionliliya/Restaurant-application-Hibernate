package com.gmail.liliyayalovchenko.dao.hibernate;

import com.gmail.liliyayalovchenko.dao.ReadyMealDAO;
import com.gmail.liliyayalovchenko.domain.Ingredient;
import com.gmail.liliyayalovchenko.domain.Order;
import com.gmail.liliyayalovchenko.domain.ReadyMeal;
import com.gmail.liliyayalovchenko.domain.Warehouse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class ReadyMealDAOImpl implements ReadyMealDAO {

    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<ReadyMeal> getAllReadyMeals() {
        return sessionFactory.getCurrentSession().createQuery("select rm from ReadyMeal rm").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeReadyMeal(int orderNumber) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select o from Order o where o.orderNumber =:orderNumber");
        query.setParameter("orderNumber", orderNumber);
        Order order = (Order) query.getResultList().get(0);
        query = session.createQuery("select rm from ReadyMeal rm where rm.orderId =:idOfOrder");
        query.setParameter("idOfOrder", order.getId());
        List<ReadyMeal> readyMealList = query.getResultList();
        readyMealList.forEach(session::delete);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addReadyMeal(ReadyMeal meal) {
        sessionFactory.getCurrentSession().save(meal);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void changeAmountOnWarehouse(Ingredient ingredient) {
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.query.Query query = session.createQuery("select w from Warehouse w where w.ingredId = " +
                "(select i.id from Ingredient i where i.name =:name)");
        query.setParameter("name", ingredient.getName());
        Warehouse warehouse = (Warehouse) query.uniqueResult();
        int newAmount = warehouse.getAmount() - 1;
        warehouse.setAmount(newAmount < 0 ? 0 : newAmount);
        session.update(warehouse);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
