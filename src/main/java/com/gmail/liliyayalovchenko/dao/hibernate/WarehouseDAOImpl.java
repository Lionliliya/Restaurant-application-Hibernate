package com.gmail.liliyayalovchenko.dao.hibernate;

import com.gmail.liliyayalovchenko.dao.IngredientDAO;
import com.gmail.liliyayalovchenko.dao.WarehouseDAO;
import com.gmail.liliyayalovchenko.domain.Ingredient;
import com.gmail.liliyayalovchenko.domain.Warehouse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class WarehouseDAOImpl implements WarehouseDAO {

    private SessionFactory sessionFactory;
    private IngredientDAO ingredientDAO;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addIngredient(Ingredient ingredient, int amount) {
        Warehouse warehouse = new Warehouse();
        warehouse.setIngredId(ingredient);
        warehouse.setAmount(amount);
        sessionFactory.getCurrentSession().save(warehouse);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeIngredient(String ingredientName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select w from Warehouse w where w.ingredId =:ingredient");
        Ingredient ingredientByName = ingredientDAO.getIngredientByName(ingredientName);
        query.setParameter("ingredient", ingredientByName);
        Warehouse warehouse = (Warehouse) query.getResultList().get(0);
        if (warehouse != null) {
            session.delete(warehouse);
        } else {
            throw new RuntimeException("Cant find ingredient on warehouse by this ingredient name");
        }

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void changeAmount(Ingredient ingredient, int delta, boolean increase) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select w from Warehouse w where w.ingredId =:ingredId");
        query.setParameter("ingredId", ingredient);
        Warehouse warehouse = (Warehouse) query.getResultList().get(0);
        if (warehouse != null) {
            warehouse.changeAmount(delta, increase);
            session.update(warehouse);
        } else {
            throw new RuntimeException("Cant find this ingredient on warehouse! Error!");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Warehouse findByName(String ingredientName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select w from Warehouse w where w.ingredId = " +
                "(select i.id from Ingredient i where i.name =:name)");
        query.setParameter("name", ingredientName);
        Warehouse warehouse = (Warehouse) query.getResultList().get(0);
        if (warehouse != null) {
            return warehouse;
        } else {
            throw new RuntimeException("Cant find ingredient on warehouse by this name");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Warehouse> getAllIngredients() {
        return sessionFactory.getCurrentSession().createQuery("select w from Warehouse w").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Warehouse> getEndingIngredients() {
        return sessionFactory.getCurrentSession().createQuery("select w from Warehouse w " +
                "where w.amount < 10").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean alreadyExist(Ingredient ingredient) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select w from Warehouse w where w.ingredId = " +
                "(select i.id from Ingredient i where i.name =:name)");
        query.setParameter("name", ingredient.getName());
        Warehouse warehouse = (Warehouse) query.getResultList().get(0);
        return null != warehouse;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setIngredientDAO(IngredientDAO ingredientDAO) {
        this.ingredientDAO = ingredientDAO;
    }
}
