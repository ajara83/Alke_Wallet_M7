package com.monedero.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.monedero.modelo.Cliente;
import com.monedero.modelo.Cuenta;
import com.monedero.modelo.Usuario;
import com.monedero.repositorios.ClienteRepository;
import com.monedero.repositorios.CuentaRepository;
import com.monedero.repositorios.UsuarioRepository;
import com.monedero.servicios.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired 
	private ClienteService clienteService;
	
	 @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaRepository cuentaRepository;
	
	
	@GetMapping("/nuevo")
	public String mostrarFormularioCrearCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "clientes/crear";
	}
	
	@PostMapping("/guardar")
	public String guardarCliente(@ModelAttribute("cliente") Cliente cliente) {
		clienteService.guardar(cliente);
		return "redirect:/clientes";
	}
	
	@GetMapping
	public String listarClientes(Model model) {
		model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
		return "clientes/listar";
	}
	
	@GetMapping("/{id}")
    public String verCliente(@PathVariable Integer id, Model model) {
        model.addAttribute("cliente", clienteService.obtenerClientePorId(id).orElse(null));
        return "clientes/ver";
    }
	
	@GetMapping("/transferir")
    public String mostrarFormularioTransferencia(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication != null) {
            username = authentication.getPrincipal().toString();
        }
        
        Usuario usuario = usuarioRepository.findByUsername(username);
        Cliente cliente = clienteRepository.findByUsuario(usuario);
        model.addAttribute("clienteActual", cliente);
        
		
        model.addAttribute("clientes", clienteService.obtenerClientesTransferencia(cliente.getId()));
        return "clientes/transferir";
    }

    @PostMapping("/transferir")
    public String transferirFondos(@RequestParam Integer idClienteOrigen, @RequestParam Integer idClienteDestino, @RequestParam double monto) {
        if (clienteService.transferirFondosEntreCuentas(idClienteOrigen, idClienteDestino, monto)) {
            return "redirect:/clientes/transferir?exito";
        } else {
            return "redirect:/clientes/transferir?error";
        }
    }
    
    @GetMapping("/deposito")
    public String mostrarFormularioDeposito(Model model) {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication != null) {
            username = authentication.getPrincipal().toString();
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
        
        return "clientes/deposito";
    }
    
    @PostMapping("/deposito")
    public String depositarFondos(@RequestParam("idCuenta") Integer idCuenta,
                                  @RequestParam("montoDeposito") double montoDeposito,
                                  RedirectAttributes redirectAttributes) {
        try {
            boolean success = clienteService.depositarFondos(idCuenta, montoDeposito);
            if (success) {
                redirectAttributes.addFlashAttribute("message", "Depósito realizado con éxito.");
            } else {
                redirectAttributes.addFlashAttribute("message", "El depósito no se pudo realizar.");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/clientes/deposito";
    }
    

}
