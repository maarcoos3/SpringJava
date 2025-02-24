package com.nebrija.gestionproyectos.controller;

import com.nebrija.gestionproyectos.entity.EstadoProyecto;
import com.nebrija.gestionproyectos.entity.Proyecto;
import com.nebrija.gestionproyectos.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoRepository proyectoRepository;

    // Listar todos los proyectos
    @GetMapping
    public String listarProyectos(Model model) {
        model.addAttribute("proyectos", proyectoRepository.findAll());
        return "proyectos/lista"; // Vista para listar proyectos
    }

    // Ver formulario para crear un nuevo proyecto
    @GetMapping("/crear")
    public String mostrarFormularioCrearProyecto(Model model) {
        Proyecto proyecto = new Proyecto();
        proyecto.setEstado(EstadoProyecto.ACTIVO);  // Asigna un valor por defecto
        model.addAttribute("proyecto", proyecto);
        return "proyectos/crear"; // Vista del formulario de creación
    }

    // Guardar un nuevo proyecto
    @PostMapping
    public String guardarProyecto(@ModelAttribute Proyecto proyecto) {
        proyectoRepository.save(proyecto);
        return "redirect:/proyectos";
    }

    // Ver detalles de un proyecto
    @GetMapping("/{id}")
    public String verDetalleProyecto(@PathVariable("id") Long id, Model model) {
        Proyecto proyecto = proyectoRepository.findById(id).orElse(null);
        model.addAttribute("proyecto", proyecto);
        return "proyectos/detalle"; // Vista de detalles del proyecto
    }

    // Eliminar un proyecto
    @GetMapping("/eliminar/{id}")
    public String eliminarProyecto(@PathVariable("id") Long id) {
        proyectoRepository.deleteById(id);
        return "redirect:/proyectos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProyecto(@PathVariable("id") Long id, Model model) {
        Proyecto proyecto = proyectoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id " + id));
        model.addAttribute("proyecto", proyecto);
        return "proyectos/editar"; // Nombre del formulario de edición
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProyecto(@PathVariable("id") Long id, @ModelAttribute Proyecto proyectoForm) {
        // Buscar el proyecto existente
        Proyecto proyectoExistente = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id " + id));
        
        // Actualizar los campos con los valores del formulario
        proyectoExistente.setNombre(proyectoForm.getNombre());
        proyectoExistente.setDescripcion(proyectoForm.getDescripcion());
        proyectoExistente.setFechaInicio(proyectoForm.getFechaInicio());
        proyectoExistente.setEstado(proyectoForm.getEstado());
        
        // Guardar los cambios
        proyectoRepository.save(proyectoExistente);
        
        // Redirigir a la lista de proyectos
        return "redirect:/proyectos";
    }

}
