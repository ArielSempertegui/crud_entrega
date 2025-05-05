package com.hibernate.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase Bicicleta
 * @author Ariel Sempertegui
 *
 */
@Entity
@Table(name="bicicleta")
public class Bicicleta {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="estado")
	private Estado estado;
	
	@OneToMany(mappedBy="bicicleta", cascade=CascadeType.ALL)
	private List<Alquiler> alquileres;
	
	/**
	 * Constructor vacío que crea la bici inicialmente libre
	 */
	public Bicicleta() {
		super();
		estado = Estado.LIBRE;
	}

	/**
	 * Devuelve el ID de la bicicleta
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Devuelve el estado de la bicicleta
	 * @return estado (libre o ocupado)
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * Devuelve los alquileres en los que aparece esta bici
	 * @return
	 */
	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Permite cambiar el estado de la bicicleta
	 * @param estado
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public void setAlquileres(List<Alquiler> alquileres) {
		this.alquileres = alquileres;
	}

}

