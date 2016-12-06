package ast;

public class NodoAsignacion extends NodoBase {
	private String identificador;
	private NodoBase expresion;
	private boolean pointer;
	
	public NodoAsignacion(String identificador) {
		super();
		this.identificador = identificador;
		this.expresion = null;
		this.pointer = false;
	}
	
	public NodoAsignacion(String identificador, NodoBase expresion) {
		super();
		this.identificador = identificador;
		this.expresion = expresion;
		this.pointer = false;
	}
	
	public NodoAsignacion(String identificador, NodoBase expresion, boolean pointer ){
		super();
		this.identificador = identificador;
		this.expresion = expresion;
		this.pointer = true;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public NodoBase getExpresion() {
		return expresion;
	}

	public void setExpresion(NodoBase expresion) {
		this.expresion = expresion;
	}
	
	
	
}
