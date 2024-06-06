/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Alejandro Penagos
 */
public class Cluster
{
    private String nombre;  // Nombre del archivo o directorio del clúster
    private boolean isDirectorio;
    private int primerCluster;  // El primer clúster del archivo
    private int tamaño;  // Tamaño del archivo en bytes
    private int siguienteCluster;  // Índice del siguiente clúster
    private byte[] bytes;  // Datos del clúster

    public Cluster(int clusterSize) {
        this.bytes = new byte[clusterSize];
        this.siguienteCluster = -1;  // -1 indica el final de la cadena de clústeres
    }

    // Getters y Setters para los campos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isDirectorio() {
        return isDirectorio;
    }

    public void setDirectorio(boolean isDirectorio) {
        this.isDirectorio = isDirectorio;
    }

    public int getPrimerCluster() {
        return primerCluster;
    }

    public void setPrimerCluster(int primerCluster) {
        this.primerCluster = primerCluster;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public int getSiguienteCluster() {
        return siguienteCluster;
    }

    public void setSiguienteCluster(int siguienteCluster) {
        this.siguienteCluster = siguienteCluster;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
