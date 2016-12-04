*      Compilacion TINY para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> leer
2:       IN       0,0,0      leer: lee un valor entero 
3:       ST       0,0(5)      leer: almaceno el valor entero leido en el id x
*      <- leer
*      -> leer
4:       IN       0,0,0      leer: lee un valor entero 
5:       ST       0,1(5)      leer: almaceno el valor entero leido en el id y
*      <- leer
*      -> asignacion
*      -> identificador
6:       LD       0,1(5)      cargar valor de identificador: y
*      -> identificador
7:       ST       0,2(5)      asignacion: almaceno el valor para el id contador
*      <- asignacion
*      -> asignacion
*      -> constante
8:       LDC       0,0(0)      cargar constante: 0
*      <- constante
9:       ST       0,3(5)      asignacion: almaceno el valor para el id r
*      <- asignacion
*      -> repeat
*      repeat: el salto hacia el final (luego del cuerpo) del repeat debe estar aqui
*      -> asignacion
*      -> Operacion: mas
*      -> Operacion: mas
*      -> identificador
10:       LD       0,3(5)      cargar valor de identificador: r
*      -> identificador
11:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> identificador
12:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
13:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
14:       ADD       0,1,0      op: +
*      <- Operacion: mas
15:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> identificador
16:       LD       0,1(5)      cargar valor de identificador: y
*      -> identificador
17:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
18:       ADD       0,1,0      op: +
*      <- Operacion: mas
19:       ST       0,3(5)      asignacion: almaceno el valor para el id r
*      <- asignacion
*      -> asignacion
*      -> Operacion: menos
*      -> identificador
20:       LD       0,2(5)      cargar valor de identificador: contador
*      -> identificador
21:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
22:       LDC       0,1(0)      cargar constante: 1
*      <- constante
23:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
24:       SUB       0,1,0      op: -
*      <- Operacion: menos
25:       ST       0,2(5)      asignacion: almaceno el valor para el id contador
*      <- asignacion
*      -> Operacion: igual
*      -> identificador
26:       LD       0,2(5)      cargar valor de identificador: contador
*      -> identificador
27:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
28:       LDC       0,0(0)      cargar constante: 0
*      <- constante
29:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
30:       SUB       0,1,0      op: ==
31:       JEQ       0,2(7)      voy dos instrucciones mas alla if verdadero (AC==0)
32:       LDC       0,0(0)      caso de falso (AC=0)
33:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
34:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: igual
35:       JEQ       0,-26(7)      repeat: jmp hacia el inicio del cuerpo
*      <- repeat
*      -> escribir
*      -> identificador
36:       LD       0,3(5)      cargar valor de identificador: r
*      -> identificador
37:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
38:       HALT       0,0,0  