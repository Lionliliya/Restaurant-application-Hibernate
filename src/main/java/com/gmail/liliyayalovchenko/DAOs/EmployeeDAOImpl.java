package com.gmail.liliyayalovchenko.DAOs;

import com.gmail.liliyayalovchenko.domainModel.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private SessionFactory sessionFactory;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(Employee employee) {
        sessionFactory.getCurrentSession().save(employee);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee getById(Long id) {
        Employee result =  sessionFactory.getCurrentSession().load(Employee.class, id);
        if (null != result) {
            return result;
        } else {
            throw new RuntimeException("Cant get employee by this id. No employee is found");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> findAll() {
        return sessionFactory.getCurrentSession().createQuery("SELECT E FROM Employee E").list();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeEmployee(String firstName, String secondName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery
                ("select e from Employee e where e.firstName =:firstName and e.secondName =:secondName");
        query.setParameter("firstName", firstName);
        query.setParameter("secondName", secondName);
        Employee employee = (Employee) query.getResultList().get(0);
        session.delete(employee);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee findByName(String firstName, String secondName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select  e from Employee e where " +
                "e.secondName =:var1 and e.firstName =:var2");

        query.setParameter("var1", secondName);
        query.setParameter("var2", firstName);
        return (Employee) query.getResultList().get(0);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
