/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package umg.fabricadesillas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author josesalazar
 */

public class Pedido {

    // Declaración de variables
    private int Cantidad;
    private String TipoSilla;
    private Date FechaPedido;

    // Getters y setters
    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public String getTipoSilla() {
        return TipoSilla;
    }

    public void setTipoSilla(String TipoSilla) {
        this.TipoSilla = TipoSilla;
    }

    public Date getFechaPedido() {
        return FechaPedido;
    }

    public void setFechaPedido(Date FechaPedido) {
        this.FechaPedido = FechaPedido;
    }

    // Método para realizar un pedido
    public void RealizarPedido(Silla silla, String archivoInventario) throws FileNotFoundException {
        // Leer el archivo de inventario para verificar las existencias
        int existenciasDisponibles = obtenerExistenciasEnInventario(archivoInventario);

        // Si no hay suficientes existencias, mostrar un mensaje y no continuar con el pedido
        if (existenciasDisponibles <= 0) {
            System.out.println("No hay suficientes existencias de sillas en el inventario.");
            return;
        }

        // Obtener los datos de la silla a través del método de la clase Silla
        // Declaración de variables para los datos del pedido
        int cantidad = 0;
        String tipoSilla = null;

        // Procesar los datos de la silla
        String[] parts = sillaData.split(",");
        for (String part : parts) {
            String[] keyValue = part.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                if (key.equals("Cantidad")) {
                    cantidad = Integer.parseInt(value);
                } else if (key.equals("TipoSilla")) {
                    tipoSilla = value;
                }
            }
        }

        // Verificar si se obtuvieron los datos necesarios
        if (cantidad > 0 && tipoSilla != null) {
            // Verificar si la cantidad deseada está disponible en el inventario
            if (existenciasDisponibles >= cantidad) {
                // Restar la cantidad pedida del inventario
                restarExistenciasDelInventario(archivoInventario, cantidad);

                // Se establecen los datos del pedido
                this.Cantidad = cantidad;
                this.TipoSilla = tipoSilla;
                this.FechaPedido = new Date(); // Fecha actual

                System.out.println("Pedido realizado: " + this.Cantidad + " sillas de tipo '" + this.TipoSilla + "' el " + this.FechaPedido);

                // Llamar al método para guardar el pedido en el archivo
                guardarPedidoEnArchivo(archivoInventario);
            } else {
                System.out.println("No hay suficientes existencias de sillas en el inventario.");
            }
        } else {
            System.out.println("No se pudieron obtener los datos de la silla.");
        }
    }

// Método para obtener las existencias disponibles en el inventario
    private int obtenerExistenciasEnInventario(String archivoInventario) throws FileNotFoundException {
        int existencias = 0;

        try (Scanner scanner = new Scanner(new File(archivoInventario))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();

                
                String[] partes = linea.split(",");

                if (partes.length == 2) {
                    String tipoSilla = partes[0].trim();
                    int cantidadExistencias = Integer.parseInt(partes[1].trim());

                    // Sumar la cantidad de existencias de esta silla a 'existencias'
                    existencias += cantidadExistencias;
                }
            }

            return existencias;
        }
    }

// Método para restar las existencias del inventario después de un pedido
    private void restarExistenciasDelInventario(String archivoInventario, int cantidadARestar) {
        List<String> lineas = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(archivoInventario))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(",");

                if (partes.length == 2) {
                    String tipoSilla = partes[0].trim();
                    int existencias = Integer.parseInt(partes[1].trim());

                    // Restar la cantidadARestar si corresponde al tipo de silla
                    if (existencias > 0 && cantidadARestar > 0) {
                        int cantidadRestada = Math.min(cantidadARestar, existencias);
                        existencias -= cantidadRestada;
                        cantidadARestar -= cantidadRestada;
                    }

                    // Agregar la línea actualizada a la lista de líneas
                    lineas.add(tipoSilla + "," + existencias);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Guardar las líneas actualizadas en el archivo
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoInventario))) {
            for (String linea : lineas) {
                writer.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para preparar un pedido
    public void PrepararPedido(Materiales inventario) {

        if (inventario != null && inventario.verificarInventario()) {
            System.out.println("Pedido preparado: " + this.Cantidad + " sillas de tipo '" + this.TipoSilla + "' el " + this.FechaPedido);
        } else {
            System.out.println("No hay suficientes materiales en el inventario para preparar el pedido.");
        }
    }

    // Método para verificar un pedido
    public void VerificarPedido() {

        int cantidadPreparada = 10;

        if (cantidadPreparada == this.Cantidad) {
            System.out.println("Pedido verificado: el pedido está completo.");
        } else {
            System.out.println("Pedido verificado: el pedido está incompleto.");
        }
    }

    private void guardarPedidoEnArchivo(String archivoInventario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void RealizarPedido(Silla silla) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void PrepararPedido(Materiales inventario, Silla silla) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static class sillaData {

        private static String[] split(String string) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public sillaData() {
        }
    }

    public void menuPedido(Materiales inventario, Silla silla) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Menú de Pedido");
            System.out.println("1. Preparar Pedido");
            System.out.println("2. Verificar Pedido");
            System.out.println("3. Realizar Pedido");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    PrepararPedido(inventario, silla);
                    break;
                case 2:
                    VerificarPedido();
                    break;
                case 3:
                    RealizarPedido(silla);
                    break;
                case 4:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, selecciona una opción válida.");
            }
        } while (opcion != 4);
    }

}
