package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sqlComand = """
                CREATE TABLE IF NOT EXISTS users (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255),
                last_name VARCHAR(255),
                age TINYINT
                )
                """;
        try (Session session = Util.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sqlComand).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlComand = "DROP TABLE IF EXISTS users";
        try (Session session = Util.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sqlComand).executeUpdate();
            session.getTransaction().commit();;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(new User(name,lastName,age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.openSession()) {
            session.beginTransaction();
            Optional.ofNullable(session.get(User.class, id)).ifPresent(session::delete);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = Util.openSession()) {
            session.beginTransaction();

            users = session.createQuery("FROM User", User.class).list();

            session.getTransaction().commit();
        } catch (Exception e) {
            throw new HibernateException(e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
