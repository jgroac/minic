*      Compilacion MINIC para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
3:       LD       0,0(5)      cargar valor de identificador: num1
*      -> identificador
4:       ST       0,-40(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> identificador
5:       LD       0,1(5)      cargar valor de identificador: num2
*      -> identificador
6:       LD       1,-40(6)      op: pop o cargo de la pila el valor izquierdo en AC1
7:       ADD       0,1,0      op: +
*      <- Operacion: mas
8:       ST       0,2(5)      asignacion: almaceno el valor para el id sum
*      <- asignacion
*      -> identificador
9:       LD       0,2(5)      cargar valor de identificador: sum
*      -> identificador
10:       LDA       7,11(5)      salto del return
11:       LDA       7,0(4)      Salto incodicional a donde fue llamada la funcion
2:       LDA       7,12(5)      Salto incodicional al main
*      -> leer
12:       IN       0,0,0      leer: lee un valor entero 
13:       ST       0,3(5)      leer: almaceno el valor entero leido en el id var1
*      <- leer
*      -> leer
14:       IN       0,0,0      leer: lee un valor entero 
15:       ST       0,4(5)      leer: almaceno el valor entero leido en el id var2
*      <- leer
*      -> asignacion
*      -> identificador : &id
16:       LDC       0,6(0)      cargar direccion de memoria: 6
*      -> identificador
17:       ST       0,5(5)      asignacion: almaceno el valor para el id var3
*      <- asignacion
*      -> asignacion tipo puntero: *id = exp
*      -> identificador
18:       LD       0,3(5)      cargar valor de identificador: var1
*      -> identificador
19:       ST       0,0(5)      llamado: guarda el valor del argumento
*      -> identificador
20:       LD       0,4(5)      cargar valor de identificador: var2
*      -> identificador
21:       ST       0,1(5)      llamado: guarda el valor del argumento
*      -> CALLFUNC
22:       LDA       4,1(7)      AC=Pos actual + 1
23:       LDA       7,3(5)      Salto a la primera linea de la funcion
24:       ST       0,6(5)      asignacion: almaceno el valor en la direccion que apunta var3
*      <- asignacion
*      -> escribir
*      -> identificador
25:       LD       0,6(5)      cargar valor de identificador: salida
*      -> identificador
26:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
27:       HALT       0,0,0    