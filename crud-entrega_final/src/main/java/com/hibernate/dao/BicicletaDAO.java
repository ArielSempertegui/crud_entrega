package com.hibernate.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.model.Bicicleta;
import com.hibernate.model.Estado;
import com.hibernate.util.HibernateUtil;

/**
 * DAO de Bicicleta
 * @author Ariel Sempertegui
 *
 */
public class BicicletaDAO {

	/**
	 * Insertar nueva bicicleta
	 * @param b
	 */
	public void insertBicicleta(Bicicleta b) {
		
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(b);
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * Actualizar Bicicleta
	 * @param b
	 */
	public void updateBicicleta(Bicicleta b) {
		
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(b);
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * Borrar Bicicleta
	 * @param id
	 */
	public void deleteBicicletaById(int id) {
		
		Transaction transaction = null;
		Bicicleta b = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			b = session.get(Bicicleta.class, id);
			session.remove(b);
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * Obtener una bicicleta por su id
	 * @param id
	 * @return Bicicleta
	 */
	public Bicicleta selectBicicletaById(int id) {
		
		Transaction transaction = null;
		Bicicleta b = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			b = session.get(Bicicleta.class, id);
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		return b;
	}
	
	/**
	 * Devuelve todas las bicicletas existentes
	 * @return Lista de Bicicletas
	 */
	public List<Bicicleta> selectAllBicicletas(){
		
		Transaction transaction = null;
		List<Bicicleta> bicicletas = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			bicicletas = session.createQuery("from Bicicleta", Bicicleta.class).getResultList();
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		return bicicletas;
	}
	
	/**
	 * Devuelve todas las bicicletas que se encuentran libres para alquilar
	 * @return Lista de Bicicletas
	 */
	public List<Bicicleta> selectAllBicicletasLibres(){
		
		Transaction transaction = null;
		Query<Bicicleta> query = null;
		List<Bicicleta> bicicletas = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			query = session.createQuery("from Bicicleta where estado = :estado", Bicicleta.class);
			query.setParameter("estado", Estado.LIBRE);
			bicicletas = query.getResultList();
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		return bicicletas;
	}
	
	/**
	 * Devuelve todas las bicicletas que se encuentras alquiladas
	 * @return Lista de Bicicletas
	 */
	public List<Bicicleta> selectAllBicicletasOcupadas(){
		
		Transaction transaction = null;
		Query<Bicicleta> query = null;
		List<Bicicleta> bicicletas = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			query = session.createQuery("from Bicicleta where estado = :estado", Bicicleta.class);
			query.setParameter("estado", Estado.OCUPADO);
			bicicletas = query.getResultList();
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		return bicicletas;
	}
}

