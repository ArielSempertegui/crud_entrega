package com.hibernate.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.model.Alquiler;
import com.hibernate.util.HibernateUtil;

/**
 * DAO de Alquiler
 * @author Ariel Sempertegui
 *
 */
public class AlquilerDAO {

	/**
	 * Insertar nuevo Alquiler
	 * @param a
	 */
	public void insertAlquiler(Alquiler a) {
		
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(a);
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * Actualizar Alquiler
	 * @param a
	 */
	public void updateAlquiler(Alquiler a) {
		
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(a);
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * Borrar alquiler por su id
	 * @param id
	 */
	public void deleteAlquilerById(int id) {
		
		Transaction transaction = null;
		Alquiler a = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			a = session.get(Alquiler.class, id);
			session.remove(a);
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * Obtener un alquiler por su id
	 * @param id
	 * @return Alquiler
	 */
	public Alquiler selectAlquilerById(int id) {
		
		Transaction transaction = null;
		Alquiler a = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			a = session.get(Alquiler.class, id);
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		return a;
	}
	
	/**
	 * Obtiene el alquiler en el que aparece una bicicleta dado su id que esté siendo alquilada
	 * @param idBicicleta
	 * @return Alquiler
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public Alquiler selectAlquilerByBicicletaId(int idBicicleta) {
		
		Transaction transaction = null;
		Query<Alquiler> query = null;
		Alquiler a = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			query = session.createQuery("from Alquiler where fecha_hora_fin is null and bicicleta.id = :idBicicleta");
			query.setParameter("idBicicleta", idBicicleta);
			a = query.uniqueResult();
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		return a;
	}
	
	/**
	 * Devuelve todos los alquileres existentes
	 * @return Lista de Alquileres
	 */
	public List<Alquiler> selectAllAlquileres(){
		
		Transaction transaction = null;
		List<Alquiler> alquileres = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			alquileres = session.createQuery("from Alquiler", Alquiler.class).getResultList();
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		return alquileres;
	}
}

