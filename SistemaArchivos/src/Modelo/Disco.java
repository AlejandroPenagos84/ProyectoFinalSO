/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;


/**
 *
 * @author Alejandro Penagos
 */
public class Disco
{

    private final int clusterSize; // Tamaño de clúster en bytes
    private final int numClusters; // Número de clústeres en el disco simulado
    private int numClusterLibres;
    private final Cluster[] disk; // Disco simulado, la primera posición se usará como control para manejar las posiciones libres

    private final String directorioRaiz = "C:\\Users\\Alejandro Penagos\\Desktop\\Alejandro\\6. ProyectosU\\ProyectoFinalSO\\Root";

    // Constructor que recibe el número de clústeres y el tamaño de cada clúster
    public Disco(int numClusters, int clusterSize) {
        this.numClusters = numClusters;
        this.clusterSize = clusterSize;
        this.numClusterLibres = numClusters;
        this.disk = new Cluster[numClusters + 1];

        // Inicialización del disco
        for (int i = 0; i <= numClusters; i++) {
            disk[i] = new Cluster(clusterSize);
            if (i == numClusters) {
                disk[i].setSiguienteCluster(0); // La última posición libre apunta a 0
            } else {
                disk[i].setSiguienteCluster(i + 1);
            }
        }
    }

    // Método para buscar el primer clúster libre
    private int findFreeCluster() {
        int freeClusterIndex = disk[0].getSiguienteCluster();
        if (freeClusterIndex == 0) {
            throw new RuntimeException("No hay clústeres libres disponibles.");
        }
        disk[0].setSiguienteCluster(disk[freeClusterIndex].getSiguienteCluster());
        return freeClusterIndex;
    }

    // Método para liberar un clúster y devolverlo a la lista de clústeres libres
    private void releaseCluster(int clusterIndex) {
        disk[clusterIndex].setNombre(null);
        disk[clusterIndex].setDirectorio(false);
        disk[clusterIndex].setPrimerCluster(0);
        disk[clusterIndex].setTamaño(0);
        disk[clusterIndex].setSiguienteCluster(disk[0].getSiguienteCluster());
        disk[0].setSiguienteCluster(clusterIndex);
        numClusterLibres++;
    }

    public void crearArchivo(String nombre, byte data[]) {
        int clusterRequeridos = (int) Math.ceil((double) data.length / clusterSize);// Obtiene el numero de cluster requeridos
        int[] clusterAsignados = new int[clusterRequeridos];
        
        System.out.println("data.length "+ data.length);
        System.out.println("CLUSTERS REQUERIDOS "+ clusterRequeridos);
        // Esta asignando los valores del nombre y el primer cluster, aun no se manipulan los bytes
        for (int i = 0; i < clusterRequeridos; i++) {
            int clusterLibre = findFreeCluster();
            clusterAsignados[i] = clusterLibre;// Guarda los indices de los cluster libres
            disk[clusterLibre].setNombre(nombre);
            disk[clusterLibre].setPrimerCluster(clusterAsignados[0]);
            if (i > 0) {
                disk[clusterAsignados[i - 1]].setSiguienteCluster(clusterLibre);
            }
            numClusterLibres--;
        }

        for (int i = 0; i < clusterRequeridos; i++) {
            int numeroCluster = clusterAsignados[i]; // Obtiene el número de clúster asignado al archivo en la iteración actual del bucle.
            int inicio = i * numeroCluster; // Calcula la posición de inicio en el array `data` para los datos que se copiarán en este clúster.
            int length = Math.min(clusterSize, data.length - inicio); // Calcula la longitud de los datos que se copiarán en este clúster.
            // La longitud se establece como la menor de `clusterSize` (tamaño máximo del clúster) y la cantidad de datos restantes en `data`.
            byte[] clusterData = new byte[length]; // Crea un nuevo array para almacenar los datos que se copiarán en este clúster.
            System.arraycopy(data, inicio, clusterData, 0, length); // Copia los datos desde la posición de inicio en `data` hasta `clusterData`.
            // `System.arraycopy` copia `length` bytes de `data` comenzando desde `inicio` hasta `clusterData` a partir de la posición 0.
            disk[numeroCluster].setBytes(clusterData); // Establece los datos copiados en el clúster correspondiente del disco virtual.

        }
        disk[clusterAsignados[clusterRequeridos - 1]].setSiguienteCluster(-1); // Último clúster
    }

    // Método para eliminar un archivo del directorio raíz
    public boolean deleteFileFromRoot(String fileName) {
        boolean fileFound = false;
        for (int i = 1; i <= numClusters; i++) {
            if (disk[i].getNombre() != null && disk[i].getNombre().equals(fileName)) {
                fileFound = true;
                int clusterIndex = i;
                while (clusterIndex != -1) {
                    int nextCluster = disk[clusterIndex].getSiguienteCluster();
                    releaseCluster(clusterIndex);
                    clusterIndex = nextCluster;
                }
                break;
            }
        }
        return fileFound;
    }

    public int getNumClusterLibres() {
        return numClusterLibres;
    }

    public void setNumClusterLibres(int numClusterLibres) {
        this.numClusterLibres = numClusterLibres;
    }

    public Cluster[] getDisk() {
        return disk;
    }
    
    
}
