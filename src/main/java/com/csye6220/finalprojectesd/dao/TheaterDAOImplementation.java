package com.csye6220.finalprojectesd.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.csye6220.finalprojectesd.config.HibernateConfig;
import com.csye6220.finalprojectesd.model.Booking;
import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Theater;

@Component
public class TheaterDAOImplementation implements TheaterDAO {

	private final SessionFactory sessionFactory;

    public TheaterDAOImplementation() {
        this.sessionFactory = HibernateConfig.buildSessionFactory();
    }
    
	@Override
	public void saveTheater(Theater theater) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(theater);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

	@Override
	public Theater getTheaterById(Long id) {
		try (Session session = sessionFactory.openSession()) {
            return session.get(Theater.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public List<Theater> getAllTheaters() {
		try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Theater", Theater.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public void updateTheater(Theater theater) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(theater);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

	@Override
	public void deleteTheater(Long id) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Theater theater = session.get(Theater.class, id);
            if (theater != null) {
                session.remove(theater);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

}
