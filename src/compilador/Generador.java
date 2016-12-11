package compilador;

import java.util.ArrayList;

import ast.*;

public class Generador {
	/* Ilustracion de la disposicion de la memoria en
	 * este ambiente de ejecucion para el lenguaje Tiny
	 *
	 * |t1	|<- mp (Maxima posicion de memoria de la TM
	 * |t1	|<- desplazamientoTmp (tope actual)
	 * |free|
	 * |free|
	 * |...	|
	 * |x	|
	 * |y	|<- gp
	 * 
	 * */
	
	
	
	/* desplazamientoTmp es una variable inicializada en 0
	 * y empleada como el desplazamiento de la siguiente localidad
	 * temporal disponible desde la parte superior o tope de la memoria
	 * (la que apunta el registro MP).
	 * 
	 * - Se decrementa (desplazamientoTmp--) despues de cada almacenamiento y
	 * 
	 * - Se incrementa (desplazamientoTmp++) despues de cada eliminacion/carga en 
	 *   otra variable de un valor de la pila.
	 * 
	 * Pudiendose ver como el apuntador hacia el tope de la pila temporal
	 * y las llamadas a la funcion emitirRM corresponden a una inserccion 
	 * y extraccion de esta pila
	 */
	private static int desplazamientoTmp = 0;
	private static TablaSimbolos tablaSimbolos = null;
	private static ArrayList<Integer> localidad_return = new ArrayList<Integer>();
	private static int saltomain;
	private static String ultimoAmbito;
	private static NodoBase pointer;
	
	public static void setTablaSimbolos(TablaSimbolos tabla){
		tablaSimbolos = tabla;
	}
	
	public static void generarCodigoObjeto(NodoBase raiz){
		System.out.println();
		System.out.println();
		System.out.println("------ CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
		System.out.println();
		System.out.println();
		generarPreludioEstandar();
		generar(raiz, true);
		/*Genero el codigo de finalizacion de ejecucion del codigo*/   
		UtGen.emitirComentario("Fin de la ejecucion.");
		UtGen.emitirRO("HALT", 0, 0, 0, "");
		System.out.println();
		System.out.println();
		System.out.println("------ FIN DEL CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
	}
	
	//Funcion principal de generacion de codigo
	//prerequisito: Fijar la tabla de simbolos antes de generar el codigo objeto 
	private static void generar(NodoBase nodo, boolean generarHermanoDerecho){
	if(tablaSimbolos!=null){
		
		if(nodo instanceof NodoPrograma){
	    	NodoFuncion funciones = (NodoFuncion) ((NodoPrograma)nodo).getListaFunciones();
	    	NodoMain  mainf = (NodoMain) ((NodoPrograma)nodo).getMain();
	    	// Si existen funciones se generar
	    	if(funciones != null) generar(funciones, true);
	    	// Si no se genera solo el main
	    	generar(mainf, true);
		}
		else if(nodo instanceof NodoFuncion){
			ultimoAmbito = ((NodoFuncion)nodo).getNombreFuncion();
			// Llamar al generador de funciones
			generarFuncion(nodo);
			
		}
		
		else if(nodo instanceof NodoMain){
			ultimoAmbito = "MAIN";
			generarMain(nodo);
		}
		else if (nodo instanceof NodoCuerpo) { 
			generar(((NodoCuerpo)nodo).getSentencias(), true);
		}
		else if(nodo instanceof NodoCallFunc){
			generarCall(nodo);
		}else if(nodo instanceof NodoReturn){
			generarReturn(nodo);
		}else if (nodo instanceof  NodoIf){
			generarIf(nodo);
		}else if (nodo instanceof  NodoRepeat){
			generarRepeat(nodo);
		}else if(nodo instanceof NodoFor){
			generarFor(nodo);
		}else if (nodo instanceof  NodoWhile){
			generarWhile(nodo);
		}else if (nodo instanceof  NodoDoWhile){
			generarDoWhile(nodo);					
		}else if (nodo instanceof  NodoAsignacion){
			generarAsignacion(nodo);
		}else if (nodo instanceof  NodoLeer){
			generarLeer(nodo);
		}else if (nodo instanceof  NodoEscribir){
			generarEscribir(nodo);
		}else if (nodo instanceof NodoValor){
			generarValor(nodo);
		}else if(nodo instanceof NodoVar) {
			// Este nodo solo identifica las variables
		}else if (nodo instanceof NodoIdentificador){
			generarIdentificador(nodo);
		}else if (nodo instanceof NodoOperacion){
			generarOperacion(nodo);
		}else{
			System.out.println("BUG: Tipo de nodo a generar desconocido");
		}
		/*Si el hijo de extrema izquierda tiene hermano a la derecha lo genero tambien*/
		if(nodo.TieneHermano())
			if(generarHermanoDerecho)
				generar(nodo.getHermanoDerecha(), true);
	}else
		System.out.println("¡¡¡ERROR: por favor fije la tabla de simbolos a usar antes de generar codigo objeto!!!");
}
	

	private static void generarFuncion(NodoBase nodo){
		//aqui debo de poner el Imen a la tabla
			desplazamientoTmp = -40;
			int pos=UtGen.emitirSalto(0);
			tablaSimbolos.setTablaPosFunc(ultimoAmbito, pos);
			NodoFuncion n = (NodoFuncion)nodo;
			if(n.getCuerpoFuncion()!=null)
				generar(n.getCuerpoFuncion(), true);			
			//coloco todos los saltos de los return ya que se donde termina la funcion
			pos=UtGen.emitirSalto(0);
			for	(int i=0; i<localidad_return.size();i++){
				UtGen.cargarRespaldo(localidad_return.get(i));
				UtGen.emitirRM("LDA", UtGen.PC, pos, UtGen.GP, "salto del return");
				UtGen.restaurarRespaldo();
			}
			localidad_return.clear();
			//Salto incondicional a donde quede
			UtGen.emitirRM("LDA", UtGen.PC, 0,UtGen.NL, "Salto incodicional a donde fue llamada la funcion");
			desplazamientoTmp=0;
	}
	
	private static void generarMain(NodoBase nodo){
			//iniciar la ejecucion en la linea #line main
			int pos = UtGen.emitirSalto(0);
			UtGen.cargarRespaldo(saltomain);
			UtGen.emitirRM("LDA", UtGen.PC, pos,UtGen.GP, "Salto incodicional al main");
			UtGen.restaurarRespaldo();
			generar(((NodoMain) nodo).getCuerpo(), true);
			pos=UtGen.emitirSalto(0);
			for	(int i=0; i<localidad_return.size();i++){
				UtGen.cargarRespaldo(localidad_return.get(i));
				UtGen.emitirRM("LDA", UtGen.PC, pos, UtGen.GP, "salto del return");
				UtGen.restaurarRespaldo();
			}
			localidad_return.clear();
	}
	
	private static void generarCall(NodoBase nodo) {
		NodoCallFunc n = (NodoCallFunc)nodo;
		if (n.getArgs()!=null){	
			NodoBase aux = n.getArgs();
			ArrayList<String> argsList = tablaSimbolos.getArgsFuncion(n.getNombreFuncion());
			for(String nom: argsList) {
				generar(aux, false);
				int dirMem = tablaSimbolos.getDireccion(nom,n.getNombreFuncion());
				UtGen.emitirRM("ST", UtGen.AC, dirMem, UtGen.GP, "llamado: guarda el valor del argumento");	
				aux = aux.getHermanoDerecha();
			}
			
		}	
		//Poner en NL la linea actual + 1
		if(UtGen.debug)	UtGen.emitirComentario("-> CALLFUNC");
		UtGen.emitirRM("LDA", UtGen.NL, 1, UtGen.PC, "(AC=Pos actual + 1)");
		
		//saltar a la linea donde empieza la funcion
		int pos = tablaSimbolos.getTablaPosFun(((n.getNombreFuncion())));
		UtGen.emitirRM("LDA", UtGen.PC, pos,UtGen.GP, "Salto a la primera linea de la funcion");
	}
	
	private static void generarReturn(NodoBase nodo){
		if(((NodoReturn)nodo).getExp()!=null)
		       generar(((NodoReturn)nodo).getExp(), true);
		//la setencia anterior deja en AC el valor retornado		
		//Guargo una posicion para saltar a la linea donde termina la funcion
		localidad_return.add(UtGen.emitirSalto(1));

	}

	private static void generarIf(NodoBase nodo){
    	NodoIf n = (NodoIf)nodo;
		int localidadSaltoElse,localidadSaltoEnd,localidadActual;
		if(UtGen.debug)	UtGen.emitirComentario("-> if");
		/*Genero el codigo para la parte de prueba del IF*/
		generar(n.getPrueba(), true);
		localidadSaltoElse = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el else debe estar aqui");
		/*Genero la parte THEN*/
		generar(n.getCuerpoIf(), true);
		localidadSaltoEnd = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el final debe estar aqui");
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoElse);
		UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadActual, "if: jmp hacia else");
		UtGen.restaurarRespaldo();
		/*Genero la parte ELSE*/
		if(n.getCuerpoElse()!=null){
			generar(n.getCuerpoElse(), true);
			localidadActual = UtGen.emitirSalto(0);
			UtGen.cargarRespaldo(localidadSaltoEnd);
			UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadActual, "if: jmp hacia el final");
			UtGen.restaurarRespaldo();
    	}
		
		if(UtGen.debug)	UtGen.emitirComentario("<- if");
	}
	
	private static void generarFor(NodoBase nodo){
		
		int localidadSaltoInicio;
		int localidadSaltoEnd;
		int localidadActual;

		if(UtGen.debug)	UtGen.emitirComentario("-> For"); /* No sirve para nada  */

			generarAsignacion(((NodoFor)nodo).getDeclaracion());
			localidadSaltoInicio = UtGen.emitirSalto(0);
			
			UtGen.emitirComentario("for: el salto hacia el final (luego del cuerpo) del for debe estar aqui");
			generarOperacion(((NodoFor)nodo).getExpresion());
			localidadSaltoEnd = UtGen.emitirSalto(1);
			
			generar(((NodoFor)nodo).getCuerpo(), true);
			generarAsignacion(((NodoFor)nodo).getDeclaracion());
			
			UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadSaltoInicio, "if: vamos hacia el inicio");
			localidadActual = UtGen.emitirSalto(0);
			UtGen.cargarRespaldo(localidadSaltoEnd);
			
			UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadActual, "for: jmp hacia el fin del cuerpo");
			UtGen.restaurarRespaldo();	
	}
	
	private static void generarRepeat(NodoBase nodo){
    	NodoRepeat n = (NodoRepeat)nodo;
		int localidadSaltoInicio;
		if(UtGen.debug)	UtGen.emitirComentario("-> repeat");
			localidadSaltoInicio = UtGen.emitirSalto(0);
			UtGen.emitirComentario("repeat: el salto hacia el final (luego del cuerpo) del repeat debe estar aqui");
			/* Genero el cuerpo del repeat */
			generar(n.getCuerpo(), true);
			/* Genero el codigo de la prueba del repeat */
			generar(n.getPrueba(), true);
			UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadSaltoInicio, "repeat: jmp hacia el inicio del cuerpo");
		if(UtGen.debug)	UtGen.emitirComentario("<- repeat");
	}
	
	private static void generarWhile(NodoBase nodo){ 
    	NodoWhile n = (NodoWhile)nodo;
		int localidadSaltoInicio,localidadSaltoCondicional,localidadActual;
		/* Genero el codigo de la prueba del while */
		if(UtGen.debug)	UtGen.emitirComentario("-> while");
		localidadSaltoInicio = UtGen.emitirSalto(0);
		UtGen.emitirComentario("while: aqui deberia ir el marcado del inicio del while");
		generar(n.getPrueba(), true);
		localidadSaltoCondicional = UtGen.emitirSalto(1);
		if(UtGen.debug)	UtGen.emitirComentario("-> cuerpo while");
		/* Genero el cuerpo del while */
		generar(n.getCuerpo(), true);
		//Salto al Inicio del while
		UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadSaltoInicio, "if: jmp hacia el final");
		
		//Salto si el while es falso (0) salto al fin
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoCondicional);
		UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadActual, "if: jmp hacia fin del while si falso (0)");
		UtGen.restaurarRespaldo();
 
	}
	
	private static void generarDoWhile(NodoBase nodo){ 
    	NodoDoWhile n = (NodoDoWhile)nodo;
		int localidadInicioCiclo;
		/* Genero el codigo de la prueba del while */
		if(UtGen.debug)	UtGen.emitirComentario("-> while");
		localidadInicioCiclo = UtGen.emitirSalto(0);
		UtGen.emitirComentario("while: aqui deberia ir el marcado del inicio del while");
		generar(n.getCuerpo(), true);
		generar(n.getPrueba(), true);
		UtGen.emitirRM_Abs("JNE", UtGen.AC, localidadInicioCiclo, "do while: jmp hacia el inicio");
	}		

	
	
	private static void generarAsignacion(NodoBase nodo){
		NodoAsignacion n = (NodoAsignacion)nodo;
		
		// Si es un puntero lo guardamos como puntero
		if(tablaSimbolos.BuscarSimbolo(n.getIdentificador(), ultimoAmbito).isPointer()) pointer = n;
		else pointer = null;
		
		if(n.isPointer()) {
			int direccion;
			if(UtGen.debug)	UtGen.emitirComentario("-> asignacion tipo puntero: *id = exp");
			/* Genero el codigo para la expresion a la derecha de la asignacion */
			generar(n.getExpresion(), true);
			/* Ahora almaceno el valor resultante  opteniendo la direccion a la que apunto*/
			direccion = tablaSimbolos.BuscarSimbolo(n.getIdentificador(), ultimoAmbito).getApuntador();
			UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "asignacion: almaceno el valor en la direccion que apunta "+n.getIdentificador());
			if(UtGen.debug)	UtGen.emitirComentario("<- asignacion");
			return;
		}
		
		
		int direccion;
		if(UtGen.debug)	UtGen.emitirComentario("-> asignacion");		
		/* Genero el codigo para la expresion a la derecha de la asignacion */
		generar(n.getExpresion(), true);
		/* Ahora almaceno el valor resultante */
		direccion = tablaSimbolos.getDireccion(n.getIdentificador(), ultimoAmbito);
		UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "asignacion: almaceno el valor para el id "+n.getIdentificador());
		if(UtGen.debug)	UtGen.emitirComentario("<- asignacion");
	}
	
	private static void generarLeer(NodoBase nodo){
		NodoLeer n = (NodoLeer)nodo;
		int direccion;
		if(UtGen.debug)	UtGen.emitirComentario("-> leer");
		UtGen.emitirRO("IN", UtGen.AC, 0, 0, "leer: lee un valor entero ");
		direccion = tablaSimbolos.getDireccion(n.getIdentificador(), ultimoAmbito);
		UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "leer: almaceno el valor entero leido en el id "+n.getIdentificador());
		if(UtGen.debug)	UtGen.emitirComentario("<- leer");
	}
	
	private static void generarEscribir(NodoBase nodo){
		NodoEscribir n = (NodoEscribir)nodo;
		if(UtGen.debug)	UtGen.emitirComentario("-> escribir");
		/* Genero el codigo de la expresion que va a ser escrita en pantalla */
		generar(n.getExpresion(), true);
		/* Ahora genero la salida */
		UtGen.emitirRO("OUT", UtGen.AC, 0, 0, "escribir: genero la salida de la expresion");
		if(UtGen.debug)	UtGen.emitirComentario("<- escribir");
	}
	
	private static void generarValor(NodoBase nodo){
    	NodoValor n = (NodoValor)nodo;
    	if(UtGen.debug)	UtGen.emitirComentario("-> constante");
    	UtGen.emitirRM("LDC", UtGen.AC, n.getValor(), 0, "cargar constante: "+n.getValor());
    	if(UtGen.debug)	UtGen.emitirComentario("<- constante");
	}
	
	private static void generarIdentificador(NodoBase nodo){
		NodoIdentificador n = (NodoIdentificador)nodo;
		int direccion;
		
		// Si es una referencia : &ID
		if(n.isReference()) {
			if(UtGen.debug)	UtGen.emitirComentario("-> identificador : &id");
			direccion = tablaSimbolos.getDireccion(n.getNombre(), ultimoAmbito);
			if(pointer != null) tablaSimbolos.BuscarSimbolo(((NodoAsignacion)pointer).getIdentificador(), ultimoAmbito).setApuntador(direccion);
			// Cargo es el valor de la direccion de memoria como una constante
			UtGen.emitirRM("LDC", UtGen.AC, direccion, 0, "cargar direccion de memoria: "+direccion);
			if(UtGen.debug)	UtGen.emitirComentario("-> identificador");
			return;
		}
		
		// Si es un puntero: *ID
		if(n.isPointer()) {
			if(UtGen.debug)	UtGen.emitirComentario("-> identificador : *id");
			direccion = tablaSimbolos.BuscarSimbolo(n.getNombre(), ultimoAmbito).getApuntador();
			// Cargo el valor que se encuentra en la direccion de  memoria a la que estoy apuntando
			UtGen.emitirRM("LD", UtGen.AC, direccion, 0, "cargar valor de la direccion de memoria a la que apunto: "+ direccion);
			if(UtGen.debug)	UtGen.emitirComentario("-> identificador");
			return;
		}
		
		
		
		if(UtGen.debug)	UtGen.emitirComentario("-> identificador");
		direccion = tablaSimbolos.getDireccion(n.getNombre(), ultimoAmbito);
		UtGen.emitirRM("LD", UtGen.AC, direccion, UtGen.GP, "cargar valor de identificador: "+n.getNombre());
		if(UtGen.debug)	UtGen.emitirComentario("-> identificador");
	}

	private static void generarOperacion(NodoBase nodo){
		NodoOperacion n = (NodoOperacion) nodo;
		if(UtGen.debug)	UtGen.emitirComentario("-> Operacion: " + n.getOperacion());
		/* Genero la expresion izquierda de la operacion */
		generar(n.getOpIzquierdo(), true);
		/* Almaceno en la pseudo pila de valor temporales el valor de la operacion izquierda */
		UtGen.emitirRM("ST", UtGen.AC, desplazamientoTmp--, UtGen.MP, "op: push en la pila tmp el resultado expresion izquierda");
		/* Genero la expresion derecha de la operacion */
		generar(n.getOpDerecho(), true);
		/* Ahora cargo/saco de la pila el valor izquierdo */
		UtGen.emitirRM("LD", UtGen.AC1, ++desplazamientoTmp, UtGen.MP, "op: pop o cargo de la pila el valor izquierdo en AC1");
		switch(n.getOperacion()){
			case	mas:	UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC1, UtGen.AC, "op: +");		
							break;
			case	menos:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: -");
							break;
			case	por:	UtGen.emitirRO("MUL", UtGen.AC, UtGen.AC1, UtGen.AC, "op: *");
							break;
			case	entre:	UtGen.emitirRO("DIV", UtGen.AC, UtGen.AC1, UtGen.AC, "op: /");
							break;		
			case	menor:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <");
							UtGen.emitirRM("JLT", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
							break;
			case	igual:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: ==");
							UtGen.emitirRM("JEQ", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC==0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
							break;	
			default:
							UtGen.emitirComentario("BUG: tipo de operacion desconocida");
		}
		if(UtGen.debug)	UtGen.emitirComentario("<- Operacion: " + n.getOperacion());
	}
	
	//TODO: enviar preludio a archivo de salida, obtener antes su nombre
	private static void generarPreludioEstandar(){
		UtGen.emitirComentario("Compilacion MINIC para el codigo objeto TM");
		UtGen.emitirComentario("Archivo: "+ "NOMBRE_ARREGLAR");
		/*Genero inicializaciones del preludio estandar*/
		/*Todos los registros en tiny comienzan en cero*/
		UtGen.emitirComentario("Preludio estandar:");
		UtGen.emitirRM("LD", UtGen.MP, 0, UtGen.AC, "cargar la maxima direccion desde la localidad 0");
		UtGen.emitirRM("ST", UtGen.AC, 0, UtGen.AC, "limpio el registro de la localidad 0");
		
		saltomain = UtGen.emitirSalto(1);
	}

}
