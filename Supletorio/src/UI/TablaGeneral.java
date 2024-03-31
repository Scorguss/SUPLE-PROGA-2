package UI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import BusinessLogic.LectorCSV;
import java.util.List;

public class TablaGeneral extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public TablaGeneral(String contraseñaInvertida) {
        initializeUI(contraseñaInvertida);
    }

    private void initializeUI(String contraseñaInvertida) {
        setTitle("Información General");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el modelo de tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Geoposición");
        tableModel.addColumn("Lunes");
        tableModel.addColumn("Martes");
        tableModel.addColumn("Miércoles");
        tableModel.addColumn("Jueves");
        tableModel.addColumn("Viernes");
        tableModel.addColumn("Tipo Arsenal");

        // Crear la tabla con el modelo
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Agregar la tabla al frame
        getContentPane().add(scrollPane);

        // Leer el CSV y filtrar los datos por contraseña invertida
        LectorCSV csvReader = new LectorCSV();
        String archivoCSV = "C:\\Users\\Leonardo\\OneDrive - Escuela Politécnica Nacional\\Escritorio\\Supletorio\\src\\Database\\COORDENADAS.csv"; // Reemplaza con la ubicación de tu archivo CSV
        List<String[]> datos = csvReader.filtrarCSVPorContraseñaInvertida(archivoCSV, contraseñaInvertida);

        for (String[] fila : datos) {
            // Eliminar los prefijos de cada elemento de la fila y conservar solo los datos
            for (int i = 0; i < fila.length; i++) {
                String elemento = fila[i];
                
                // Verificar si el elemento tiene un prefijo
                if (elemento.contains(":")) {
                    // Dividir el elemento en dos partes usando ":" como separador
                    String[] partes = elemento.split(":", 2);
                    
                    // Conservar solo la segunda parte (los datos)
                    fila[i] = partes[1].trim();
                }
            }
            
            // Agregar la fila formateada a la tabla
            tableModel.addRow(fila);
        }
    }

    //metodo para insertar dentro de la base de datos
    public void insertarDatos(List<String[]> datos) {
        String url = "jdbc:sqlite:C:\\Users\\Leonardo\\OneDrive - Escuela Politécnica Nacional\\Escritorio\\Supletorio\\src\\Database\\guerra.db"; // Reemplaza con la ubicación de tu base de datos SQLite
        String sql = "INSERT INTO infClasiffied (geoposicion, lunes, martes, miercoles, jueves, viernes, tipoArsenal) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (String[] fila : datos) {
                // Eliminar los prefijos de cada elemento de la fila y conservar solo los datos
                for (int i = 1; i < fila.length; i++) { // Empezamos desde 1 para omitir el primer elemento (ID)
                    String elemento = fila[i];

                    // Verificar si el elemento tiene un prefijo
                    if (elemento.contains(":")) {
                        // Dividir el elemento en dos partes usando ":" como separador
                        String[] partes = elemento.split(":", 2);

                        // Conservar solo la segunda parte (los datos)
                        fila[i] = partes[1].trim();
                    }
                }

                String geoposicion = fila[1];
                String lunes = fila[2];
                String martes = fila[3];
                String miercoles = fila[4];
                String jueves = fila[5];
                String viernes = fila[6];
                String tipoArsenal = fila[7];

                pstmt.setString(1, geoposicion);
                pstmt.setString(2, lunes);
                pstmt.setString(3, martes);
                pstmt.setString(4, miercoles);
                pstmt.setString(5, jueves);
                pstmt.setString(6, viernes);
                pstmt.setString(7, tipoArsenal);

                pstmt.executeUpdate();
            }

            System.out.println("Datos insertados en la base de datos SQLite.");

        } catch (SQLException e) {
            System.err.println("Error al insertar datos en la base de datos SQLite: " + e.getMessage());
        }
    }


}
