package jdbc.controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class reservaController {
	
	private ReservaDAO reservaDao;

	public reservaController() {
		Connection con = new ConnectionFactory().recuperarConexion();
		this.reservaDao = new ReservaDAO(con);
	}
	
	public void guardar (Reserva reserva) {
		
		this.reservaDao.guardar(reserva);
		
	}
	
	public List<Reserva> mostrar(){
		return this.reservaDao.mostrar();
	}
	
	public List<Reserva> buscar(String id){
		return this.reservaDao.buscarId(id);
	}
	
	public void actualizarReserva(LocalDate fechaEntrada, LocalDate fechaSalida, String valor, String formaPago, Integer id) {
		
		this.reservaDao.actualizar(fechaEntrada, fechaSalida, valor, formaPago, id);

	}		
	
	public void eliminar(Integer id) {
		this.reservaDao.eliminar(id);
		
	}
	
}
