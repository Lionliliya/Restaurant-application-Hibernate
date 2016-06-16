package com.gmail.liliyayalovchenko.dao.hibernate;

import com.gmail.liliyayalovchenko.dao.DishDAO;
import com.gmail.liliyayalovchenko.domain.Dish;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

//import javax.persistence.Query;

public class DishDAOImpl implements DishDAO {

    private SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(DishDAOImpl.class);

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(Dish dish) {
        try {
            sessionFactory.getCurrentSession().save(dish);
            LOGGER.info("Dish is saved successfully.");
        } catch (HibernateException ex) {
            System.out.println("Error! Dish was not save.");
            LOGGER.error("Error. While saving dish " + ex + "\n" + Arrays.toString(ex.getStackTrace()));
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Dish> findAll() {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            LOGGER.info("Current session is got");
            List<Dish> list = currentSession.createQuery("SELECT d FROM Dish d").list();
            LOGGER.info("List of dish is successfully retrieved.");
            return list;
        } catch (HibernateException ex) {
            System.out.println("Error! dish list is not got.");
            LOGGER.error("Error while getting all dishes " + Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Dish getDishByName(String dishName) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            LOGGER.info("Current session is got");
            Query query = session.createQuery("select d from Dish d where d.name like :dishName");
            query.setParameter("dishName", dishName);
            Dish dish = (Dish) query.list().get(0);
            LOGGER.info("Dish is got successfully!");
            return dish;
        } catch (HibernateException ex) {
            System.out.println("Error. Dish was not retrieved.");
            LOGGER.error("Error while getting dish by name " + ex + "\n" + Arrays.toString(ex.getStackTrace()));
        }

        return null;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeDish(Dish dish) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            LOGGER.info("Current session is got.");
            currentSession.delete(dish);
            LOGGER.info("Dish is deleted successfully!");
        } catch (HibernateException ex) {
            System.out.println("Cant remove dish. Error wile connecting to database.");
            LOGGER.error("Error was occurring wile removing dish  " + ex + "\n" + Arrays.toString(ex.getStackTrace()));
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Dish getDishById(int dishId) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
            LOGGER.info("Current session is got.");
            Query query = session.createQuery("select d from Dish d where d.id =:dishId");
            query.setParameter("dishId", dishId);
            Dish dish = (Dish) query.list().get(0);
            LOGGER.info("Dish is got by id successfully.");
            return dish;
        } catch (HibernateException ex) {
            System.out.println("Cant get dish by id. Error.");
            LOGGER.error("Error wile getting dish by id " + ex + "\n" + Arrays.toString(ex.getStackTrace()));
        }

        return null;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
