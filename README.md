Proyecto LiterAlura

Descripción

LiterAlura es una aplicación de catálogo de libros y autores que permite a los usuarios buscar, filtrar y consultar información sobre libros disponibles. La aplicación está construida con Java y utiliza el framework Spring para el desarrollo del backend. Además, consume datos de APIs externas como Gutendex para enriquecer su funcionalidad.

Tecnologías Utilizadas

Java 17

Spring Boot 3.4.1

Hibernate (JPA para la persistencia de datos)

Jackson (para serialización/deserialización JSON)

PostgreSQL (base de datos relacional)

Gutendex API (API externa para la consulta de libros)

Características Principales

Búsqueda de libros:

Consulta de libros por título utilizando la API de Gutendex.

Almacena los resultados relevantes en la base de datos.

Filtrado por idioma:

Permite a los usuarios visualizar libros disponibles según el idioma.

Relación Autor-Libro:

Cada libro está asociado a un autor registrado en la base de datos.

Persistencia de datos:

Uso de Hibernate para almacenar información de libros y autores en una base de datos PostgreSQL.