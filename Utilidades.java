package utilidades;

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Utilidades {
	static boolean noerror;

	// el metodo tiene que devolver un arrayList de objeto
	// poner la salida del metodo en la funcion
	// Utilidades.dibujarTabla(prestamos);

	public static void dibujarTabla(ArrayList<?> datos) {
		System.out.println();
		// Encontrar los nombres de los campos
		Field[] campos = datos.get(0).getClass().getDeclaredFields(); // coje los campos declarados

		ArrayList<String> nombresCampos = new ArrayList<String>();
		for (Field campo : campos) {

			nombresCampos.add(campo.getName());
		}

		// Encontrar la longitud m�xima de cada campo
		ArrayList<Integer> longitudesMaximas = new ArrayList<Integer>();
		for (int i = 0; i < campos.length; i++) {
			campos[i].setAccessible(true);
			int longitudMaxima = nombresCampos.get(i).length();
			for (Object dato : datos) {
				try {
					String valorCampo = String.valueOf(campos[i].get(dato));
					longitudMaxima = Math.max(longitudMaxima, valorCampo.length());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			longitudesMaximas.add(longitudMaxima);
		}

		// Dibujar la fila superior con los nombres de los campos
		for (int i = 0; i < campos.length; i++) {
			int anchoCampo = longitudesMaximas.get(i) + 2;
			System.out.printf("| %-" + anchoCampo + "s", nombresCampos.get(i));
		}
		System.out.println("|");

		// Dibujar la l�nea separadora entre la fila superior y las filas de datos
		for (int i = 0; i < campos.length; i++) {
			int anchoCampo = longitudesMaximas.get(i) + 2;
			System.out.print("+");
			for (int j = 0; j < anchoCampo; j++) {
				System.out.print("-");
			}
		}
		System.out.println("+");

		// Dibujar las filas de datos
		for (Object dato : datos) {
			for (int i = 0; i < campos.length; i++) {
				int anchoCampo = longitudesMaximas.get(i) + 2;
				try {
					String valorCampo = String.valueOf(campos[i].get(dato));
					System.out.printf("| %-" + anchoCampo + "s", valorCampo);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			System.out.println("|");
		}

		// Dibujar la l�nea inferior de la tabla
		for (int i = 0; i < campos.length; i++) {
			int anchoCampo = longitudesMaximas.get(i) + 2;
			System.out.print("+");
			for (int j = 0; j < anchoCampo; j++) {
				System.out.print("-");
			}
		}
		System.out.println("+");
	}

	// llamar a la funcion RegistrarError(Exception e, String mensaje) para escribir
	// el mensaje al fichero

	public static void añadirRegistroLog(String mensaje, Exception e) {
	    BufferedWriter bw = null;

	    System.out.println(mensaje);
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    e.printStackTrace(pw);
	    String error = sw.toString();

	    try {
	        bw = new BufferedWriter(new FileWriter("log/log.txt", false));
	        bw.write(error);
	        bw.newLine();
	    } catch (IOException a) {
	        System.out.println("Error al escribir el fichero" + a.getMessage());
	        e.printStackTrace();
	    } finally {
	        try {
	            if (bw != null) {
	                bw.close();
	            }
	        } catch (IOException ex) {
	            System.out.println("Error al cerrar el BufferedWriter: " + ex.getMessage());
	        }
	    }
	}
}
