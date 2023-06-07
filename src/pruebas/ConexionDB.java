package pruebas;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConexionDB {
	
	public DataSource dts;
	
	public static void main(String[] args) {
		
		ComboPooledDataSource comboPool = new ComboPooledDataSource();
		comboPool.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
		comboPool.setUser("root");
		comboPool.setPassword("201089Andres");
		
		System.out.println("cerrada");
		
	}

}
