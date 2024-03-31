import BusinessLogic.LectorTXT;
import BusinessLogic.LoginApp;

public class App {
    public static void main(String[] args) {
        System.out.println("[+] Informacion: ");
        System.out.println("Cedula: 1755315916");
        System.out.println("Nombre: Leonardo Maldonado");
        System.out.println("Correo: brayan.maldonado@epn.edu.ec");

        //inicio login
        LoginApp login = new LoginApp();
        String contraseña = login.ingreso();

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

        //leer txt
        String rutaArchivo = "C:\\\\Users\\\\Leonardo\\\\OneDrive - Escuela Politécnica Nacional\\\\Escritorio\\\\Splex\\\\src\\\\Database\\\\MaldonadoLeonardo.txt";

        LectorTXT lector2 = new LectorTXT(rutaArchivo);
        lector2.leerConBarraDeCarga();
        
        //texto
        
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

        

        String archivo = "C:\\\\Users\\\\Leonardo\\\\OneDrive - Escuela Politécnica Nacional\\\\Escritorio\\\\Splex\\\\src\\\\Database\\\\horario.txt";

        LectorTXT lector = new LectorTXT(archivo);
         lector.leerArchivoYInsertarEnBaseDeDatos();
    }
}
