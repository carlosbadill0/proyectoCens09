package com.cens.tareas.models.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import javax.persistence.Lob;;



@Entity
@Table(name="tarea")
public class Tarea implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotBlank
@Column(name="titulo")
private String titulo;


@Column(name="asignada")
private String asignada;


@DateTimeFormat(pattern = "yyyy-MM-dd")
@Column(name="fechainicio")
private LocalDate fechaInicio;


@DateTimeFormat(pattern = "yyyy-MM-dd")
@Column(name="fechafin")
private LocalDate fechaFin;


@Lob
@Column(name="descripcion")
private String descripcion;

@Column(name="horas")
private Integer horas;

@Column(name="completada")
private Boolean completada;



	
	public Tarea() {}


	public Long getId() {
		return id;
	}


	public String getTitulo() {
		return titulo;
}
public void setTitulo(String titulo) {
	this.titulo = titulo;
}
public String getAsignada() {
		return asignada;
}
public void setAsignada(String asignada) {
	this.asignada = asignada;
}
public LocalDate getFechaInicio() {
		return fechaInicio;
}
public void setFechaInicio(LocalDate fechaInicio) {
	this.fechaInicio = fechaInicio;
}
public LocalDate getFechaFin() {
		return fechaFin;
}
public void setFechaFin(LocalDate fechaFin) {
	this.fechaFin = fechaFin;
}
public String getDescripcion() {
		return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
public Integer getHoras() {
		return horas;
}
public void setHoras(Integer horas) {
	this.horas = horas;
}
public Boolean getCompletada() {
		return completada;
}
public void setCompletada(Boolean completada) {
	this.completada = completada;
}

	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarea other = (Tarea) obj;
		return Objects.equals(id, other.id);
	}


	private static final long serialVersionUID = 1L;
	
}
