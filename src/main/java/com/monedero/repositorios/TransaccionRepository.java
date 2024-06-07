package com.monedero.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.monedero.modelo.Transaccion;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion,Long> {
	//List<Transaccion> findByCuentaOrigenIdOrCuentaDestinoId(Long cuentaOrigenId, Long cuentaDestinoId);
	
	 @Query("SELECT t FROM Transaccion t WHERE t.cuentaOrigen = :cuentaId OR t.cuentaDestino = :cuentaId")
    List<Transaccion> findByCuentaId(@Param("cuentaId") Long cuentaId);
}
