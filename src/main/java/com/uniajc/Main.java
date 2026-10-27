package com.uniajc;

import com.uniajc.controlador.ControladorEstudiante;
import com.uniajc.vista.VistaEstudiante;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenido al Sistema Académico!");

        javax.swing.SwingUtilities.invokeLater(() -> {
            new ControladorEstudiante(new VistaEstudiante());
        });
    }
}