package Logica;
import GUI.EntidadGrafica;

/**
 * Clase que modela una celda.
 */
public class Celda {
	private Integer valor;
	private EntidadGrafica entidadGrafica;
	private int fila,columna;
	
	/**
	 * Crea una nueva celda.
	 */
	public Celda() {
		this.valor = null;
		this.entidadGrafica = new EntidadGrafica();
	}
	
	/**
	 * Colorea la celda de rojo.
	 */
	public void pintarRojo() {
		this.entidadGrafica.comboboxRojo();
		this.entidadGrafica.etiquetaRojo();
	}
	
	/**
	 * Colorea la celda de blanco.
	 */
	public void pintarBlanco() {
		this.entidadGrafica.comboboxBlanco();
		this.entidadGrafica.etiquetaBlanco();
	}
	
	/**
	 * Colorea la celda de verde.
	 */
	public void pintarVerde() {
		this.entidadGrafica.comboboxVerde();
		this.entidadGrafica.etiquetaVerde();
	}
	
	/**
	 * Retorna el valor de la celda.
	 * @return Valor de la celda.
	 */
	public Integer getValor() {
		return this.valor;
	}
	
	/**
	 * Retorna la columna en la que se encuentra ubicada la celda.
	 * @return Columna.
	 */
	public int getColumna() {
		return this.columna;
	}
	
	/**
	 * Retorna la fila en la que se encuentra ubicada la fila.
	 * @return Fila.
	 */
	public int getFila() {
		return this.fila;
	}
	
	/**
	 * Asigna un valor a la celda.
	 * @param valor Valor numérico.
	 */
	public void setValor(Integer valor) {
		this.valor = valor;
	}
	
	/**
	 * Asigna una columna a la celda.
	 * @param c Columna.
	 */
	public void setColumna(int c) {
		this.columna = c;
	}
	
	/**
	 * Asigna una fila a la celda.
	 * @param f Fila.
	 */
	public void setFila(int f) {
		this.fila = f;
	}
	
	/**
	 * Retorna la entidad gráfica de la celda.
	 * @return Entidad gráfica.
	 */
	public EntidadGrafica getEntidadGrafica() {
		return this.entidadGrafica;
	}
	
	/**
	 * Asigna una entidad gráfica a la celda.
	 * @param g Entidad gráfica.
	 */
	public void setGrafica(EntidadGrafica g) {
		this.entidadGrafica = g;
	}
	
}
