package umg.fabricadesillas;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Silla {
    private Connection connection;
    private int cantidad;

    public Silla() {
        // Establecer la conexión a la base de datos PostgreSQL
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String usuario = "postgres";
        String contraseña = "1234";

        try {
            connection = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos PostgreSQL.");
            e.printStackTrace();
        }
    }
    private String TipoMaterial;
    private String Color;
    private String Estilo;
    

    public Silla(String TipoMaterial, String Color, String Estilo) {
        this.TipoMaterial = TipoMaterial;
        this.Color = Color;
        this.Estilo = Estilo;
    }

    public void FabricarSilla(Materiales inventario) {
        if (verificarInventario(inventario)) {
            System.out.println("Fabricando una silla con los siguientes detalles:");
            System.out.println("Tipo de material: " + TipoMaterial);
            System.out.println("Color: " + Color);
            System.out.println("Estilo: " + Estilo);
            System.out.println("La silla ha sido fabricada con éxito.");

            // Guardar los detalles de la silla en un archivo
            guardarSillaEnArchivo(TipoMaterial, Color, Estilo);
        } else {
            System.out.println("No hay suficientes materiales para fabricar la silla.");
        }
    }

    public void ArmarSilla(Materiales inventario) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa el tipo de material:");
        System.out.println("1. Madera");
        System.out.println("2. Plástico");
        System.out.print("Tipo de material: ");
        int tipoMaterialSeleccionado = scanner.nextInt();

        String tipoMaterial = (tipoMaterialSeleccionado == 1) ? "Madera" : "Plástico";

        System.out.println("Ingresa el color:");
        System.out.println("1. Café");
        System.out.println("2. Blanca");
        System.out.print("Color: ");
        int colorSeleccionado = scanner.nextInt();

        String color = (colorSeleccionado == 1) ? "Café" : "Blanca";

        System.out.println("Ingresa el estilo:");
        System.out.println("1. Moderna");
        System.out.println("2. Antigua");
        System.out.print("Estilo: ");
        int estiloSeleccionado = scanner.nextInt();

        String estilo = (estiloSeleccionado == 1) ? "Moderna" : "Antigua";

        if (verificarInventario(inventario)) {
            System.out.println("Armando una silla con los siguientes detalles:");
            System.out.println("Tipo de material: " + tipoMaterial);
            System.out.println("Color: " + color);
            System.out.println("Estilo: " + estilo);
            System.out.println("La silla ha sido armada con éxito.");

            // Guardar los detalles de la silla en un archivo
            guardarSillaEnArchivo(tipoMaterial, color, estilo);
        } else {
            System.out.println("No hay suficientes materiales para armar la silla.");
        }
    }

    
    private void guardarSillaEnArchivo(String tipoMaterial, String color, String estilo) {
        try (FileWriter archivo = new FileWriter("sillas.txt", true);
             PrintWriter escritor = new PrintWriter(archivo)) {
            // Escribir los detalles de la silla en el archivo.
            escritor.println("Tipo de material: " + tipoMaterial);
            escritor.println("Color: " + color);
            escritor.println("Estilo: " + estilo);
            escritor.println("----------------------------------");
        } catch (IOException e) {
            System.err.println("Error al guardar la silla en el archivo.");
            e.printStackTrace();
        }
    }

    private boolean verificarInventario(Materiales inventario) {
        // Obtener la cantidad de material necesario para fabricar o armar la silla
        int cantidadMaterialNecesario = 1; // Puedes ajustar esta cantidad según tus necesidades

        // Verificar si hay suficiente disponibilidad en el inventario
        return inventario.getDisponibilidad() >= cantidadMaterialNecesario;
    }

    public void mostrarMenu(Materiales inventario) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Menú de Silla");
            System.out.println("1. Fabricar Silla");
            System.out.println("2. Armar Silla");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    FabricarSilla(inventario);
                    break;
                case 2:
                    ArmarSilla(inventario);
                    break;
                case 3:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, selecciona una opción válida.");
            }
        } while (opcion != 3);
    }
    private void registrarCompraEnBaseDeDatos(String material, String Color, double costoUnidad, double costoTotal) {
        String query = "INSERT INTO compras (fecha, material, cantidad, costo_unidad, costo_total) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Timestamp fecha = new Timestamp(System.currentTimeMillis());
            preparedStatement.setTimestamp(1, fecha);
            preparedStatement.setString(2, material);
            preparedStatement.setInt(3, cantidad);
            preparedStatement.setDouble(4, costoUnidad);
            preparedStatement.setDouble(5, costoTotal);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar la compra en la base de datos.");
            e.printStackTrace();
        }
    }
}
