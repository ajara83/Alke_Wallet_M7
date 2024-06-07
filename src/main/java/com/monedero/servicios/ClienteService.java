package com.monedero.servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monedero.modelo.Cliente;
import com.monedero.modelo.Transaccion;
import com.monedero.repositorios.ClienteRepository;
import com.monedero.repositorios.TransaccionRepository;

@Service
public class ClienteService {
	
	
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private TransaccionRepository transaccionRepository;
	
	public List<Cliente> obtenerTodosLosClientes(){
		return clienteRepository.findAll();
	}
	
	public List<Cliente> obtenerClientesTransferencia(Integer id){
		return clienteRepository.findByIdNot(id);
	}
	
	public Optional<Cliente> obtenerClientePorId(Integer id){
		return clienteRepository.findById(id);
	}
	
	
	
	public Cliente guardar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public void eliminar(Integer id) {
		clienteRepository.deleteById(id);
	}
	
	public boolean depositarFondos(Integer idCliente, double monto) {
		
		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(()-> new IllegalArgumentException("Cliente no encontrado"));
		cliente.getCuenta().depositar(monto);
		clienteRepository.save(cliente);
		
		Transaccion transaccion = new Transaccion(LocalDateTime.now(),"DEPOSITO",monto,cliente.getCuenta().getId(), null);
		transaccionRepository.save(transaccion);
		return true;
	}
	
	public void retirarFondos(Integer idCliente, double monto) {
		Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(()-> new IllegalArgumentException("Cliente no encontrado"));
		cliente.getCuenta().retirar(monto);
		clienteRepository.save(cliente);
		
		Transaccion transaccion = new Transaccion(LocalDateTime.now(),"RETIRO",monto,cliente.getCuenta().getId(), null);
		transaccionRepository.save(transaccion);
	}
	
	public boolean transferirFondosEntreCuentas(Integer idClienteOrigen, Integer idClienteDestino, double monto ) {
		Cliente clienteOrigen = clienteRepository.findById(idClienteOrigen).orElseThrow(()-> new IllegalArgumentException("Cliente no encontrado"));
		Cliente clienteDestino = clienteRepository.findById(idClienteDestino).orElseThrow(()-> new IllegalArgumentException("Cliente no encontrado"));
		
		if(clienteOrigen.getCuenta().getSaldo() >= monto) {
			clienteOrigen.getCuenta().retirar(monto);
			clienteDestino.getCuenta().depositar(monto);
			
			clienteRepository.save(clienteOrigen);
			clienteRepository.save(clienteDestino);
			
			Transaccion transaccion = new Transaccion(LocalDateTime.now(),"TRANSFERENCIA",monto,clienteOrigen.getCuenta().getId(), clienteDestino.getCuenta().getId());
			transaccionRepository.save(transaccion);	
			
			return true;
		}
		
		return false;
	}
	
	
	
	
	
	
	
}
