package com.gmail.liliyayalovchenko.dao.hibernate;

import com.gmail.liliyayalovchenko.dao.IngredientDAO;
import com.gmail.liliyayalovchenko.domain.Ingredient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IngredientDAOImpl implements IngredientDAO {

    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Ingredient getIngredientById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select i from Ingredient i where i.id =:id");
        query.setParameter("id", id);
        Ingredient ingredient = (Ingredient) query.getResultList().get(0);
        if (ingredient != null) {
            return ingredient;
        } else {
            throw new RuntimeException("Cant get ingredient by this id.");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Ingredient> getAllIngredients() {
        return sessionFactory.getCurrentSession().createQuery("select i from Ingredient i").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Ingredient getIngredientByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select i from Ingredient i where i.name =:name");
        query.setParameter("name", name);
        Ingredient ingredient = (Ingredient) query.getResultList().get(0);
        if (ingredient == null) {
            throw new RuntimeException("Cant get ingredient by this name. Wrong name!");
        } else {
            return ingredient;
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean exist(Ingredient ingredient) {
        Session session = sessionFactory.getCurrentSession();
        Set<Ingredient> allIngredient = new HashSet<>(session.createQuery("select i from  Ingredient i").list());
        return allIngredient.contains(ingredient);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addIngredient(Ingredient ingredient) {
        sessionFactory.getCurrentSession().save(ingredient);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
