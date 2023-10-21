package umg.fabricadesillas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionDB {
    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String usuario = "admin";
    private static String contraseña = "1234";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void guardarSillaEnDB(Silla silla) {
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO sillas (tipo, color, estilo) VALUES (?, ?, ?)")) {
            stmt.setString(1, silla.getTipoMaterial());
            stmt.setString(2, silla.getColor());
            stmt.setString(3, silla.getEstilo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void mostrarSillasAlmacenadas() {
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM sillas");
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Sillas almacenadas en la base de datos:");
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                String color = rs.getString("color");
                String estilo = rs.getString("estilo");
                System.out.println("Tipo: " + tipo + ", Color: " + color + ", Estilo: " + estilo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
