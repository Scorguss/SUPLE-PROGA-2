package UI;

/*


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginApp extends JFrame {
private JTextField usernameField;
    private JPasswordField passwordField;
    private int loginAttempts = 0; // Contador de intentos de inicio de sesión

    public LoginApp() {
        initialize();
    }

    private void initialize() {
        setTitle("Inicio de Sesión");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel lblUsername = new JLabel("Usuario:");
        usernameField = new JTextField();
        JLabel lblPassword = new JLabel("Contraseña:");
        passwordField = new JPasswordField();
        JButton btnLogin = new JButton("Iniciar Sesión");

        panel.add(lblUsername);
        panel.add(usernameField);
        panel.add(lblPassword);
        panel.add(passwordField);
        panel.add(new JLabel()); // Espacio en blanco
        panel.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (verifyLogin(username, password)) {
                    JOptionPane.showMessageDialog(LoginApp.this, "Inicio de sesión exitoso.");
                } else {
                    JOptionPane.showMessageDialog(LoginApp.this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
                    loginAttempts++;
                    if (loginAttempts >= 3) {
                        // Si se superan los 3 intentos, cierra la aplicación
                        JOptionPane.showMessageDialog(LoginApp.this, "Demasiados intentos fallidos. Cerrando la aplicación.");
                        System.exit(0);
                    }
                }
            }
        });

        add(panel);
    }

    // Método para verificar el inicio de sesión con la base de datos SQLite
    private boolean verifyLogin(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Conexión a la base de datos SQLite
            connection = DriverManager.getConnection( "jdbc:sqlite:C:\\Users\\Leonardo\\OneDrive - Escuela Politécnica Nacional\\Escritorio\\Supletorio\\src\\Database\\guerra.db");
            //"jdbc:sqlite:Database"+File.separator+"guerra.db"

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
                return storedPassword.equals(hashedPassword);
            } else {
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

}
*/
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private int loginAttempts = 0; // Contador de intentos de inicio de sesión
    private OnLoginSuccessListener loginSuccessListener;
    private String invertedPassword; // Variable para almacenar la contraseña invertida

    public LoginApp() {
        initialize();
    }

    private void initialize() {
        setTitle("Inicio de Sesión");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel lblUsername = new JLabel("Usuario:");
        usernameField = new JTextField();
        JLabel lblPassword = new JLabel("Contraseña:");
        passwordField = new JPasswordField();
        JButton btnLogin = new JButton("Iniciar Sesión");

        panel.add(lblUsername);
        panel.add(usernameField);
        panel.add(lblPassword);
        panel.add(passwordField);
        panel.add(new JLabel()); // Espacio en blanco
        panel.add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // Invertir la contraseña y almacenarla en la variable
                invertedPassword = reversePassword(password);

                if (verifyLogin(username, password)) {
                    JOptionPane.showMessageDialog(LoginApp.this, "Inicio de sesión exitoso.");
                    // Mostrar la contraseña invertida en un cuadro de diálogo
                    //JOptionPane.showMessageDialog(LoginApp.this, "Contraseña invertida: " + invertedPassword, "Contraseña Invertida", JOptionPane.INFORMATION_MESSAGE);
                    // Notificar al oyente que el inicio de sesión fue exitoso
                    notifyLoginSuccess();
                } else {
                    JOptionPane.showMessageDialog(LoginApp.this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
                    loginAttempts++;
                    if (loginAttempts >= 3) {
                        // Si se superan los 3 intentos, cierra la aplicación
                        JOptionPane.showMessageDialog(LoginApp.this, "Demasiados intentos fallidos. Cerrando la aplicación.");
                        System.exit(0);
                    }
                }
            }
        });

        add(panel);
    }

    // Método para verificar el inicio de sesión con la base de datos SQLite
    private boolean verifyLogin(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Conexión a la base de datos SQLite
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Leonardo\\OneDrive - Escuela Politécnica Nacional\\Escritorio\\Supletorio\\src\\Database\\guerra.db");

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
                return storedPassword.equals(hashedPassword);
            } else {
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

    // Método para invertir la contraseña
    private String reversePassword(String password) {
        return new StringBuilder(password).reverse().toString();
    }

    // Método para obtener la contraseña invertida
    public String getInvertedPassword() {
        return invertedPassword;
    }

    // Método para establecer el oyente para el inicio de sesión exitoso
    public void setOnSuccessfulLoginListener(OnLoginSuccessListener onLoginSuccessListener) {
        this.loginSuccessListener = onLoginSuccessListener;
    }

    // Método para notificar al oyente que el inicio de sesión fue exitoso
    private void notifyLoginSuccess() {
        if (loginSuccessListener != null) {
            loginSuccessListener.onLoginSuccess();
        }
    }

    // Interfaz para el oyente de inicio de sesión exitoso
    interface OnLoginSuccessListener {
        void onLoginSuccess();
    }
}



