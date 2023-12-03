package com.csye6220.finalprojectesd.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.csye6220.finalprojectesd.model.Booking;
import com.csye6220.finalprojectesd.util.HibernateUtil;

@Repository
public class BookingDAOImplementation implements BookingDAO {

	private final SessionFactory sessionFactory;

    public BookingDAOImplementation() {
        this.sessionFactory = HibernateUtil.buildSessionFactory();
    }
    
	@Override
	public void saveBooking(Booking booking) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(booking);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

	@Override
	public Booking getBookingById(Long bookingId) {
		try (Session session = sessionFactory.openSession()) {
            return session.get(Booking.class, bookingId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public Booking getBookingByUserId(Long userId) {
		try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Booking WHERE user.userId = :userId", Booking.class)
                    .setParameter("userId", userId)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public List<Booking> getAllBookings() {
		try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Booking", Booking.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public void updateBooking(Booking booking) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(booking);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

	@Override
	public void deleteBooking(Booking booking) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(booking);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

}
