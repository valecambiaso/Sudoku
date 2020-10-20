package GUI;
import java.awt.EventQueue;




import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Logica.Sudoku;
import Logica.Celda;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Color;
import java.awt.Component;

/**
 * Clase que modela un tablero de Sudoku.
 */
public class Tablero {
	
	private JFrame frame;
	private Sudoku sudoku;
	private JLabel numerosFaltantes;
	private RelojGrafica reloj;
	private JLabel [] tiempo;

	/**
	 * Ejecuta el juego.
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tablero window = new Tablero();
					if(window.frame != null) { 
						window.frame.setVisible(true);
					}
				} catch (Exception e) {
					Component frame = null;
					JOptionPane.showMessageDialog(frame,
						    "No hay tablero para mostrar.",
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

	/**
	 * Crea la aplicación e inicializa todos los componentes. 
	 */
	public Tablero() {
		sudoku = new Sudoku();
		
		if(sudoku.leerArchivo("archivos/numeros1.txt")) {	//Verifico que el archivo de texto ingresado sea válido.
			
			if(sudoku.validacionInicial()) { //Verifico que el archivo de texto contenga una solución sudoku-válida.
				
				//Inicializacion de la lógica.
				sudoku.inicializar();
				
				//Inicializacion de la matriz de celdas.
				for(int i = 0;i<sudoku.getFila();i++) {
					for(int j = 0;j<sudoku.getColumna();j++) {
						Celda c = sudoku.getCelda(i,j);
						Integer valor = c.getValor();
						if(valor != null) {
							c.getEntidadGrafica().iniciarImagen(valor);
						}else {
							c.getEntidadGrafica().iniciarJComboBox();
							c.getEntidadGrafica().getCombobox().addActionListener((ActionListener) new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									Integer num = (Integer) c.getEntidadGrafica().getCombobox().getSelectedItem();
									boolean cumple = sudoku.validarValor(c.getFila(),c.getColumna(),num);
									actualizarFaltantes();
									if(!cumple) {
										String comida = sudoku.obtenerComida(num);
										//Cartel que se muestra cuando se ingresa un valor que colisiona con otro.
										Component frame = null;
										JOptionPane.showMessageDialog(frame,
											    "No se puede ingresar "+comida+" en esa posición.",
											    "Warning",
											    JOptionPane.WARNING_MESSAGE);
										c.getEntidadGrafica().eliminarSeleccion();
										sudoku.limpiar(); 
									}
									if(sudoku.getNumerosFaltantes() == 0) {
										sudoku.pararReloj();
										sudoku.ganar();
										numerosFaltantes.setText("¡LISTO!");
										//Cartel que se muestra cuando se gana el juego.
										Component frame = null;
										JOptionPane.showMessageDialog(frame,
											    "¡Ganaste!");
									}
								}
							});
						}
					}
				}

				//Inicialización de tiempo.
				tiempo = new JLabel [8];
				reloj = new RelojGrafica(tiempo);
				sudoku.iniciarReloj(reloj);
				
				initialize();
				
			}else {
				//Cartel que se muestra cuando el archivo no contiene valores válidos para comenzar a jugar.
				Component frame = null;
				JOptionPane.showMessageDialog(frame,
					    "El tablero no es válido.",
					    "Warning",
					    JOptionPane.WARNING_MESSAGE);
			}
			
		}else {
			/*Cartel que se muestra cuando no se pudo encontrar la ruta del archivo o cuando el archivo no cuenta con el formato
			 *de 9 filas de 9 numeros separados por espacios. 
			 */
			Component frame = null;
			JOptionPane.showMessageDialog(frame,
				    "Se produjo un error durante la lectura del archivo.",
				    "Warning",
				    JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Inicialización de los componentes de la GUI.
	 */
	private void initialize() {
		
		frame = new JFrame("Foodoku");
		frame.setBounds(100, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/img/un cupcake.png"));
		frame.setIconImage(imageIcon.getImage());
		
		Panel panel = new Panel();
		panel.setBackground(Color.PINK);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(3, 3, 3, 3));
	
		armarPanelesCeldas(panel);
		
		//Panel para reservar espacio en la parte superior de la ventana.
		Panel header = new Panel();
		header.setBackground(new java.awt.Color(255,130,162));
		frame.getContentPane().add(header, BorderLayout.NORTH);
		header.setLayout(new GridLayout(1, 3, 0, 0));
		
		//Panel con números faltantes.
		Panel info = new Panel();
		header.add(info);
		numerosFaltantes = new JLabel();
		info.add(numerosFaltantes);
		actualizarFaltantes();
		
		//Panel con el reloj.
		Panel reloj = new Panel();
		header.add(reloj);
		reloj.setLayout(new GridLayout(1,8));
		for(int i = 0;i<tiempo.length;i++) {
			reloj.add(tiempo[i]);
		}

	}
	
	/**
	 * Actualiza la etiqueta con los numeros faltantes del Sudoku.
	 */
	private void actualizarFaltantes() {
		numerosFaltantes.setText("¡FALTAN "+sudoku.getNumerosFaltantes()+" COMIDAS!");
	}
	
	/**
	 * Arma los paneles que van a contener a las imagenes.
	 * @param panel Panel que va a contener a los paneles que contienen a las imagenes.
	 */
	private void armarPanelesCeldas(Panel panel) {
		int sl = sudoku.getSubmatrizLong();
		int cantFilaSubPanel = (int) sudoku.getFila() / sl;
		JPanel [][] paneles = new JPanel[cantFilaSubPanel][cantFilaSubPanel];

		for (int i = 0;i < cantFilaSubPanel;i++){
			for (int j = 0;j < cantFilaSubPanel;j++){
				paneles[i][j] = new JPanel();
				paneles[i][j].setBackground(new java.awt.Color(255,211,222));
				panel.add(paneles[i][j]);
				paneles[i][j].setLayout(new GridLayout(sl, sl, sl, sl));
			}
		}

		for (int i = 0; i< sudoku.getFila(); i++){
			int m = (int) i / cantFilaSubPanel;
			for (int j = 0; j < sudoku.getColumna(); j++){
				int n = (int) j / cantFilaSubPanel;
				Celda c = sudoku.getCelda(i,j);
				if(c.getValor() != null) {
					paneles[m][n].add(c.getEntidadGrafica().getGrafico());
				}else {
					paneles[m][n].add(c.getEntidadGrafica().getCombobox());
				}
			}
		}
	}
}
