package com.example.LiterAlura.service;

import com.example.LiterAlura.model.Autor;
import com.example.LiterAlura.model.Libro;
import com.example.LiterAlura.repository.AutorRepository;
import com.example.LiterAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroRepository libroRepository;

    public Libro insertarLibroConAutor(Libro libro) {
        // Verificamos si el autor ya existe, si no, lo insertamos
        Autor autor = libro.getAutor();

        // Buscamos el autor por su nombre
        if (autor.getNombre() == null || autorRepository.getByNombre(autor.getNombre()) == null) {
            // Si no existe, lo guardamos
            autor = autorRepository.save(autor);
        } else {
            // Si existe, lo obtenemos de la base de datos
            autor = autorRepository.getByNombre(autor.getNombre());
        }

        // Asignamos el autor al libro y guardamos el libro
        libro.setAutor(autor);
        return libroRepository.save(libro);
    }
}
