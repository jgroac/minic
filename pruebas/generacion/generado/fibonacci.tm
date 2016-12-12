*      Compilacion MINIC para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
2:       LDA       7,3(5)      Salto incodicional al main
*      -> asignacion
*      -> constante
3:       LDC       0,0(0)      cargar constante: 0
*      <- constante
4:       ST       0,2(5)      asignacion: almaceno el valor para el id a
*      <- asignacion
*      -> asignacion
*      -> constante
5:       LDC       0,1(0)      cargar constante: 1
*      <- constante
6:       ST       0,3(5)      asignacion: almaceno el valor para el id b
*      <- asignacion
*      -> asignacion
*      -> constante
7:       LDC       0,0(0)      cargar constante: 0
*      <- constante
8:       ST       0,4(5)      asignacion: almaceno el valor para el id fib
*      <- asignacion
*      -> leer
9:       IN       0,0,0      leer: lee un valor entero 
10:       ST       0,1(5)      leer: almaceno el valor entero leido en el id n
*      <- leer
*      -> escribir
*      -> identificador
11:       LD       0,2(5)      cargar valor de identificador: a
*      -> identificador
12:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      -> escribir
*      -> identificador
13:       LD       0,3(5)      cargar valor de identificador: b
*      -> identificador
14:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      -> asignacion
*      -> constante
15:       LDC       0,2(0)      cargar constante: 2
*      <- constante
16:       ST       0,0(5)      asignacion: almaceno el valor para el id i
*      <- asignacion
*      -> while
*      while: aqui deberia ir el marcado del inicio del while
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
17:       LD       0,2(5)      cargar valor de identificador: a
*      -> identificador
18:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> identificador
19:       LD       0,3(5)      cargar valor de identificador: b
*      -> identificador
20:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
21:       ADD       0,1,0      op: +
*      <- Operacion: mas
22:       ST       0,4(5)      asignacion: almaceno el valor para el id fib
*      <- asignacion
*      -> asignacion
*      -> identificador
23:       LD       0,3(5)      cargar valor de identificador: b
*      -> identificador
24:       ST       0,2(5)      asignacion: almaceno el valor para el id a
*      <- asignacion
*      -> asignacion
*      -> identificador
25:       LD       0,4(5)      cargar valor de identificador: fib
*      -> identificador
26:       ST       0,3(5)      asignacion: almaceno el valor para el id b
*      <- asignacion
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
27:       LD       0,0(5)      cargar valor de identificador: i
*      -> identificador
28:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
29:       LDC       0,1(0)      cargar constante: 1
*      <- constante
30:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
31:       ADD       0,1,0      op: +
*      <- Operacion: mas
32:       ST       0,0(5)      asignacion: almaceno el valor para el id i
*      <- asignacion
*      -> escribir
*      -> identificador
33:       LD       0,4(5)      cargar valor de identificador: fib
*      -> identificador
34:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      -> Operacion: menor
*      -> identificador
35:       LD       0,0(5)      cargar valor de identificador: i
*      -> identificador
36:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> identificador
37:       LD       0,1(5)      cargar valor de identificador: n
*      -> identificador
38:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
39:       SUB       0,1,0      op: <
40:       JLT       0,2(7)      voy dos instrucciones mas alla if verdadero AC<0
41:       LDC       0,0(0)      caso de falso AC=0
42:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 {es falso evito colocarlo verdadero}
43:       LDC       0,1(0)      caso de verdadero AC=1
*      <- Operacion: menor
44:       JNE       0,-28(7)      do while: jmp hacia el inicio
*      Fin de la ejecucion.
45:       HALT       0,0,0  