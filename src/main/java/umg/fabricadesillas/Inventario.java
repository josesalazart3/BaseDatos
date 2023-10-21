import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Compras {
    public static void registrarCompra(String nombreMaterial, int cantidad, double costoUnidad, double costoTotal) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("registro_compras.txt", true))) {
            String registro = String.format("Material: %s, Cantidad: %d, Costo por unidad: %.2f, Costo total: %.2f",
                    nombreMaterial, cantidad, costoUnidad, costoTotal);
            writer.println(registro);
            System.out.println("Compra registrada con éxito.");
        } catch (IOException e) {
            System.err.println("Error al registrar la compra en el archivo.");
            e.printStackTrace();
        }
    }

    public static void mostrarComprasRealizadas() {
        System.out.println("Compras realizadas:");
        try (BufferedReader reader = new BufferedReader(new FileReader("registro_compras.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de registro de compras.");
            e.printStackTrace();
        }
    }
}
