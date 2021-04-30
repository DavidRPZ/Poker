package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConexionBD {

	public static Connection abrirConexion() {
		Connection con = null;
		try {
			String url = "jdbc:mysql://localhost:3306/Poker?serverTimezone=UTC";

		Properties props = new Properties();

		props.put("user", "root");
		props.put("password", "");

		con = DriverManager.getConnection(url, props);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/*
	 * try { // 2. preparar la sentencia de SQL Statement st =
	 * con.createStatement(); String consulta = "select * from libros"; ResultSet
	 * res = st.executeQuery(consulta); // 3. Procesar las filas resultado de la
	 * consulta int i = 0; while (res.next()) {
	 * 
	 * //titulos[i]= res.getString("titulo"); autores[i]=res.getString("autor");
	 * //precios[i]=(float)res.getDouble("precio");
	 * 
	 * Libro lib = new Libro(res.getString("titulo"), res.getString("autor"),
	 * (float) res.getDouble("precio")); libros.add(lib); i++; } // 4. Cerrar los
	 * objetos Statement y ResultSet res.close(); st.close(); } catch (SQLException
	 * e) { e.printStackTrace(); } finally { cerrarConexion(con); }
	 */

	public static void cerrarConexion(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
