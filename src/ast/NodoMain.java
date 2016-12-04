package ast;

public class NodoMain {
	private NodoBase cuerpo;
	
	public NodoMain(NodoBase cuerpo){
		this.cuerpo = cuerpo;
	}
	
	
	public void setCuerpo(NodoBase cuerpo) {
		this.cuerpo = cuerpo;
	}
	
	public NodoBase getCuerpo() {
		return this.cuerpo;
	}

}
