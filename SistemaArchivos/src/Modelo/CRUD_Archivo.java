/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo;

import java.io.File;

/**
 *
 * @author Alejandro Penagos
 */
public interface CRUD_Archivo
{
    public File crearArchivo(String nombre);
    public boolean modificarArchivo(String nombreActual, String nuevoNombre);
    public boolean eliminarArchivo(String nombre);
    public File mostrarArchivo();
}
