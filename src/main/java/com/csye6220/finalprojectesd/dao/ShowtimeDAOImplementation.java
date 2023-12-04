package com.csye6220.finalprojectesd.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Showtime;
import com.csye6220.finalprojectesd.model.Theater;
import com.csye6220.finalprojectesd.util.HibernateUtil;

@Repository
public class ShowtimeDAOImplementation implements ShowtimeDAO {

	private final SessionFactory sessionFactory;

    public ShowtimeDAOImplementation() {
        this.sessionFactory = HibernateUtil.buildSessionFactory();
    }
    
	@Override
	public void saveShowtime(Showtime showtime) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(showtime);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

	@Override
	public Showtime getShowtimeById(Long id) {
		try (Session session = sessionFactory.openSession()) {
            return session.get(Showtime.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	@Override
	public List<Showtime> getAllShowtimes() {
		try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Showtime", Showtime.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	@Override
	public List<Showtime> getAllShowtimesByMovie(Movie movie) {
		 try (Session session = sessionFactory.openSession()) {
	            return session.createQuery("FROM Showtime WHERE movie.movieId = :movieId", Showtime.class)
	            		.setParameter("movieId", movie.getMovieId())
	            		.getResultList();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	}
	
	@Override
	public List<Showtime> getAllShowtimesByTheater(Theater thaterId) {
		try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Showtime WHERE theater.thaterId = :thaterId", Showtime.class)
            		.setParameter("thaterId", thaterId.getTheaterId())
            		.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public void updateShowtime(Showtime showtime) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(showtime);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

	@Override
	public void deleteShowtime(Showtime showtime) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(showtime);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

}
