/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Alejandro Penagos
 */
public class FAT implements CRUD_Archivo
{

    private static final int CLUSTER_SIZE = 512; // Tamaño de clúster en bytes
    private static final int NUM_CLUSTERS = 100; // Número de clústeres en el disco simulado

    private static final Cluster[] disk = new Cluster[NUM_CLUSTERS + 1]; // Disco simulado, la primera posicion se usara como un de control para manejar las posiciones libres

    private static final String directorioRaiz = "C:\\Users\\Alejandro Penagos\\Desktop\\Alejandro\\6. ProyectosU\\ProyectoFinalSO\\Root";

    @Override
    public File crearArchivo(String nombre) {
        File archivo = null;
        BufferedWriter writer = null; // Declara el BufferedWriter fuera del try-catch para poder cerrarlo después

        try {
            // Concatena el nombre del archivo con la ruta del directorio raíz
            String rutaCompleta = directorioRaiz + File.separator + nombre;
            archivo = new File(rutaCompleta);

            if (archivo.createNewFile()) {
                System.out.println("Archivo creado en: " + rutaCompleta);
            } else {
                System.out.println("El archivo ya existe.");
            }

            writer = new BufferedWriter(new FileWriter(archivo));
            writer.write("File Allocation Table (FAT) is a file system developed for personal computers and was the default filesystem for MS-DOS and Windows 9x operating systems.[3] Originally developed in 1977 for use on floppy disks, it was adapted for use on hard disks and other devices. The increase in disk drives capacity required four major variants: FAT12, FAT16, FAT32, and ExFAT. FAT was replaced with NTFS as the default file system on Microsoft operating systems starting with Windows XP.[4] Nevertheless, FAT continues to be used on flash and other solid-state memory cards and modules (including USB flash drives), many portable and embedded devices because of its compatibility and ease of implementation.[5]");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al crear el archivo.");
            e.printStackTrace();
        } finally {
            // Cierra el BufferedWriter en el bloque finally para asegurar que se cierre incluso si ocurre una excepción
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Ocurrió un error al cerrar el BufferedWriter.");
                e.printStackTrace();
            }
        }
        return archivo;
    }

    // Modificar el nombre de un archivo existente
    @Override
    public boolean modificarArchivo(String nombreActual, String nuevoNombre) {
        File archivoActual = new File(nombreActual);
        File archivoNuevo = new File(nuevoNombre);

        // Verificar si el archivo actual existe y si el nuevo nombre no está en uso
        if (archivoActual.exists() && !archivoNuevo.exists()) {
            boolean renombrado = archivoActual.renameTo(archivoNuevo);
            if (renombrado) {
                System.out.println("Archivo renombrado a: " + archivoNuevo.getName());
                return true;
            } else {
                System.out.println("No se pudo renombrar el archivo.");
                return false;
            }
        } else {
            System.out.println("El archivo actual no existe o el nuevo nombre ya está en uso.");
            return false;
        }
    }

    @Override
    public boolean eliminarArchivo(String nombre) {
        File myObj = new File(nombre);
        boolean estado;
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
            estado = true;
        } else {
            System.out.println("Failed to delete the file.");
            estado = false;
        }
        return estado;
    }

    @Override
    public File mostrarArchivo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
