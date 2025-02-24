package com.nebrija.gestionproyectos.repository;

import com.nebrija.gestionproyectos.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
}
