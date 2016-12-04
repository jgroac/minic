package ast;

public class NodoIf extends NodoBase {

	private NodoBase prueba;
	private NodoBase cuerpoIf;
	private NodoBase cuerpoElse;
	
	public NodoIf(NodoBase prueba, NodoBase cuerpoIf) {
		super();
		this.prueba = prueba;
		this.cuerpoIf = cuerpoIf;
		this.cuerpoElse = null;
	}
	
	public NodoIf(NodoBase prueba, NodoBase cuerpoIf, NodoBase cuerpoElse) {
		super();
		this.prueba = prueba;
		this.cuerpoIf = cuerpoIf;
		this.cuerpoElse = cuerpoElse;
	}
	
	public NodoIf() {
		super();
		this.prueba = null;
		this.cuerpoIf = null;
		this.cuerpoElse = null;		
	}

	public NodoBase getPrueba() {
		return prueba;
	}

	public void setPrueba(NodoBase prueba) {
		this.prueba = prueba;
	}
	
	/* Determina si el nodo tiene o no una parte else, facilita la escritura de codigo 
	 *  si tiene un cuerpoElse retorna true
	 *  si no tiene un cuerpoElse retornara false
	 * */ 
	public boolean hasElse() {
		if(this.cuerpoElse != null) return true;
		
		return false;
	}

	public NodoBase getCuerpoIf() {
		return cuerpoIf;
	}

	public void setCuerpoIf(NodoBase parteThen) {
		this.cuerpoIf = parteThen;
	}

	public NodoBase getCuerpoElse() {
		return cuerpoElse;
	}

	public void getCuerpoElse(NodoBase parteElse) {
		this.cuerpoElse = parteElse;
	}
	
	
	
}
