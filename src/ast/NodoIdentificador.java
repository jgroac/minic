package ast;

public class NodoIdentificador extends NodoBase {
	private String nombre;
	private NodoBase expresion;
	private boolean pointer;

	public NodoIdentificador(String nombre) {
		super();
		this.nombre = nombre;
		this.expresion = null;
		this.pointer = false;
	}
	
	public NodoIdentificador(String nombre, NodoBase expresion) {
		super();
		this.nombre = nombre;
		this.expresion = expresion;
		this.pointer = false;
	}
	
	public NodoIdentificador(String nombre, NodoBase expresion, boolean pointer) {
		super();
		this.nombre = nombre;
		this.expresion = expresion;
		this.pointer = pointer;
	}

	public NodoIdentificador() {
		super();
	}

	public String getNombre() {
		return nombre;
	}
	
	public NodoBase getExpresion(){
		return this.expresion;
	}

}
