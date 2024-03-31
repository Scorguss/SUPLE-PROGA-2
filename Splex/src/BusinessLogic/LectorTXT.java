package BusinessLogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class LectorTXT{
    private String archivo;
    private int totalLineas;
    private String contraseña;

    public LectorTXT(String archivo) {
        this.archivo = archivo;
        this.totalLineas = contarLineas(archivo);
    }

    public LectorTXT(String archivo, String contraseña) {
        this.archivo = archivo;
        this.contraseña = contraseña;
    }

    public void leerConBarraDeCarga() {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean esEncabezado = true;
            int progreso = 0;

            while ((linea = br.readLine()) != null) {
                if (esEncabezado) {
                    esEncabezado = false;
                    continue; // Saltar la línea de encabezado
                }

                // Eliminar espacios en blanco al inicio y final de la línea
                linea = linea.trim();

                // Dividir la línea en columnas utilizando el carácter ';'
                String[] columnas = linea.split(";");

                // Mostrar la animación de carga de 0 al 100%
                for (int carga = 0; carga <= 100; carga++) {
                    mostrarCarga(carga, columnas.length * 2);
                    Thread.sleep(10); // Puedes ajustar el tiempo de espera según tus necesidades
                }

                progreso++;
                mostrarCarga(100, columnas.length * 2); // Asegura que la carga llegue al 100%
                mostrarDatos(columnas);

                // Mostrar la siguiente línea
                if (progreso < totalLineas) {
                    Thread.sleep(100); // Espera antes de mostrar la siguiente línea
                    System.out.println(); // Nueva línea antes de mostrar la siguiente línea
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método para contar las líneas en el archivo
    private int contarLineas(String archivo) {
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }

    // Método para mostrar la animación de carga en la consola
    private void mostrarCarga(int progreso, int longitudLinea) {
        StringBuilder animacion = new StringBuilder();
        animacion.append("\r[");

        int caracteresCargados = (int) (progreso / 2.0 * longitudLinea / 100);
        for (int i = 0; i < longitudLinea; i++) {
            if (i < caracteresCargados) {
                animacion.append("=");
            } else {
                animacion.append(" ");
            }
        }
        animacion.append(String.format("] %d%%", progreso));

        System.out.print(animacion);
    }

    // Método para mostrar los datos en la consola
    private void mostrarDatos(String[] datos) {
        System.out.println();
        for (String columna : datos) {
            System.out.print(columna.trim() + " "); // Trim para quitar espacios en blanco
        }
    }

    public void leerArchivoYInsertarEnBaseDeDatos() {
        String url = "jdbc:sqlite:C:\\Users\\Leonardo\\OneDrive - Escuela Politécnica Nacional\\Escritorio\\Splex\\src\\Database\\Coordenadas.db";

        try (Connection conn = DriverManager.getConnection(url);
             BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                } else {
                    String[] columns = line.split("\\|");

                    String horarios = columns[0].trim();
                    String lunes = columns[1].trim();
                    String martes = columns[2].trim();
                    String miercoles = columns[3].trim();
                    String jueves = columns[4].trim();
                    String viernes = columns[5].trim();
                    System.out.println(String.format("%-11s | %-7s | %-7s | %-9s | %-7s | %-7s |",
                        horarios, lunes, martes, miercoles, jueves, viernes));

                    insertarDatosSQL(conn, horarios, lunes, martes, miercoles, jueves, viernes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertarDatosSQL(Connection conn, String horarios, String lunes, String martes, String miercoles, String jueves, String viernes) {
        try {
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

