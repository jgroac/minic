package ast;

public class NodoValor extends NodoBase {
	private int valor;
	private int tipo; // Tipo -> 0 Integer , Tipo -> 1 Boolean
	
	public NodoValor(int valor) {
		super();
		this.valor = valor;
		this.tipo = 0;
	}
	
	public NodoValor(int valor, int tipo) {
		super();
		this.valor = valor;
		this.tipo = tipo;
	}
	
	public int getValor() {
		return valor;
	}
	
	public int getTipo() {
		return tipo;
	}

}
