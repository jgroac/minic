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
			}		
		}
	}//Fin Recorrido

}
