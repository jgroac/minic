package compilador;

public class RegistroSimbolo {
	
	private String identificador;
	private int NumLinea;
	private int DireccionMemoria;
	private String ambito;
	private String tipo;
	
	public RegistroSimbolo(String identificador, int numLinea,
			int direccionMemoria) {
		super();
		this.identificador = identificador;
		NumLinea = numLinea;
		DireccionMemoria = direccionMemoria;
	}
	
	public RegistroSimbolo(String identificador, String tipo, int numLinea,
			int direccionMemoria, String ambito) {
		super();
		this.identificador = identificador;
		this.tipo = tipo;
		NumLinea = numLinea;
		DireccionMemoria = direccionMemoria;
		this.ambito = ambito;
	}

	public String getIdentificador() {
		return identificador;
	}

	public int getNumLinea() {
		return NumLinea;
	}
	
	public String getAmbito() { 
		return this.ambito;
	}

	public int getDireccionMemoria() {
		return DireccionMemoria;
	}

	public void setDireccionMemoria(int direccionMemoria) {
		DireccionMemoria = direccionMemoria;
	}
	
	public String getTipo(){
		return this.tipo;
	}
	
}
