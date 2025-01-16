package com.alura.challenge.repository;


import com.alura.challenge.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {

    List<Autor> findAll();


    List<Autor> findByCumpleaniosLessThanOrFechaFallecimientoGreaterThanEqual(int anioBuscado, int anioBuscado1);

    Optional<Autor> findFirstByNombreContainsIgnoreCase(String escritor);

    @Query("""
       SELECT a FROM Autor a 
       WHERE a.cumpleanios <= :anio 
         AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento > :anio)
       """)
    List<Autor> findAutoresVivosEnAnio(@Param("anio") int anio);
}
