package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    SessionFactory factory = Util.getFactory();

    @Override
    public void createUsersTable() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("create table if not exists users " +
                            "(id bigint not null auto_increment," +
                            " name varchar(50), " +
                            "lastName varchar(50)," +
                            "age tinyint, primary key (id))")
                    .executeUpdate();
            try {
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                try {
                    session.getTransaction().rollback();
                } catch (RuntimeException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("drop table if exists users").executeUpdate();
            try {
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                try {
                    session.getTransaction().rollback();
                } catch (RuntimeException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем - " + name + " добавлен в базу данных");
            try {
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                try {
                    session.getTransaction().rollback();
                } catch (RuntimeException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.getCurrentSession()) {
            User temp = session.get(User.class, id);
            session.delete(temp);
            try {
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                try {
                    session.getTransaction().rollback();
                } catch (RuntimeException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<User> usersList = session.createQuery("from User").getResultList();
            try {
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                try {
                    session.getTransaction().rollback();
                } catch (RuntimeException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            return usersList;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            try {
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                session.getTransaction().rollback();
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
