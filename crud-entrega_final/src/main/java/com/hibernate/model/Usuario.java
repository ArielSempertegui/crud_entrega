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
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase Usuario
 * @author Ariel Sempertegui
 *
 */
@Entity
@Table(name="usuario")
public class Usuario{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="nif", columnDefinition="CHAR(9)", nullable=false, unique=true)
	private String nif;
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@Column(name="email", nullable=false, unique=true)
	private String email;
	
	@Column(name="telefono", columnDefinition="CHAR(9)", nullable=false, unique=true)
	private String telefono;
	
	@Lob
	@Column(name="foto", columnDefinition="MEDIUMBLOB", nullable=false)
	private byte[] foto;
	
	@Enumerated(EnumType.STRING)
	@Column(name="estado")
	private Estado estado;
	
	@OneToMany(mappedBy="usuario", cascade=CascadeType.ALL)
	private List<Alquiler> alquileres;
	
	public Usuario() {
		super();
	}

	/**
	 * Constructor para crear un usuario inicialmente sin bici asignada
	 * @param nif
	 * @param nombre
	 * @param email
	 * @param telefono
	 * @param foto
	 */
	public Usuario(String nif, String nombre, String email, String telefono, byte[] foto) {
		super();
		this.nif = nif;
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.foto = foto;
		this.estado = Estado.LIBRE;
	}

	/**
	 * Devuelve el ID del usuario
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Devuelve el nif del usuario
	 * @return Número de Identificación Fiscal
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Devuelve el nombre del usuario
	 * @return Nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve el email del usuario
	 * @return Correo Electrónico
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Devuelve el número de teléfono del usuario
	 * @return Número de teléfono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Devuelve la foto de perfil del usuario
	 * @return Foto
	 */
	public byte[] getFoto() {
		return foto;
	}

	/**
	 * Devuelve el estado en el que se encuentra el usuario
	 * @return Libre o Ocupado
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * Devuelve los alquileres en los que ha participado el usuario
	 * @return
	 */
	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Permite modificar el nif
	 * @param nif
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * Permite modificar el nombre
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Permie modificar el email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Permie modificar el número de teléfono
	 * @param telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Permie modificar la foto de perfil
	 * @param foto
	 */
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	/**
	 * Permite modificar el estado del usuario
	 * @param estado
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public void setAlquileres(List<Alquiler> alquileres) {
		this.alquileres = alquileres;
	}

}