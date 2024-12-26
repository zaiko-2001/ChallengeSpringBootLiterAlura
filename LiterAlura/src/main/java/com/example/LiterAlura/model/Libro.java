package com.example.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonAlias("title") // Alias para mapear "title" del JSON a "titulo"
    @Column(nullable = false)
    private String titulo;

    @JsonAlias("languages") // Alias para mapear "languages" del JSON a "idioma"
    @Transient // No se persiste directamente
    private List<String> idiomas;

    @Column(nullable = true)
    private String idioma;

    @JsonAlias("download_count") // Alias para mapear "download_count" del JSON a "descargas"
    @Column(nullable = true)
    private int descargas;

    // Relación muchos a uno con el autor
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id", nullable = true)
    private Autor autor;

    @JsonAlias("authors") // Alias para mapear "authors" del JSON a una lista de autores
    @Transient // No se persiste directamente
    private List<Autor> autores;

    public Libro() {}

    public Libro(String titulo, String idioma, int descargas, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.descargas = descargas;
        this.autor = autor;
    }

    @PostLoad
    public void procesarDatos() {
        // Validar que idiomas no sea null o vacío
        if (idiomas != null && !idiomas.isEmpty()) {
            this.idioma = idiomas.get(0);
        }

        // Validar que autores no sea null o vacío
        if (autores != null && !autores.isEmpty()) {
            this.autor = autores.get(0);
        } else {
            System.out.println("No se encontraron autores en el JSON para el libro: " + this.titulo);
        }
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }
}
