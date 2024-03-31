package BusinessLogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV {
    public List<String[]> leerCSV(String archivoCSV) {
        List<String[]> datos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            boolean primeraLinea = true; // Para omitir la primera línea con encabezados

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue; // Saltar la primera línea de encabezados
                }

                String[] campos = linea.split(";");
                datos.add(campos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return datos;
    }

public List<String[]> filtrarCSVPorContraseñaInvertida(String archivoCSV, String contraseñaInvertida) {
    List<String[]> datos = leerCSV(archivoCSV);
    List<String[]> filasFiltradas = new ArrayList<>();

    if (datos.isEmpty()) {
        System.out.println("No se encontraron datos en el archivo CSV.");
        return filasFiltradas;
    }

    // Descomponer la contraseña invertida en sus dígitos
    char[] digitosContraseña = contraseñaInvertida.toCharArray();

    // Filtrar y guardar las filas que coinciden con la contraseña invertida
    for (String[] fila : datos) {
        // Obtener los valores de los días de la fila actual
        String lunes = fila[2].substring(fila[2].indexOf(':') + 1).trim();
        String martes = fila[3].substring(fila[3].indexOf(':') + 1).trim();
        String miercoles = fila[4].substring(fila[4].indexOf(':') + 1).trim();
        String jueves = fila[5].substring(fila[5].indexOf(':') + 1).trim();
        String viernes = fila[6].substring(fila[6].indexOf(':') + 1).trim();

        // Verificar si al menos uno de los días contiene un valor
        if (!lunes.isEmpty() || !martes.isEmpty() || !miercoles.isEmpty() || !jueves.isEmpty() || !viernes.isEmpty()) {
            // Verificar si el ID coincide con alguno de los dígitos de la contraseña invertida
            for (char digito : digitosContraseña) {
                if (fila[0].equals(String.valueOf(digito))) {
                    // Reemplazar letras de arsenal con palabras
                    String arsenalReemplazado = ConvertirArsenal.reemplazarLetrasConPalabras(fila[7]);

                    // Agregar la fila filtrada a la lista
                    String[] filaFiltrada = new String[]{
                            "ID: " + fila[0], "Geoposición: " + fila[1],
                            "Lunes: " + lunes, "Martes: " + martes, "Miércoles: " + miercoles,
                            "Jueves: " + jueves, "Viernes: " + viernes,
                            "Tipo Arsenal: " + arsenalReemplazado
                    };
                    filasFiltradas.add(filaFiltrada);
                }
            }
        }
    }

    if (filasFiltradas.isEmpty()) {
        System.out.println("No se encontraron filas que coincidan con la contraseña invertida.");
    }

    return filasFiltradas;
}



}



