package compilador;
import ast.*;

public class Semantico {

	private TablaSimbolos tablaSimbolos;
	private String ultimoAmbito;	
	public boolean debug = true;
	private boolean anyError = false;
	
	public Semantico(TablaSimbolos tablaSimbolos) {
		super();
		this.tablaSimbolos = tablaSimbolos;
	}
	
	public void recorrido(NodoBase raiz){
		while(raiz != null){
			
			/* Recorro el Arbol de manera recursiva*/ 
			if(raiz instanceof NodoPrograma){
				if(((NodoPrograma)raiz).hasListaFunciones()){
					recorrido(((NodoPrograma)raiz).getListaFunciones());
					recorrido(((NodoPrograma)raiz).getMain());
				}else{
					recorrido(((NodoPrograma)raiz).getMain());			
				}
			}//NodoPrograma		
			
			else if(raiz instanceof NodoMain){
				ultimoAmbito = "MAIN";
				recorrido(((NodoMain)raiz).getCuerpo()); 		
			}
			
			else if(raiz instanceof NodoCuerpo) {
				recorrido(((NodoCuerpo)raiz).getSentencias());
			}
		
			else if(raiz instanceof NodoFuncion){
				ultimoAmbito = ((NodoFuncion)raiz).getNombreFuncion();
				recorrido(((NodoFuncion)raiz).getArgs());
				recorrido(((NodoFuncion)raiz).getCuerpoFuncion());						
			}
			
			else if(raiz instanceof NodoIf){
				comprobarIf(raiz);
			}
			
			else if(raiz instanceof NodoWhile){
				comprobarWhile(raiz);
			}
			
			else if(raiz instanceof NodoDoWhile){
				comprobarDoWhile(raiz);
			}
			
			else if(raiz instanceof NodoLeer) {
				comprobarLectura(raiz);
			}
			
			else if(raiz instanceof NodoEscribir) {
				comprobarEscritura(raiz);
			}
			
			else if(raiz instanceof NodoFor){
				
				this.anyError = !comprobarAsignacion(((NodoFor) raiz).getDeclaracion());
				
				if(((NodoFor) raiz).getExpresion() instanceof NodoOperacion){
					if(comprobarTipoOperacion(((NodoFor) raiz).getExpresion()) != "Boolean"){
						System.out.println("Error, la expresión no es booleana");
						this.anyError = true;
					}
				}				
				
			}
			
			else if(raiz instanceof NodoAsignacion){
				this.anyError = !comprobarAsignacion(raiz);
			}
			
			else if(raiz instanceof NodoCallFunc){
				NodoCallFunc fun = (NodoCallFunc)raiz;
				if(this.tablaSimbolos.BuscarFuncion(fun.getNombreFuncion()) == null){
					System.out.println("La funcion " + fun.getNombreFuncion()+ " no de encuentra definida");
					this.anyError = true;
					// Asignar error al semantico
				}
			}
			
			raiz = raiz.getHermanoDerecha();
		}//Fin While
	}//Fin Recorrido

	private void comprobarIf(NodoBase nodo){
		if(comprobarTipoOperacion(((NodoIf)nodo).getPrueba()) != "Boolean"){
			System.out.println("La operación no devuelve un valor booleano");
			this.anyError = true;
		}
		recorrido(((NodoIf)nodo).getCuerpoIf());
		if(((NodoIf)nodo).hasElse()){
			recorrido(((NodoIf)nodo).getCuerpoElse());
		}
	}
	
	private void comprobarWhile(NodoBase nodo){
		recorrido(((NodoWhile)nodo).getPrueba());
		if(comprobarTipoOperacion(((NodoWhile)nodo).getPrueba()) != "Boolean"){
			System.out.println("La operación no devuelve un valor booleano");
			this.anyError = true;
		}
		recorrido(((NodoWhile)nodo).getCuerpo());
	}
	
	private void comprobarDoWhile(NodoBase nodo){
		recorrido(((NodoDoWhile)nodo).getCuerpo());
		recorrido(((NodoDoWhile)nodo).getPrueba());
		
		if(comprobarTipoOperacion(((NodoDoWhile)nodo).getPrueba()) != "Boolean"){
			System.out.println("La operación no devuelve un valor booleano");
			this.anyError = true;
		}	
	}
	
	private boolean comprobarAsignacion(NodoBase nodo){
		NodoAsignacion nodoasig = (NodoAsignacion) nodo;
		String identificador = nodoasig.getIdentificador();
		RegistroSimbolo simboloasig = this.tablaSimbolos.BuscarSimbolo(identificador, ultimoAmbito);
		
		/* si la variable existe */
		if(verificarExistencia(identificador, ultimoAmbito)){
			if(nodoasig.getExpresion() instanceof NodoOperacion) {
				if (comprobarTipoOperacion(nodoasig.getExpresion()) == "Error") {
					System.out.println("Los tipos de la operacion son diferentes");
					return false;
				} else {
					return true;
				}
			}
			else if(nodoasig.getExpresion() instanceof NodoCallFunc){
				String funcName = ((NodoCallFunc)nodoasig.getExpresion()).getNombreFuncion();
				RegistroSimbolo func = this.tablaSimbolos.BuscarFuncion(funcName);
				if(func != null) {
					if(func.getTipo() == simboloasig.getTipo()) {
						return true;
					} else {
						System.out.println("El tipo de " + simboloasig.getIdentificador() + " el tipo de " + funcName + " no son el mismo");
						return false;
					}
				} else {
					System.out.println("La funcion no esta definida");
					return false;
				}
			}
			else if(nodoasig.getExpresion() instanceof NodoIdentificador) {
				RegistroSimbolo simboloder = this.tablaSimbolos.BuscarSimbolo(((NodoIdentificador)nodoasig.getExpresion()).getNombre(), ultimoAmbito);
				if(simboloder == null) {
					System.out.println("La variable " + ((NodoIdentificador)nodoasig.getExpresion()).getNombre()  + " no esta definida");
					return false;
				}
				
				if(simboloasig.getTipo() == simboloder.getTipo()) {
					return true;
				} else {
					System.out.println("Los tipo " +simboloasig.getIdentificador()  + "  ," + simboloder.getIdentificador() + " son incopatibles");
					return false;
				}
			}
			
			else if(nodoasig.getExpresion() instanceof NodoValor) {
				String tipo = "Integer";
				if(((NodoValor)nodoasig.getExpresion()).getTipo() == 1) tipo = "Boolean";
				
				
				if(simboloasig.getTipo() == tipo) {
					return true;
				} else {
					System.out.println("Los tipos de datos no son captibles:" + simboloasig.getTipo() + " " + tipo );
					return false;
				}
			}
			
			
			return true;
		}else{
			System.out.print("El Identificador " + identificador + " no ha sido definido");
			return false;
		}
	}
	
	private String comprobarTipoOperacion (NodoBase nodo) {
		if (nodo instanceof NodoOperacion) {
			
			String tipoOperadorHI = comprobarTipoOperacion(((NodoOperacion)nodo).getOpIzquierdo());
			String tipoOperadorHD = comprobarTipoOperacion(((NodoOperacion)nodo).getOpDerecho());
			tipoOp operador = ((NodoOperacion)nodo).getOperacion();
			
			if(operador == tipoOp.and || operador == tipoOp.or){
				if(tipoOperadorHI == "Boolean" && tipoOperadorHD == "Boolean"){
					return "Boolean";
				} else {
					return "Error";
				}
			}
			
			else if (operador == tipoOp.igual || operador == tipoOp.diferente) {
				if(tipoOperadorHD == "Boolean" && tipoOperadorHI == "Boolean") {
					return "Boolean";
				} else if (tipoOperadorHI == "Integer" && tipoOperadorHD == "Integer") {
					return "Boolean";
				}
				else {
					return "Error";
				}
			}
			
			else if (  operador == tipoOp.mas 
					|| operador == tipoOp.menos 
					|| operador == tipoOp.entre
					|| operador == tipoOp.por ) {
				
					if(tipoOperadorHI == "Integer" && tipoOperadorHD == "Integer" ) {
						return "Integer";
					} else {
						return "Error";
					}
				
			} else {
				if(tipoOperadorHI == "Integer" && tipoOperadorHD == "Integer") {
					return "Boolean";
				} else {
					return "Error";
				}
			}
		}
		
		else if (nodo instanceof NodoIdentificador){
			String nombre = ((NodoIdentificador)nodo).getNombre();
			if(verificarExistencia(nombre,ultimoAmbito)){
				String tipo = this.tablaSimbolos.BuscarSimbolo(nombre, ultimoAmbito).getTipo();
				return tipo;
			}
			System.out.println("Error la variable " + nombre + " no esta definida");
			return "Error";
		}
		
		else if (nodo instanceof NodoValor) {
			
			Integer tipo = ((NodoValor)nodo).getTipo();
			
			if(tipo == 0)
				return "Integer";
			if(tipo == 1)
				return "Boolean";	
		}
		
		return "Error";
	}
	
	private void comprobarLectura(NodoBase nodo){
		RegistroSimbolo simbolo = this.tablaSimbolos.BuscarSimbolo(((NodoLeer)nodo).getIdentificador(), ultimoAmbito);
		if(simbolo != null) {

			if(simbolo.getTipo() != "Integer"){
				System.out.println("El parametro de la funcion gets() no es un entero");
				this.anyError = true;
			}
		}else{
			System.out.println("Variable no declarada");
			this.anyError = true;
		}
	}
	
	private void comprobarEscritura(NodoBase nodo){
		NodoEscribir nodoEscribir = (NodoEscribir) nodo;
		
		/*------------- Verificar que la variable que lee es entera -------------*/
		if(nodoEscribir.getExpresion() instanceof NodoIdentificador){
			String identificador = ((NodoIdentificador)nodoEscribir.getExpresion()).getNombre();		
			RegistroSimbolo simbolo = this.tablaSimbolos.BuscarSimbolo(identificador, ultimoAmbito);
			if(simbolo != null){
				if(simbolo.getTipo() != "Integer"){
					System.out.println("El parametro de la funcion puts() no es una variable u operacion entera");
					this.anyError = true;
				}
			}else{
				System.out.println("Variable no declarada");
				this.anyError = true;
			}			
		}
		
		/*------- Verificar que la operacion aritmetica devuelva un enetro -------*/
		else if(nodoEscribir.getExpresion() instanceof NodoOperacion){
			if(comprobarTipoOperacion(nodoEscribir.getExpresion()) != "Integer") {
				System.out.println("La funcion puts acepta solo expresiones enteras");
				this.anyError = true;
			}
		}
		else if(nodoEscribir.getExpresion() instanceof NodoValor){
			NodoValor valor = (NodoValor) nodoEscribir.getExpresion();
			if(valor.getTipo() != 0) {
				System.out.println("La funcion puts acepta solo expresiones enteras");
				this.anyError = true;
			}
		}
	}
	
	public boolean hasErrors(){
		return this.anyError;
	}

	private boolean verificarExistencia(String nombre, String ambito){
		return this.tablaSimbolos.BuscarSimbolo(nombre, ambito) != null;
	}

}
