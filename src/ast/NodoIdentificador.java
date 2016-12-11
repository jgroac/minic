package ast;

public class NodoIdentificador extends NodoBase {
	private String nombre;
	private NodoBase expresion;
	private boolean pointer;
	private boolean reference;

	public NodoIdentificador(String nombre) {
		super();
		this.nombre = nombre;
		this.expresion = null;
		this.pointer = false;
		this.reference = false;
	}
	
	public NodoIdentificador(String nombre, NodoBase expresion) {
		super();
		this.nombre = nombre;
		this.expresion = expresion;
		this.pointer = false;
		this.reference = false;
	}
	
	public NodoIdentificador(String nombre, NodoBase expresion, boolean pointer) {
		super();
		this.nombre = nombre;
		this.expresion = expresion;
		this.pointer = pointer;
		this.reference = false;
	}

	public NodoIdentificador() {
		super();
	}
	
	public void setReference(boolean ref) {
		this.reference = ref;
	}
	
	public boolean isReference() {
		return this.reference;
	}

	public String getNombre() {
		return nombre;
	}
	
	public boolean isPointer() {
		return this.pointer;
	}
	
	public NodoBase getExpresion(){
		return this.expresion;
	}

}
