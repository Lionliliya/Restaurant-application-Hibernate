package com.gmail.liliyayalovchenko.DAOs;

import com.gmail.liliyayalovchenko.domainModel.Dish;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class DishDAOImpl implements DishDAO {

    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(Dish dish) {
        sessionFactory.getCurrentSession().save(dish);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Dish> findAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT d FROM Dish d").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Dish getDishByName(String dishName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select d from Dish d where d.name like :dishName");
        query.setParameter("dishName", dishName);
        return (Dish) query.getResultList().get(0);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeDish(Dish dish) {
        sessionFactory.getCurrentSession().delete(dish);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Dish getDishById(int dishId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select d from Dish d where d.id =:dishId");
        query.setParameter("dishId", dishId);
        return (Dish) query.getResultList().get(0);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
