package jdbc.controller;

import java.sql.Connection;

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
	
	

}
