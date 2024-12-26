package com.example.LiterAlura.repository;

import com.example.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Método para obtener el autor por su nombre
    Autor getByNombre(String nombre);
}
