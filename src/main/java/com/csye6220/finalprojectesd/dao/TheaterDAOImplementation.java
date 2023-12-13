package com.csye6220.finalprojectesd.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.csye6220.finalprojectesd.model.Theater;
import com.csye6220.finalprojectesd.util.HibernateUtil;

@Repository
public class TheaterDAOImplementation implements TheaterDAO {

	private final SessionFactory sessionFactory;

    public TheaterDAOImplementation() {
        this.sessionFactory = HibernateUtil.buildSessionFactory();
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

	@Override
	public List<Theater> searchTheatersByNameOrMovieAvailability(String searchTerm) {
	    try (Session session = sessionFactory.openSession()) {
	        return session.createQuery(
	                "SELECT DISTINCT t " +
	                        "FROM Theater t " +
	                        "LEFT JOIN FETCH t.showtimes s " +
	                        "LEFT JOIN FETCH s.movie m " +
	                        "WHERE lower(t.name) LIKE :searchTerm " +
	                        "OR lower(m.title) LIKE :searchTerm", Theater.class)
	                .setParameter("searchTerm", "%" + searchTerm.toLowerCase() + "%")
	                .getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}
}
