package com.example.LiterAlura.main;

import com.example.LiterAlura.model.Libro;
import com.example.LiterAlura.model.Autor;  // Asegúrate de importar la clase Autor
import com.example.LiterAlura.service.ConsumoApi;
import com.example.LiterAlura.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class Main implements CommandLineRunner {

    private final ConsumoApi consumoApi;
    private final ConvierteDatos convierteDatos;
    private final List<Libro> librosConsultados;

    public Main(ConsumoApi consumoApi, ConvierteDatos convierteDatos) {
        this.consumoApi = consumoApi;
        this.convierteDatos = convierteDatos;
        this.librosConsultados = new ArrayList<>();
    }

    @Override
    public void run(String... args) {
        mostrarMenu();
    }

    private void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== Menú Principal =====");
            System.out.println("1. Consultar libro por título");
            System.out.println("2. Ver lista de libros consultados");
            System.out.println("3. Listar autores");
            System.out.println("4. Listar autores vivos en un año");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido.");
                scanner.next();
            }

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    consultarLibroPorTitulo(scanner);
                    break;
                case 2:
                    mostrarListaLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosEnAno(scanner);
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (opcion != 5);

        scanner.close();
    }

    private void consultarLibroPorTitulo(Scanner scanner) {
        System.out.print("Ingresa el título del libro que deseas consultar: ");
        scanner.nextLine();  // Limpiar el buffer
        String tituloLibro = scanner.nextLine();

        try {
            // Codificar el título del libro en formato URL
            String tituloCodificado = URLEncoder.encode(tituloLibro, StandardCharsets.UTF_8);
            String url = "https://gutendex.com/books/?search=" + tituloCodificado;

            // Obtener datos de la API
            String json = consumoApi.obtenerDatos(url);

            // Convertir el JSON a un objeto de tipo Libro
            Libro libro = convierteDatos.obtenerDatos(json, Libro.class);

            if (libro != null) {
                // Guardar el libro en la lista de libros consultados
                librosConsultados.add(libro);

                // Mostrar información del libro
                System.out.println("\n===== Información del Libro =====");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre()); // Accede al nombre del autor
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Descargas: " + libro.getDescargas());
            } else {
                System.out.println("No se encontró información para el título ingresado.");
            }
        } catch (RuntimeException e) {
            System.out.println("Hubo un error al consultar el libro: " + e.getMessage());
        }
    }


    private void mostrarListaLibros() {
        if (librosConsultados.isEmpty()) {
            System.out.println("No has consultado ningún libro aún.");
        } else {
            System.out.println("\n===== Lista de Libros Consultados =====");
            for (Libro libro : librosConsultados) {
                System.out.println("Título: " + libro.getTitulo() + ", Autor: " + libro.getAutor().getNombre() +
                        ", Idioma: " + libro.getIdioma() + ", Descargas: " + libro.getDescargas());
            }
        }
    }

    private void listarAutores() {
        Set<String> autores = new HashSet<>();  // Usamos un Set para evitar duplicados

        for (Libro libro : librosConsultados) {
            if (libro.getAutor() != null) {
                autores.add(libro.getAutor().getNombre());  // Añade el nombre del autor
            }
        }

        if (autores.isEmpty()) {
            System.out.println("No se han encontrado autores en los libros consultados.");
        } else {
            System.out.println("\n===== Lista de Autores =====");
            for (String autor : autores) {
                System.out.println(autor);
            }
        }
    }

    private void listarAutoresVivosEnAno(Scanner scanner) {
        System.out.print("Ingresa el año para listar autores vivos en ese año: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingresa un año válido.");
            scanner.next();
        }

        int ano = scanner.nextInt();
        Set<String> autoresVivos = new HashSet<>();

        for (Libro libro : librosConsultados) {
            if (libro.getAutor() != null) {
                Autor autorObj = libro.getAutor();  // Obtén el objeto Autor
                int anoNacimiento = autorObj.getAnoNacimiento();  // Año de nacimiento del autor
                int anoMuerte = autorObj.getAnoMuerte();  // Año de muerte del autor

                // Verifica si el autor está vivo en el año ingresado
                if (anoNacimiento <= ano && (anoMuerte >= ano || anoMuerte == 0)) {
                    autoresVivos.add(autorObj.getNombre());  // Añade el nombre del autor
                }
            }
        }

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
        } else {
            System.out.println("\n===== Lista de Autores Vivos en el Año " + ano + " =====");
            for (String autor : autoresVivos) {
                System.out.println(autor);
            }
        }
    }
}
