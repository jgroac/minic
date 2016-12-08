package compilador;


import java.util.*;


import ast.NodoAsignacion;
import ast.NodoBase;
import ast.NodoCuerpo;
import ast.NodoEscribir;
import ast.NodoFuncion;
import ast.NodoIdentificador;
import ast.NodoIf;
import ast.NodoOperacion;
import ast.NodoPrograma;
import ast.NodoVar;
import ast.NodoWhile;
import java_cup.internal_error;
import ast.NodoDoWhile;
import ast.NodoLeer;
import ast.NodoMain;
import ast.NodoFor;


public class TablaSimbolos {
	private HashMap <String, HashMap<String, RegistroSimbolo>> tabla;
	private HashMap <String, String> tablaFunciones;
	
	/* Argumentos que recibe una funcion */
	public ArrayList<String> listaArgumentos;
	private HashMap <String, ArrayList<String>> tablaDeArgumentos;
	
	
	private HashMap <String, String> tablaTipo;
	String tipoVar;
	String ultimoAmbito;
	public boolean error = false;
	
	private int direccion;  //Contador de las localidades de memoria asignadas a la tabla
	
	public TablaSimbolos() {
		super();
		tabla = new HashMap<String, HashMap<String, RegistroSimbolo>>();
		direccion=0;
		tablaFunciones = new HashMap <String, String>();
		tablaDeArgumentos = new HashMap <String, ArrayList<String>>();
		tablaTipo = new HashMap<String,String>();
		direccion=0;
	}

	public void cargarTabla(NodoBase raiz){
		
		while (raiz != null) {
			
		    if (raiz instanceof NodoPrograma){
		    	cargarTabla(((NodoPrograma)raiz).getListaFunciones());
		    	cargarTabla(((NodoPrograma)raiz).getMain());
		    }
		    
		    if (raiz instanceof NodoFuncion){
		    	NodoFuncion func = (NodoFuncion) raiz;
		    	String nombreFuncion = func.getNombreFuncion();
		    	if(!existeAmbito(nombreFuncion)) {
		    		ultimoAmbito = nombreFuncion;
		    		tablaTipo.put(ultimoAmbito, func.getTipoFuncion());
		    		tabla.put(ultimoAmbito, new HashMap<String, RegistroSimbolo>());
		    		tablaFunciones.put(nombreFuncion, func.getTipoFuncion());
		    		if(func.getArgs() != null) {
		    			listaArgumentos = new ArrayList<String>();
		    			cargarAgumentos(func.getArgs());
		    			tablaDeArgumentos.put(ultimoAmbito, listaArgumentos);
		    		}
		    		
		    		cargarTabla(func.getCuerpoFuncion());
		    		
		    	} else {
		    		printError("La funcion: " + func.getNombreFuncion() + " ya esta definida");
		    	}
		    	
		    }
		    
		    if (raiz instanceof NodoMain){
		    	ultimoAmbito = "MAIN";
	    		tabla.put(ultimoAmbito, new HashMap<String, RegistroSimbolo>());
	    		cargarTabla(((NodoMain)raiz).getCuerpo());
	    		
		    }
		    
		    if(raiz instanceof NodoCuerpo) {
		    	cargarTabla(((NodoCuerpo)raiz).getSentencias());
		    }
		    
		    if (raiz instanceof NodoVar){
		    	HashMap<String, RegistroSimbolo> tablaAmbito = tabla.get(ultimoAmbito);
		    	NodoVar nodovar = ((NodoVar)raiz);
		    	String nombreVar = nodovar.getIdentificador();
		    	tipoVar = nodovar.getTipo();  
		    	InsertarSimbolo(nombreVar, tipoVar, 0, ultimoAmbito);
		    	//TODO: Añadir el numero de linea y localidad de memoria correcta
		    }
		    
		    
		    
		    if(raiz instanceof NodoFor){
		    	cargarTabla(((NodoFor)raiz).getListaDeclaracion());
		    	cargarTabla(((NodoFor)raiz).getExpresion());
		    	cargarTabla(((NodoFor)raiz).getDeclaracion());
		    	cargarTabla(((NodoFor)raiz).getCuerpo()); 
		    }
		    
	
		    /* Hago el recorrido recursivo */
		    if (raiz instanceof  NodoIf){
		    	cargarTabla(((NodoIf)raiz).getPrueba());
		    	cargarTabla(((NodoIf)raiz).getCuerpoIf());
		    	if(((NodoIf)raiz).hasElse()){
		    		cargarTabla(((NodoIf)raiz).getCuerpoElse());
		    	}
		    }
		    else if (raiz instanceof  NodoAsignacion)
		    {
		    	cargarTabla(((NodoAsignacion)raiz).getExpresion());
		    }
		    
		    else if (raiz instanceof  NodoEscribir)
		    	cargarTabla(((NodoEscribir)raiz).getExpresion());

		    else if (raiz instanceof NodoOperacion){
		    	cargarTabla(((NodoOperacion)raiz).getOpIzquierdo());
		    	cargarTabla(((NodoOperacion)raiz).getOpDerecho());
		    }
		    
		    else if (raiz instanceof  NodoWhile)
		    {
		    	cargarTabla(((NodoWhile)raiz).getPrueba());
		    	cargarTabla(((NodoWhile)raiz).getCuerpo());
		    }
		    
		    else if (raiz instanceof  NodoDoWhile)
		    {
		    	cargarTabla(((NodoDoWhile)raiz).getCuerpo());
		    	cargarTabla(((NodoDoWhile)raiz).getPrueba());
		    }
	
		    raiz = raiz.getHermanoDerecha();
	  }
	}
	
	//true es nuevo no existe se insertara, false ya existe NO se vuelve a insertar 
	public boolean InsertarSimbolo(String identificador, String tipo, int numLinea, String ambito){
		HashMap<String, RegistroSimbolo> tablaAmbito = tabla.get(ambito);
		
		// Si el ambito no existe mostrar error
		if(tablaAmbito == null) {
			printError("No fue posible obtener el ambito" + ambito);
			return false;
		}
			
		// Si la variable ya fue declarada lanzar error;	
		if(!tablaAmbito.containsKey(identificador)) {
			RegistroSimbolo simbolo = new RegistroSimbolo(identificador, tipo, numLinea, direccion++, ambito);
			tablaAmbito.put(identificador, simbolo);
			return true;
		}
		System.out.println("Errorr ---------------------------------------- *****");
		printError("La variable " + identificador + " ya esta definida en el ambito " + ultimoAmbito);
		return false;
	}
	
	public RegistroSimbolo BuscarSimbolo(String identificador, String ambito){
		HashMap<String, RegistroSimbolo> tablaAmbito = tabla.get(ambito);
		RegistroSimbolo simbolo=(RegistroSimbolo) tablaAmbito.get(identificador);
		return simbolo;
	}
	
	public RegistroSimbolo BuscarFuncion(String identificador){
		if(tablaFunciones.containsKey(identificador)){
			String tipo = tablaFunciones.get(identificador);
			return new RegistroSimbolo(identificador, tipo, 0, 0, "Programa");
		}
		return null;
	}
	
	public void ImprimirClaves(){
		System.out.println("*** Tabla de Simbolos ***");
		for( Iterator <String>it = tabla.keySet().iterator(); it.hasNext();) { 
            String s = (String)it.next();
            System.out.println("Consegui Key: " + s );
            HashMap<String, RegistroSimbolo> tablaAmbito = tabla.get(s);
            for( Iterator <String>it2 = tablaAmbito.keySet().iterator(); it2.hasNext();) { 
            	String s2 = (String)it2.next();
            	 System.out.print("  ");
            	 System.out.println("Consegui Key: "+s2+" con direccion: " + BuscarSimbolo(s2, s).getDireccionMemoria());
            }
		}
	}

	public int getDireccion(String Clave, String ambito){
		return BuscarSimbolo(Clave, ambito).getDireccionMemoria();
	}
	
	private void cargarAgumentos(NodoBase nodo){
    	NodoVar args = (NodoVar) nodo;
    	InsertarSimbolo(args.getIdentificador(), args.getTipo(), 0, ultimoAmbito);
    
		if (args.TieneHermano()) cargarAgumentos(((NodoVar)nodo).getHermanoDerecha());
	}
	
	public void cargarIdentificadores(NodoVar identificador){
    	
    	//if(!InsertarSimbolo(identificador.getIdentificador(), 1, direccion++, ultimoAmbito))
    		//printError("Variable " +identificador.getNombre()+" ya ha sido declarada en el ambito " + ultimoAmbito);
    	//if(identificador.TieneHermano()) // Compruebo que el identificador tenga hermanos 
    		//cargarIdentificadores((NodoIdentificador)identificador.getSiguiente());	  	
	}
	
	private void printError(Object chain){		
		System.err.println("[Error Semantico]: " + chain);
		error = true;
	}
	
	public boolean existeAmbito(String ambito) {
		return tabla.get(ambito) != null;
	}
	
	/*
	 * TODO:
	 * 1. Crear lista con las lineas de codigo donde la variable es usada.
	 * */
}
