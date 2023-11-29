package com.csye6220.finalprojectesd.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.csye6220.finalprojectesd.config.HibernateConfig;
import com.csye6220.finalprojectesd.model.Review;
import com.csye6220.finalprojectesd.model.User;
import com.csye6220.finalprojectesd.model.UserRole;

@Component
public class UserDAOImplementation implements UserDAO {

	private final SessionFactory sessionFactory;

    public UserDAOImplementation() {
        this.sessionFactory = HibernateConfig.buildSessionFactory();
    }
    
	@Override
	public void saveUser(User user) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

	@Override
	public User getUserByUsername(String username) {
		try (Session session = sessionFactory.openSession()) {
            String hql = "FROM User WHERE username = :username";
            return session.createQuery(hql, User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public User getUserById(Long id) {
		try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public List<User> getUserByRole(UserRole role) {
		try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User WHERE role = :role", User.class)
                    .setParameter("role", role)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public List<User> getAllUsers() {
		try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public void updateUser(User user) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

	@Override
	public void deleteUser(User user) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

	@Override
	public List<Review> findReviewsByUser(User user) {
		try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Review WHERE user = :user";
            return session.createQuery(hql, Review.class)
                    .setParameter("user", user)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

}
