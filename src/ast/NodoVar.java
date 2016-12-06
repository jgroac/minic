package ast;

public class NodoVar extends NodoBase {

	private String tipo;
	private String identificador;
	
	public NodoVar(String tipo, String identificador) {
		this.tipo = tipo;
		this.identificador = identificador;
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public String getIdentificador() {
		return this.identificador;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setIdentificador(String identificador){
		this.identificador = identificador;
	}

}
