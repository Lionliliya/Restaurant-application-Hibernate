package com.gmail.liliyayalovchenko.dao.hibernate;

import com.gmail.liliyayalovchenko.dao.EmployeeDAO;
import com.gmail.liliyayalovchenko.domain.Employee;
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
        Session currentSession = sessionFactory.getCurrentSession();
        Employee employee =  currentSession.load(Employee.class, id);
        if (employee == null) {
            throw new RuntimeException("Cant get Employee by this id. Wrong id!");
        } else {
            return employee;
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Employee> findAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        List<Employee> list = currentSession.createQuery("SELECT E FROM Employee E").list();
        return list;
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
        if (employee != null) {
            session.delete(employee);
        } else {
            throw new RuntimeException("Cant find employee by this name. Error!");
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Employee findByName(String firstName, String secondName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select  e from Employee e where " +
                "e.secondName =:var1 and e.firstName =:var2");

        query.setParameter("var1", secondName);
        query.setParameter("var2", firstName);
        Employee employee = (Employee) query.getResultList().get(0);
        if (employee == null) {
            throw new RuntimeException("Cant get employee by this name!Wrong name!");
        } else {
            return employee;
        }
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
