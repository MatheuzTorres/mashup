package br.ufrrj.mashup.controller;

import br.ufrrj.mashup.service.MashupService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mashup")
public class MashupController {

    private final MashupService service;

    public MashupController(MashupService service) {
        this.service = service;
    }

    @GetMapping
    public double calcularVenda(
            @RequestParam int caixas,
            @RequestParam double distancia
    ) {
        return service.calcularVendaFinal(caixas, distancia);
    }
}
