package com.csye6220.finalprojectesd.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.csye6220.finalprojectesd.model.Review;
import com.csye6220.finalprojectesd.model.User;
import com.csye6220.finalprojectesd.model.UserRole;
import com.csye6220.finalprojectesd.util.HibernateUtil;

@Repository
public class UserDAOImplementation implements UserDAO {

	private final SessionFactory sessionFactory;

    public UserDAOImplementation() {
        this.sessionFactory = HibernateUtil.buildSessionFactory();
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
	public User getUserByEmail(String email) {
		try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User WHERE LOWER(email) = :email", User.class)
                    .setParameter("email", email.toLowerCase())
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	@Override
	public User getUserByUsername(String username) {
		try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User WHERE LOWER(username) = :username", User.class)
                    .setParameter("username", username.toLowerCase())
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	@Override
	public User getUserByUsernameOrEmail(String usernameOrEmail) {
		try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User WHERE LOWER(username) = :name or LOWER(email) =:name", User.class)
                    .setParameter("name", usernameOrEmail.toLowerCase())
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
