package jdbc.controller;

import java.sql.Connection;

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
	
}
