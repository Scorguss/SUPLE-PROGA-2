package BusinessLogic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginApp {
    private static final int MAX_INTENTOS = 3; // Número máximo de intentos permitidos
    private int intentosRestantes = MAX_INTENTOS;
    private String contraseña; // Campo para almacenar la contraseña

    // Método para verificar el inicio de sesión con la base de datos SQLite
    private boolean verifyLogin(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Conexión a la base de datos SQLite (reemplaza con la ubicación de tu base de datos)
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Leonardo\\OneDrive - Escuela Politécnica Nacional\\Escritorio\\Splex\\src\\Database\\Coordenadas.db");

            // Calcular el MD5 de la contraseña proporcionada por el usuario
            String hashedPassword = md5(password);

            // Consulta SQL para obtener la contraseña encriptada correspondiente al nombre de usuario
            String selectSQL = "SELECT contraseña FROM usuarios WHERE nombre = ?";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("contraseña");

                // Comparar la contraseña encriptada almacenada con la contraseña encriptada calculada
                boolean loginSuccessful = storedPassword.equals(hashedPassword);

                if (loginSuccessful) {
                    System.out.println("Inicio de sesión exitoso.");
                    contraseña = password; // Almacenar la contraseña
                } else {
                    System.out.println("Contraseña incorrecta.");
                }

                return loginSuccessful;
            } else {
                System.out.println("Usuario no encontrado.");
                return false; // Usuario no encontrado
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false; // Error durante la verificación
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para calcular el MD5 de una cadena
    private static String md5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : messageDigest) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    public String ingreso() {
        Scanner scanner = new Scanner(System.in);

        LoginApp loginApp = new LoginApp();

        while (loginApp.intentosRestantes > 0) {
            System.out.print("Ingrese su Usuario: ");
            String username = scanner.nextLine();

            System.out.print("Clave: ");
            String password = scanner.nextLine();

            boolean loginResult = loginApp.verifyLogin(username, password);

            if (loginResult) {
                // Obtiene la contraseña después del inicio de sesión exitoso
                String contraseña = loginApp.getContraseña();
                return contraseña;
            } else {
                loginApp.intentosRestantes--;
                System.out.println("Inicio de sesión fallido. Intentos restantes: " + loginApp.intentosRestantes);
            }
        }

        if (loginApp.intentosRestantes == 0) {
            System.out.println("Se han agotado los intentos. Cerrando la aplicación.");
            System.exit(0); // Cierra la aplicación
        }

        return null; // En caso de falla en el inicio de sesión
    }

    // Método para obtener la contraseña después de un inicio de sesión exitoso
    public String getContraseña() {
        return contraseña;
    }
}
