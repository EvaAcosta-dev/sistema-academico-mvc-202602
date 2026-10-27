package com.uniajc.controlador;

import com.uniajc.modelo.Estudiante;
import com.uniajc.service.EstudianteService;
import com.uniajc.vista.VistaEstudiante;
import java.util.ArrayList;
import java.util.List;

public class ControladorEstudiante {
    private final VistaEstudiante vista;
    private final EstudianteService estudianteService;
    private final List<Estudiante> estudiantes;

    public ControladorEstudiante(VistaEstudiante vista) {
        this(vista, new EstudianteService());
    }

    public ControladorEstudiante(VistaEstudiante vista, EstudianteService estudianteService) {
        this.vista = vista;
        this.estudianteService = estudianteService;
        this.estudiantes = new ArrayList<>();
        this.vista.setControlador(this);
        cargarEstudiantes();
    }

    private void cargarEstudiantes() {
        try {
            List<Estudiante> lista = estudianteService.listarTodos();
            estudiantes.clear();
            estudiantes.addAll(lista);
            vista.cargarEstudiantes(estudiantes);
        } catch (Exception e) {
            vista.mostrarMensaje("Error al cargar estudiantes: " + e.getMessage());
        }
    }

    public void registrarEstudiante() {
        Estudiante estudiante = vista.obtenerEstudianteFormulario();
        if (estudiante == null) {
            return;
        }

        try {
            estudianteService.registrar(estudiante);
            estudiantes.add(estudiante);
            vista.agregarEstudianteTabla(estudiante);
            vista.limpiarFormulario();
            vista.mostrarMensaje("Estudiante registrado correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            vista.mostrarMensaje(e.getMessage());
        }
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }
}
