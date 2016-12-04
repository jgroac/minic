package ast;

public class NodoWhile extends NodoBase {

	private NodoBase prueba;
	private NodoBase cuerpo;
	
	
	public NodoWhile(NodoBase prueba,NodoBase cuerpo) {
		super();
		this.cuerpo = cuerpo;
		this.prueba = prueba;
	}
	
	public NodoWhile() {
		super();
		this.cuerpo = null;
		this.prueba = null;		
	}

	public NodoBase getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(NodoBase cuerpo) {
		this.cuerpo = cuerpo;
	}

	public NodoBase getPrueba() {
		return prueba;
	}

	public void setPrueba(NodoBase prueba) {
		this.prueba = prueba;
	}
	
	
	
	
	
}
