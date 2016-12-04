*      Compilacion TINY para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> if
*      -> constante
2:       LDC       0,1(0)      cargar constante: 0
*      <- constante
*      If: el salto hacia el else debe estar aqui
*      -> escribir
*      -> constante
4:       LDC       0,33(0)      cargar constante: 33
*      <- constante
5:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      If: el salto hacia el final debe estar aqui
3:       JEQ       0,3(7)      if: jmp hacia else
*      -> escribir
*      -> constante
7:       LDC       0,99(0)      cargar constante: 99
*      <- constante
8:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
6:       LDA       7,2(7)      if: jmp hacia el final
*      <- if
*      Fin de la ejecucion.
9:       HALT       0,0,0      
