package jdbc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import jdbc.modelo.usuarios;
import views.Login;
import views.MenuUsuario;

public class usuariosController implements ActionListener {
	
	private Login login;

	public usuariosController(Login login) {
		this.login=login;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String nombre = login.getNombre();
		String contrasenha = login.getContrasenha();
		
		if (usuarios.validacionUsuario(nombre, contrasenha)) {
			
			MenuUsuario menu = new MenuUsuario();
			menu.setVisible(true);
			login.dispose();
						
		}else {
			JOptionPane.showMessageDialog(login, "Usuario o contraseña no validos");
		}
	}

}
