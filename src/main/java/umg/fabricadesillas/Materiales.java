package umg.fabricadesillas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Materiales {

    private Connection connection;

    public Materiales() {
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

    public void comprarMateriales(String material, int cantidad, double precioUnidad) {
        if (cantidad <= 0 || precioUnidad <= 0) {
            System.out.println("La cantidad y el precio por unidad deben ser mayores que cero.");
            return;
        }

        double costoTotal = cantidad * precioUnidad;

        // Registrar la compra en la base de datos
        registrarCompraEnBaseDeDatos(material, cantidad, precioUnidad, costoTotal);

        System.out.println(cantidad + " unidades de " + material + " compradas con éxito.");
        System.out.println("Costo total de la compra: $" + costoTotal);
    }

    private void registrarCompraEnBaseDeDatos(String material, int cantidad, double costoUnidad, double costoTotal) {
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
