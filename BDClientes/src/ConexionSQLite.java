import java.sql.*;

public class ConexionSQLite {
	Connection conexion;
	Statement sentencia;
	
	public ConexionSQLite(){
		//Cargar el driver:
		try{
			Class.forName("org.sqlite.JDBC");
		}catch(ClassNotFoundException e){
			System.out.println("Error al cargar el driver");
			e.printStackTrace();
		}
		//Establecer conexion con fuente de datos:
		try{
			conexion = DriverManager.getConnection("jdbc:sqlite:facturas.db");
			sentencia = conexion.createStatement();
		}catch(SQLException e){
			System.out.println("Error de conexion con la BD"); 
			e.printStackTrace();
		}
	}
	
	public ResultSet mostrarClientes(){
		try{
			return sentencia.executeQuery("SELECT * FROM Clientes;");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public int altaCliente(int codigo,String dni, String nombre, String fec){
		try{
			return sentencia.executeUpdate(
					"INSERT INTO Clientes VALUES ("+codigo+",'"+dni+"','"+nombre+"','"+fec+"');");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	
	public int bajaCliente(int codigo){
		try{
			return sentencia.executeUpdate(
					"DELETE FROM Clientes WHERE codigo= "+codigo+";");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	
	public ResultSet consultaCliente(int cod){
		try{
			return sentencia.executeQuery("SELECT * FROM Clientes WHERE codigo= "+cod+";");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public int modificarCliente(int codigo,String dni, String nombre, String fec){
		try{
			return sentencia.executeUpdate(
					"UPDATE Clientes SET dni='"+dni+"',nombre='"+nombre+"',fecnac='"+fec+"' WHERE codigo="+codigo+";");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	
	public void cerrarRecursos(){
		try{
			sentencia.close();
			conexion.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
