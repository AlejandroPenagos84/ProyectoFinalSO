/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author Alejandro Penagos
 */
public class main
{

    public static void main(String[] args) {
        FAT fat = new FAT();
        // Crear una instancia del disco virtual

        Disco disco = new Disco(100, 512); // Ejemplo de creación de un disco virtual con 100 clústeres de 512 bytes cada uno

        File prueba1 = fat.crearArchivo("PRUEBA1.txt");
        File prueba2 = fat.crearArchivo("PRUEBA2.txt");
        File prueba3 = fat.crearArchivo("PRUEBA3.txt");
        File prueba4 = fat.crearArchivo("PRUEBA4.txt");
        File prueba5 = fat.crearArchivo("PRUEBA5.txt");
        File prueba6 = fat.crearArchivo("PRUEBA6.txt");
        File prueba7 = fat.crearArchivo("PRUEBA7.txt");
        File prueba8 = fat.crearArchivo("PRUEBA8.txt");
        File prueba9 = fat.crearArchivo("PRUEBA9.txt");
        File prueba10 = fat.crearArchivo("PRUEBA10.txt");

        File[] pruebas = {prueba1, prueba2, prueba3, prueba4, prueba5, prueba6, prueba7, prueba8, prueba9, prueba10};
        for (File prueba : pruebas) {
            String nombreArchivo = prueba.getName();
            byte[] bytesArchivo = obtenerBytesArchivo(prueba.getAbsolutePath());
            disco.crearArchivo(nombreArchivo, bytesArchivo);
        }
        System.out.println("Numero de clusters libres " + disco.getNumClusterLibres());
        Cluster[] disk = disco.getDisk();

        for (int i = 0; i < disk.length; i++) {
            if (disk[i].getNombre() == null && i != 0) {
                break;
            }
            System.out.println("Cluster " + i);
            System.out.println("Nombre " + disk[i].getNombre());
            System.out.println("Bytes " + disk[i].getBytes());
            System.out.println("Tam Bytes " + disk[i].getBytes().length);

        }

        System.out.println(convertirBytesACadena(obtenerBytesDeArchivoEnDisco("PRUEBA1.txt", disk)));

    }

    // Método para obtener los bytes de un archivo
    private static byte[] obtenerBytesArchivo(String rutaArchivo) {
        try {
            // Convierte la ruta del archivo a un objeto Path
            Path path = Paths.get(rutaArchivo);

            // Lee todos los bytes del archivo utilizando Files.readAllBytes()
            return Files.readAllBytes(path);
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo.");
            e.printStackTrace();
            return new byte[0]; // Retorna un arreglo vacío en caso de error
        }
    }

    public static byte[] obtenerBytesDeArchivoEnDisco(String nombreArchivo, Cluster[] disk) {
        for (Cluster cluster : disk) {
            // Si el cluster tiene el nombre del archivo y no es un directorio
            if (cluster.getNombre() != null && cluster.getNombre().equals(nombreArchivo) && !cluster.isDirectorio()) {
                List<Byte> bytesList = new ArrayList<>();
                int clusterIndex = cluster.getPrimerCluster();
                // Recorre los clusters del archivo
                while (clusterIndex != -1) {
                    Cluster clusterActual = disk[clusterIndex];
                    byte[] bytesCluster = clusterActual.getBytes(); // Obtiene los bytes del cluster
                    for (byte b : bytesCluster) {
                        bytesList.add(b); // Agrega cada byte a la lista de bytes
                    }
                    clusterIndex = clusterActual.getSiguienteCluster(); // Obtiene el índice del siguiente cluster
                }
                // Convierte la lista de bytes a un arreglo de bytes
                byte[] bytesArray = new byte[bytesList.size()];
                for (int i = 0; i < bytesArray.length; i++) {
                    bytesArray[i] = bytesList.get(i);
                }
                return bytesArray;
            }
        }
        System.out.println("El archivo " + nombreArchivo + " no se encontró en el disco.");
        return new byte[0]; // Retorna un arreglo vacío si el archivo no se encuentra
    }

    public static String convertirBytesACadena(byte[] bytes) {
        return new String(bytes);
    }
}
