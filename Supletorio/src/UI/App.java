package UI;
/*import javax.swing.*;

import BusinessLogic.LectorCSV;
import UI.LoginApp;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginApp loginApp = new LoginApp(); // Crear una instancia de LoginApp
                loginApp.setVisible(true); // Mostrar la ventana de inicio de sesión
            }
        });
        //Leer el CSV
        LectorCSV csvReader = new LectorCSV();
        String archivoCSV = "C:\\Users\\Leonardo\\OneDrive - Escuela Politécnica Nacional\\Escritorio\\Supletorio\\src\\Database\\COORDENADAS.csv"; // Reemplaza con la ubicación de tu archivo CSV

        List<String[]> datos = csvReader.leerCSV(archivoCSV);

        // Ahora puedes trabajar con la lista de datos en esta clase
        for (String[] fila : datos) {
            System.out.println("ID: " + fila[0] + ", Geoposición: " + fila[1] + ", Lunes: " + fila[2] +
                               ", Martes: " + fila[3] + ", Miércoles: " + fila[4] + ", Jueves: " + fila[5] +
                               ", Viernes: " + fila[6] + ", Tipo Arsenal: " + fila[7]);
        }
        //texto
        System.out.println("[+] Leyendo: ");
        System.out.println("\t - Coordenadas... ");
        Thread.sleep(200);
        System.out.println("\t - Tipo de Coordenadas... ");
        Thread.sleep(200);
        System.out.println("\t - Arsenal... ");
        Thread.sleep(200);
        System.out.println("\t - Horarios... ");
        Thread.sleep(200);
        System.out.println("[+] Guardando: ");
        System.out.println("\t - Coordenadas... ");
        Thread.sleep(200);
        System.out.println("\t - Tipo de Coordenadas... ");
        Thread.sleep(200);
        System.out.println("\t - Arsenal... ");
        Thread.sleep(200);
        System.out.println("\t - Horarios... ");
        Thread.sleep(200);
    }

}
*/
import javax.swing.SwingUtilities;
import java.util.List;
import BusinessLogic.LectorCSV;

public class App {
    public static void main(String[] args) throws Exception {
        // Crear una instancia de LoginApp y establecer un oyente para el inicio de sesión exitoso
        LoginApp loginApp = new LoginApp();
        loginApp.setOnSuccessfulLoginListener(new LoginApp.OnLoginSuccessListener() {
            @Override
            public void onLoginSuccess() {
                
                // Obtener la contraseña invertida del LoginApp
                String contraseñaInvertida = loginApp.getInvertedPassword();

                // Realizar las operaciones de lectura e impresión aquí después del inicio de sesión exitoso
                LectorCSV csvReader = new LectorCSV();
                String archivoCSV = "C:\\Users\\Leonardo\\OneDrive - Escuela Politécnica Nacional\\Escritorio\\Supletorio\\src\\Database\\COORDENADAS.csv"; // Reemplaza con la ubicación de tu archivo CSV
                List<String[]> datos = csvReader.filtrarCSVPorContraseñaInvertida(archivoCSV, contraseñaInvertida);

                for (String[] fila : datos) {
                    for (String campo : fila) {
                        System.out.print(campo + " | "); // Imprimir cada campo de la fila separado por '|'
                    }
                    System.out.println(); // Imprimir un salto de línea para la siguiente fila
                }

                //texto
                System.out.println("[+] Leyendo: ");
                System.out.println("\t - Coordenadas... ");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("\t - Tipo de Coordenadas... ");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("\t - Arsenal... ");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("\t - Horarios... ");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("[+] Guardando: ");
                System.out.println("\t - Coordenadas... ");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("\t - Tipo de Coordenadas... ");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("\t - Arsenal... ");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("\t - Horarios... ");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Crear una instancia de TablaGeneral y mostrarla
                SwingUtilities.invokeLater(() -> {
                    TablaGeneral tablaGeneral = new TablaGeneral(contraseñaInvertida);
                    tablaGeneral.setVisible(true);
                    //Insertamos los datos en la base de datos
                    tablaGeneral.insertarDatos(datos);
                });
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                loginApp.setVisible(true); // Mostrar la ventana de inicio de sesión
            }
        });
    }
}
