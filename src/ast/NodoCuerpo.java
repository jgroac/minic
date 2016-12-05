package ast;

public class NodoCuerpo extends NodoBase {
	
	private NodoBase sentencias;

	public NodoCuerpo(NodoBase sentencias) {
		this.sentencias = sentencias;
	}
	
	public NodoBase getSentencias() {
		return this.sentencias;
	}
	
	public void setSentencias (NodoBase sentencias){
		this.sentencias = sentencias;
	}
}
