package com.uniajc.db;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConexion {
    // Conexión única (singleton)
    private static Connection connection = null;

    /**
     * Obtiene la conexión a la base de datos. Si no existe o fue cerrada, la crea usando config.properties.
     * @return Objeto Connection listo para usar.
     */
    public static Connection getConnection() {
        Properties properties = new Properties();
        
        try {
            // Verificamos si es nula O si fue cerrada previamente
            if (connection == null || connection.isClosed()) { 
                try {
                    // Cargar las propiedades desde el archivo config.properties
                    properties.load(new FileInputStream(new File("config.properties")));

                    // Leer los parámetros de conexión
                    String url = properties.get("URL").toString();
                    String user = properties.get("USER").toString();
                    String password = properties.get("PASSWORD").toString();

                    System.out.println("Intentando conectar a: " + url);

                    // Establecer la conexión
                    connection = DriverManager.getConnection(url, user, password);
                    System.out.println("¡Conexión exitosa!");
                    
                } catch (FileNotFoundException e) {
                    System.out.println("Error: El archivo config.properties no existe.");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("Error de lectura en config.properties.");
                    e.printStackTrace();
                }
            }
        } catch (SQLException error) {
            System.out.println("Error al verificar o establecer la conexión: " + error.getMessage());
        }
        
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos si está abierta.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null; //  Reseteamos a null para que se pueda volver a conectar en el futuro
                System.out.println("Conexión cerrada exitosamente.");
            } catch (SQLException e) {
                System.out.println("Fallo al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Método MAIN para probar el funcionamiento de la clase
     */
    public static void main(String[] args) {
        System.out.println("--- Prueba 1: Primera conexión ---");
        Connection c1 = DatabaseConexion.getConnection();

        System.out.println("\n--- Prueba 2: Intentar conectar de nuevo (Debe reusar la misma) ---");
        Connection c2 = DatabaseConexion.getConnection();
        System.out.println("¿Es la misma instancia de conexión? " + (c1 == c2));

        System.out.println("\n--- Prueba 3: Cerrar la conexión ---");
        DatabaseConexion.closeConnection();

        System.out.println("\n--- Prueba 4: Volver a conectar después del cierre (Debe crear una nueva) ---");
        Connection c3 = DatabaseConexion.getConnection();
        System.out.println("¿Nueva conexión creada con éxito? " + (c3 != null));
        
        // Limpieza final
        DatabaseConexion.closeConnection();
    }
}
