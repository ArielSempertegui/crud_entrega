package com.hibernate.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Clase Alquiler
 * @author Ariel Sempertegui
 * 
 */
@Entity
@Table(name="alquiler")
public class Alquiler {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="fecha_hora_inicio", columnDefinition="DATETIME", nullable=false)
	private LocalDateTime fecha_hora_inicio;
	
	@Column(name="fecha_hora_fin", columnDefinition="DATETIME")
	private LocalDateTime fecha_hora_fin;

	@Column(name="importe")
	private double importe;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="bicicleta_id")
	private Bicicleta bicicleta;
	
	public Alquiler() {
		super();
	}

	/**
	 * Constructor que crea el alquiler asignandole una bici y usuario
	 * @param usuario
	 * @param bicicleta
	 */
	public Alquiler(Usuario usuario, Bicicleta bicicleta) {
		super();
		this.fecha_hora_inicio = LocalDateTime.now();
		this.fecha_hora_fin = null;
		this.importe = 0;
		this.usuario = usuario;
		this.bicicleta = bicicleta;
	}
	
	/**
	 * Devuelve el Id de Alquiler
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Devuelve la fecha y hora de inicio a la que se alquilo la bici
	 * @return fecha y hora inicial
	 */
	public LocalDateTime getFecha_hora_inicio() {
		return fecha_hora_inicio;
	}

	/**
	 * Devuelve la fecha y hora final en la que se devolvió la bici
	 * @return fecha y hora final
	 */
	public LocalDateTime getFecha_hora_fin() {
		return fecha_hora_fin;
	}

	/**
	 * Devuelve el importe o costo total tras devolver la bici
	 * @return importe
	 */
	public double getImporte() {
		return importe;
	}

	/**
	 * Devuelve el usuario asociado al alquiler
	 * @return usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Devuelve la bicicleta asociada al alquiler
	 * @return bicicleta
	 */
	public Bicicleta getBicicleta() {
		return bicicleta;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	public void setFecha_hora_inicio(LocalDateTime fecha_hora_inicio) {
		this.fecha_hora_inicio = fecha_hora_inicio;
	}

	/**
	 * Sirve para asignar la fecha y hora de cierre del alquiler
	 * @param fecha_hora_fin
	 */
	public void setFecha_hora_fin(LocalDateTime fecha_hora_fin) {
		this.fecha_hora_fin = fecha_hora_fin;
	}

	/**
	 * Permite asignar el importe al alquiler
	 * @param importe
	 */
	public void setImporte(double importe) {
		this.importe = importe;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setBicicleta(Bicicleta bicicleta) {
		this.bicicleta = bicicleta;
	}

}
