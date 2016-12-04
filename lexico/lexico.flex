package Tiny;

import java_cup.runtime.*;
//import otros.*;

%%
/* Habilitar la compatibilidad con el interfaz CUP para el generador sintactico*/
%cup
/* Llamar Scanner a la clase que contiene el analizador Lexico */
%class Scanner

/*-- DECLARACIONES --*/
%{
	public Scanner(java.io.InputStream r, SymbolFactory sf){
		this(r);
		this.sf=sf;
		lineanum=0;
		debug=true;
	}
	private SymbolFactory sf;
	private int lineanum;
	private boolean debug;


/******************************************************************
BORRAR SI NO SE NECESITA
	//TODO: Cambiar la SF por esto o ver que se hace
	//Crear un nuevo objeto java_cup.runtime.Symbol con información sobre el token actual sin valor
 	  private Symbol symbol(int type){
    		return new Symbol(type,yyline,yycolumn);
	  }
	//Crear un nuevo objeto java_cup.runtime.Symbol con información sobre el token actual con valor
	  private Symbol symbol(int type,Object value){
    		return new Symbol(type,yyline,yycolumn,value);
	  }
******************************************************************/
%}
%eofval{
    return sf.newSymbol("EOF",sym.EOF);
%eofval}

/* Acceso a la columna y fila actual de analisis CUP */
%line
%column



digito		= [0-9]
numero		= {digito}+
letra		= [a-zA-Z]
identificador	= {letra}+
nuevalinea	= \n | \n\r | \r\n
espacio		= [ \t]+
%%
"main"          {	if(debug) System.out.println("token MAIN");
			return sf.newSymbol("MAIN",sym.MAIN);
			}
"void"          {	if(debug) System.out.println("token VOID");
			return sf.newSymbol("VOID",sym.VOID);
			}
"return"        {	if(debug) System.out.println("token RETURN");
			return sf.newSymbol("RETURN",sym.RETURN);
			}
"bool"          {	if(debug) System.out.println("token BOOL");
			return sf.newSymbol("BOOL",sym.BOOL);
			}
"int"           {	if(debug) System.out.println("token INT");
			return sf.newSymbol("INT",sym.INT);
			}
"^"             {	if(debug) System.out.println("token POINT");
			return sf.newSymbol("POINT",sym.POINT);
			}
"puts"          {	if(debug) System.out.println("token PUTS");
			return sf.newSymbol("PUTS",sym.PUTS);
			}
"gets"          {	if(debug) System.out.println("token GETS");
			return sf.newSymbol("GETS",sym.GETS);
			}
"for"           {	if(debug) System.out.println("token FOR");
			return sf.newSymbol("FOR",sym.FOR);
			}
"do"            {	if(debug) System.out.println("token DO");
			return sf.newSymbol("DO",sym.DO);
			}
"while"         {	if(debug) System.out.println("token WHILE");
			return sf.newSymbol("WHILE",sym.WHILE);
			}
"true"          {	if(debug) System.out.println("token TRUE");
			return sf.newSymbol("TRUE",sym.TRUE);
			}
"false"         {	if(debug) System.out.println("token FALSE");
			return sf.newSymbol("FALSE",sym.FALSE);
			}
"+"             {	if(debug) System.out.println("token MAS");
			return sf.newSymbol("MAS",sym.MAS);
			}
"-"             {	if(debug) System.out.println("token MENOS");
			return sf.newSymbol("MENOS",sym.MENOS);
			}
"*"             {	if(debug) System.out.println("token MULT");
			return sf.newSymbol("MULT",sym.MULT);
			}
"/"             {	if(debug) System.out.println("token DIV");
			return sf.newSymbol("DIV",sym.DIV);
			}
"="             {	if(debug) System.out.println("token ASIG");
			return sf.newSymbol("ASIG",sym.ASIG);
			}
"<"             {	if(debug) System.out.println("token MENOR");
			return sf.newSymbol("MENOR",sym.MENOR);
			}
"<="            {	if(debug) System.out.println("token MENIG");
			return sf.newSymbol("MENIG",sym.MENIG);
			}
">"             {	if(debug) System.out.println("token MAYOR");
			return sf.newSymbol("MAYOR",sym.MAYOR);
			}
">="            {	if(debug) System.out.println("token MAYIG");
			return sf.newSymbol("MAYIG",sym.MAYIG);
			}
"!="            {	if(debug) System.out.println("token IGUAL");
			return sf.newSymbol("IGUAL",sym.IGUAL);
			}
"<>"            {	if(debug) System.out.println("token DIF");
			return sf.newSymbol("DIF",sym.DIF);
			}
"&&"            {	if(debug) System.out.println("token AND");
			return sf.newSymbol("AND",sym.AND);
			}
"||"            {	if(debug) System.out.println("token OR");
			return sf.newSymbol("OR",sym.OR);
			}
"if"            {	if(debug) System.out.println("token IF");
			return sf.newSymbol("IF",sym.IF);
			}
"else"          {	if(debug) System.out.println("token ELSE");
			return sf.newSymbol("ELSE",sym.ELSE);
			}
"("             {	if(debug) System.out.println("token LPAREN");
			return sf.newSymbol("LPAREN",sym.LPAREN);
			}
")"             {	if(debug) System.out.println("token RPAREN");
			return sf.newSymbol("RPAREN",sym.RPAREN);
			}
"{"             {	if(debug) System.out.println("token LLI");
			return sf.newSymbol("LLI",sym.LLI);
			}
"}"             {	if(debug) System.out.println("token LLD");
			return sf.newSymbol("LLD",sym.LLD);
			}
";"             {	if(debug) System.out.println("token PYC");
			return sf.newSymbol("PYC",sym.PYC);
			}
","             {	if(debug) System.out.println("token COMA");
			return sf.newSymbol("COMA",sym.COMA);
			}
"&"             {	if(debug) System.out.println("token AMP");
			return sf.newSymbol("AMP",sym.AMP);
			}
{numero}        {	if(debug) System.out.println("token NUM");
			return sf.newSymbol("NUM",sym.NUM,new String(yytext()));
			}
{identificador}	{	if(debug) System.out.println("token ID");
				return sf.newSymbol("ID",sym.ID,new String(yytext()));
			}
{nuevalinea}    {lineanum++;}
{espacio}       { /* saltos espacios en blanco*/}
"/*"[^*/]+"*/"   { /* salto comentarios */ if(debug) System.out.println("token COMENTARIO"); }
.               {System.err.println("Caracter Ilegal encontrado en analisis lexico: " + yytext() + "\n");}