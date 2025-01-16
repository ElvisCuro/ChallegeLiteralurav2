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
    private static String API_BASE = "https://gutendex.com/books/?search=";
    private List<Libro> datosLibro = new ArrayList<>();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<DatosLibro> resultadoLibros;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivosFecha();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:

                    break;
                case 9:

                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }
    private Libro getDatosLibro() {
        System.out.println("Escribe el nombre del libro");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(API_BASE + nombreLibro.replace(" ", "%20"));
        System.out.println(json);

        LibrosRespuestaApi datos = conversor.obtenerDatos(json, LibrosRespuestaApi.class);

        if (datos != null && datos.getResultadoLibros() != null && !datos.getResultadoLibros().isEmpty()) {
            DatosLibro primerLibro = datos.getResultadoLibros().get(0); // Obtener el primer libro de la lista
            return new Libro(primerLibro);
        } else {
            System.out.println("No se encontraron resultados.");
            return null;
        }
    }

    private void buscarLibroPorTitulo(){
        Libro libro = getDatosLibro();

        if (libro == null){
            System.out.println("Libro no encontrado. el valor es null");
            return;
        }
        try{
            boolean libroExists = libroRepository.existsByTitulo(libro.getTitulo());
            if (libroExists){
                System.out.println("El libro ya existe en la base de datos!");
            }else {
                libroRepository.save(libro);
                System.out.println(libro.toString());
            }
        }catch (InvalidDataAccessApiUsageException e){
            System.out.println("No se puede persisitir el libro buscado!");
        }
    }

    @Transactional(readOnly = true)
    private void listarLibros(){
        //datosLibro.forEach(System.out::println);
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en la base de datos.");
        } else {
            System.out.println("Libros encontrados en la base de datos:");
            for (Libro libro : libros) {
                System.out.println(libro.toString());
            }
        }
    }

    private void listarAutores(){
        var autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores en la base de datos.");
        } else {
            System.out.println("Autores encontrados en la base de datos:");
            for (var autor : autores) {
                System.out.println(autor.toString());
            }
        }
    }
    @Transactional(readOnly = true)
    private void listarAutoresVivosFecha(){
        System.out.println("Ingrese el año");
        int anio = teclado.nextInt();

        var autoresVivos = autorRepository.findAutoresVivosEnAnio(anio);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio + ".");

        }else {
            System.out.println("Autores vivos en el año " + anio + ":");
            for (var autor : autoresVivos) {
                System.out.println(autor.toString());
            }
        }
    }

    private void listarLibrosPorIdioma(){
        System.out.println("Ingrese el idioma (por ejemplo, 'en' para inglés, 'es' para español): ");
        String idiomaBuscado = teclado.nextLine().trim();

        // Consultar a la base de datos por libros con el idioma especificado
        List<Libro> librosPorIdioma = libroRepository.findByIdioma(idiomaBuscado);

        // Verificar si existen resultados
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros registrados en el idioma: " + idiomaBuscado);
        } else {
            System.out.println("Libros en el idioma '" + idiomaBuscado + "':");
            for (Libro libro : librosPorIdioma) {
                System.out.println("Título: " + libro.getTitulo() +
                        ", Idiomas: " + String.join(", ", libro.getIdioma()));
            }
        }
    }
}


