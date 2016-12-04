package ast;

public class NodoLeer extends NodoBase {
	private String id;
	private NodoBase array;
	private NodoBase posArray;

	public NodoLeer(String identificador) {
		super();
		this.id = identificador;
	}

	public NodoLeer(NodoBase array) {
		super();
		this.id = ((NodoIdentificador) array ).getNombre();
		this.array = array;
		this.posArray = ((NodoIdentificador)array ).getExpresion();
	}

	public String getIdentificador() {
		return id;
	}

	public void setExpresion(String identificador) {
		this.id = identificador;
	}

}
