package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.modelo.Reserva;

public class ReservaDAO {
	
	private Connection con;

	public ReservaDAO(Connection con) {
		super();
		this.con =con;
	}
	
	
	public void guardar(Reserva reserva) {
		try {
			String sql = "INSERT INTO reservas (fecha_entrada, fecha_salida, valor, forma_de_pago)"
					+ "VALUES (?,?,?,?)";
			
			try (PreparedStatement pstm = con.prepareStatement(sql,java.sql.Statement.RETURN_GENERATED_KEYS)) {
				
				pstm.setObject(1, reserva.getFechaEntrada());
				pstm.setObject(2, reserva.getFechaSalida());
				pstm.setObject(3, reserva.getValor());
				pstm.setObject(4, reserva.getFormaPago());
				pstm.executeUpdate();
				
				try (ResultSet rst = pstm.getGeneratedKeys()){
					
					while(rst.next()) {
						reserva.setId(rst.getInt(1));;
					}
					
				} 
				
			} 
		} catch (SQLException e) {
			throw new RuntimeException("Error"+e.getMessage(),e);
		}
		
	}
	

}
