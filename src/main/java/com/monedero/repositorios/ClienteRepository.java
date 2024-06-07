package com.monedero.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monedero.modelo.Cliente;
import com.monedero.modelo.Usuario;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
	Cliente findByUsuario(Usuario usuario);
	Optional<Cliente> findByCuentaId(Integer cuentaId);
	List<Cliente> findByIdNot(Integer id);
	//Cliente findByNombre(String nombre);
}
