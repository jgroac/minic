package ast;

public class Util {
	
	static int sangria = 0;
	
	//Imprimo en modo texto con sangrias el AST
	public static void imprimirAST(NodoBase raiz){
		  sangria+=2;
		  while (raiz != null) {
		    printSpaces();
		    // Nodo Programa
		    if(raiz instanceof NodoPrograma) {
		    	System.out.println("NodoPrograma");
		    	NodoFuncion funciones = (NodoFuncion) ((NodoPrograma)raiz).getListaFunciones();
		    	NodoMain  mainf = (NodoMain) ((NodoPrograma)raiz).getMain();
		    	imprimirAST(funciones);
		    	imprimirAST(mainf);
		    	break;
		    }
		    
		    // Nodo Funcion
		    else if (raiz instanceof NodoFuncion) {
		    	NodoFuncion  nodofun = ((NodoFuncion)(raiz));
		    	System.out.println("decl_fun:" + nodofun.getNombreFuncion());
		    	imprimirAST(nodofun.getArgs());
		    	imprimirAST(nodofun.getCuerpoFuncion());
		    }
		    
		    // Nodo Main
		    else if(raiz instanceof NodoMain) {
		    	System.out.println("Main");
		    	imprimirAST((NodoBase) ((NodoMain)raiz).getCuerpo());
		    }
		    
		    // Nodo Cuerpo
		    else if (raiz instanceof NodoCuerpo) {
		    	System.out.println("Cuerpo");
		    	imprimirAST((NodoBase) ((NodoCuerpo)raiz).getSentencias());
		    }
		    
		    // Nodo Variables
		    else if(raiz instanceof NodoVar) {
		    	NodoVar nodovar = ((NodoVar)raiz);
		    	System.out.print("Var tipo:" + nodovar.getTipo() + " id:" + nodovar.getIdentificador());
		    	
		    }
		    
		    // Nodo llamada a funcion
		    else if(raiz instanceof NodoCallFunc) {
		    	NodoCallFunc callfun = ((NodoCallFunc)raiz);
		    	System.out.println("Llamada : " + callfun.getNombreFuncion());
		    	imprimirAST(callfun.getArgs());
		    }
		    	

		   // Nodo Return
		    else if (raiz instanceof NodoReturn) {
		    	System.out.println("return");
		    	imprimirAST(((NodoReturn)raiz).getExp());
		    }
		    	
		    
		    else if (raiz instanceof  NodoRepeat)
		    	System.out.println("Repeat");
		    
		    else if (raiz instanceof  NodoAsignacion)
		    	System.out.println("Asignacion a: "+((NodoAsignacion)raiz).getIdentificador());

		    else if (raiz instanceof  NodoLeer)  
		    	System.out.println("Lectura: "+((NodoLeer)raiz).getIdentificador());

		    else if (raiz instanceof  NodoEscribir)
		    	System.out.println("Escribir");
		    
		    else if (raiz instanceof NodoOperacion
		    		|| raiz instanceof NodoValor
		    		|| raiz instanceof NodoIdentificador )
		    	imprimirNodo(raiz);
		    else System.out.println("Tipo de nodo desconocido");;
		    
		    /* Hago el recorrido recursivo */
		    if (raiz instanceof  NodoIf){
		    	printSpaces();
		    	System.out.println("**Prueba IF**");
		    	imprimirAST(((NodoIf)raiz).getPrueba());
		    	printSpaces();
		    	System.out.println("**Cuerpo IF**");
		    	imprimirAST(((NodoIf)raiz).getCuerpoIf());
		    	if(((NodoIf)raiz).hasElse()){
		    		printSpaces();
		    		System.out.println("**Cuerpo Else**");
		    		imprimirAST(((NodoIf)raiz).getCuerpoElse());
		    	}
		    }
		    else if (raiz instanceof  NodoRepeat){
		    	printSpaces();
		    	System.out.println("**Cuerpo REPEAT**");
		    	imprimirAST(((NodoRepeat)raiz).getCuerpo());
		    	printSpaces();
		    	System.out.println("**Prueba REPEAT**");
		    	imprimirAST(((NodoRepeat)raiz).getPrueba());
		    }
		    else if (raiz instanceof  NodoAsignacion)
		    	imprimirAST(((NodoAsignacion)raiz).getExpresion());
		    else if (raiz instanceof  NodoEscribir)
		    	imprimirAST(((NodoEscribir)raiz).getExpresion());
		    else if (raiz instanceof NodoOperacion){
		    	printSpaces();
		    	System.out.println("**Expr Izquierda Operacion**");
		    	imprimirAST(((NodoOperacion)raiz).getOpIzquierdo());
		    	printSpaces();
		    	System.out.println("**Expr Derecha Operacion**");		    	
		    	imprimirAST(((NodoOperacion)raiz).getOpDerecho());
		    }
		    raiz = raiz.getHermanoDerecha();
		  }
		  sangria-=2;
		}

/* Imprime espacios con sangria */
static void printSpaces()
{ int i;
  for (i=0;i<sangria;i++)
	  System.out.print(" ");
}

/* Imprime informacion de los nodos */
static void imprimirNodo( NodoBase raiz )
{
	if(	raiz instanceof NodoRepeat
		||	raiz instanceof NodoLeer
		||	raiz instanceof NodoEscribir  ){
		System.out.println("palabra reservada: "+ raiz.getClass().getName());
	}
	
	if(	raiz instanceof NodoAsignacion )
		System.out.println(":=");
	
	if(	raiz instanceof NodoOperacion ){
		tipoOp sel=((NodoOperacion) raiz).getOperacion();
		if(sel==tipoOp.menor)
			System.out.println("<"); 
		if(sel==tipoOp.igual)
			System.out.println("=");
		if(sel==tipoOp.mas)
			System.out.println("+");
		if(sel==tipoOp.menos)
			System.out.println("-");
		if(sel==tipoOp.por)
			System.out.println("*");
		if(sel==tipoOp.entre)
			System.out.println("/");
	}

	if(	raiz instanceof NodoValor ){
		System.out.println("NUM, val= "+ ((NodoValor)raiz).getValor());
	}

	if(	raiz instanceof NodoIdentificador ){
		System.out.println("ID, nombre= "+ ((NodoIdentificador)raiz).getNombre());
	}

}


}
