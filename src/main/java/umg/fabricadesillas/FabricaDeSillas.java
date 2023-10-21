package umg.fabricadesillas;

import java.util.Scanner;

public class FabricaDeSillas {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Crear una instancia de Materiales
        Materiales materiales = new Materiales();

        int opcion;
        
        do {
            System.out.println("Menú Principal");
            System.out.println("1. Fabricar Silla");
            System.out.println("2. Compras");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    System.out.println("Introduce los detalles de la silla:");
                    System.out.print("Tipo de material: ");
                    String tipoMaterial = scanner.next();
                    System.out.print("Color: ");
                    String color = scanner.next();
                    System.out.print("Estilo: ");
                    String estilo = scanner.next();
                    
                    Silla silla = new Silla(tipoMaterial, color, estilo);
                    silla.FabricarSilla(materiales);
                    break;
                
                case 2:
                    System.out.print("Nombre del material: ");
                    String material = scanner.next();
                    System.out.print("Cantidad: ");
                    int cantidad = scanner.nextInt();
                    System.out.print("Precio por unidad: ");
                    double precioUnidad = scanner.nextDouble();
                    materiales.comprarMateriales(material, cantidad, precioUnidad);
                    break;
                
                case 3:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, selecciona una opción válida.");
            }
        } while (opcion != 3);
    }
}
