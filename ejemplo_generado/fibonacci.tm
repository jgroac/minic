*      Compilacion TINY para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> leer
2:       IN       0,0,0      leer: lee un valor entero 
3:       ST       0,4(5)      leer: almaceno el valor entero leido en el id x
*      <- leer
*      -> asignacion
*      -> constante
4:       LDC       0,0(0)      cargar constante: 0
*      <- constante
5:       ST       0,0(5)      asignacion: almaceno el valor para el id c
*      <- asignacion
*      -> asignacion
*      -> constante
6:       LDC       0,1(0)      cargar constante: 1
*      <- constante
7:       ST       0,1(5)      asignacion: almaceno el valor para el id a
*      <- asignacion
*      -> asignacion
*      -> constante
8:       LDC       0,1(0)      cargar constante: 1
*      <- constante
9:       ST       0,2(5)      asignacion: almaceno el valor para el id b
*      <- asignacion
*      -> repeat
*      repeat: el salto hacia el final (luego del cuerpo) del repeat debe estar aqui
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
10:       LD       0,1(5)      cargar valor de identificador: a
*      -> identificador
11:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> identificador
12:       LD       0,2(5)      cargar valor de identificador: b
*      -> identificador
13:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
14:       ADD       0,1,0      op: +
*      <- Operacion: mas
15:       ST       0,3(5)      asignacion: almaceno el valor para el id r
*      <- asignacion
*      -> escribir
*      -> identificador
16:       LD       0,3(5)      cargar valor de identificador: r
*      -> identificador
17:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      -> asignacion
*      -> identificador
18:       LD       0,2(5)      cargar valor de identificador: b
*      -> identificador
19:       ST       0,1(5)      asignacion: almaceno el valor para el id a
*      <- asignacion
*      -> asignacion
*      -> identificador
20:       LD       0,3(5)      cargar valor de identificador: r
*      -> identificador
21:       ST       0,2(5)      asignacion: almaceno el valor para el id b
*      <- asignacion
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
22:       LD       0,0(5)      cargar valor de identificador: c
*      -> identificador
23:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
24:       LDC       0,1(0)      cargar constante: 1
*      <- constante
25:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
26:       ADD       0,1,0      op: +
*      <- Operacion: mas
27:       ST       0,0(5)      asignacion: almaceno el valor para el id c
*      <- asignacion
*      -> Operacion: igual
*      -> identificador
28:       LD       0,0(5)      cargar valor de identificador: c
*      -> identificador
29:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> identificador
30:       LD       0,4(5)      cargar valor de identificador: x
*      -> identificador
31:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
32:       SUB       0,1,0      op: ==
33:       JEQ       0,2(7)      voy dos instrucciones mas alla if verdadero (AC==0)
34:       LDC       0,0(0)      caso de falso (AC=0)
35:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
36:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: igual
37:       JEQ       0,-28(7)      repeat: jmp hacia el inicio del cuerpo
*      <- repeat
*      Fin de la ejecucion.
38:       HALT       0,0,0    