package ast;

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
	
}
