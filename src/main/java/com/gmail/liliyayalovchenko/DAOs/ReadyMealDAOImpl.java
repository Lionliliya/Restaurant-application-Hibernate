package com.gmail.liliyayalovchenko.DAOs;

import com.gmail.liliyayalovchenko.domainModel.ReadyMeal;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public void addReadyMeal(ReadyMeal meal) {
        sessionFactory.getCurrentSession().save(meal);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
