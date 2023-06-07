package jdbc.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.factory.ConnectionFactory;

public class usuarios {
	
	private String nombre;
	private String contrasenha;
	
	public usuarios(String nombre, String contrasenha) {
		this.nombre = nombre;
		this.contrasenha = contrasenha;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrasehna() {
		return contrasenha;
	}
	public void setContrasehna(String contrasenha) {
		this.contrasenha = contrasenha;
	}
	
	public static boolean validacionUsuario(String nombre, String contrasenha){
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		
		try {
			con = factory.recuperarConexion();
			statement = con.prepareStatement("SELECT * FROM usuarios WHERE nombre=? AND contrasenha =?");
			statement.setString(1, nombre);
			statement.setString(2, contrasenha);
			result = statement.executeQuery();
			return result.next();
					
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				if(result != null)
					result.close();
				if (statement != null ) 
					statement.close();
				if (con != null)
					con.close();
				
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
	}
	

}
