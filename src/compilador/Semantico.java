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
					if(((NodoPrograma)raiz).hasListaFunciones()){
						System.out.println("Error: Toda Función debe ser declarada antes del main");
						break;
					}					
				}
			}//NodoPrograma		
			
			if(raiz instanceof NodoMain){
				recorrido(((NodoMain)raiz).getCuerpo()); 				
			}
			else if(raiz instanceof NodoIf){
				comprobarIf(raiz);
			}
		}//Fin While
	}//Fin Recorrido

	private void comprobarIf(NodoBase nodo){
		
		if(verificarTipo(((NodoIf)nodo).getPrueba()) != "Boolean"){
			System.out.println("La operación no devuelve un valor booleano");
		}
		
		recorrido(((NodoIf)nodo).getCuerpoIf());
		if(((NodoIf)nodo).hasElse()){
			recorrido(((NodoIf)nodo).getCuerpoElse());
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
					return "typeError";
				}
			}
			
			else if (operador == tipoOp.igual || operador == tipoOp.diferente) {
				if(tipoOperadorHD == "Boolean" && tipoOperadorHI == "Boolean") {
					return "Boolean";
				} else if (tipoOperadorHI == "Integer" && tipoOperadorHD == "Integer") {
					return "Boolean";
				}
				else {
					return "typeError";
				}
			}
			
			else if (  operador == tipoOp.mas 
					|| operador == tipoOp.menos 
					|| operador == tipoOp.entre
					|| operador == tipoOp.por ) {
				
					if(tipoOperadorHI == "Integer" && tipoOperadorHD == "Integer" ) {
						return "Integer";
					} else {
						return "typeError";
					}
				
			} else {
				if(tipoOperadorHI == "Integer" && tipoOperadorHD == "Integer") {
					return "Boolean";
				} else {
					return "typeError";
				}
			}
		}
		
		/*else if (nodo instanceof NodoIdentificador){
			String nombre = ((NodoIdentificador)nodo).getNombre();
			if(verificarExistencia(nombre)){
				String tipo = this.tablaSimbolos.getTipo(nombre);
				return tipo;
			}
			return "typeError";
		}*/
		
		else if (nodo instanceof NodoValor) {
			
			Integer tipo = ((NodoValor)nodo).getTipo();
			
			if(tipo == 0)
				return "Integer";
			if(tipo == 1)
				return "Boolean";	
		}
		
		return "typeError";
	}
		
	/*
	private boolean verificarExistencia(String nombre){
		return this.tablaSimbolos.BuscarVariable(nombre);
	}*/

}
