package com.example.LiterAlura.service;

import com.example.LiterAlura.model.Autor;
import com.example.LiterAlura.model.Libro;
import com.example.LiterAlura.repository.AutorRepository;
import com.example.LiterAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LibroService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroRepository libroRepository;

    public Libro insertarLibroConAutor(Libro libro) {
        Autor autor = libro.getAutor();

        // Verificamos si el autor ya existe
        if (autor.getNombre() == null || autorRepository.getByNombre(autor.getNombre()) == null) {
            autor = autorRepository.save(autor); // Guardamos el autor si no existe
        } else {
            autor = autorRepository.getByNombre(autor.getNombre());
        }

        libro.setAutor(autor); // Asignamos el autor al libro
        return libroRepository.save(libro);
    }

    public Map<String, Long> contarLibrosPorIdioma(List<String> idiomas) {
        Map<String, Long> conteoPorIdioma = new HashMap<>();
        for (String idioma : idiomas) {
            conteoPorIdioma.put(idioma, libroRepository.countByIdioma(idioma));
        }
        return conteoPorIdioma;
    }

    public List<Autor> listarAutoresVivosEnAno(int ano) {
        return autorRepository.findAutoresVivosEnAno(ano);
    }
}
