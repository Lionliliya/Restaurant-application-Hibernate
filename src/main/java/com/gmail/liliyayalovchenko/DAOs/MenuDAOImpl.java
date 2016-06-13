package com.gmail.liliyayalovchenko.DAOs;

import com.gmail.liliyayalovchenko.domainModel.Dish;
import com.gmail.liliyayalovchenko.domainModel.Menu;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MenuDAOImpl implements MenuDAO {

    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addNewMenu(String menuName, List<Dish> dishList) {
        Menu menu = new Menu();
        menu.setName(menuName);
        menu.setDishList(dishList);
        sessionFactory.getCurrentSession().save(menu);

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createMenu(Menu menu) {
        Set<Menu> allMenu = new HashSet<>(getAllMenu());
        if (!allMenu.contains(menu)) {
            sessionFactory.getCurrentSession().save(menu);
        } else {
            System.out.println("Menu already exist!");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeMenu(Menu menu) {
        sessionFactory.getCurrentSession().delete(menu);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Menu getMenuByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select m from Menu m where m.name =:menuName");
        query.setParameter("menuName", name);
        return (Menu) query.getResultList().get(0);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void showAllMenus() {
        sessionFactory.getCurrentSession().createQuery("select m from Menu m").list().
                forEach(System.out::println);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addDishToMenu(int menuId, Dish dish) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select m from Menu m where m.id =:menuId");
        query.setParameter("menuId", menuId);
        Menu menu = (Menu) query.getResultList().get(0);
        menu.addDishToMenu(dish);
        session.update(menu);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeDishFromMenu(int menuId, Dish dish) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select m from Menu m where m.id =:menuId");
        query.setParameter("menuId", menuId);
        Menu menu = (Menu) query.getResultList().get(0);
        menu.removeDishFromMenu(dish);
        session.update(menu);
    }

    @Override
    @Transactional
    public List<Menu> getAllMenu() {
        return sessionFactory.getCurrentSession().createQuery("select m from Menu m").list();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
