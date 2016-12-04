package ast;

public class NodoIdentificador extends NodoBase {
	private String nombre;
	private NodoBase expresion;

	public NodoIdentificador(String nombre) {
		super();
		this.nombre = nombre;
		this.expresion = null;
	}
	
	public NodoIdentificador(String nombre, NodoBase expresion) {
		super();
		this.nombre = nombre;
		this.expresion = expresion;
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
