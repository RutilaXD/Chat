/*
 * Programa cliente
 */
package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import cliente.Client;

/**
 *
 * @author Gabriel
 */
public class server {

    private Socket miServicio;
    private ServerSocket socketServicio;

    private OutputStream outputStream;
    private InputStream inputStream;

    private DataOutputStream salidaDatos;
    private DataInputStream entradaDatos;

    private boolean opcion = true;
    private Scanner scanner;
    private String esctribir;

    //APERTURA DE SOCKET
    public void conexion(int numeroPuerto) {
        try {
            socketServicio = new ServerSocket(numeroPuerto);
            System.out.println("El servidor se esta escuchando en el puerto: " + numeroPuerto);
            miServicio = socketServicio.accept();
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (opcion) {
                        System.out.print("SERVIDOR: ");
                        recibirDatos();

                    }
                }
            });
            hilo.start();
            while (opcion) {
                scanner = new Scanner(System.in);
                esctribir = scanner.nextLine();
                if (!esctribir.equals("CLIENTE: fin")) {
                    enviarDatos("SERVIDOR: " + esctribir);
                } else {
                    opcion = false;
                }
            }
            miServicio.close();
        } catch (Exception ex) {
            System.out.println("Error al abrir los sockets");
        }
    }

    public void enviarDatos(String datos) {
        try {
            outputStream = miServicio.getOutputStream();
            salidaDatos = new DataOutputStream(outputStream);
            salidaDatos.writeUTF(datos);
            salidaDatos.flush();
        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void recibirDatos() {
        try {
            inputStream = miServicio.getInputStream();
            entradaDatos = new DataInputStream(inputStream);
            System.out.println(entradaDatos.readUTF());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarTodo() {
        try {
            salidaDatos.close();
            entradaDatos.close();
            socketServicio.close();
            miServicio.close();

        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        server serv = new server();
        serv.conexion(5555);
    }

}
