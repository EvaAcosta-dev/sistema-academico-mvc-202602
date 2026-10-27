package com.uniajc.vista;

import com.uniajc.controlador.ControladorEstudiante;
import com.uniajc.modelo.Estudiante;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VistaEstudiante extends JFrame {
    private ControladorEstudiante controlador;

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;

    public VistaEstudiante() {
        inicializarComponentes();
    }

    public void setControlador(ControladorEstudiante controlador) {
        this.controlador = controlador;
    }

    private void inicializarComponentes() {
        setTitle("Sistema Académico - Estudiantes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Registro de Estudiantes");
        lblTitulo.setBounds(250, 20, 200, 25);
        add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 70, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(140, 70, 170, 25);
        add(txtNombre);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(30, 110, 100, 25);
        add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(140, 110, 170, 25);
        add(txtApellido);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 150, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(140, 150, 170, 25);
        add(txtEmail);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, 200, 100, 30);
        btnGuardar.addActionListener(e -> {
            if (controlador != null) {
                controlador.registrarEstudiante();
            }
        });
        add(btnGuardar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(250, 200, 100, 30);
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        add(btnLimpiar);

        String[] columnas = {"ID", "Nombre", "Apellido", "Email"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEstudiantes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaEstudiantes);
        scrollPane.setBounds(350, 70, 300, 250);
        add(scrollPane);

        setVisible(true);
    }

    public Estudiante obtenerEstudianteFormulario() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
            mostrarMensaje("Todos los campos son obligatorios.");
            return null;
        }

        return new Estudiante(0, nombre, apellido, email);
    }

    public void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtNombre.requestFocus();
    }

    public void agregarEstudianteTabla(Estudiante estudiante) {
        modeloTabla.addRow(new Object[]{
            estudiante.getIdEstudiante(),
            estudiante.getNombre(),
            estudiante.getApellido(),
            estudiante.getEmail()
        });
    }

    public void cargarEstudiantes(List<Estudiante> estudiantes) {
        modeloTabla.setRowCount(0);
        for (Estudiante estudiante : estudiantes) {
            agregarEstudianteTabla(estudiante);
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    }
}
