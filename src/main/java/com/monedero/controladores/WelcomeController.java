package com.monedero.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.monedero.modelo.Cliente;
import com.monedero.modelo.Cuenta;
import com.monedero.modelo.Usuario;
import com.monedero.repositorios.ClienteRepository;
import com.monedero.repositorios.CuentaRepository;
import com.monedero.repositorios.UsuarioRepository;

@Controller
public class WelcomeController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @GetMapping("/")
    public String welcome(Model model) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication != null) {
        	return "/clientes/login"; 
        }

        if (username != null) {
            // Obtener el usuario
            Usuario usuario = usuarioRepository.findByUsername(username);
            if (usuario != null) {
                // Obtener el cliente asociado
                Cliente cliente = clienteRepository.findByUsuario(usuario);
                
                // Obtener la cuenta asociada al cliente
                Cuenta cuenta = null;
                if (cliente != null) {
                    cuenta = cuentaRepository.findByCliente(cliente);
                }

                // Pasar los datos a la vista
                model.addAttribute("usuario", usuario);
                model.addAttribute("cliente", cliente);
                model.addAttribute("cuenta", cuenta);
            }
        }

        return "home"; 
    }
}

