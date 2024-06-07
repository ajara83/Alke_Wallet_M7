package com.monedero.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.monedero.modelo.Transaccion;
import com.monedero.modelo.Usuario;
import com.monedero.modelo.Cliente;
import com.monedero.repositorios.ClienteRepository;
import com.monedero.repositorios.TransaccionRepository;
import com.monedero.repositorios.UsuarioRepository;

import java.util.List;

@Service
public class TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Transaccion> obtenerTransaccionesPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Cliente cliente = clienteRepository.findByUsuario(usuario);
        return transaccionRepository.findByCuentaId(cliente.getCuenta().getId());
    }
}
