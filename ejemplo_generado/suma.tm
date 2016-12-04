*      Compilacion TINY para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> leer
2:       IN       0,0,0      leer: lee un valor entero 
3:       ST       0,1(5)      leer: almaceno el valor entero leido en el id x
*      <- leer
*      -> asignacion
*      -> Operacion: mas
*      -> Operacion: mas
*      -> Operacion: mas
*      -> identificador
4:       LD       0,1(5)      cargar valor de identificador: x
*      -> identificador
5:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> Operacion: por
*      -> constante
6:       LDC       0,2(0)      cargar constante: 2
*      <- constante
7:       ST       0,-1(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
8:       LDC       0,3(0)      cargar constante: 3
*      <- constante
9:       LD       1,-1(6)      op: pop o cargo de la pila el valor izquierdo en AC1
10:       MUL       0,1,0      op: *
*      <- Operacion: por
11:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
12:       ADD       0,1,0      op: +
*      <- Operacion: mas
13:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> identificador
14:       LD       0,1(5)      cargar valor de identificador: x
*      -> identificador
15:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
16:       ADD       0,1,0      op: +
*      <- Operacion: mas
17:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> Operacion: por
*      -> constante
18:       LDC       0,5(0)      cargar constante: 5
*      <- constante
19:       ST       0,-1(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
20:       LDC       0,6(0)      cargar constante: 6
*      <- constante
21:       LD       1,-1(6)      op: pop o cargo de la pila el valor izquierdo en AC1
22:       MUL       0,1,0      op: *
*      <- Operacion: por
23:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
24:       ADD       0,1,0      op: +
*      <- Operacion: mas
25:       ST       0,0(5)      asignacion: almaceno el valor para el id r
*      <- asignacion
*      -> escribir
*      -> identificador
26:       LD       0,0(5)      cargar valor de identificador: r
*      -> identificador
27:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
28:       HALT       0,0,0      