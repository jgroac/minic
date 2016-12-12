package ast;

import java.util.ArrayList;

public class NodoPrograma extends NodoBase {
	private NodoBase lista_funciones;
	private NodoBase mainf;
	
	public NodoPrograma(NodoBase lista_funciones, NodoBase mainf){
		this.lista_funciones = lista_funciones;
		this.mainf = mainf;
	}
	
	public NodoPrograma(NodoBase mainf ) {
		this.mainf = mainf;
		this.lista_funciones = null;
	}
	
	
	/* Determina si un NodoPrograma posee o no funciones
	 *  Si tiene una lista de funciones retorna true
	 *  Si el programa no posee funciones retorna false
	 * */
	public boolean hasListaFunciones() {
		if(this.lista_funciones != null ) return true;
		
		return false;
	}
	
	
	public void setMain(NodoBase mainf) {
		this.mainf = mainf;
	}
	
	public void setListaFunciones(NodoBase lista_funciones) {
		this.lista_funciones = lista_funciones;
	}
	
	
	public NodoBase getMain() {
		return this.mainf;
	}
	
	public NodoBase getListaFunciones() {
		return this.lista_funciones;
	}
	
	public void organizarFunciones() {
		ArrayList<NodoFuncion> funciones = new ArrayList<NodoFuncion>();
		NodoFuncion funcion = (NodoFuncion) this.lista_funciones;
		// Se agregan todas las funciones al arrayList
		while(funcion != null) {
			funciones.add(funcion);
			funcion = (NodoFuncion) funcion.getHermanoDerecha();
		}
		
		
		// Se recorre el arraylist en sentido inverso para organizar las funciones
		for(int i = funciones.size() - 1; i > 0 ; i--){
			funcion = funciones.get(i);
			funcion.setHermanoDerecha(funciones.get( i - 1));
		}
		
		funciones.get(0).setHermanoDerecha(null);
		
		funcion = funciones.get(funciones.size() - 1);
		this.lista_funciones = funcion;
		
	}
	
}
