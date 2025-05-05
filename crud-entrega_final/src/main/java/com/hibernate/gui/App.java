package com.hibernate.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import com.hibernate.dao.AlquilerDAO;
import com.hibernate.dao.BicicletaDAO;
import com.hibernate.dao.UsuarioDAO;
import com.hibernate.model.Alquiler;
import com.hibernate.model.Bicicleta;
import com.hibernate.model.Estado;
import com.hibernate.model.Usuario;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.swing.border.TitledBorder;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class App {

	private static final String EXP_REG_NIF = "^\\d{8}[A-Z]$";
	private static final String EXP_REG_EMAIL = "^\\w+@[a-z]+\\.[a-z]{2,3}$";
	private static final String EXP_REG_TELEFONO = "^[67]\\d{8}$";
	private static final double TASA_POR_MINUTO = 0.15;
	
	private JFrame ventana;
	private DefaultTableModel tableModelUsuario;
	private DefaultTableModel tableModelBicicleta;
	private DefaultTableModel tableModelAlquiler;
	private JTable tablaUsuario;
	private JTable tablaBicicleta;
	private JTable tablaAlquiler;
	private JTextField txtNombre;
	private JTextField txtNif;
	private JTextField txtEmail;
	private JTextField txtTelefono;
	private JButton btnSeleccionarArchivo;
	private JLabel lblFotoPerfil;
	private JLabel lblId;
	private JLabel lblIdUsuario;
	private JButton btnAlquilar;
	private JButton btnDevolver;
	private JRadioButtonMenuItem rdbtnIdioma1;
	private JRadioButtonMenuItem rdbtnIdioma2;
	private JRadioButtonMenuItem rdbtnIdioma3;
	
	private JComboBox<String> cBoxUsuariosLibres;
	private JComboBox<Integer> cBoxBicicletasLibres;
	private JComboBox<Integer> cBoxBicicletasOcupadas;
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private BicicletaDAO bicicletaDAO = new BicicletaDAO();
	private AlquilerDAO alquilerDAO = new AlquilerDAO();
	private static String rutaFoto;

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	private void updateData() {
		mostrarTablaUsuario();
		mostrarTablaBicicleta();
		mostrarTablaAlquiler();
		actualizarComboBoxesAlquiler();
		actualizarComboBoxDevolver();
	}
	
	private void mostrarTablaUsuario(){
		
		tableModelUsuario.setRowCount(0);
		List<Usuario> usuarios = usuarioDAO.selectAllUsuarios(); 
		
		for(Usuario usuario : usuarios) {
			Object[] row = new Object[6];
			row[0] = usuario.getId();
			row[1] = usuario.getNif();;
			row[2] = usuario.getNombre();
			row[3] = usuario.getEmail();
			row[4] = usuario.getTelefono();
			row[5] = usuario.getFoto();
			tableModelUsuario.addRow(row);
		}
	}
	
	private void mostrarTablaBicicleta(){
		
		tableModelBicicleta.setRowCount(0);
		List<Bicicleta> bicicletas = bicicletaDAO.selectAllBicicletas(); 
		
		for(Bicicleta bicicleta : bicicletas) {
			Object[] row = new Object[2];
			row[0] = bicicleta.getId();
			row[1] = bicicleta.getEstado();
			tableModelBicicleta.addRow(row);
		}
	}
	
	private void mostrarTablaAlquiler(){
		
		tableModelAlquiler.setRowCount(0);
		List<Alquiler> alquileres = alquilerDAO.selectAllAlquileres(); 
		
		for(Alquiler alquiler : alquileres) {
			Object[] row = new Object[6];
			row[0] = alquiler.getId();
			row[1] = alquiler.getUsuario().getNif();
			row[2] = alquiler.getBicicleta().getId();
			row[3] = alquiler.getFecha_hora_inicio();
			row[4] = alquiler.getFecha_hora_fin();
			row[5] = alquiler.getImporte();
			tableModelAlquiler.addRow(row);
		}
	}
	
	private void borrarInputsPanelUsuario() {
		
		lblId.setVisible(false);
		lblIdUsuario.setText("");
		txtNombre.setText("");
		txtNif.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");
		rutaFoto = null;
		ImageIcon imgPerfilDefault = new ImageIcon("img/perfil1.png");
		Icon icoPerfilDefault = new ImageIcon(imgPerfilDefault.getImage().getScaledInstance(lblFotoPerfil.getWidth(), lblFotoPerfil.getHeight(), Image.SCALE_SMOOTH));
		lblFotoPerfil.setIcon(icoPerfilDefault);
		
	}
	
	private void habilitarInputsPanelUsuario(boolean flag) {
		
		txtNombre.setEditable(flag);
		txtNif.setEditable(flag);
		txtEmail.setEditable(flag);
		txtTelefono.setEditable(flag);
		btnSeleccionarArchivo.setEnabled(flag);
		
	}
	
	private void cambiarColorFondoInputsPanelUsuario(Color color) {
		
		txtNombre.setBackground(color);
		txtNif.setBackground(color);
		txtEmail.setBackground(color);
		txtTelefono.setBackground(color);
	}
	
	private void actualizarComboBoxesAlquiler(){
		
		btnAlquilar.setEnabled(true);
		cBoxUsuariosLibres.removeAllItems();
		cBoxBicicletasLibres.removeAllItems();
		
		List<Usuario> usuariosLibres = usuarioDAO.selectAllUsuarioLibres();
		if(!usuariosLibres.isEmpty()) {
			for(Usuario u : usuariosLibres) {
				cBoxUsuariosLibres.addItem(u.getNif());	
			}
		} else {
			btnAlquilar.setEnabled(false);
		}
		
		List<Bicicleta> bicicletasLibres = bicicletaDAO.selectAllBicicletasLibres();
		if(!bicicletasLibres.isEmpty()) {
			for(Bicicleta b : bicicletasLibres) {
				cBoxBicicletasLibres.addItem(b.getId());	
			}
		} else {
			btnAlquilar.setEnabled(false);
		}
	}
	
	private void actualizarComboBoxDevolver(){
		
		btnDevolver.setEnabled(true);
		cBoxBicicletasOcupadas.removeAllItems();
		
		List<Bicicleta> bicicletasOcupadas = bicicletaDAO.selectAllBicicletasOcupadas();
		if(!bicicletasOcupadas.isEmpty()) {
			for(Bicicleta b : bicicletasOcupadas) {
				cBoxBicicletasOcupadas.addItem(b.getId());	
			}
		} else {
			btnDevolver.setEnabled(false);
		}
	}
	
	private double calcularImporteAlquiler(Alquiler a) {
		
		Duration duration = Duration.between(a.getFecha_hora_inicio(), a.getFecha_hora_fin());
		
		double importe = duration.toMinutes() * TASA_POR_MINUTO + TASA_POR_MINUTO;
		
		return importe;
	}
	
	private void cambiarIdioma(){
		//TODO
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		ventana = new JFrame();
		ventana.setResizable(false);
		ventana.setTitle("App Next-Bike (Alquiler de Bicicletas)");
		ventana.setBounds(100, 100, 880, 636);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		ButtonGroup grupoIdiomas = new ButtonGroup();
		
		JMenuBar barraDeMenu = new JMenuBar();
		barraDeMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		ventana.setJMenuBar(barraDeMenu);
		
		JMenu menuCambiarFondo = new JMenu("Cambiar fondo");
		menuCambiarFondo.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		barraDeMenu.add(menuCambiarFondo);
		
		JCheckBoxMenuItem chBoxFondoBasico = new JCheckBoxMenuItem("Fondo básico");
		menuCambiarFondo.add(chBoxFondoBasico);
		
		JMenu menuIdiomas = new JMenu("Idiomas");
		menuIdiomas.setHorizontalTextPosition(SwingConstants.LEADING);
		menuIdiomas.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		ImageIcon imgBandera1 = new ImageIcon("img/bandera1.png");
		Icon icoBandera1 = new ImageIcon(imgBandera1.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
		menuIdiomas.setIcon(icoBandera1);
		barraDeMenu.add(menuIdiomas);
		
		rdbtnIdioma1 = new JRadioButtonMenuItem("Español");
		rdbtnIdioma1.setHorizontalTextPosition(SwingConstants.LEADING);
		rdbtnIdioma1.setSelected(true);
		rdbtnIdioma1.setIcon(icoBandera1);
		grupoIdiomas.add(rdbtnIdioma1);
		menuIdiomas.add(rdbtnIdioma1);
		
		rdbtnIdioma2 = new JRadioButtonMenuItem("English");
		rdbtnIdioma2.setHorizontalTextPosition(SwingConstants.LEADING);
		ImageIcon imgBandera2 = new ImageIcon("img/bandera2.png");
		Icon icoBandera2 = new ImageIcon(imgBandera2.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
		rdbtnIdioma2.setIcon(icoBandera2);
		grupoIdiomas.add(rdbtnIdioma2);
		menuIdiomas.add(rdbtnIdioma2);
		
		rdbtnIdioma3 = new JRadioButtonMenuItem("Català");
		rdbtnIdioma3.setHorizontalTextPosition(SwingConstants.LEADING);
		ImageIcon imgBandera3 = new ImageIcon("img/bandera3.png");
		Icon icoBandera3 = new ImageIcon(imgBandera3.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH));
		rdbtnIdioma3.setIcon(icoBandera3);
		grupoIdiomas.add(rdbtnIdioma3);
		menuIdiomas.add(rdbtnIdioma3);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		ventana.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelGestionUsuariosYBicicletas = new JPanel();
		tabbedPane.addTab("Usuarios y Bicis", null, panelGestionUsuariosYBicicletas, null);
		panelGestionUsuariosYBicicletas.setLayout(null);
		
		JScrollPane scrollPaneTablaUsuario = new JScrollPane();
		scrollPaneTablaUsuario.setBounds(19, 29, 401, 249);
		panelGestionUsuariosYBicicletas.add(scrollPaneTablaUsuario);
		
		tableModelUsuario = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false; // ninguna celda editable
		    }
		};
		tableModelUsuario.addColumn("ID");
		tableModelUsuario.addColumn("NIF");
		tableModelUsuario.addColumn("Nombre");
		tableModelUsuario.addColumn("Email");
		tableModelUsuario.addColumn("Teléfono");
		tableModelUsuario.addColumn("Foto");
		
		tablaUsuario = new JTable(tableModelUsuario);
		tablaUsuario.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaUsuario.setDefaultRenderer(Object.class, centerRenderer);
		tablaUsuario.getColumnModel().getColumn(0).setMinWidth(0);
		tablaUsuario.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaUsuario.getColumnModel().getColumn(0).setPreferredWidth(0);
		tablaUsuario.getColumnModel().getColumn(5).setMinWidth(0);
		tablaUsuario.getColumnModel().getColumn(5).setMaxWidth(0);
		tablaUsuario.getColumnModel().getColumn(5).setPreferredWidth(0);
		scrollPaneTablaUsuario.setViewportView(tablaUsuario);
		
		JScrollPane scrollPaneTablaBicicletas = new JScrollPane();
		scrollPaneTablaBicicletas.setBounds(72, 331, 295, 169);
		panelGestionUsuariosYBicicletas.add(scrollPaneTablaBicicletas);
		
		tableModelBicicleta = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false; // ninguna celda editable
		    }
		};
		tableModelBicicleta.addColumn("ID");
		tableModelBicicleta.addColumn("Estado");
		
		tablaBicicleta = new JTable(tableModelBicicleta);
		tablaBicicleta.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaBicicleta.setDefaultRenderer(Object.class, centerRenderer);
		scrollPaneTablaBicicletas.setViewportView(tablaBicicleta);
		
		JPanel panelCreacionUsuario = new JPanel();
		panelCreacionUsuario.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelCreacionUsuario.setBounds(439, 11, 401, 267);
		panelGestionUsuariosYBicicletas.add(panelCreacionUsuario);
		panelCreacionUsuario.setLayout(null);
		
		JLabel lblTituloPerfilUsuario = new JLabel("Perfil");
		lblTituloPerfilUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloPerfilUsuario.setFont(new Font("Courier New", Font.BOLD, 18));
		lblTituloPerfilUsuario.setBounds(99, 11, 202, 30);
		panelCreacionUsuario.add(lblTituloPerfilUsuario);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNombre.setBounds(15, 55, 54, 14);
		panelCreacionUsuario.add(lblNombre);
		
		JLabel lblNif = new JLabel("NIF:");
		lblNif.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNif.setBounds(15, 83, 54, 14);
		panelCreacionUsuario.add(lblNif);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmail.setBounds(15, 108, 54, 14);
		panelCreacionUsuario.add(lblEmail);
		
		JLabel lblTeléfono = new JLabel("Teléfono:");
		lblTeléfono.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTeléfono.setBounds(15, 133, 54, 14);
		panelCreacionUsuario.add(lblTeléfono);
		
		txtNombre = new JTextField();
		txtNombre.setBackground(Color.WHITE);
		txtNombre.setBounds(85, 52, 128, 20);
		panelCreacionUsuario.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtNif = new JTextField();
		txtNif.setBackground(Color.WHITE);
		txtNif.setColumns(10);
		txtNif.setBounds(85, 80, 128, 20);
		panelCreacionUsuario.add(txtNif);
		
		txtEmail = new JTextField();
		txtEmail.setBackground(Color.WHITE);
		txtEmail.setColumns(10);
		txtEmail.setBounds(85, 105, 128, 20);
		panelCreacionUsuario.add(txtEmail);
		
		txtTelefono = new JTextField();
		txtTelefono.setBackground(Color.WHITE);
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(85, 130, 128, 20);
		panelCreacionUsuario.add(txtTelefono);
		
		JButton btnAgregarUsuario = new JButton("Agregar");
		btnAgregarUsuario.setBounds(33, 204, 89, 23);
		panelCreacionUsuario.add(btnAgregarUsuario);
		
		JButton btnEditarUsuario = new JButton("Editar");
		btnEditarUsuario.setEnabled(false);
		btnEditarUsuario.setBounds(155, 204, 89, 23);
		panelCreacionUsuario.add(btnEditarUsuario);
		
		JButton btnEliminarUsuario = new JButton("Eliminar");
		btnEliminarUsuario.setEnabled(false);
		btnEliminarUsuario.setBounds(277, 204, 89, 23);
		panelCreacionUsuario.add(btnEliminarUsuario);

		JButton btnVolver = new JButton("Volver");
		btnVolver.setVisible(false);
		btnVolver.setBounds(33, 204, 89, 23);
		panelCreacionUsuario.add(btnVolver);
		
		JButton btnAceptarEdicion = new JButton("Aceptar");
		btnAceptarEdicion.setVisible(false);
		btnAceptarEdicion.setBounds(155, 204, 89, 23);
		panelCreacionUsuario.add(btnAceptarEdicion);
		
		btnSeleccionarArchivo = new JButton("Foto");
		btnSeleccionarArchivo.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		btnSeleccionarArchivo.setBounds(105, 161, 89, 23);
		panelCreacionUsuario.add(btnSeleccionarArchivo);
		
		lblFotoPerfil = new JLabel("");
		lblFotoPerfil.setOpaque(true);
		lblFotoPerfil.setBackground(new Color(208, 223, 238));
		lblFotoPerfil.setBounds(245, 52, 130, 99);
		ImageIcon imgPerfilDefault = new ImageIcon("img/perfil1.png");
		Icon icoPerfilDefault = new ImageIcon(imgPerfilDefault.getImage().getScaledInstance(lblFotoPerfil.getWidth(), lblFotoPerfil.getHeight(), Image.SCALE_SMOOTH));
		lblFotoPerfil.setIcon(icoPerfilDefault);
		panelCreacionUsuario.add(lblFotoPerfil);
		
		lblId = new JLabel("ID:");
		lblId.setVisible(false);
		lblId.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setBounds(261, 163, 46, 14);
		panelCreacionUsuario.add(lblId);
		
		lblIdUsuario = new JLabel("");
		lblIdUsuario.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIdUsuario.setBounds(312, 163, 46, 14);
		panelCreacionUsuario.add(lblIdUsuario);
		
		JPanel panelCreacionBicicleta = new JPanel();
		panelCreacionBicicleta.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelCreacionBicicleta.setBounds(492, 346, 295, 119);
		panelGestionUsuariosYBicicletas.add(panelCreacionBicicleta);
		panelCreacionBicicleta.setLayout(null);
		
		JLabel lblTituloBicicleta = new JLabel("Bicicleta");
		lblTituloBicicleta.setFont(new Font("Courier New", Font.BOLD, 18));
		lblTituloBicicleta.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloBicicleta.setBounds(46, 11, 202, 30);
		panelCreacionBicicleta.add(lblTituloBicicleta);
		
		JButton btnAgregarBicicleta = new JButton("Agregar");
		btnAgregarBicicleta.setBounds(39, 76, 89, 23);
		panelCreacionBicicleta.add(btnAgregarBicicleta);
		
		JButton btnEliminarBicicleta = new JButton("Eliminar");
		btnEliminarBicicleta.setEnabled(false);
		btnEliminarBicicleta.setBounds(167, 76, 89, 23);
		panelCreacionBicicleta.add(btnEliminarBicicleta);
		
		JLabel lblIdBicicleta = new JLabel("");
		lblIdBicicleta.setVisible(false);
		lblIdBicicleta.setBounds(124, 52, 46, 14);
		panelCreacionBicicleta.add(lblIdBicicleta);
		
		JLabel lblTituloTablaUsuario = new JLabel();
		lblTituloTablaUsuario.setOpaque(true);
		lblTituloTablaUsuario.setForeground(Color.WHITE);
		lblTituloTablaUsuario.setBackground(Color.BLACK);
		lblTituloTablaUsuario.setText("Usuarios");
		lblTituloTablaUsuario.setFont(new Font("Courier New", Font.BOLD, 16));
		lblTituloTablaUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloTablaUsuario.setBounds(19, 9, 401, 20);
		panelGestionUsuariosYBicicletas.add(lblTituloTablaUsuario);
		
		JLabel lblTituloTablaBicicleta = new JLabel("Bicicletas");
		lblTituloTablaBicicleta.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloTablaBicicleta.setFont(new Font("Courier New", Font.BOLD, 16));
		lblTituloTablaBicicleta.setForeground(Color.WHITE);
		lblTituloTablaBicicleta.setBackground(Color.BLACK);
		lblTituloTablaBicicleta.setOpaque(true);
		lblTituloTablaBicicleta.setBounds(72, 311, 295, 20);
		panelGestionUsuariosYBicicletas.add(lblTituloTablaBicicleta);
		
		JPanel panelGestionAlquiler = new JPanel();
		tabbedPane.addTab("Gestion Alquiler", null, panelGestionAlquiler, null);
		panelGestionAlquiler.setLayout(null);
		
		JScrollPane scrollPaneAlquiler = new JScrollPane();
		scrollPaneAlquiler.setBounds(353, 59, 459, 316);
		panelGestionAlquiler.add(scrollPaneAlquiler);
		
		tableModelAlquiler = new DefaultTableModel(){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false; // ninguna celda editable
		    }
		};
		tableModelAlquiler.addColumn("ID");
		tableModelAlquiler.addColumn("Usuario");
		tableModelAlquiler.addColumn("Bici");
		tableModelAlquiler.addColumn("Fecha Inicio");
		tableModelAlquiler.addColumn("Fecha Fin");
		tableModelAlquiler.addColumn("Importe");
		
		tablaAlquiler = new JTable(tableModelAlquiler);
		tablaAlquiler.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaAlquiler.setDefaultRenderer(Object.class, centerRenderer);
		scrollPaneAlquiler.setViewportView(tablaAlquiler);
		
		JLabel lblTitulo = new JLabel("Alquileres");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Courier New", Font.BOLD, 18));
		lblTitulo.setBounds(516, 21, 133, 27);
		panelGestionAlquiler.add(lblTitulo);
		
		JLabel lblAlquilar = new JLabel("- Alquilar Bicicleta:");
		lblAlquilar.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAlquilar.setBounds(10, 28, 204, 27);
		panelGestionAlquiler.add(lblAlquilar);
		
		JLabel lblDevolver = new JLabel("- Devolver Bicicleta:");
		lblDevolver.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDevolver.setBounds(10, 250, 204, 27);
		panelGestionAlquiler.add(lblDevolver);
		
		JLabel lblEligirUsuario = new JLabel("Eliga un usuario:");
		lblEligirUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEligirUsuario.setBounds(30, 66, 117, 27);
		panelGestionAlquiler.add(lblEligirUsuario);
		
		JLabel lblElegirBicicleta = new JLabel("Eliga una bici:");
		lblElegirBicicleta.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblElegirBicicleta.setBounds(30, 124, 117, 27);
		panelGestionAlquiler.add(lblElegirBicicleta);
		
		JLabel lblDevolverBicicleta = new JLabel("Eliga una bici:");
		lblDevolverBicicleta.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDevolverBicicleta.setBounds(30, 298, 117, 27);
		panelGestionAlquiler.add(lblDevolverBicicleta);
		
		cBoxUsuariosLibres = new JComboBox<String>();
		cBoxUsuariosLibres.setBounds(157, 68, 117, 22);
		panelGestionAlquiler.add(cBoxUsuariosLibres);
		
		cBoxBicicletasLibres = new JComboBox<Integer>();
		cBoxBicicletasLibres.setBounds(206, 126, 68, 22);
		panelGestionAlquiler.add(cBoxBicicletasLibres);
		
		cBoxBicicletasOcupadas = new JComboBox<Integer>();
		cBoxBicicletasOcupadas.setBounds(206, 300, 68, 22);
		panelGestionAlquiler.add(cBoxBicicletasOcupadas);
		
		btnAlquilar = new JButton("Alquilar");
		btnAlquilar.setBounds(185, 187, 89, 23);
		panelGestionAlquiler.add(btnAlquilar);
		
		btnDevolver = new JButton("Devolver");
		btnDevolver.setBounds(185, 359, 89, 23);
		panelGestionAlquiler.add(btnDevolver);
		
		JLabel lbl1 = new JLabel("El importe es:");
		lbl1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl1.setBounds(101, 419, 96, 27);
		panelGestionAlquiler.add(lbl1);
		
		JLabel lblImporte = new JLabel("0€");
		lblImporte.setForeground(new Color(0, 128, 64));
		lblImporte.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblImporte.setBounds(213, 419, 96, 27);
		panelGestionAlquiler.add(lblImporte);
		
		JPanel panelGestionOtrasCosas = new JPanel();
		tabbedPane.addTab("Otras Cosas", null, panelGestionOtrasCosas, null);
		panelGestionOtrasCosas.setLayout(null);
		
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				txtNombre.setBackground(new Color(255, 255, 255));
			}
		});
		
		txtNif.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				txtNif.setBackground(new Color(255, 255, 255));
			}
		});
		
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				txtEmail.setBackground(new Color(255, 255, 255));
			}
		});
		
		txtTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				txtTelefono.setBackground(new Color(255, 255, 255));
			}
		});
		
		tablaUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				cambiarColorFondoInputsPanelUsuario(new Color(184 ,207, 229)); // new Color(208, 223, 238)
				habilitarInputsPanelUsuario(false);
				
				btnAgregarUsuario.setVisible(false);
				btnVolver.setVisible(true);
				btnAceptarEdicion.setVisible(false);
				btnEditarUsuario.setVisible(true);
				btnEditarUsuario.setEnabled(true);
				btnEliminarUsuario.setEnabled(true);
				
				int fila = tablaUsuario.getSelectedRow();
				TableModel model = tablaUsuario.getModel();
				
				lblId.setVisible(true);
				lblIdUsuario.setText(model.getValueAt(fila, 0).toString());
				txtNombre.setText(model.getValueAt(fila, 2).toString());
				txtNif.setText(model.getValueAt(fila, 1).toString());
				txtEmail.setText(model.getValueAt(fila, 3).toString());
				txtTelefono.setText(model.getValueAt(fila, 4).toString());
				byte[] foto = (byte[]) model.getValueAt(fila, 5);
				try {
					BufferedImage imagen = ImageIO.read(new ByteArrayInputStream(foto));
                    Icon icoFoto = new ImageIcon(imagen.getScaledInstance(lblFotoPerfil.getWidth(), lblFotoPerfil.getHeight(), Image.SCALE_SMOOTH));
                    lblFotoPerfil.setIcon(icoFoto);
				} catch(IOException ex) {
					//vacio
				}
			}
		});
		
		tablaBicicleta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				btnEliminarBicicleta.setEnabled(true);
				
				int fila = tablaBicicleta.getSelectedRow();
				TableModel model = tablaBicicleta.getModel();
				
				lblIdBicicleta.setText(model.getValueAt(fila, 0).toString());
			}
		});
		
		tablaAlquiler.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				/*int index = table.getSelectedRow();
				TableModel model = table.getModel();
				txtID.setText(model.getValueAt(index, 0).toString());
				txtName.setText(model.getValueAt(index, 1).toString());
				txtAge.setText(model.getValueAt(index, 2).toString());
				txtCity.setText(model.getValueAt(index, 3).toString());
				*/
			}
		});
		
		btnSeleccionarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
            	JFileChooser fChooser = new JFileChooser();
                fChooser.showOpenDialog(null);
                try {
                	rutaFoto = fChooser.getSelectedFile().getAbsolutePath();
                    
                    BufferedImage imagen = ImageIO.read(new File(rutaFoto));
                    Icon icoFoto = new ImageIcon(imagen.getScaledInstance(lblFotoPerfil.getWidth(), lblFotoPerfil.getHeight(), Image.SCALE_SMOOTH));
                    lblFotoPerfil.setIcon(icoFoto);
                    
                } catch(NullPointerException ex1) {
                	//vacio
                } catch(IOException ex2) {
                	//vacio
                }
			}
		});
		
		btnAgregarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nombre = txtNombre.getText();
				String nif = txtNif.getText();
				String email = txtEmail.getText();
				String telefono = txtTelefono.getText();
				
				if(nombre.isBlank()) {
					txtNombre.setBackground(new Color(255, 255, 128));
					JOptionPane.showMessageDialog(panelCreacionUsuario, "Introduce un nombre", "Advertencia", JOptionPane.WARNING_MESSAGE);
				} else if(nif.isBlank() || !Pattern.matches(EXP_REG_NIF, nif)) {
					txtNif.setBackground(Color.PINK);
					JOptionPane.showMessageDialog(panelCreacionUsuario, "Introduce NIF con letra Mayúscula", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(email.isBlank() || !Pattern.matches(EXP_REG_EMAIL, email)) {
					txtEmail.setBackground(Color.PINK);
					JOptionPane.showMessageDialog(panelCreacionUsuario, "Introduce un email existente", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(telefono.isBlank() || !Pattern.matches(EXP_REG_TELEFONO, telefono)) {
					txtTelefono.setBackground(Color.PINK);
					JOptionPane.showMessageDialog(panelCreacionUsuario, "Introduce un número de teléfono existente", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(rutaFoto == null){
		    		JOptionPane.showMessageDialog(panelCreacionUsuario, "Por favor, Introduce una foto", "Advertencia", JOptionPane.WARNING_MESSAGE);
				} else {
					
					try {
						byte[] foto = Files.readAllBytes(Paths.get(rutaFoto));
						Usuario usuario = new Usuario(nif, nombre, email, telefono, foto);
						usuarioDAO.insertUsuario(usuario);

						mostrarTablaUsuario();
						actualizarComboBoxesAlquiler();
						borrarInputsPanelUsuario();
						
					} catch(IOException ex2) {
						//vacio
					}

				}
			}
		});
		
		btnEditarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				habilitarInputsPanelUsuario(true);
				cambiarColorFondoInputsPanelUsuario(Color.WHITE);
				btnEditarUsuario.setVisible(false);
				btnAceptarEdicion.setVisible(true);
				
			}
		});
		
		btnEliminarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int id = Integer.parseInt(lblIdUsuario.getText());
				
				if(usuarioDAO.selectUsuarioById(id).getEstado() == Estado.LIBRE) {
					
					int confirmacion = JOptionPane.showConfirmDialog(panelCreacionUsuario, "Eliminar usuario con ID: "+id+" ?", "Eliminar usuario", JOptionPane.YES_NO_OPTION);
					
					if(confirmacion == JOptionPane.YES_OPTION) {
						
						usuarioDAO.deleteUsuarioById(id);
						
						mostrarTablaUsuario();
						actualizarComboBoxesAlquiler();
						btnVolver.doClick();;
					}
				} else {
		    		JOptionPane.showMessageDialog(panelCreacionBicicleta, "Ese usuario está alquilando !", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnVolver.setVisible(false);
				btnAgregarUsuario.setVisible(true);
				btnAceptarEdicion.setVisible(false);
				btnEditarUsuario.setVisible(true);
				btnEditarUsuario.setEnabled(false);
				btnEliminarUsuario.setEnabled(false);
				
				borrarInputsPanelUsuario();
				habilitarInputsPanelUsuario(true);
				cambiarColorFondoInputsPanelUsuario(Color.WHITE);
				tablaUsuario.clearSelection();
			}
		});
		
		btnAceptarEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int id = Integer.parseInt(lblIdUsuario.getText());
				String nombre = txtNombre.getText();
				String nif = txtNif.getText();
				String email = txtEmail.getText();
				String telefono = txtTelefono.getText();
				
				if(nombre.isBlank()) {
					txtNombre.setBackground(new Color(255, 255, 128));
					JOptionPane.showMessageDialog(panelCreacionUsuario, "Introduce un nombre", "Advertencia", JOptionPane.WARNING_MESSAGE);
				} else if(nif.isBlank() || !Pattern.matches(EXP_REG_NIF, nif)) {
					txtNif.setBackground(Color.PINK);
					JOptionPane.showMessageDialog(panelCreacionUsuario, "Introduce NIF con letra Mayúscula", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(email.isBlank() || !Pattern.matches(EXP_REG_EMAIL, email)) {
					txtEmail.setBackground(Color.PINK);
					JOptionPane.showMessageDialog(panelCreacionUsuario, "Introduce un email existente", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(telefono.isBlank() || !Pattern.matches(EXP_REG_TELEFONO, telefono)) {
					txtTelefono.setBackground(Color.PINK);
					JOptionPane.showMessageDialog(panelCreacionUsuario, "Introduce un número de teléfono existente", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(rutaFoto == null){
		    		JOptionPane.showMessageDialog(panelCreacionUsuario, "Por favor, Introduce una foto", "Advertencia", JOptionPane.WARNING_MESSAGE);
				} else {
						
					int confirmacion = JOptionPane.showConfirmDialog(panelCreacionUsuario, "Actualizar usuario con ID: "+id+" ?", "Actualizar usuario", JOptionPane.YES_NO_OPTION);
					
					if(confirmacion == JOptionPane.YES_OPTION) {
					
						Usuario usuario = usuarioDAO.selectUsuarioById(id);
						usuario.setNombre(nombre);
						usuario.setNif(nif);
						usuario.setEmail(email);
						usuario.setTelefono(telefono);
					    try {
					    	byte[] foto = Files.readAllBytes(Paths.get(rutaFoto));
					    	usuario.setFoto(foto);
					    	usuarioDAO.updateUsuario(usuario);
						
					    	mostrarTablaUsuario();
					    	btnVolver.doClick();
					    }catch (IOException ex) {
					    	//vacio
						}
					}
				}
			}
		});
		
		
		btnAgregarBicicleta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btnEliminarBicicleta.setEnabled(false);
				Bicicleta bicicleta = new Bicicleta();
				bicicletaDAO.insertBicicleta(bicicleta);
				mostrarTablaBicicleta();
				actualizarComboBoxesAlquiler();

			}
		});
		
		btnEliminarBicicleta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int id = Integer.parseInt(lblIdBicicleta.getText());
				
				if(bicicletaDAO.selectBicicletaById(id).getEstado() == Estado.LIBRE) {
					
					int confirmacion = JOptionPane.showConfirmDialog(panelCreacionBicicleta, "Eliminar bicicleta "+id+" ?", "Eliminar bicicleta", JOptionPane.YES_NO_OPTION);
					
					if(confirmacion == JOptionPane.YES_OPTION) {
						
						bicicletaDAO.deleteBicicletaById(id);
						
						mostrarTablaBicicleta();
						actualizarComboBoxesAlquiler();
						btnEliminarBicicleta.setEnabled(false);
						tablaBicicleta.clearSelection();
					}
				} else {
		    		JOptionPane.showMessageDialog(panelCreacionBicicleta, "Esa bicicleta "+id+" está ocupada !", "Advertencia", JOptionPane.WARNING_MESSAGE);
				}
				

			}
		});
		
		btnAlquilar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblImporte.setText("");
				
				String nif = cBoxUsuariosLibres.getSelectedItem().toString();
				int idBicicleta = (int) cBoxBicicletasLibres.getSelectedItem();
				
				Usuario usuario = usuarioDAO.selectUsuarioByNif(nif);
				usuario.setEstado(Estado.OCUPADO);
				usuarioDAO.updateUsuario(usuario);
				Bicicleta bicicleta = bicicletaDAO.selectBicicletaById(idBicicleta);
				bicicleta.setEstado(Estado.OCUPADO);
				bicicletaDAO.updateBicicleta(bicicleta);
				
				Alquiler alquiler = new Alquiler(usuario, bicicleta);
				alquilerDAO.insertAlquiler(alquiler);
				updateData();
			}
		});
		
		
		btnDevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int idBicicleta = (int) cBoxBicicletasOcupadas.getSelectedItem();
				
				Alquiler alquiler = alquilerDAO.selectAlquilerByBicicletaId(idBicicleta);
				alquiler.setFecha_hora_fin(LocalDateTime.now());
				alquiler.setImporte(calcularImporteAlquiler(alquiler));
				alquilerDAO.updateAlquiler(alquiler);
				
				Bicicleta bicicleta = alquiler.getBicicleta();
				bicicleta.setEstado(Estado.LIBRE);
				bicicletaDAO.updateBicicleta(bicicleta);
				
				Usuario usuario = alquiler.getUsuario();
				usuario.setEstado(Estado.LIBRE);
				usuarioDAO.updateUsuario(usuario);
				
				lblImporte.setText(alquiler.getImporte()+"€");
				
				updateData();
			}
		});
		
		rdbtnIdioma1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				menuIdiomas.setIcon(icoBandera1);
				
				//TODO
				
			}
		});
		
		rdbtnIdioma2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				menuIdiomas.setIcon(icoBandera2);
				
				//TODO
				
			}
		});
		
		rdbtnIdioma3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				menuIdiomas.setIcon(icoBandera3);
			}
		});
		
		chBoxFondoBasico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//TODO
				
			}
		});
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.ventana.setVisible(true);
					window.updateData();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

