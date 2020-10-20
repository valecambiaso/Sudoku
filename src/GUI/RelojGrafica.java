package GUI;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Clase que modela la parte gráfica del reloj.
 */
public class RelojGrafica {
	
	private String [] imagenes;
	private JLabel [] numeros;
	
	/**
	 * Inicializa la gráfica del reloj.
	 * @param numeros Arreglo de etiquetas donde se pondrán las imagenes de los numeros.
	 */
	public RelojGrafica(JLabel [] numeros){
		imagenes = new String [] {"/numeros/zero.png","/numeros/one.png","/numeros/two.png","/numeros/three.png","/numeros/four.png","/numeros/five.png","/numeros/six.png","/numeros/seven.png","/numeros/eight.png","/numeros/nine.png","/numeros/two-dots.png"};
		this.numeros = numeros;
		
		inicializar();
	}
	
	/**
	 * Inicializa las etiquetas del tiempo.
	 */
	private void inicializar() {
		ImageIcon imagenCero = new ImageIcon(this.getClass().getResource(this.imagenes[0]));
		ImageIcon imagenPuntos = new ImageIcon(this.getClass().getResource(this.imagenes[10]));
		
		for(int i = 0;i<numeros.length;i++) {
			if((i+1) % 3 == 0) { 
				numeros[i] = new JLabel(imagenPuntos);
				redimensionar(numeros[i],imagenPuntos);
			}else {
				numeros[i] = new JLabel(imagenCero);
				redimensionar(numeros[i],imagenCero);
			}
		}
	}
	
	/**
	 * Actualiza las imagenes de los segundos.
	 * @param s1 Decenas de los segundos.
	 * @param s2 Unidades de los segundos.
	 */
	public void actualizarSegundos(int s1,int s2) {
		ImageIcon imagenS1 = new ImageIcon(this.getClass().getResource(this.imagenes[s1]));
		ImageIcon imagenS2 = new ImageIcon(this.getClass().getResource(this.imagenes[s2]));
		redimensionar(numeros[6],imagenS1);
		redimensionar(numeros[7],imagenS2);
		numeros[6].setIcon(imagenS1);
		numeros[7].setIcon(imagenS2); 
	}
	
	/**
	 * Actualiza las imagenes de los minutos.
	 * @param m1 Decenas de los minutos.
	 * @param m2 Unidades de los minutos.
	 */
	public void actualizarMinutos(int m1,int m2) {
		ImageIcon imagenM1 = new ImageIcon(this.getClass().getResource(this.imagenes[m1]));
		ImageIcon imagenM2 = new ImageIcon(this.getClass().getResource(this.imagenes[m2]));
		redimensionar(numeros[3],imagenM1);
		redimensionar(numeros[4],imagenM2);
		numeros[3].setIcon(imagenM1);
		numeros[4].setIcon(imagenM2); 
	}
	
	/**
	 * Actualiza las imagenes de los segundos.
	 * @param h1 Decenas de las horas.
	 * @param h2 Unidades de las horas.
	 */
	public void actualizarHoras(int h1,int h2) {
		ImageIcon imagenH1 = new ImageIcon(this.getClass().getResource(this.imagenes[h1]));
		ImageIcon imagenH2 = new ImageIcon(this.getClass().getResource(this.imagenes[h2]));
		redimensionar(numeros[0],imagenH1);
		redimensionar(numeros[1],imagenH2);
		numeros[0].setIcon(imagenH1);
		numeros[1].setIcon(imagenH2); 
	}
	
	/**
	 * Redimensiona los números del reloj.
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
}
