package com.nebrija.gestionproyectos.repository;

import com.nebrija.gestionproyectos.entity.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    
}
