package com.monedero.controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.monedero.repositorios.TransaccionRepository;

@Controller
@RequestMapping("/cartola")
public class TransaccionController {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @GetMapping
    public String listarTransacciones(Model model) {
        model.addAttribute("transacciones", transaccionRepository.findAll());
        return "transacciones/cartola";
    }
}
