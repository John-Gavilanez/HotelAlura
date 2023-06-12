package jdbc.controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import jdbc.dao.HuespedDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;

public class huespedController {
	
	private HuespedDAO huespedesDao;

	public huespedController() {
		Connection con = new ConnectionFactory().recuperarConexion();
		this.huespedesDao = new HuespedDAO(con);
	}
	
	public void guardar(Huespedes huespedes) {
		this.huespedesDao.guardar(huespedes);

	}	
	
	public List<Huespedes> mostrarHuesped() {
		return this.huespedesDao.mostrar();
	}
	
	public List<Huespedes> buscarHuesped(String id) {
		return this.huespedesDao.buscarId(id);
	}
	
	public void actualizarHuespedes(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		
		this.huespedesDao.actualizarHuesped(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, id);
		
	}
	
	public void Eliminar(Integer idReserva) {
		this.huespedesDao.Eliminar(idReserva);
		
	}

}
