package com.example.LiterAlura.repository;

import com.example.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {


    Autor getByNombre(String nombre);
    @Query("SELECT a FROM Autor a WHERE (a.anoMuerte IS NULL OR a.anoMuerte >= :anio) AND a.anoNacimiento <= :anio")
    List<Autor> findAutoresVivosEnAno(@Param("anio") int anio);}
