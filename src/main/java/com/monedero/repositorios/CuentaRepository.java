package com.monedero.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monedero.modelo.Cliente;
import com.monedero.modelo.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta,Long> {
	Cuenta findByCliente(Cliente cliente);
}