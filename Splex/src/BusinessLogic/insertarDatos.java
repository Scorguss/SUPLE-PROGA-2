package BusinessLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class insertarDatos {
    public static void insertarDatosSQL(String horarios, String lunes, String martes, String miercoles, String jueves, String viernes) {
        String url = "jdbc:sqlite:C:\\Users\\Leonardo\\OneDrive - Escuela Politécnica Nacional\\Escritorio\\Splex\\src\\Database\\Coordenadas.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            String insertSQL = "INSERT INTO Horario_Coordenada (Horarios, Lunes, Martes, Miercoles, Jueves, Viernes) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, horarios);
                pstmt.setString(2, lunes);
                pstmt.setString(3, martes);
                pstmt.setString(4, miercoles);
                pstmt.setString(5, jueves);
                pstmt.setString(6, viernes);

                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
