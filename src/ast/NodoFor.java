package ast;

public class NodoFor extends NodoBase {
	private NodoBase lista_declaracion;
	private NodoBase exp;
	private NodoBase declaracion;
	private NodoBase cuerpo;
	
	public NodoFor(NodoBase lista_declaracion, NodoBase exp, NodoBase declaracion, NodoBase cuerpo) {
		this.lista_declaracion = lista_declaracion;
		this.exp = exp;
		this.declaracion = declaracion;
		this.cuerpo = cuerpo;
	}
	
	public NodoBase getListaDeclaracion(){
		return this.lista_declaracion;
	}
	
	public NodoBase getExpresion(){
		return this.exp;
	}
	
	public NodoBase getDeclaracion(){
		return this.declaracion;
	}
	
	public NodoBase getCuerpo(){
		return this.cuerpo;
	}
}
