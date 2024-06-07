package com.monedero.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monedero.modelo.Rol;

public interface RolRepository extends JpaRepository<Rol,Integer>{

	Rol findByName(String name);
}
