package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import jdbc.modelo.Huespedes;
import jdbc.modelo.Reserva;

public class HuespedDAO {

	private Connection con;

	public HuespedDAO(Connection con) {
		super();
		this.con = con;
	}

	public void guardar(Huespedes huespedes) {

		try {
			String sql = "INSERT INTO huespedes (nombre, apellido, fecha_nacimiento, nacionalidad "
					+ ", telefono, id_reserva) VALUE(?,?,?,?,?,?)";

			try (PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstm.setString(1, huespedes.getNombre());
				pstm.setString(2, huespedes.getApellido());
				pstm.setObject(3, huespedes.getFechaNacimiento());
				pstm.setString(4, huespedes.getNacionalidad());
				pstm.setString(5, huespedes.getTelefono());
				pstm.setInt(6, huespedes.getIdReserva());
				pstm.execute();

				try (ResultSet rst = pstm.getGeneratedKeys()) {

					while (rst.next()) {
						huespedes.setId(rst.getInt(1));
						;
					}

				}

			}

		} catch (SQLException e) {
			throw new RuntimeException("Error" + e.getMessage(), e);
		}

	}
	
	

	public List<Huespedes> mostrar() {

		List<Huespedes> huespedes = new ArrayList<Huespedes>();

		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes";

			try (PreparedStatement psmt = con.prepareStatement(sql)) {
				psmt.execute();
				convertirReserva(huespedes, psmt);

			}
			return huespedes;

		} catch (SQLException e) {
			throw new RuntimeException();
		}

	}
	
	public List<Huespedes> buscarId(String id) {

		List<Huespedes> huespedes = new ArrayList<Huespedes>();

		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes WHERE id=?";

			try (PreparedStatement psmt = con.prepareStatement(sql)) {
				psmt.setString(1, id);
				psmt.execute();
				convertirReserva(huespedes, psmt);

			}
			return huespedes;

		} catch (SQLException e) {
			throw new RuntimeException();
		}

	}
	
	
	public void actualizarHuesped(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		try (PreparedStatement pstm = con.prepareStatement(""
				+ "UPDATE huespedes SET nombre=?, apellido=?, fecha_nacimiento=?, nacionalidad=?,"
				+ "telefono=?, id_reserva=? WHERE id=?")){
			
			pstm.setString(1, nombre);
			pstm.setString(2, apellido);
			pstm.setObject(3, fechaNacimiento);
			pstm.setString(4, nacionalidad);
			pstm.setString(5,telefono);
			pstm.setInt(6, idReserva);
			pstm.setInt(7, id);
			pstm.execute();
			
		} catch (SQLException e) {
			//throw new RuntimeException(e);
			throw new RuntimeException("Error" + e.getMessage(), e);
		}
		
	}
	
	public void Eliminar(Integer id) {
		try (PreparedStatement pstm = con.prepareStatement("DELETE FROM huespedes WHERE id=?")){
			
			pstm.setInt(1, id);
			pstm.execute();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	private void convertirReserva(List<Huespedes> huespedes, PreparedStatement psmt) throws SQLException {

		//try (ResultSet rst = psmt.getResultSet()) {

		try (ResultSet rst = psmt.executeQuery()) {
			while (rst.next()) {
				int id = rst.getInt("id");
				String nombre = rst.getString("nombre");
				String apellido = rst.getString("apellido");
				LocalDate fechaNacimiento = rst.getDate("fecha_nacimiento").toLocalDate().plusDays(1);
				String nacionalidad = rst.getString("nacionalidad");
				String telefono = rst.getString("telefono");
				int idReserva = rst.getInt("id_reserva");


				Huespedes huesped = new Huespedes(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
				huespedes.add(huesped);
			}

		}

	}

}
