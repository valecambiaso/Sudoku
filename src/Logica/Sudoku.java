package Logica;

import java.io.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import GUI.RelojGrafica;

/**
 * Clase que modela la parte lógica del Sudoku.
 *
 */
public class Sudoku {
	private final int fila = 9;
	private final int columna = 9;
	private final int submatrizLong = 3;
	private int [][] tablero;
    private int numerosFaltantes;
    private Celda [][] tableroCeldas;
    private Timer timer = new Timer();
	private final int milisec = 1000;
	
    /**
     * Crea un nuevo sudoku.
     */
    public Sudoku() {
    	tablero = new int [fila][columna];
    	numerosFaltantes = 0;
    }
    
    /**
     * Inicia el reloj.
     * @param reloj Entidad gráfica del reloj.
     */
    public void iniciarReloj(RelojGrafica reloj) {
    	TimerTask task = new MiTarea(timer,reloj);
		timer.scheduleAtFixedRate(task,milisec,milisec);
    }
    
    /**
     * Detiene el reloj.
     */
    public void pararReloj() {
    	timer.cancel();
    }
    
    /**
     * Retorna la celda en la fila f y columna c.
     * @param f Fila.
     * @param c Columna.
     * @return Celda en la fila f y columna c.
     */
    public Celda getCelda(int f,int c) {
    	return tableroCeldas[f][c];
    }
    
    /**
     * Retorna la cantidad de filas del sudoku.
     * @return Cantidad de filas.
     */
    public int getFila() {
		return fila;
	}
    
    /**
     * Retorna la cantidad de columnas del sudoku.
     * @return Cantidad de columnas.
     */
	public int getColumna() {
		return columna;
	}
	
	/**
	 * Retorna la longitud de las submatrices del sudoku.
	 * @return Longitud de submatrices.
	 */
	public int getSubmatrizLong() {
		return this.submatrizLong;
	}

	/**
     * Retorna la cantidad de numeros faltantes en la matriz.
     * @return Cantidad de numeros faltantes en la matriz.
     */
    public int getNumerosFaltantes() {
    	return numerosFaltantes;
    }
    
	/**
	 * Leo el archivo cuya ruta fue pasada como parametro y cargo los numeros del mismo en el tablero, en
	 * caso de que el archivo no cuente con el formato esperado o no se pueda leer retorna falso.
	 * @param path Ruta del archivo que contiene los numeros del juego.
	 * @return Verdadero si el archivo se pudo leer correctamente, falso en caso contrario.
	 */
	public boolean leerArchivo(String path) {
		
		BufferedReader archivo;
		String numeros [] = new String [columna];
		String linea;
		boolean valido = true;
		
		try {
			InputStream in = Sudoku.class.getClassLoader().getResourceAsStream(path);
            InputStreamReader inr = new InputStreamReader(in);
            archivo =  new BufferedReader(inr);
			//archivo = new BufferedReader(new FileReader(path));
			linea = archivo.readLine();
			valido = linea != null;
			
			for(int i = 0;i<fila && valido;i++) {
				valido = linea != null;
				if(valido) {
					numeros = linea.split(" ");
					valido = numeros.length == this.columna;
					
					for(int j = 0;j<columna && valido;j++) {
						tablero[i][j] = Integer.parseInt(numeros[j]);
					}
					linea = archivo.readLine();
				}
			}
			
			if(linea != null) { //Si el archivo tiene líneas de más ya no es válido.
				valido = false;
			}
			
			archivo.close();
			
		}catch(IOException error){
			valido = false;
		}
		
		return valido;
	}
	
	/**
	 * Verifica si los numeros de dada columna cumplen la condicion de ser todos diferentes.
	 * @param columna Columna de la matriz.
	 * @return Verdadero si se cumple con la condicion, falso en caso contrario.
	 */
	private boolean validarColumna(int c) {
		boolean cumple = true;
		Map<Integer,Integer> mapeo = new HashMap<Integer,Integer>(); //Armo un mapeo para controlar las columnas.
		
		for(int i = 0;i < fila && cumple;i++) {
			if(tablero[i][c] == 0 || mapeo.get(Math.abs(tablero[i][c])) == null) {
					mapeo.put(Math.abs(tablero[i][c]),Math.abs(tablero[i][c]));
				}else {
					cumple = false;
				}
		}
		
		return cumple;	
	}
	
	/**
	 * Verifica si los numeros de dada fila cumplen la condicion de ser todos diferentes.
	 * @param fila Fila de la matriz.
	 * @return Verdadero si se cumple con la condicion, falso en caso contrario.
	 */
	private boolean validarFila(int f) {
		boolean cumple = true;
		Map<Integer,Integer> mapeo = new HashMap<Integer,Integer>(); //Armo un mapeo para controlar las filas.
		
		for(int i = 0;i < columna && cumple;i++) {
			if(tablero[f][i] == 0 || mapeo.get(Math.abs(tablero[f][i])) == null) {
					mapeo.put(Math.abs(tablero[f][i]),Math.abs(tablero[f][i]));
				}else {
					cumple = false;
				}
		}
		
		return cumple;
	}
	
	/**
	 * Valida si los numeros de una de las submatrices del tablero cumple la condicion de que todos los numeros sean diferentes.
	 * @param f Fila donde comienza la submatriz.
	 * @param c Columna donde comienza la submatriz.
	 * @return Verdadero si se cumple con la condicion, falso en caso contrario.
	 */
	private boolean validarSubmatriz(int f,int c) {
		boolean cumple = true;
		Map<Integer,Integer> mapeo = new HashMap<Integer,Integer>(); //Armo un mapeo para controlar las matrices.
		
		for(int i = f;i < f+this.submatrizLong && cumple;i++) {
			for(int j = c;j < c+this.submatrizLong && cumple;j++) {
				if(tablero[i][j] == 0 || mapeo.get(Math.abs(tablero[i][j])) == null) {
					mapeo.put(Math.abs(tablero[i][j]),Math.abs(tablero[i][j]));
				}else {
					cumple = false;
				}
			}
		}
		
		return cumple;
	}
	
	/**
	 * Valida que la matriz de numeros cumpla con la condicion de que no hayan numeros repetidos en ninguna fila, columna o submatriz 3x3.
	 * @return Verdadero si cumple la condicion, falso en caso contrario.
	 */
	public boolean validacionInicial() {
		boolean cumple = true;
		
		//Validamos las submatrices.
		for(int i = 0;i<fila && cumple;i+=this.submatrizLong) {
			for(int j = 0;j<columna && cumple;j+=this.submatrizLong) {
				cumple = validarSubmatriz(i,j);
			}
		}
		
		//Validamos las filas y columnas.
		for(int k = 0;k<fila && cumple;k++) {
			cumple = validarFila(k) && validarColumna(k);
			for(int l = 0;l<columna && cumple;l++) { 
				//Valido que en el tablero se hayan ingresado dígitos entre 1 y 9.
				cumple = (tablero[l][k] >=1) && (tablero[l][k] <= 9);
			}
		}
		
		return cumple;
	}
	
	/**
	 * Creo un tablero de celdas en base a los valores del tablero de enteros, ocultando algunos de los números.
	 * @return Tablero de celdas.
	 */
	public void inicializar() {
		tableroCeldas = new Celda[fila][columna];
		Random ran;
		int valor;
		for(int i = 0;i<fila;i++) {
			for(int j = 0;j<columna;j++) {
				ran = new Random();
				valor = ran.nextInt(2);
				tableroCeldas[i][j] = new Celda();
				/* Genero un valor entre 0 y 1 que luego me será de utilidad para diferenciar las celdas que cuentan
				 * con un valor inicial de las que debo dejar que el jugador adivine. En caso de ser 1, inicializo 
				 * la celda de tableroC con el valor numerico correspondiente en el tablero. En caso de ser 0, 
				 * inicializo la celda de tableroC con el valor 0.
				 */
				tableroCeldas[i][j].setFila(i);
				tableroCeldas[i][j].setColumna(j);
				if(valor != 0) {
					tableroCeldas[i][j].setValor(tablero[i][j]);
				}else {
					numerosFaltantes++;
					tablero[i][j] = 0;
					tableroCeldas[i][j].setValor(null);
				}
			}
		}
	}
	
	/**
	 * Dado un cierto dígito que se quiere ingresar en la posición [f][c] del tablero, se analiza si es posible realizar esta acción.
	 * @param f Fila en la que se quiere ingresar el valor.
	 * @param c Columna en la que se quiere ingresar el valor.
	 * @param valor Valor que se quiere ingresar en la posición [f][c].
	 * @return Verdadero si es posible, falso en caso contrario.
	 */
	public boolean validarValor(int f,int c,int valor) {
		boolean cumple = true;
		boolean encontre;
		
		int old = tablero[f][c];
		tablero[f][c] = valor;
			
		//Valido su respectiva submatriz.
		int m = (int)f / this.submatrizLong;
		int n = (int)c / this.submatrizLong;
		m *= this.submatrizLong;
		n *= this.submatrizLong;
		boolean cumpleSubmatriz = validarSubmatriz(m,n);
		
		//Valido su respectiva columna y fila.
		boolean cumpleFila = validarFila(f);
		boolean cumpleCol = validarColumna(c);
		
		cumple = cumpleSubmatriz && cumpleFila && cumpleCol;
			
		if(!cumple || tablero[f][c] == 0) {
			/*Si el valor ingresado por el jugador no es valido o es 0 y el valor anterior en la celda no es cero, es porque
			 * o el jugador reemplazó un valor válido por otro que no lo es o simplemente eliminó el valor válido, luego falta 
			 * un número más que antes.*/
			if(old != 0) {
				numerosFaltantes++;
			}
			tablero[f][c] = 0;
			tableroCeldas[f][c].setValor(null);
		}else {
			/*Si el valor ingresado por el jugador es válido y el valor anterior en la celda es igual a cero, es porque
			 * se ingresó un nuevo valor válido que no reemplaza a otro válido y por lo tanto falta un número menos.*/
			if(old == 0) {
				numerosFaltantes--;
			}
			tableroCeldas[f][c].setValor(valor);
		}

		if(!cumpleSubmatriz) {
			/*
			 * Si hubo una colisión en la submatriz, busco la celda con la que colisiona el nuevo valor agregado.
			 */
			encontre = false;
			for(int i = m;i<m+this.submatrizLong && !encontre;i++) {
				for(int j = n;j<n+this.submatrizLong && !encontre;j++) {
					if(tablero[i][j] == valor) {
						encontre = true;
						Celda celda = tableroCeldas[i][j];
						celda.pintarRojo();
					}
				}
			}
		}
		
		if(!cumpleFila) {
			/*
			 * Si hubo una colisión en la fila, busco la celda con la que colisiona el nuevo valor agregado.
			 */
			encontre = false;
			for(int i = 0;i<this.fila && !encontre;i++) {
				if(tablero[f][i] == valor) {
					encontre = true;	
					Celda celda = tableroCeldas[f][i];
					celda.pintarRojo();
				}
			}
		}
		
		if(!cumpleCol) {
			/*
			 * Si hubo una colisión en la columna, busco la celda con la que colisiona el nuevo valor agregado.
			 */
			encontre = false;
			for(int j = 0;j<this.columna && !encontre;j++) {
				if(tablero[j][c] == valor) {
					encontre = true;
					Celda celda = tableroCeldas[j][c];
					celda.pintarRojo();
				}
			}
		}
		
		return cumple;
	}
	
	/**
	 * Vuelve a colorear todas las casillas de blanco luego de que se haya detectado una colisión.
	 */
	public void limpiar() {
		for(int i = 0;i<this.fila;i++) {
			for(int j = 0;j<this.columna;j++) {
				Celda c = tableroCeldas[i][j];
				c.pintarBlanco();
			}
		}
	}
	
	/**
	 * Colorea todas las casillas de verde cuando se gana el juego.
	 */
	public void ganar() {
		for(int i = 0;i<this.fila;i++) {
			for(int j = 0;j<this.columna;j++) {
				Celda c = tableroCeldas[i][j];
				c.getEntidadGrafica().getCombobox().setEnabled(false);
				c.pintarVerde();
			}
		}
	}
	
	/**
	 * Obtiene el nombre de la comida asociada al número pasado como parámetro.
	 * @param num Numero asociado a una comida.
	 * @return Nombre de la comida.
	 */
	public String obtenerComida(int num) {
		String nombre = "";
		
		String [] imagenes = tableroCeldas[0][0].getEntidadGrafica().getImagenes();
		nombre = imagenes[num-1];
		nombre = nombre.substring(nombre.lastIndexOf("/")+1, nombre.lastIndexOf("."));

		return nombre;
	}
}
