import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.PriorityQueue;

import entrada_salida.EscritorBinario;
import entrada_salida.LectorBinario;
import estructuras_datos.ArbolHuffman;

/********************************************************************************************
 *  Ejecución: 
 *  	% Comprimir:    java PlantillaCodificacionHuffman -c filePathIn filePathOut
 *      % Decomprimir:  java PlantillaCodificacionHuffman -d filePathIn filePathOut
 *  
 *  Utilidad: Permite la compresión/descompresión usando el algoritmo de Huffman
 *  de un archivo de entrada hacia un archivo de salida. 
 *  
 *
 *********************************************************************************************/

public class PlantillaCodificacionHuffman {
	
	private Hashtable<Character,String> diccionarioCodigos = new Hashtable<Character,String>();;

	// Constructor
	private PlantillaCodificacionHuffman(){
		
	}
	
	/*
	* Se lee el archivo de entrada (filePathIn, a comprimir) como secuencia de palabras de 8 bits 
	* usando LectorBinario, después se codifica con el algoritmo de Huffman y el resultado 
	* se escribe usando la clase EscritorBinario hacia el archivo de salida (filePathOut, comprimido).
	*/
    public void comprimir(String filePathIn, String filePathOut) {
		
    	LectorBinario lector = new LectorBinario(filePathIn);
		// Leer archivo de entrada y almacenar en una cadena
		StringBuilder sb = new StringBuilder();
		while (!lector.esVacio()) {
			char b = lector.leerPalabra();
			sb.append(b); 	// OJO! leerPalabra() devuelve una palabra 
							// de 8 bits y el tipo char es de 16 bits
		}
		char[] input = sb.toString().toCharArray();

		///////////////////////TAREA1.1///////////////////////
		// Generar tabla de frecuencias (freq) a partir del array de tipo char input.
		
		//////////////////////////////////////////////////////
		Hashtable<Character, Integer> freq = new Hashtable<Character, Integer>();
		for(int i = 0; i < input.length; i++) {
			if(freq.containsKey(input[i])) {
				Integer frecuencia = freq.get(input[i]);
				freq.replace(input[i], ++frecuencia);
			} else {
				freq.put(input[i], 1);
			}
		}
		// Construir árbol de Huffman.
        ArbolHuffman arbol = construirArbol(freq); 
		
		// Construir diccionario de búsqueda -> Pares (símbolo,código).
		// diccionarioCodigos será una estructura de tipo Map, Hashtable, String[], ...,
		// dependiendo de la implementación elegida.
        construirCodigos(diccionarioCodigos,arbol,"");
		
		// Codificar la trama (char[]input) usando el diccionario de códigos.
        codificar(input,diccionarioCodigos,filePathOut,arbol);
	}
	
   /* 
    * Construir arbol de Huffman a partir de la tabla de frecuencias.
    * (Si se ha usado un arreglo int[] para albergar la tabla de frecuencias).
    */
    private ArbolHuffman construirArbol(int[] freq) {    
    	
        ///////////////////////TAREA1.2///////////////////////
        // Instanciar cola de prioridad (de tipo TreeSet, PriorityQueue o una 
    	// implementación propia).
    
        //////////////////////////////////////////////////////
    		
    	
    	///////////////////////TAREA1.3///////////////////////
        // Inicializar la cola de prioridad con árboles simples (nodos hoja) para 
    	// cada símbolo de la tabla de frecuencias. Usar la estructura de datos 
    	// de tipo arbol binario que se facilita en los recursos de la práctica
    	// (ArbolHuffman.java).
    	
        //////////////////////////////////////////////////////
    	
    	
    	///////////////////////TAREA1.4///////////////////////
    	// Construir el arbol de Huffman final/completo de manera iterativa 
    	// retirando de la cola de prioridad el par de nodos con menor frecuencia.
    	
        //////////////////////////////////////////////////////  
    	
    	// Sustituir este objeto retornando el árbol de Huffman final 
    	// construido en la TAREA1.4
    	return new ArbolHuffman(); 
    }
    
   /* 
    * Construir arbol de Huffman a partir de la tabla de frecuencias.
    * (Si se ha usado una estructura Map para albergar la tabla de frecuencias).
    */
 	private ArbolHuffman construirArbol(Hashtable<Character,Integer> freq) {
 		
 		///////////////////////TAREA1.2///////////////////////
       
        //////////////////////////////////////////////////////
 		PriorityQueue<ArbolHuffman> arboles = new PriorityQueue<ArbolHuffman>();
 		
    	///////////////////////TAREA1.3///////////////////////
       
        //////////////////////////////////////////////////////
 		Enumeration<Character> keys = freq.keys();
 		while(keys.hasMoreElements()) {
 			char key = keys.nextElement().charValue();
 			ArbolHuffman arbol = new ArbolHuffman(key, freq.get(key), null, null);
 			arboles.add(arbol);
 			//System.out.println(arbol.toString());
 		}
    	///////////////////////TAREA1.4///////////////////////
    	
        //////////////////////////////////////////////////////  
 		while(arboles.size() != 1) {
 			ArbolHuffman subarbol1 = arboles.poll();
 			ArbolHuffman subarbol2 = arboles.poll();
 			ArbolHuffman arbolete = new ArbolHuffman('\0', subarbol1.getFrecuencia() + subarbol2.getFrecuencia(), subarbol1, subarbol2);
 			arboles.add(arbolete);
 		}
    	
 		// Sustituir este objeto retornando el árbol de Huffman final 
 		// construido en la TAREA1.4
     	return arboles.poll(); 
 	}
 	
   /* 
    * Construir diccionario de búsqueda -> Pares (símbolo,código).
    * (Si se usa un arreglo String[] para albergar el diccionario de códigos).
    */
    private void construirCodigos(String [] diccionarioCodigos, ArbolHuffman arbol,String codigoCamino){
    	
    	///////////////////////TAREA1.5///////////////////////
        // Para hacer la codificación más rápida, construir un diccionario de búsqueda 
        // (String[], Map, Hashtable, ...) que permita obtener la codificación binaria  
        // de cada uno de los símbolos. Construir dicho diccionario/tabla requerirá recorrer 
        // el árbol de Huffman generado en la TAREA1.4. 
        // Para obtener la máxima calificación en esta tarea la tabla debe construirse 
        // recorriendo al árbol una sola vez.

        //////////////////////////////////////////////////////
    }
    
   /* 
    * Construir diccionario de búsqueda -> Pares (símbolo,código).
    * (Si se usa una estructura Map para albergar el diccionario de códigos).
    */
    private void construirCodigos(Hashtable<Character,String> diccionarioCodigos, ArbolHuffman arbol,String codigoCamino){
    	
    	///////////////////////TAREA1.5///////////////////////

        //////////////////////////////////////////////////////
    		if(!arbol.esHoja()) {
    			if(arbol.getIzquierdo() != null) {
    				codigoCamino += '0';
    				construirCodigos(diccionarioCodigos, arbol.getIzquierdo(), codigoCamino);
    			}
    			if(arbol.getDerecho() != null) {
    				codigoCamino += '1';
    				construirCodigos(diccionarioCodigos, arbol.getDerecho(), codigoCamino);    				
    			}
    		} else {
        		diccionarioCodigos.put(arbol.getSimbolo(), codigoCamino);
    		}
    }
    
   /* 
    * Codificar la trama (char[]input) usando el diccionario de códigos y escribirla en el
    * archivo de salida cuyo path (String filePathOut) se facilita como argumento.
    * (Si se usa un arreglo String[] para albergar el diccionario de códigos).
    */
    private void codificar(char[] input, String [] diccionarioCodigos, String filePathOut, ArbolHuffman arbol){
    	
    	EscritorBinario escritor = new EscritorBinario(filePathOut);
    	
    	// Serializar árbol de Huffman para recuperarlo posteriormente en la descompresión.
        serializarArbol(arbol,escritor);
        
        // Escribir también el número de bytes del mensaje original (sin comprimir).
        escritor.escribirEntero(input.length);
    	
    	///////////////////////TAREA1.6///////////////////////
        // Codificación usando el diccionario de códigos y escritura en el archivo de salida. 
    	
        //////////////////////////////////////////////////////
        
    	escritor.cerrarFlujo();
    }
    
   /* 
    * Codificar la trama (char[]input) usando el diccionario de códigos y escribirla en el
    * archivo de salida cuyo path (String filePathOut) se facilita como argumento.
    * (Si se usa una estructura Map para albergar el diccionario de códigos).
    */
    private void codificar(char[] input, Hashtable<Character,String> diccionarioCodigos, String filePathOut, ArbolHuffman arbol){
    	
    	EscritorBinario escritor = new EscritorBinario(filePathOut);
    	
    	// Serializar árbol de Huffman para recuperarlo posteriormente en la descompresión.
        serializarArbol(arbol,escritor);
        
        // Escribir también el número de bytes del mensaje original (sin comprimir).
        escritor.escribirEntero(input.length);
    	
    	///////////////////////TAREA1.6///////////////////////
        // Codificación usando el diccionario de códigos y escritura en el archivo de salida. 
        
        //////////////////////////////////////////////////////
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
    * Serializar árbol de Huffman para recuperarlo posteriormente en la descompresión. Se
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
    * usando LectorBinario, después se descodifica usando el árbol final de Huffman y el resultado 
    * se escribe con la clase EscritorBinario en el archivo de salida (filePathOut, descomprimido).
    */
    public void descomprimir(String filePathIn, String filePathOut) {
    
    	LectorBinario lector = new LectorBinario(filePathIn);
    	EscritorBinario escritor = new EscritorBinario(filePathOut);

    	ArbolHuffman arbol = leerArbol(lector);

    	// Númerod e bytes a escribir
    	int length = lector.leerEntero();

    	///////////////////////TAREA1.7///////////////////////
    	// Decodificar usando el árbol de Huffman.
    	for (int i = 0; i < length; i++) {
    		ArbolHuffman x = arbol;
    		while (!x.esHoja()) {
    			boolean bit = lector.leerBit();
    			if (bit) x = x.getDerecho();
    			else     x = x.getIzquierdo();
    		}
    		escritor.escribirPalabra(x.getSimbolo());
    	}
    	//////////////////////////////////////////////////////

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
		if(args.length==3){ // Control de argumentos mejorable!!
			if(args[0].equals("-c")){
				huffman.comprimir(args[1],args[2]);
			}else if (args[0].equals("-d")){
				huffman.descomprimir(args[1], args[2]);
			}
		}
	}

}
