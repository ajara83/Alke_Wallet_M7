package com.monedero.controladores;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.monedero.modelo.Cliente;
import com.monedero.modelo.Cuenta;
import com.monedero.modelo.Rol;
import com.monedero.modelo.Usuario;
import com.monedero.repositorios.ClienteRepository;
import com.monedero.repositorios.CuentaRepository;
import com.monedero.repositorios.RolRepository;
import com.monedero.repositorios.UsuarioRepository;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private CuentaRepository cuentaRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "clientes/login";
    }

    @GetMapping("/registerform")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "nuevoCliente";
    }

    /*@PostMapping("/register")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // Asignar rol USUARIO a nuevos usuarios
        Rol userRole = rolRepository.findByName("USUARIO");
        Set<Rol> roles = new HashSet<>();
        roles.add(userRole);
        usuario.setRoles(roles);
        
        usuarioRepository.save(usuario);
        return "redirect:/login";
    }*/
    
    /*@PostMapping("/register")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // Asignar rol USUARIO a nuevos usuarios
        Rol userRole = rolRepository.findByName("USUARIO");
        Set<Rol> roles = new HashSet<>();
        roles.add(userRole);
        usuario.setRoles(roles);
        
        usuarioRepository.save(usuario);

        // Crear Cliente vinculado al Usuario
        Cliente cliente = new Cliente();
        cliente.setNombre(usuario.getUsername()); // o cualquier otra lógica para inicializar el Cliente
        cliente.setUsuario(usuario);
        
        // Crear Cuenta vinculada al Cliente
        Cuenta cuenta = new Cuenta();
        cuenta.setTitular(usuario.getUsername());
        cuenta.setSaldo(0.0); // Inicialmente el saldo puede ser cero
        cliente.setCuenta(cuenta);
        
        cuenta.setCliente(cliente);
        
        // Guardar Cliente y Cuenta
        cuentaRepository.save(cuenta);
        clienteRepository.save(cliente);

        return "redirect:/login";
    }*/
    
    @PostMapping("/register")
    public String registrarUsuario(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("nombre") String nombre,
            @RequestParam("rut") String rut,
            @RequestParam("email") String email) {

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));

        // Asignar rol USUARIO a nuevos usuarios
        Rol userRole = rolRepository.findByName("USUARIO");
        Set<Rol> roles = new HashSet<>();
        roles.add(userRole);
        usuario.setRoles(roles);

        usuarioRepository.save(usuario);

        // Crear Cliente vinculado al Usuario
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setUsuario(usuario);
        cliente.setRut(rut);

        // Crear Cuenta vinculada al Cliente
        Cuenta cuenta = new Cuenta();
        cuenta.setTitular(username);
        cuenta.setSaldo(0.0); // Inicialmente el saldo puede ser cero
        cuenta.setUSD(false);
        cliente.setCuenta(cuenta);

        cuenta.setCliente(cliente);

        // Guardar Cliente y Cuenta
        cuentaRepository.save(cuenta);
        clienteRepository.save(cliente);

        return "redirect:/login";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}
