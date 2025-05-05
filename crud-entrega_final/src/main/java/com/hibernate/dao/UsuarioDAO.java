package com.hibernate.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.hibernate.model.Estado;
import com.hibernate.model.Usuario;
import com.hibernate.util.HibernateUtil;

/**
 * DAO de Usuario
 * @author Ariel Sempertegui
 *
 */
public class UsuarioDAO {

	/**
	 * Insertar nuevo usuario
	 * @param u
	 */
	@SuppressWarnings("deprecation")
	public void insertUsuario(Usuario u) {
		
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(u);
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * Actualizar Usuario
	 * @param u
	 */
	public void updateUsuario(Usuario u) {
		
		Transaction transaction = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(u);
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * Borrar usuario por su id
	 * @param id
	 */
	public void deleteUsuarioById(int id) {
		
		Transaction transaction = null;
		Usuario u = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			u = session.get(Usuario.class, id);
			session.remove(u);
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * Obtener usuario por su id
	 * @param id
	 * @return Usuario
	 */
	public Usuario selectUsuarioById(int id) {
		
		Transaction transaction = null;
		Usuario u = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			u = session.get(Usuario.class, id);
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		return u;
	}
	
	/**
	 * Devuelve el usuario con un NIF determinado que le pases de parámetro
	 * @param nif
	 * @return Usuario
	 */
	public Usuario selectUsuarioByNif(String nif) {
		
		Transaction transaction = null;
		Query<Usuario> query = null;
		Usuario u = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			query = session.createQuery("from Usuario where nif = :nif", Usuario.class);
			query.setParameter("nif", nif);
			u = query.uniqueResult();
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		return u;
	}
	
	/**
	 * Devuelve todos los usuarios existentes
	 * @return Lista de Usuarios
	 */
	public List<Usuario> selectAllUsuarios(){
		
		Transaction transaction = null;
		List<Usuario> usuarios = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			usuarios = session.createQuery("from Usuario", Usuario.class).getResultList();
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		return usuarios;
	}
	
	/**
	 * Devuelve todos los usuarios que se encuentren libres para poder alquilar una bici
	 * @return Lista de Usuario
	 */
	public List<Usuario> selectAllUsuarioLibres(){
		
		Transaction transaction = null;
		Query<Usuario> query = null;
		List<Usuario> usuarios = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			query = session.createQuery("from Usuario where estado = :estado", Usuario.class);
			query.setParameter("estado", Estado.LIBRE);
			usuarios = query.getResultList();
			transaction.commit();
		} catch(Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
		}
		
		return usuarios;
	}
}
