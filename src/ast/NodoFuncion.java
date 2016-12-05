package ast;

public class NodoFuncion extends NodoBase {
	private String tipo_funcion;
	private String nombre_funcion;
	private NodoBase args;
	private NodoBase cuerpo;
	private int numero_args;
	
	public NodoFuncion()
	{
		super();
		this.tipo_funcion = "";
		this.nombre_funcion = "";
		this.args = null;
		this.cuerpo = null;
		this.numero_args = 0;
	}
	
	
	public NodoFuncion(String tipo_funcion, String nombre_funcion, NodoBase cuerpo)
	{
		super();
		this.tipo_funcion = tipo_funcion;
		this.nombre_funcion = nombre_funcion;
		this.args = null;
		this.cuerpo = cuerpo;
		this.numero_args = 0;
	}
	
	
	public NodoFuncion(String tipo_funcion, String nombre_funcion, NodoBase args, NodoBase cuerpo)
	{
		super();
		this.tipo_funcion = tipo_funcion;
		this.nombre_funcion = nombre_funcion;
		this.args = args;
		this.cuerpo = cuerpo;
		this.numero_args = 0;
	}
	
	public NodoFuncion(String tipo_funcion, String nombre_funcion, NodoBase args, NodoBase cuerpo, int numero_args)
	{
		super();
		this.tipo_funcion = tipo_funcion;
		this.nombre_funcion = nombre_funcion;
		this.args = args;
		this.cuerpo = cuerpo;
		this.numero_args = numero_args;
	}

	public String getTipoFuncion() {
		return this.tipo_funcion;
	}

	public void setTipoFuncion(String tipo_funcion) {
		this.tipo_funcion = tipo_funcion;
	}

	public String getNombreFuncion() {
		return this.nombre_funcion;
	}

	public void setNombreFuncion(String nombre_funcion) {
		this.nombre_funcion = nombre_funcion;
	}

	public NodoBase getArgs() {
		return this.args;
	}

	public void setArgs(NodoBase args) {
		this.args = args;
	}

	public NodoBase getCuerpoFuncion() {
		return cuerpo;
	}

	public void setCuerpoFuncion(NodoBase cuerpo) {
		this.cuerpo = cuerpo;
	}

	public int getNumeroArgs() {
		return this.numero_args;
	}

	public void setNumeroArgs(int numero_args) {
		this.numero_args = numero_args;
	}
}
