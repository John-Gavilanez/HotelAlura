package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
public List<Reserva> buscarId(String id) {
		
		List<Reserva> reserva = new ArrayList<Reserva>();
		
		try {
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_de_pago FROM reservas WHERE id= ?";
			
			try(PreparedStatement psmt = con.prepareStatement(sql)){
				
				psmt.setString(1, id);
				psmt.execute();
				
				convertirReserva(reserva, psmt);
				
			}
			return reserva;
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
	}

	public void actualizar(LocalDate fechaEntrada, LocalDate fechaSalida, String valor, String formaPago, Integer id) {
		
		try (PreparedStatement pstm = con.prepareStatement("UPDATE reservas SET "
				+ "fecha_entrada=?, fecha_salida=?, valor=?, forma_de_pago=? WHERE id=?")) {
			
			pstm.setObject(1, java.sql.Date.valueOf(fechaEntrada));
			pstm.setObject(2, java.sql.Date.valueOf(fechaSalida));
			pstm.setObject(3, valor);
			pstm.setObject(4, formaPago);
			pstm.setObject(5, id);
			pstm.execute();
			
		} catch (SQLException e) {
			throw new RuntimeException("Error"+e.getMessage(),e);
		}

	}
	
	public void eliminar(Integer id) {
		try {
			
			Statement estate = con.createStatement();
			estate.execute("SET GLOBAL FOREIGN_KEY_CHECKS=0");
			PreparedStatement pstm = con.prepareStatement("DELETE FROM reservas WHERE id = ?");
			pstm.setInt(1,id);
			pstm.execute();
			estate.execute("SET GLOBAL FOREIGN_KEY_CHECKS=1");
			
		} catch (SQLException e) {
			//throw new RuntimeException();
			throw new RuntimeException("Error"+e.getMessage(),e);
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
