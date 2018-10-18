import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.PriorityQueue;

import entrada_salida.EscritorBinario;
import entrada_salida.LectorBinario;
import estructuras_datos.ArbolHuffman;

/********************************************************************************************
 * Autores: Luis Miguel Ortiz Rozalén, Amanda Sánchez García
 * 
 *  Ejecucion: 
 *  	% Comprimir:    java PlantillaCodificacionHuffman -c filePathIn filePathOut
 *      % Decomprimir:  java PlantillaCodificacionHuffman -d filePathIn filePathOut
 *  
 *  Utilidad: Permite la compresion/descompresion usando el algoritmo de Huffman
 *  de un archivo de entrada hacia un archivo de salida. 
 *  
 *
 *********************************************************************************************/

public class PlantillaCodificacionHuffman {
	
	private Hashtable<Character,String> diccionarioCodigos = new Hashtable<Character,String>();;

	// Constructor
	private PlantillaCodificacionHuffman(){}
	
	/*
	* Se lee el archivo de entrada (filePathIn, a comprimir) como secuencia de palabras de 8 bits 
	* usando LectorBinario, despues se codifica con el algoritmo de Huffman y el resultado 
	* se escribe usando la clase EscritorBinario hacia el archivo de salida (filePathOut, comprimido).
	*/
    public void comprimir(String filePathIn, String filePathOut) {
		
    	LectorBinario lector = new LectorBinario(filePathIn);
		// Leer archivo de entrada y almacenar en una cadena
		StringBuilder sb = new StringBuilder();
		while (!lector.esVacio()) {
			char b = lector.leerPalabra();
			sb.append(b); 	// OJO! leerPalabra() devuelve una palabra de 8 bits y el tipo char es de 16 bits
		}
		char[] input = sb.toString().toCharArray();

		/* TAREA1.1: Generar tabla de frecuencias (freq) a partir del array de tipo char input. */
		
		Hashtable<Character, Integer> freq = new Hashtable<Character, Integer>();
		for(int i = 0; i < input.length; i++) {
			if(freq.containsKey(input[i])) {
				Integer frecuencia = freq.get(input[i]);
				freq.replace(input[i], ++frecuencia);
			} else {
				freq.put(input[i], 1);
			}
		}
		// Construir Arbol de Huffman.
        ArbolHuffman arbol = construirArbol(freq); 
		
		// Construir diccionario de busqueda -> Pares (simbolo,codigo).
		// diccionarioCodigos sera una estructura de tipo Map, Hashtable, String[], dependiendo de la implementacion elegida.
        construirCodigos(diccionarioCodigos,arbol,"");
		
		// Codificar la trama (char[]input) usando el diccionario de codigos.
        codificar(input,diccionarioCodigos,filePathOut,arbol);
	}
    
   /* 
    * Construir arbol de Huffman a partir de la tabla de frecuencias.
    * (Si se ha usado una estructura Hashtable para albergar la tabla de frecuencias).
    */
 	private ArbolHuffman construirArbol(Hashtable<Character,Integer> freq) {
 		
 		/* TAREA 1.2: Implementar una estructura de tipo "ColaPrioridad" o intanciar la que proporciona Java.*/
 		
 		PriorityQueue<ArbolHuffman> arboles = new PriorityQueue<ArbolHuffman>();
 		
    	/* TAREA 1.3: Inicializar la cola de prioridad con arboles de Huffman simples.*/
 		
 		Enumeration<Character> keys = freq.keys();
 		while(keys.hasMoreElements()) {
 			char key = keys.nextElement().charValue();
 			ArbolHuffman arbol = new ArbolHuffman(key, freq.get(key), null, null);
 			arboles.add(arbol);
 		}
 		
    	/* TAREA 1.4: Crear el arbol de Huffman final iterando sobre la cola de prioridad.*/  
 		
 		while(arboles.size() != 1) {
 			ArbolHuffman subarbol1 = arboles.poll();
 			ArbolHuffman subarbol2 = arboles.poll();
 			ArbolHuffman arbolete = new ArbolHuffman('\0', subarbol1.getFrecuencia() + subarbol2.getFrecuencia(), subarbol1, subarbol2);
 			arboles.add(arbolete);
 		}
    	
 		/* Retorna el arbol de Huffman final construido en la TAREA 1.4 */
     	return arboles.poll(); 
 	}

   /* 
    * Construir diccionario de busqueda -> Pares (simbolo,codigo).
    * (Si se usa una estructura Hashtable para albergar el diccionario de codigos).
    */
    private void construirCodigos(Hashtable<Character,String> diccionarioCodigos, ArbolHuffman arbol,String codigoCamino){
    	
    	/* TAREA 1.5: Construir diccionario de búsqueda. */
    	
    		if(!arbol.esHoja()) {
    			if(arbol.getIzquierdo() != null) {
    				construirCodigos(diccionarioCodigos, arbol.getIzquierdo(), codigoCamino+'0');
    			}
    			if(arbol.getDerecho() != null) {
    				//codigoCamino += '1';
    				construirCodigos(diccionarioCodigos, arbol.getDerecho(), codigoCamino+'1');    				
    			}
    		} else {
        		diccionarioCodigos.put(arbol.getSimbolo(), codigoCamino);
    		}
    }

    
   /* 
    * Codificar la trama (char[]input) usando el diccionario de codigos y escribirla en el
    * archivo de salida cuyo path (String filePathOut) se facilita como argumento.
    * (Si se usa una estructura Hashtable para albergar el diccionario de codigos).
    */
    private void codificar(char[] input, Hashtable<Character,String> diccionarioCodigos, String filePathOut, ArbolHuffman arbol){
    	
    	EscritorBinario escritor = new EscritorBinario(filePathOut);
    	
    	// Serializar Arbol de Huffman para recuperarlo posteriormente en la descompresion.
        serializarArbol(arbol,escritor);
        
        // Escribir tambien el numero de bytes del mensaje original (sin comprimir).
        escritor.escribirEntero(input.length);
    	
    	/* TAREA 1.6: Codificar trama y escribir el resultado en el archivo de salida. */
        for(int i = 0; i < input.length; i++) {
        			String codigo = diccionarioCodigos.get(input[i]);
        			for(int j = 0; j < codigo.length(); j++) {
        				if(codigo.charAt(j) == '0') {
                			escritor.escribirBit(false);
        				} else if(codigo.charAt(j) == '1') {
        					escritor.escribirBit(true);
        				}
        		}
        }
    	escritor.cerrarFlujo();
    }
    
   /* 
    * Serializar Arbol de Huffman para recuperarlo posteriormente en la descompresion. Se
    * escribe en la parte inicial del archivo de salida.
    */
    
    private void serializarArbol(ArbolHuffman arbol, EscritorBinario escritor){
    	
    	if (arbol.esHoja()) {
    		escritor.escribirBit(true);
    		escritor.escribirPalabra(arbol.getSimbolo()); //Escribir palabra de 8bits
    		return;
    	}
    	escritor.escribirBit(false);
    	serializarArbol(arbol.getIzquierdo(),escritor);
    	serializarArbol(arbol.getDerecho(),escritor);
    }
    
    /*
    * Se lee el archivo de entrada (filePathIn, a descomprimir) como secuencia de bits 
    * usando LectorBinario, despues se descodifica usando el Arbol final de Huffman y el resultado 
    * se escribe con la clase EscritorBinario en el archivo de salida (filePathOut, descomprimido).
    */
    public void descomprimir(String filePathIn, String filePathOut) {
    
    	LectorBinario lector = new LectorBinario(filePathIn);
    	EscritorBinario escritor = new EscritorBinario(filePathOut);

    	ArbolHuffman arbol = leerArbol(lector);

    	// Numero de bytes a escribir
    	int length = lector.leerEntero();

    	/* TAREA1.7: Decodificacion de la trama codificada. */
    	
    	for (int i = 0; i < length; i++) {
    		ArbolHuffman x = arbol;
    		while (!x.esHoja()) {
    			boolean bit = lector.leerBit();
    			if (bit) x = x.getDerecho();
    			else     x = x.getIzquierdo();
    		}
    		escritor.escribirPalabra(x.getSimbolo());
    	}
    	escritor.cerrarFlujo();
    }
    
    private ArbolHuffman leerArbol(LectorBinario lector) {
    	
    	boolean esHoja = lector.leerBit();
    	if (esHoja) {
    		char simbolo = lector.leerPalabra();
    		return new ArbolHuffman(simbolo, -1, null, null);
    	}
    	else {
    		return new ArbolHuffman('\0', -1, leerArbol(lector), leerArbol(lector));
    	}
    }

	public static void main(String[] args) {
		
		PlantillaCodificacionHuffman huffman = new PlantillaCodificacionHuffman();
		if(args.length==3){ 
			if(args[0].equals("-c")){
				huffman.comprimir(args[1],args[2]);
			}else if (args[0].equals("-d")){
				huffman.descomprimir(args[1], args[2]);
			}
		}
	}

}
