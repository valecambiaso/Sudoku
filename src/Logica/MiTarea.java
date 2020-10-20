package Logica;

import java.util.Timer;


import java.util.TimerTask;

import GUI.RelojGrafica;


/**
 * Clase que contabiliza el tiempo.
 */
public class MiTarea extends TimerTask {
	private int segundos;
	private int minutos;
	private int horas;
	private final int max_segundos = 60;
	private final int max_minutos = 60;
	private final int max_horas = 24;
	Timer timer;
	RelojGrafica reloj;
	
	/**
	 * Inicializa el reloj del juego.
	 * @param timer Timer.
	 * @param reloj RelojGrafica que modela la parte grafica del reloj.
	 */
	public MiTarea(Timer timer,RelojGrafica reloj) {
		this.timer = timer;
		segundos = 0;
		minutos = 0;
		horas = 0;
		this.reloj = reloj;
	}
	
	@Override
	public void run() {
		segundos++;
		if(segundos == max_segundos) {
			minutos++;
			segundos = 0;
			if(minutos == max_minutos) {
				horas++;
				minutos = 0;
				if(horas == max_horas) {
					horas = 0;
				}
				reloj.actualizarHoras(horas/10,horas%10);
			}
			reloj.actualizarMinutos(minutos/10,minutos%10);
		}
		reloj.actualizarSegundos(segundos/10,segundos%10);
	}	
}