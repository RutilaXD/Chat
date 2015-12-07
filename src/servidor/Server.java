/*
 * Programa cliente
 */
package servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import cliente.Client;

/**
 *
 * @author Raul
 */
public class Server {

    
    private Socket servicio;
    private ServerSocket sc;

    private OutputStream out;
    private InputStream in;

    private DataOutputStream salidaDatos;
    private DataInputStream entradaDatos;
    boolean opcion;
    
    public Server(){
       opcion = true;
    }



    /**
     * 
     * @param numeroPuerto recibe el puerto en el que abrirá la conexion
     */
    public void conexion(int numeroPuerto) {
        Scanner scanner;
        String escribe;
        try {
            //creamos un socket de servidor
            sc = new ServerSocket(numeroPuerto);
            System.out.println("Servidor activo en el puerto: " + numeroPuerto);
            //se capetan las conexiones
            servicio = sc.accept();
            
            while (opcion) {
                System.out.print("SERVIDOR: ");
                recibirDatos();

            }
            
            //Mientras en el cliente no se diga 'fin' se enviarán los datos ingresados en el teclado
            while (opcion) {
                scanner = new Scanner(System.in);
                escribe = scanner.nextLine();
                if (!escribe.equals("CLIENTE: fin")) {
                    enviarDatos("Server: " + escribe);
                } else {
                    opcion = false;
                }
            }
            
            //se cierra la conexion del socket servidor
            servicio.close();
        } catch (Exception ex) {
            System.out.println("Error al abrir los sockets");
        }
    }

    /**
     * 
     * @param datos que recibe la cadena de texto escrito por el porgrama servido
     * mediante el teclado
     */
    public void enviarDatos(String datos) {
        try {
            //se obtiene el flujo de salida
            out = servicio.getOutputStream();
            //se prepara para escribir algo
            salidaDatos = new DataOutputStream(out);
            //se escribe el mensaje
            salidaDatos.writeUTF(datos);
            //se envia el mensaje
            salidaDatos.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    //metodo que recibe los datos del socket cliente
    public void recibirDatos() {
        try {
            //obtenemos el flujo de datos
            in = servicio.getInputStream();
            //se prepara para leer el flujo de datos
            entradaDatos = new DataInputStream(in);
            //se leen los datos en UTF
            System.out.println(entradaDatos.readUTF());
            
            //en caso de un error
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //metodo que cierra entrada y salida de datos así como los sockets
    public void cerrarTodo() {
        try {
            salidaDatos.close();
            entradaDatos.close();
            sc.close();
            servicio.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Metodo principal que ejecuta la clase Server
    public static void main(String[] args) {
        Server serv = new Server();
        //Iniciamos el socket servidor en el puerto 1234
        serv.conexion(1234);
    }

}
