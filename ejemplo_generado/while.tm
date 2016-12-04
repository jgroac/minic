*      Compilacion TINY para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> leer
2:       IN       0,0,0      leer: lee un valor entero 
3:       ST       0,0(5)      leer: almaceno el valor entero leido en el id x
*      <- leer
*      -> while
*      while: aqui deberia ir el marcado del inicio del while
*      -> Operacion: menor
*      -> identificador
4:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
5:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
6:       LDC       0,10(0)      cargar constante: 10
*      <- constante
7:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
8:       SUB       0,1,0      op: <
9:       JLT       0,2(7)      voy dos instrucciones mas alla if verdadero (AC<0)
10:       LDC       0,0(0)      caso de falso (AC=0)
11:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
12:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: menor
*      -> cuerpo while
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
14:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
15:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
16:       LDC       0,1(0)      cargar constante: 1
*      <- constante
17:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
18:       ADD       0,1,0      op: +
*      <- Operacion: mas
19:       ST       0,0(5)      asignacion: almaceno el valor para el id x
*      <- asignacion
*      -> escribir
*      -> identificador
20:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
21:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
22:       LDA       7,-19(7)      if: jmp hacia el final
13:       JEQ       0,9(7)      if: jmp hacia fin del while si falso (0)
*      Fin de la ejecucion.
23:       HALT       0,0,0      
