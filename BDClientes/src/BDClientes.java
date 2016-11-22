import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class BDClientes extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel modelo;
	private JTable tabla;
	private JTextField tfDni;
	private JTextField tfCodigo;
	private ConexionSQLite bd;
	private JTextField tfNombre;
	private int codigo, numReg;
	private String dni, nombre, fecha;
	private JTextField tfYear;
	private JComboBox<String> chMes, chDia;
	private JLabel lbInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BDClientes frame = new BDClientes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BDClientes() {
		setTitle("Gestion de clientes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 547);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Opciones");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmAlta = new JMenuItem("Alta");
		mnNewMenu.add(mntmAlta);
		mntmAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alta();
			}
		});
		mntmAlta.setHorizontalAlignment(SwingConstants.CENTER);
		
		JMenuItem mntmBaja = new JMenuItem("Baja por codigo");
		mnNewMenu.add(mntmBaja);
		mntmBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				baja();
			}
		});
		mntmBaja.setHorizontalAlignment(SwingConstants.CENTER);
		
		JMenuItem mntmModificar = new JMenuItem("Modificar");
		mnNewMenu.add(mntmModificar);
		mntmModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificar();
			}
		});
		mntmModificar.setHorizontalAlignment(SwingConstants.CENTER);
		
		JMenuItem mntmBuscar = new JMenuItem("Buscar por codigo");
		mnNewMenu.add(mntmBuscar);
		mntmBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		mntmBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabla = new JTable();
		tabla.setBounds(10, 11, 250, 450);
		modelo = new DefaultTableModel(new Object[][]{},new String[]{"Codigo","DNI","Nombre","Fecha Nac."});
		tabla.setModel(modelo);
		
		JScrollPane scrollPane = new JScrollPane(tabla);
		tabla.setFillsViewportHeight(true);
		tabla.setForeground(Color.BLACK);
		tabla.setRowHeight(30);
		scrollPane.setBounds(10, 194, 414, 231);
		contentPane.add(scrollPane);	
		
		JPanel panel = new JPanel();
		panel.setBounds(268, 25, 156, 129);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(4, 0, 0, 0));
		
		JButton botonAlta = new JButton("Alta");
		botonAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alta();
			}
		});
		botonAlta.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(botonAlta);
		
		JButton botonBaja = new JButton("Baja por codigo");
		botonBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				baja();
			}
		});
		botonBaja.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(botonBaja);
		
		JButton botonModificar = new JButton("Modificar");
		botonModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificar();
			}
		});
		botonModificar.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(botonModificar);
		
		JButton botonBuscar = new JButton("Buscar por codigo");
		botonBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		botonBuscar.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(botonBuscar);
		
		JLabel lblNewLabel = new JLabel("DNI");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 57, 50, 14);
		contentPane.add(lblNewLabel);
		
		tfDni = new JTextField();
		tfDni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfNombre.requestFocus();
			}
		});
		tfDni.setBounds(80, 56, 148, 20);
		contentPane.add(tfDni);
		tfDni.setColumns(10);
		
		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCodigo.setBounds(10, 25, 60, 20);
		contentPane.add(lblCodigo);
		
		tfCodigo = new JTextField();
		tfCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tfDni.requestFocus();
			}
		});
		tfCodigo.setColumns(10);
		tfCodigo.setBounds(80, 27, 148, 20);
		contentPane.add(tfCodigo);
		
		JButton bSalir = new JButton("SALIR");
		bSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bd.cerrarRecursos();
				System.exit(0);
			}
		});
		bSalir.setBounds(324, 438, 89, 23);
		contentPane.add(bSalir);
		
		JLabel label = new JLabel("Nombre");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(10, 87, 71, 14);
		contentPane.add(label);
		
		tfNombre = new JTextField();
		tfNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfYear.requestFocus();
			}
		});
		tfNombre.setColumns(10);
		tfNombre.setBounds(10, 107, 218, 20);
		contentPane.add(tfNombre);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento");
		lblFechaNacimiento.setHorizontalAlignment(SwingConstants.CENTER);
		lblFechaNacimiento.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFechaNacimiento.setBounds(10, 133, 148, 20);
		contentPane.add(lblFechaNacimiento);
		
		chDia = new JComboBox<String>();
		chDia.setBounds(10, 164, 77, 20);
		contentPane.add(chDia);
		
		chMes = new JComboBox<String>();
		chMes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(chMes.getSelectedIndex()>0)
					llenarDias(Integer.parseInt(chMes.getSelectedItem().toString()));
			}
		});
		chMes.setBounds(98, 164, 77, 20);
		contentPane.add(chMes);
		
		tfYear = new JTextField();
		tfYear.setBounds(185, 163, 77, 20);
		contentPane.add(tfYear);
		tfYear.setColumns(10);
		
		JLabel lblAo = new JLabel("(a\u00F1o)");
		lblAo.setBounds(268, 168, 46, 14);
		contentPane.add(lblAo);
		
		lbInfo = new JLabel("");
		lbInfo.setBounds(20, 442, 294, 14);
		contentPane.add(lbInfo);
		
		//Creamos un objeto de conexion con la BD Facturas
		bd = new ConexionSQLite();
		
		//Llenamos las listas desplegables con los meses y dias
		llenarMeses();
		llenarDias(1);
		
		//Llenamos la tabla con los datos iniciales
		actualizarTabla();
		
	}
	public void llenarMeses(){
		chMes.addItem((String)"Mes");
		for(int i=1;i<=12;i++){
			chMes.addItem(Integer.toString(i));
		}
	}
	public void llenarDias(int mes){
		chDia.removeAllItems();
		int ultimoDia=0;
		chDia.addItem((String)"Dia");
		switch(mes){
			case 2: ultimoDia=29;break;
			case 4:
			case 6:
			case 9:
			case 11: ultimoDia=30;break;
			default: ultimoDia=31;break;
		}
		for(int i=1;i<=ultimoDia;i++){
			if(i<10)
				chDia.addItem("0"+i);
			else
				chDia.addItem(Integer.toString(i));
		}
	}
	
	public void actualizarTabla(){
		modelo.setRowCount(0);
		Object[] fila;
		int cod;
		String fech,dia,mes,year;
		try{
			ResultSet res = bd.mostrarClientes();
			if(res!=null){
				while(res.next()){
					try{
						cod = res.getInt("codigo");
						//Coger el string de la fecha que da SQLite y pasarlo a formato español
						fech = res.getString("fecnac");
						dia=fech.substring(fech.length()-2,fech.length());
						mes=fech.substring(5,7);
						year=fech.substring(0,4);
						fila = new Object[]{
								cod,res.getString("dni"),res.getString("nombre"),
								dia+"/"+mes+"/"+year};
						modelo.addRow(fila);
					}catch(NumberFormatException e){
						e.printStackTrace();
					}
				}
			}
			res.close();
		}catch (SQLException e) {
			mostrarDialog("Problema al actualizar la tabla");
			e.printStackTrace();
		}
	}
	public void alta(){
		boolean datosCorrectos = cogerDatos();
		numReg=-1;
		if(datosCorrectos){
			//Se da de alta el registro en la BD
			numReg=bd.altaCliente(codigo,dni,nombre,fecha);
			if(numReg==-1)
				lbInfo.setText("No se inserto el registro");
			else
				lbInfo.setText("Nuevo registro insertado (cantidad: "+numReg+")");
			actualizarTabla();
		}
	}
	public void baja(){
		numReg=-1;
		try{
			codigo = Integer.parseInt(tfCodigo.getText());
			//Preguntar confirmacion de baja con un dialog
			int opcion=JOptionPane.showConfirmDialog(BDClientes.this,"Confirme la baja del registro","Confirmacion",JOptionPane.YES_NO_OPTION);
			if(JOptionPane.YES_OPTION==opcion){
				numReg=bd.bajaCliente(codigo); //...baja en la BD
				if(numReg==-1)
					lbInfo.setText("No se borro ningun registro");
				else
					lbInfo.setText("Registros borrados: "+numReg);
				actualizarTabla();
			}
		}catch(NumberFormatException e){
			mostrarDialog("Introduzca un codigo correcto");
			tfCodigo.setText("");
		}
	}
	public void modificar(){
		boolean datosCorrectos = cogerDatos();
		numReg= -1;
		try{
			codigo = Integer.parseInt(tfCodigo.getText());
			if(datosCorrectos){ 
				//si los datos introducidos son correctos se pide confirmacion de modificacion...
				int opcion=JOptionPane.showConfirmDialog(BDClientes.this,"Confirme la modificacion del registro","Confirmacion",JOptionPane.YES_NO_OPTION);
				if(JOptionPane.YES_OPTION==opcion){
					numReg=bd.modificarCliente(codigo,dni,nombre,fecha);
					if(numReg==-1)
						lbInfo.setText("No se produjo modificacion");
					else
						lbInfo.setText("Numero de registros modificados: "+numReg);
					actualizarTabla();
				}
			}
		}catch(NumberFormatException e){
			mostrarDialog("Introduzca un codigo correcto");
			tfCodigo.setText("");
		}
	}
	public void buscar(){
		String fech;
		try{
			codigo = Integer.parseInt(tfCodigo.getText());
			try{
				ResultSet res = bd.consultaCliente(codigo);
				if(res!=null){
					if(res.next()){ //mostramos el registro en los campos de texto...
						tfDni.setText(res.getString("dni"));
						tfNombre.setText(res.getString("nombre"));
						//Mostrar la fecha en las listas seleccionables y el campo de texto...
						fech = res.getString("fecnac");
						tfYear.setText(fech.substring(0,4));
						chMes.setSelectedIndex(Integer.parseInt(fech.substring(5,7)));
						chDia.setSelectedIndex(Integer.parseInt(fech.substring(8,10)));
						
						lbInfo.setText("Registro mostrado");
					}
					else
						lbInfo.setText("No se encontro el registro");
				}
				res.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}catch(NumberFormatException e){
			mostrarDialog("Introduzca un codigo correcto");
			tfCodigo.setText("");
		}
	}
	
	public boolean cogerDatos(){
		int numMes=0;
		try{
			codigo = Integer.parseInt(tfCodigo.getText());
			dni = tfDni.getText();
			nombre = tfNombre.getText();
			//Pasamos la fecha al formato correcto...
			if(tfYear.getText().length()==4 && 
					chDia.getSelectedIndex()>0 && chMes.getSelectedIndex()>0){
				int yearNac = Integer.parseInt(tfYear.getText());
				String mesNac = chMes.getSelectedItem().toString();
				String diaNac = chDia.getSelectedItem().toString();
				numMes =Integer.parseInt(mesNac);
				//Si el año no es divisible entre 4 o entre 100 o 400, no es bisiesto
				if(yearNac%4!=0 || yearNac%100!=0 || yearNac%400!=0){
					if(numMes==2 && Integer.parseInt(diaNac)==29)
						diaNac="28"; //Si no es bisiesto, el ultimo dia en febrero es el 28
				}
				//Pasar los datos de fecha al formato "yyyy-mm-dd" de sqlite
				if(numMes<10)
					fecha=Integer.toString(yearNac)+"-0"+mesNac+"-"+diaNac;
				else
					fecha=Integer.toString(yearNac)+"-"+mesNac+"-"+diaNac;
			}
			else{
				mostrarDialog("No ha introducido una fecha correcta");
				return false;
			}
		}catch(NumberFormatException e){
			mostrarDialog("Introduzca numeros enteros en el codigo y el año");
			tfCodigo.setText("");
			tfYear.setText("");
			return false;
		}
		return true;
	}
	public void mostrarDialog(String mensaje){
		JOptionPane.showConfirmDialog(BDClientes.this,mensaje,"MENSAJE",JOptionPane.CLOSED_OPTION); 
	}
}
