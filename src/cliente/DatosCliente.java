/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 *
 * @author Raul
 */
public class DatosCliente {
    private String nombre;
    private String contrasenia;
    private int edad;

    public DatosCliente(String nombre, String contrasenia, int edad) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.edad = edad;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @return the edad
     */
    public int getEdad() {
        return edad;
    }
}
