/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.modificaarchivos;

/**
 *
 * @author semomo1208
 */

import java.io.*;
import java.util.*;

public class ModificaArchivos {
    
    
        // Crea el archivo de inventario pidiendo registros al usuario
    private static void crearArchivoInventario(Scanner sc, String rutaArchivo) {
        System.out.println("\n=== CREACIÓN DEL INVENTARIO ===");
        System.out.println("Se guardará en este formato: Producto, Stock, Precio");
        System.out.println("Escribe 'FIN' como producto para terminar.\n");

        // try-with-resources asegura cierre del archivo
        try {

            
            FileWriter outFile = new FileWriter(rutaArchivo);
            BufferedWriter bw = new BufferedWriter(outFile);
            // Cabecera opcional (puedes quitarla si no la quieres)
            bw.write("Producto,Stock,Precio");
            bw.newLine();

            while (true) {
                System.out.print("Escriba un producto (o FIN para terminar): ");
                //.trim() elimina los espacios en blanco antes y después
                String producto = sc.nextLine().trim();
                if (producto.equalsIgnoreCase("FIN")) break;

                System.out.print("Escriba el stock: ");
                String stockStr = sc.nextLine().trim();
                int stock = Integer.parseInt(stockStr);        
                
                System.out.print("Escriba el precio: ");
                String precioStr = sc.nextLine().trim();
                double precio = Double.parseDouble(precioStr);
                
                
                if (!producto.isEmpty() && !stockStr.isEmpty() && !precioStr.isEmpty()){
                     // Guardado secuencial: una línea por registro
                    bw.write(producto + "\t" + stockStr + "\t" + precioStr);
                    bw.newLine();
                }

               
            }
            bw.close();
            System.out.println("\n Archivo creado: " + rutaArchivo);
            
        }
        
         catch (IOException ex) {
            System.out.println("Error creando el archivo: ");
            ex.printStackTrace();
        }
    }
    
    
    // Incrementa los precios en 5% leyendo secuencialmente y escribiendo a un archivo temporal
    private static void modificarArchivoInventario(String rutaArchivo) throws IOException {
        

        File original = new File(rutaArchivo);
        //Se debe leer el archivo original
        BufferedReader br = new BufferedReader(new FileReader(original));
        
        //Se crea un archivo en blanco para copiar el inventario modificado
        //Se crea una ruta temporal o puede ser en la misma ruta
        String rutaTemp = rutaArchivo + ".tmp";
        File temporal = new File(rutaTemp);
        BufferedWriter bw = new BufferedWriter(new FileWriter(temporal));
        
        try {

            String linea;

            while ((linea = br.readLine()) != null) {


                // Parseo del .txt: Producto, Stock, Precio
                // (Para ejercicios introductorios, split con este tipo de archivo está bien; en casos reales usarías un archivo y parser CSV)
                String[] partes = linea.split("\t");
                if (partes.length != 3) {
                    // Si una línea no tiene el formato esperado, se copia igual (o podrías reportarla)
                    bw.write(linea);
                    bw.newLine();
                    continue;
                }

                String producto = partes[0].trim();
                String stockStr = partes[1].trim();
                String precioStr = partes[2].trim();

                int stock = Integer.parseInt(stockStr);
                double precio = Double.parseDouble(precioStr);

                double nuevoPrecio = precio * 1.05; // +5%
                String nuevoPrecioStr = String.valueOf(nuevoPrecio);

                bw.write(producto + "\t" + stockStr + "\t" + nuevoPrecioStr);
                bw.newLine();
            }
            
            
            
        }
        
         catch (IOException ex) {
            System.out.println("Error creando el archivo: ");
            ex.printStackTrace();
        }

        // Reemplazar el archivo original por el temporal (operación típica al "modificar" texto secuencial)
        // Reemplazar original por temporal
        bw.close();
        br.close();
        original.delete();
        temporal.renameTo(original);
        System.out.println("Archivo actualizado correctamente.");
        
        
    }

    

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        String rutaArchivo = "inventario.txt"; // archivo por defecto
        int opcionMenu;
        
        do {
            System.out.println("\n===== MENÚ INVENTARIO =====");
            System.out.println("1. Crear archivo de inventario");
            System.out.println("2. Modificar archivo (incrementar precios 5%)");
            System.out.println("3. Mostrar archivo");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            opcionMenu = Integer.parseInt(sc.nextLine());
            
            


            try {
                switch (opcionMenu) {
                    case 1: {//Se llama a la función de crear archivo
                        System.out.print("Ingrese el nombre/ruta del archivo a crear (ej: inventario.txt): ");
                        rutaArchivo = sc.nextLine().trim();
                        crearArchivoInventario(sc, rutaArchivo);
                        break;
                    }
                    case 2: {
                        System.out.print("\n¿Deseas modificar el archivo? (S/N): ");
                        String opcion = sc.nextLine().trim().toUpperCase();

                        if (opcion.equals("S")) {
                            modificarArchivoInventario(rutaArchivo);
                            System.out.println("Archivo modificado");
                            break;
                        } else {
                            System.out.println("No se modifica el archivo");
                            break;
                        }
                    }
                    case 3: // Mostrar el archivo original/modificado. Función multiuso.
                        BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
                        System.out.println("\n--- Contenido final del inventario ---");
                        try {
                            String ll;
                            while ((ll = br.readLine()) != null) {
                                System.out.println(ll);
                            }
                            br.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case 4:
                        System.out.println("Saliendo del programa...");
                        break;

                    default:
                        System.out.println(" Opción inválida.");

                }

            } catch (IOException e) {
                System.out.println("Error de archivo: " + e.getMessage());
            }

        } while (opcionMenu != 4);

        sc.close();

    }
}
