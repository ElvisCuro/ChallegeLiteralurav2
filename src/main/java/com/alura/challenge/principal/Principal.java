package com.alura.challenge.principal;

import com.alura.challenge.model.Libro;
import com.alura.challenge.model.LibrosRespuestaApi;
import com.alura.challenge.model.records.DatosLibro;
import com.alura.challenge.repository.AutorRepository;
import com.alura.challenge.repository.LibroRepository;
import com.alura.challenge.service.ConsumoAPI;
import com.alura.challenge.service.ConvierteDatos;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private static final String API_BASE = "https://gutendex.com/books/?search=";
    private List<Libro> datosLibro = new ArrayList<>();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        int opcion;
        do {
            System.out.println("""
                    ================ MENÚ ==================
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    ========================================
                    """);
            System.out.print("Seleccione una opción: ");
            opcion = teclado.nextInt();
            teclado.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivosFecha();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("⚠️ Opción inválida. Por favor, seleccione una opción válida.");
            }
            System.out.println(); // Salto de línea para mejorar la salida
        } while (opcion != 0);
    }

    private Libro getDatosLibro() {
        System.out.print("Escribe el nombre del libro: ");
        String nombreLibro = teclado.nextLine().trim();
        var json = consumoApi.obtenerDatos(API_BASE + nombreLibro.replace(" ", "%20"));

        LibrosRespuestaApi datos = conversor.obtenerDatos(json, LibrosRespuestaApi.class);

        if (datos != null && datos.getResultadoLibros() != null && !datos.getResultadoLibros().isEmpty()) {
            DatosLibro primerLibro = datos.getResultadoLibros().get(0); // Obtener primer libro
            return new Libro(primerLibro);
        } else {
            System.out.println("⚠️ No se encontraron resultados para el libro ingresado.");
            return null;
        }
    }

    private void buscarLibroPorTitulo() {
        Libro libro = getDatosLibro();

        if (libro == null) {
            System.out.println("⚠️ Libro no encontrado o el valor es null.");
            return;
        }

        try {
            if (libroRepository.existsByTitulo(libro.getTitulo())) {
                System.out.println("⚠️ El libro ya existe en la base de datos.");
            } else {
                libroRepository.save(libro);
                System.out.println("✅ Libro guardado con éxito:");
                System.out.println(libro);
            }
        } catch (InvalidDataAccessApiUsageException e) {
            System.out.println("⚠️ No se puede persistir el libro buscado. Error en la base de datos.");
        }
    }

    @Transactional(readOnly = true)
    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("⚠️ No se encontraron libros en la base de datos.");
        } else {
            System.out.println("📚 Libros registrados en la base de datos:");
            System.out.println("=========================================");
            for (Libro libro : libros) {
                System.out.printf("📖 Título: %s | Autor: %s | Idioma: %s | Descargas: %d%n",
                        libro.getTitulo(),
                        libro.getAutores() != null ? libro.getAutores().getNombre() : "Desconocido",
                        libro.getIdioma(),
                        libro.getCantidadDescargas());
            }
            System.out.println("=========================================");
        }
    }

    @Transactional(readOnly = true)
    private void listarAutores() {
        var autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("⚠️ No se encontraron autores en la base de datos.");
        } else {
            System.out.println("🖋️ Autores registrados en la base de datos:");
            System.out.println("=========================================");
            autores.forEach(autor -> System.out.printf("👤 Nombre: %s | Año de Nacimiento: %d | Año de Fallecimiento: %s%n",
                    autor.getNombre(),
                    autor.getCumpleanios(),
                    autor.getFechaFallecimiento() != null ? autor.getFechaFallecimiento() : "Vivo"));
            System.out.println("=========================================");
        }
    }

    @Transactional(readOnly = true)
    private void listarAutoresVivosFecha() {
        System.out.print("Ingrese el año: ");
        int anio = teclado.nextInt();

        var autoresVivos = autorRepository.findAutoresVivosEnAnio(anio);
        if (autoresVivos.isEmpty()) {
            System.out.printf("⚠️ No se encontraron autores vivos en el año %d.%n", anio);
        } else {
            System.out.printf("🖋️ Autores vivos en el año %d:%n", anio);
            System.out.println("=========================================");
            autoresVivos.forEach(autor -> System.out.printf("👤 Nombre: %s | Año de Nacimiento: %d%n",
                    autor.getNombre(),
                    autor.getCumpleanios()));
            System.out.println("=========================================");
        }
    }

    @Transactional(readOnly = true)
    private void listarLibrosPorIdioma() {
        System.out.print("Ingrese el idioma (por ejemplo, 'en' para inglés, 'es' para español): ");
        String idiomaBuscado = teclado.nextLine().trim();

        List<Libro> librosPorIdioma = libroRepository.findByIdioma(idiomaBuscado);

        if (librosPorIdioma.isEmpty()) {
            System.out.printf("⚠️ No se encontraron libros registrados en el idioma: %s%n", idiomaBuscado);
        } else {
            System.out.printf("📚 Libros en el idioma '%s':%n", idiomaBuscado);
            System.out.println("=========================================");
            librosPorIdioma.forEach(libro -> System.out.printf("📖 Título: %s | Autor: %s | Idioma: %s%n",
                    libro.getTitulo(),
                    libro.getAutores() != null ? libro.getAutores().getNombre() : "Desconocido",
                    libro.getIdioma()));
            System.out.println("=========================================");
        }
    }
}