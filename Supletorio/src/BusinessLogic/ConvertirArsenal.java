package BusinessLogic;

/**
 * La clase ConvertirArsenal proporciona una funcionalidad para convertir letras en palabras
 * asociadas a un arsenal específico. Esto se utiliza para traducir un código de letras en una
 * descripción más legible.
 */
public class ConvertirArsenal {
    
    /**
     * Convierte una cadena de letras en una cadena de palabras asociadas al arsenal.
     *
     * @param input Cadena de letras que representan el arsenal.
     * @return Cadena de palabras separadas por comas que describen el arsenal.
     */
    public static String reemplazarLetrasConPalabras(String input) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char letra = input.charAt(i);
            String palabraReemplazo = obtenerPalabraParaLetra(letra);
            if (palabraReemplazo != null) {
                resultado.append(palabraReemplazo);
                if (i < input.length() - 1) {
                    resultado.append(",");
                }
            } else {
                // Si la letra no tiene una palabra correspondiente, la dejamos sin cambios.
                resultado.append(letra);
            }
        }
        return resultado.toString();
    }

    /**
     * Obtiene la palabra asociada a una letra del arsenal.
     *
     * @param letra Letra que representa un componente del arsenal.
     * @return Palabra asociada a la letra o null si la letra no tiene un reemplazo definido.
     */
    private static String obtenerPalabraParaLetra(char letra) {
        switch (Character.toLowerCase(letra)) {
            case 'a':
                return "avion";
            case 'b':
                return "barco";
            case 'c':
                return "convoy";
            case 'd':
                return "dron";
            case 't':
                return "tanque";
            default:
                return null; // Devuelve null si la letra no tiene un reemplazo definido.
        }
    }
}
