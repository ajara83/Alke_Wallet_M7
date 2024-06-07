package com.monedero.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDateTime fecha;
	private String tipo;
	private double monto;
	private Long cuentaOrigen;
	private Long cuentaDestino;
	
	public Transaccion(LocalDateTime fecha, String tipo, double monto, Long cuentaOrigen, Long cuentaDestino) {
		super();
		this.fecha = fecha;
		this.tipo = tipo;
		this.monto = monto;
		this.cuentaOrigen = cuentaOrigen;
		this.cuentaDestino = cuentaDestino;
	}

	public Transaccion() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Long getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(Long cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}

	public Long getCuentaDestino() {
		return cuentaDestino;
	}

	public void setCuentaDestino(Long cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}
	
	
	
	
	
	
	
}
