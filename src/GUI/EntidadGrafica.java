package GUI;
import java.awt.Color;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 * Clase que modela a la entidad gráfica de cada celda.
 */
public class EntidadGrafica{
	private JLabel graficoEtiqueta; 
	private JComboBox<Integer> combobox;
	private String[] imagenes;
	private final Color rojo = new java.awt.Color(255,93,93);
	private final Color verde = new java.awt.Color(154,255,181);
	private final Color blanco = new java.awt.Color(255,244,249);
	
	/**
	 * Crea una entidad gráfica.
	 */
	public EntidadGrafica() {
		this.graficoEtiqueta = new JLabel();
		this.imagenes = new String[] {"/img/bananas.png","/img/un brocoli.png","/img/una zanahoria.png","/img/un cafe.png","/img/un cupcake.png","/img/una dona.png","/img/una hamburguesa.png","/img/pizza.png","/img/un taco.png"};
		this.combobox = new JComboBox<Integer>();
	}
	
	/**
	 * Inicia la imagen de la celda.
	 * @param indice Número que va en la celda.
	 */
	public void iniciarImagen(int indice) {
		if (indice < this.imagenes.length + 1) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[indice-1]));
			
			redimensionar(graficoEtiqueta,imageIcon);
			graficoEtiqueta.setIcon(imageIcon);
			graficoEtiqueta.setOpaque(true);
			graficoEtiqueta.setBackground(blanco);
		}
	}
	
	/**
	 * Inicia el JComboBox de la celda.
	 */
	public void iniciarJComboBox() {
		for(int i = 0;i<this.imagenes.length + 1;i++) {
			combobox.addItem(i);
		}
		combobox.setRenderer(new RenderJComboBox());
		combobox.setSelectedIndex(0);
		combobox.setOpaque(true);
		combobox.setBackground(Color.WHITE);
	}
	
	/**
	 * Pinta el fondo de la imagen de rojo.
	 */
	public void etiquetaRojo() {
		graficoEtiqueta.setBackground(rojo);
	}
	
	/**
	 * Pinta el fondo de la imagen de blanco.
	 */
	public void etiquetaBlanco() {
		graficoEtiqueta.setBackground(blanco);
	}
	
	/**
	 * Pinta el fondo de la imagen de verde.
	 */
	public void etiquetaVerde() {
		graficoEtiqueta.setBackground(verde);
	}
	
	/**
	 * Pinta el fondo del combobox de rojo.
	 */
	public void comboboxRojo() {
		combobox.setBackground(rojo);
	}
	
	/**
	 * Pinta el fondo del combobox de blanco.
	 */
	public void comboboxBlanco() {
		combobox.setBackground(Color.WHITE);
	}
	
	/**
	 * Pinta el fondo del combobox de verde.
	 */
	public void comboboxVerde() {
		combobox.setBackground(verde);
	}
	
	/**
	 * Elimina la seleccion de la caja.
	 */
	public void eliminarSeleccion() {
		combobox.setSelectedIndex(0);
	}
	
	/**
	 * Redimensiona las imagenes del tablero.
	 * @param label Etiqueta en la que se va a redimensionar la imagen.
	 * @param grafico Imagen a redimensionar.
	 */
	private void redimensionar(JLabel label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {  
			Image newimg = image.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			label.repaint();
		}
	}
	
	/**
	 * Obtiene el gráfico de la celda.
	 * @return ImageIcon de la celda.
	 */
	public JLabel getGrafico() {
		return this.graficoEtiqueta;
	}
	
	/**
	 * Asigna una imagen a la celda.
	 * @param grafico ImageIcon.
	 */
	public void setGrafico(JLabel grafico) {
		this.graficoEtiqueta = grafico;
	}
	
	/**
	 * Obtiene las imagenes de la celda.
	 * @return Arreglo de nombres de imagenes.
	 */
	public String[] getImagenes() {
		return this.imagenes;
	}
	
	/**
	 * Asigna las imagenes de la celda.
	 * @param imagenes Arreglo de nombres de imagenes.
	 */
	public void setImagenes(String[] imagenes) {
		this.imagenes = imagenes;
	}
	
	/**
	 * Obtiene el JComboBox de la celda.
	 * @return JComboBox de la celda.
	 */
	public JComboBox<Integer> getCombobox() {
		return this.combobox;
	}
	
	/**
	 * Asigna un JComboBox a la celda.
	 * @param combobox JComboBox.
	 */
	public void setCombobox(JComboBox<Integer> combobox) {
		this.combobox = combobox;
	}
	
}

