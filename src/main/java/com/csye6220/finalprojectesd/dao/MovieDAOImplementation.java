package com.csye6220.finalprojectesd.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.csye6220.finalprojectesd.model.Genre;
import com.csye6220.finalprojectesd.model.Movie;
import com.csye6220.finalprojectesd.model.Review;
import com.csye6220.finalprojectesd.util.HibernateUtil;

@Repository
public class MovieDAOImplementation implements MovieDAO {

	private final SessionFactory sessionFactory;

    public MovieDAOImplementation() {
        this.sessionFactory = HibernateUtil.buildSessionFactory();
    }
    
	@Override
	public void saveMovie(Movie movie) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(movie);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

	@Override
	public Movie getMovieById(Long id) {
		try (Session session = sessionFactory.openSession()) {
            return session.get(Movie.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public List<Movie> getAllMovies() {
		try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Movie", Movie.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public void updateMovie(Movie movie) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(movie);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
	}

	@Override
	public void deleteMovie(Long id) {
		Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Movie movie = session.get(Movie.class, id);
            if (movie != null) {
                session.remove(movie);
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
	public List<Movie> findByMovieName(String movieName) {
		try (Session session = sessionFactory.openSession()) {
			System.out.println(movieName);
            return session.createQuery("FROM Movie WHERE LOWER(title) LIKE LOWER(:name)", Movie.class)
                    .setParameter("name", "%" + movieName + "%")
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public List<Movie> findByGenre(Genre genre) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Movie WHERE genre = :genre", Movie.class)
	                .setParameter("genre", genre)
	                .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	@Override
	public List<Movie> findByGenreAndName(Genre genre, String movieName) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM Movie WHERE genre = :genre and LOWER(title) LIKE LOWER(:name)", Movie.class)
	                .setParameter("genre", genre)
	                .setParameter("name", "%" + movieName + "%")
	                .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public List<Review> findReviewsByMovie(Long id) {
		try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Review WHERE movie.movieId = :movieId", Review.class)
            		.setParameter("movieId", id)
            		.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

}
