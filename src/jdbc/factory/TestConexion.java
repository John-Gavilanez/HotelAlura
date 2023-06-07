package jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConexion {
	
	public static void main(String[] args) throws SQLException {
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection con = factory.recuperarConexion();
		System.out.println("abriendo conexion");
		con.close();
		System.out.println("cerrando conexion");
		
	}
	
}
