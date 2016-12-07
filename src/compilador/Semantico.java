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
			
			else if(raiz instanceof NodoAsignacion){
				if(!comprobarAsignacion(raiz)){
					System.out.println("Variable no declarada");
				}
			}
			
			else if(raiz instanceof NodoCallFunc){	
			}
			
			raiz = raiz.getHermanoDerecha();
		}//Fin While
	}//Fin Recorrido

	private void comprobarIf(NodoBase nodo){
		System.out.println("Entra");
		if(verificarTipo(((NodoIf)nodo).getPrueba()) != "Boolean"){
			System.out.println("La operación no devuelve un valor booleano");
		}
		recorrido(((NodoIf)nodo).getCuerpoIf());
		if(((NodoIf)nodo).hasElse()){
			recorrido(((NodoIf)nodo).getCuerpoElse());
		}
	}
	
	private void comprobarWhile(NodoBase nodo){
		recorrido(((NodoWhile)nodo).getPrueba());
		if(verificarTipo(((NodoWhile)nodo).getPrueba()) != "Boolean"){
			System.out.println("La operación no devuelve un valor booleano");
		}
		recorrido(((NodoWhile)nodo).getCuerpo());
	}
	
	private void comprobarDoWhile(NodoBase nodo){
		recorrido(((NodoDoWhile)nodo).getCuerpo());
		recorrido(((NodoDoWhile)nodo).getPrueba());
		
		if(verificarTipo(((NodoDoWhile)nodo).getPrueba()) != "Boolean"){
			System.out.println("La operación no devuelve un valor booleano");
		}	
	}
	
	private boolean comprobarAsignacion(NodoBase nodo){
		
		String identificador = ((NodoAsignacion)nodo).getIdentificador();
		
		/* Verifico si la variable existe*/
		if(!verificarExistencia(identificador, ultimoAmbito)){
			return true;
		}else{
			return false;
		}
	}
	
	private String verificarTipo (NodoBase nodo) {
		if (nodo instanceof NodoOperacion) {
			
			String tipoOperadorHI = verificarTipo(((NodoOperacion)nodo).getOpIzquierdo());
			String tipoOperadorHD = verificarTipo(((NodoOperacion)nodo).getOpDerecho());
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
		
		/*else if (nodo instanceof NodoIdentificador){
			String nombre = ((NodoIdentificador)nodo).getNombre();
			if(verificarExistencia(nombre)){
				String tipo = this.tablaSimbolos.getTipo(nombre);
				return tipo;
			}
			return "Error";
		}*/
		
		else if (nodo instanceof NodoValor) {
			
			Integer tipo = ((NodoValor)nodo).getTipo();
			
			if(tipo == 0)
				return "Integer";
			if(tipo == 1)
				return "Boolean";	
		}
		
		return "Error";
	}

	private boolean verificarExistencia(String nombre, String ambito){
		return this.tablaSimbolos.BuscarSimbolo(nombre, ambito) != null;
	}

}
