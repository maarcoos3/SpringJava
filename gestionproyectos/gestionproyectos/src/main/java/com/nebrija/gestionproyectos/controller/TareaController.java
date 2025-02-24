package com.nebrija.gestionproyectos.controller;

import com.nebrija.gestionproyectos.entity.Tarea;
import com.nebrija.gestionproyectos.entity.Proyecto;
import com.nebrija.gestionproyectos.repository.TareaRepository;
import com.nebrija.gestionproyectos.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    // Mostrar formulario para crear una nueva tarea asociada a un proyecto
@GetMapping("/crear")
public String mostrarFormularioCrearTarea(@RequestParam(value = "proyectoId", required = false) Long proyectoId, Model model) {
    if (proyectoId == null) {
        // Si no se pasa el proyectoId, redirige a la lista de proyectos
        return "redirect:/proyectos";
    }
    Proyecto proyecto = proyectoRepository.findById(proyectoId).orElse(null);
    if (proyecto == null) {
        // Si no se encuentra el proyecto redirige a la lista de proyectos
        return "redirect:/proyectos";
    }
    Tarea tarea = new Tarea();
    tarea.setProyecto(proyecto);
    model.addAttribute("tarea", tarea);
    return "tareas/crear";
}


    // Guardar la nueva tarea
    @PostMapping
    public String guardarTarea(@ModelAttribute Tarea tarea) {
        tareaRepository.save(tarea);
        return "redirect:/proyectos/" + tarea.getProyecto().getId();
    }

    // Eliminar una tarea
    @GetMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable("id") Long id) {
        Tarea tarea = tareaRepository.findById(id).orElse(null);
        if (tarea != null) {
            Long proyectoId = tarea.getProyecto().getId();
            tareaRepository.deleteById(id);
            return "redirect:/proyectos/" + proyectoId;
        }
        return "redirect:/proyectos";
    }
}
