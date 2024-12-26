package com.example.LiterAlura.controller;

import com.example.LiterAlura.model.Libro;
import com.example.LiterAlura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @PostMapping
    public Libro agregarLibro(@RequestBody Libro libro) {
        return libroService.insertarLibroConAutor(libro);
    }

    // Agrega más endpoints si es necesario
}
