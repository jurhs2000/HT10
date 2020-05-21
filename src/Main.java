/**
 * Universidad del Valle de Guatemala
 * Algoritmos y estructuras de datos
 * Hoja de Trabajo 10
 * 21/05/2020
 * 
 * Programa de implementación de grafos para algoritmo de Floyd
 * Búsqueda de ruta más corta entre nodos (ciudades),
 * Lectura de grafo actual por medio de un archivo y opcion de
 * agregar nuevos nodos, y modificar las rutas
 * 
 * @author Julio Herrera 19402
 * @version 1.0
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	/**
	 * 
	 * Este método es utilizado para leer el archivo datos.txt. La lectura se
	 * realiza para todas las líneas del archivo y separa cada caracter que tenga el
	 * archivo para agregarlo a la lista de Strings que devolverá.
	 * 
	 * @return una lista de todos los elementos del archivo separados
	 * @throws Exception excepción general para la lectura del archivo
	 */
	public static ArrayList<String> textReader(String fileName) throws Exception {
		final String bar = File.separator;
		final String dir = System.getProperty("user.dir");
		/**
		 * AQUI SE LEE EL ARCHIVO TXT si no corre se debe de reemplazar en el parentesis
		 * (dir + barra +"NOMBRE DEL FOLDER EN DONDE ESTA EL PROYECTO" +barra+
		 * "datos.txt") El error del archivo de texto puede pasar si se corre el
		 * programa en eclipse y no en consola o tambien sucede al trabajar con paquetes
		 */
		final File file = new File(dir + bar + fileName);
		if (!file.exists()) {
			throw new FileNotFoundException("No se encontro el archivo, ver lineas comentadas");
		}
		FileReader fr;
		fr = new FileReader(file);
		final BufferedReader br = new BufferedReader(fr);
		ArrayList<String> lineList = new ArrayList<>(); //ya que solo se agrega al final
		String line = "";
		while ((line = br.readLine()) != null) {
			lineList.add(line);
		}
		br.close();
		return lineList;
	}

	public static void main(final String[] args) throws Exception {
		/**
		 * Menu de eleccion de archivo a leer
		 */
		Scanner scan = new Scanner(System.in);
		boolean isCorrect = false;
		String fileName = "";
		while (!isCorrect) {
            System.out.println("Escriba el nombre del archivo que va a leer");
            System.out.println("Si presiona solo enter se escoge por default ('guategrafo.txt'): ");
			fileName = scan.nextLine();
			if (fileName.split(".").length < 1) {
                System.out.println("Indique la extension del archivo .txt");
            } else {
                isCorrect = true;
            }
		}
        isCorrect = false;
		if (fileName.equals("")) {
			fileName = "guategrafo.txt";
		}
		/**
		 * Se lee el archivo de texto
		 */
		ArrayList<String> grafoTexto = textReader(fileName);
		/**
		 * Se cuenta la cantidad de ciudades para crear el grafo
		 */
		ArrayList<String> citiesCount = new ArrayList<>();
		for (String relation : grafoTexto) {
			String[] splitRelation = relation.split(" ");
			if (!citiesCount.contains(splitRelation[0])) {
				citiesCount.add(splitRelation[0]);
			}
			if (!citiesCount.contains(splitRelation[1])) {
				citiesCount.add(splitRelation[1]);
			}
		}
        /**
		 * Estructuras de datos
		 * Se crea el grafo para almacenar las ciudades del documento de texto
         */
        Graph<City> graph = new FloydGraph<>(citiesCount.size(), City.class);
		/**
		 * Comienza la lectura de los elementos del archivo en la lista para ingresarlos
		 * al grafo.
		 */
		for (String relation : grafoTexto) {
			String[] splitRelation = relation.split(" ");
			City sourceCity = new City(splitRelation[0]);
			City targetCity = new City(splitRelation[1]);
			Integer distance = Integer.valueOf(splitRelation[2]);
			graph.addEdge(sourceCity, targetCity, distance);
		}
		/**
		 * Se hace el caluclo de las rutas mas cortas (inicial)
		 */
		graph.calculateNewEdges();
		/**
		 * Se agregan las palabras a la lista de oraciones tambien como asociaciones
		 */
		boolean isCorrectParam = false;
		int userOption = -1;
		while (userOption != 5) {
			// MENU
			System.out.println("\nBienvenido al programa de calculo de rutas entre ciudades!\n"+
			"1. Mostrar el valor de la ruta mas corta entre dos ciudades\n"+
			"2. Modificar ruta (Edita, Crea y Elimina)\n"+
			"3. Mostrar la ciudad central\n"+
			"4. Ver matriz\n"+
			"5. Salir");
			// Try para evitar ingreso de letras
			try {
				userOption = scan.nextInt();
			} catch (Exception e) {
				userOption = -1;
			}
			scan.nextLine();
			City sourceCity = new City();
			City targetCity = new City();
			Integer distance = 0;
			isCorrectParam = false;
			switch (userOption) {
				case 1:
					// Lee la ciudad de origen
					while (!isCorrectParam) {
						System.out.println("\nIngrese el nombre de la ciudad de origen");
						sourceCity = new City(scan.nextLine());
						isCorrectParam = graph.containsVertex(sourceCity);
						if (!isCorrectParam) {
							System.out.println("Ingrese una ciudad que se encuentre en el registro!");
						}
					}
					isCorrectParam = false;
					// Lee la ciudad de destino
					while (!isCorrectParam) {
						System.out.println("\nIngrese el nombre de la ciudad de destino");
						targetCity = new City(scan.nextLine());
						isCorrectParam = graph.containsVertex(targetCity);
						if (!isCorrectParam) {
							System.out.println("Ingrese una ciudad que se encuentre en el registro!");
						}
					}
					// Muestra la distancia mas corta
					System.out.println("\nLa distancia de " + sourceCity.getName() + " a " +
					targetCity.getName() + " es:");
					Integer resultDistance = graph.getEdge(sourceCity, targetCity);
					if (resultDistance == Integer.MAX_VALUE) {
						System.out.println("No hay paso entre estas ciudades.");
					} else {
						System.out.println(resultDistance);
					}
					// Muestra las ciudades por las que se debe pasar
					System.out.println("\nLas ciudades que debes cruzar son:");
					graph.showEdges(sourceCity, targetCity);
				break;
				case 2:
					// Recibe la ciudad de origen
					while (!isCorrectParam) {
						System.out.println("\nIngrese el nombre de la ciudad de origen");
						sourceCity = new City(scan.nextLine());
						isCorrectParam = graph.containsVertex(sourceCity);
						if (!isCorrectParam) {
							System.out.println("Ingrese una ciudad que se encuentre en el registro!");
						}
					}
					// Recibe la ciudad de desitno
					isCorrectParam = false;
					while (!isCorrectParam) {
						System.out.println("\nIngrese el nombre de la ciudad de destino");
						targetCity = new City(scan.nextLine());
						isCorrectParam = graph.containsVertex(targetCity);
						if (!isCorrectParam) {
							System.out.println("Ingrese una ciudad que se encuentre en el registro!");
						}
					}
					isCorrectParam = false;
					// Recibe la nueva distancia entre las ciudades
					while (!isCorrectParam) {
						System.out.println("\nIngrese la nueva distancia entre las ciudades "+
						"(Al ingresar 0 se borrara la ruta!)");
						try {
							distance = scan.nextInt();
							scan.nextLine();
							if (distance < 0) {
								distance = Integer.valueOf("generar error");
							} else {
								isCorrectParam = true;
							}
						} catch (Exception e) {
							System.out.println("Ingrese un numero valido y mayor o igual a cero");
						}
					}
					// Muestra la accion que se realizo
					if (distance > 0) {
						if (graph.getDirectEdge(sourceCity, targetCity) == Integer.MAX_VALUE) {
							System.out.println("\nSe creo la ruta con distancia " + distance);
						} else {
							System.out.println("\nSe cambio la distancia de la ruta de " + 
							graph.getDirectEdge(sourceCity, targetCity) + " a " + distance);
						}
					} else {
						System.out.println("\nSe elimino la ruta!");
					}
					// Agrega la nueva ruta y recalcula las rutas mas cortas
					graph.addEdge(sourceCity, targetCity, distance);
					graph.calculateNewEdges();
				break;
				case 3:
					// Muestra la ciudad que es el centro del grafo
					System.out.println("\nLa ciudad central es:");
					System.out.println(graph.getCenter().getName());
				break;
				case 4:
					// Muestra las distintas matrices del grafo
					System.out.println("Los nodos de la matriz son:");
					graph.showVertexes();
					System.out.println("\nLa matriz original es: ");
					graph.showOriginal();
					System.out.println("La matriz de adyacencia es: ");
					graph.showGraph();
					System.out.println("La matriz de nodos intermedios es: ");
					graph.showIntermediateVertex();
				break;
				case 5:
					System.out.println("\nGracias por utilizar el programa!");
				break;
				default:
					System.out.println("\nElige una opcion valida!\n");
				break;
			}
		}
		/**
		 * Se termina el programa
		 */
		scan.close();
	}
}
