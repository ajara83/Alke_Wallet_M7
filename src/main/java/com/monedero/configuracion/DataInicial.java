package com.monedero.configuracion;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.monedero.modelo.Rol;
import com.monedero.modelo.Usuario;
import com.monedero.repositorios.RolRepository;
import com.monedero.repositorios.UsuarioRepository;

@Configuration
public class DataInicial {
	
	 @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean
    public CommandLineRunner inicializarData() {
        return args -> {
            // Crear rol ADMIN si no existe
            Rol adminRole = rolRepository.findByName("ADMIN");
            if (adminRole == null) {
                adminRole = new Rol();
                adminRole.setName("ADMIN");
                rolRepository.save(adminRole);
            }

            // Crear rol USUARIO si no existe
            Rol userRole = rolRepository.findByName("USUARIO");
            if (userRole == null) {
                userRole = new Rol();
                userRole.setName("USUARIO");
                rolRepository.save(userRole);
            }

            // Crear usuario admin si no existe
            if (usuarioRepository.findByUsername("admin") == null) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                Set<Rol> roles = new HashSet<>();
                roles.add(adminRole);
                admin.setRoles(roles);
                usuarioRepository.save(admin);
            }
        };
    }

}
