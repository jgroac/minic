package compilador;

import ast.NodoBase;
import java_cup.runtime.DefaultSymbolFactory;
import java_cup.runtime.SymbolFactory;

public class Compilador {
	
	public static void main(String args[]) throws Exception {
		@SuppressWarnings("deprecation")
		SymbolFactory sf = new DefaultSymbolFactory();
		parser parser_obj;
		Scanner s;
		
//		if (args.length==0) {
//			s=new Scanner(System.in,sf);
//			parser_obj=new parser(s,sf);
//		}
//		else{ 
//			s=new Scanner(new java.io.FileInputStream(args[0]),sf);
//			parser_obj=new parser(s,sf);
//		}
		
		parser_obj=new parser(new Scanner(new java.io.FileInputStream("pruebas/generacion/fuente/puts.mc"),sf),sf);

		parser_obj.parse();
		
		
		NodoBase root=parser_obj.action_obj.getASTroot();
		System.out.println();
		System.out.println("IMPRESION DEL AST GENERADO");
		System.out.println();
		ast.Util.imprimirAST(root);
		TablaSimbolos ts = new TablaSimbolos();
		ts.cargarTabla(root);
		ts.ImprimirClaves();
		Semantico analizadorSemantico = new Semantico(ts);
		analizadorSemantico.recorrido(root);
		if(!analizadorSemantico.hasErrors()){
			compilador.Generador.setTablaSimbolos(ts);
			compilador.Generador.generarCodigoObjeto(root);
		}
	}
	
}
