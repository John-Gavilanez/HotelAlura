package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<Reserva> mostrar() {
		
		List<Reserva> reserva = new ArrayList<Reserva>();
		
		try {
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_de_pago FROM reservas";
			
			try(PreparedStatement psmt = con.prepareStatement(sql)){
				psmt.execute();
				
				convertirReserva(reserva, psmt);
				
			}
			return reserva;
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
	}
	
	
	private void convertirReserva(List<Reserva> reserva, PreparedStatement psmt) throws SQLException {
		
		try (ResultSet rst = psmt.getResultSet()){
			
			while(rst.next()) {
				int id = rst.getInt("id");
				LocalDate FechaEntrada = rst.getDate("fecha_entrada").toLocalDate().plusDays(1);
				LocalDate FechaSalida = rst.getDate("fecha_salida").toLocalDate().plusDays(1);
				String valor = rst.getString("valor");
				String formaPago = rst.getString("forma_de_pago");
				
				Reserva habitacion = new Reserva(id, FechaEntrada, FechaSalida, valor, formaPago);
				reserva.add(habitacion);
						
				
			}
			
		} 
		

	}
	

}
