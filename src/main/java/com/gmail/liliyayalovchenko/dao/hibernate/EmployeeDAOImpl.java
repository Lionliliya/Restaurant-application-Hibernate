package com.gmail.liliyayalovchenko.dao.hibernate;

import com.gmail.liliyayalovchenko.dao.EmployeeDAO;
import com.gmail.liliyayalovchenko.domain.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private SessionFactory sessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDAOImpl.class);

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(Employee employee) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            LOGGER.info("Session is got.");
            currentSession.save(employee);
            LOGGER.info("Employee is saved successfully!");
        } catch (HibernateException ex) {
            System.out.println("Cant save employee. Error.");
            LOGGER.error("Error wile saving Employee " + ex + Arrays.toString(ex.getStackTrace()));
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee getById(Long id) {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            LOGGER.info("Session is got.");
            Employee result =  currentSession.load(Employee.class, id);
            return result;
        } catch (HibernateException ex) {
            System.out.println("Cant get employee by id. Error.");
            LOGGER.error("Cannot get employee by id " + ex + "\n" + Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> findAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        LOGGER.info("Session is got");
        List<Employee> list = currentSession.createQuery("SELECT E FROM Employee E").list();
        LOGGER.info("List of all employee is successfully got");
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeEmployee(String firstName, String secondName) {
        Session session = sessionFactory.getCurrentSession();
        LOGGER.info("Session is got");
        Query query = session.createQuery
                ("select e from Employee e where e.firstName =:firstName and e.secondName =:secondName");
        query.setParameter("firstName", firstName);
        query.setParameter("secondName", secondName);
        Employee employee = (Employee) query.getResultList().get(0);
        session.delete(employee);
        LOGGER.info("Employee is deleted.");
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee findByName(String firstName, String secondName) {
        Session session = sessionFactory.getCurrentSession();
        LOGGER.info("Session is got.");
        Query query = session.createQuery("select  e from Employee e where " +
                "e.secondName =:var1 and e.firstName =:var2");

        query.setParameter("var1", secondName);
        query.setParameter("var2", firstName);
        Employee employee = (Employee) query.getResultList().get(0);
        LOGGER.info("Employee is got successfully.");
        return employee;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
