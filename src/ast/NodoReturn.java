package ast;

public class NodoReturn extends NodoBase {
	
	private NodoBase exp;
	
	public NodoReturn()
	{
		super();
		this.exp = null;
	}
	
	public NodoReturn(NodoBase exp)
	{
		super();
		this.exp = exp;
	}

	public NodoBase getExp() {
		return exp;
	}

	public void setExp(NodoBase exp) {
		this.exp = exp;
	}

}
