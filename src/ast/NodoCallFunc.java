package ast;

public class NodoCallFunc extends NodoBase {
	private String nombre;
	private NodoBase sent_args;

	public NodoCallFunc(String nombre, NodoBase sent_args) {
		this.nombre = nombre;
		this.sent_args = sent_args;
	}
	
	public String getNombreFuncion(){
		return this.nombre;
	}
	
	public NodoBase getArgs() {
		return this.sent_args;
	}
}
