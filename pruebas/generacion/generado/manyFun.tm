*      Compilacion MINIC para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> escribir
*      -> constante
3:       LDC       0,3(0)      cargar constante: 3
*      <- constante
4:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
5:       LDA       7,0(4)      Salto incodicional a donde fue llamada la funcion
*      -> CALLFUNC
6:       LDA       4,1(7)      AC=Pos actual + 1
7:       LDA       7,3(5)      Salto a la primera linea de la funcion
8:       LDA       7,0(4)      Salto incodicional a donde fue llamada la funcion
*      -> CALLFUNC
9:       LDA       4,1(7)      AC=Pos actual + 1
10:       LDA       7,6(5)      Salto a la primera linea de la funcion
11:       LDA       7,0(4)      Salto incodicional a donde fue llamada la funcion
*      -> CALLFUNC
12:       LDA       4,1(7)      AC=Pos actual + 1
13:       LDA       7,9(5)      Salto a la primera linea de la funcion
14:       LDA       7,0(4)      Salto incodicional a donde fue llamada la funcion
2:       LDA       7,15(5)      Salto incodicional al main
*      -> CALLFUNC
15:       LDA       4,1(7)      AC=Pos actual + 1
16:       LDA       7,12(5)      Salto a la primera linea de la funcion
*      Fin de la ejecucion.
17:       HALT       0,0,0      