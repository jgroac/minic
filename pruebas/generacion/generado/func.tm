*      Compilacion MINIC para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> Operacion: mas
*      -> identificador
3:       LD       0,0(5)      cargar valor de identificador: a
*      -> identificador
4:       ST       0,-40(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> identificador
5:       LD       0,1(5)      cargar valor de identificador: b
*      -> identificador
6:       LD       1,-40(6)      op: pop o cargo de la pila el valor izquierdo en AC1
7:       ADD       0,1,0      op: +
*      <- Operacion: mas
8:       LDA       7,9(5)      salto del return
9:       LDA       7,0(4)      Salto incodicional a donde fue llamada la funcion
2:       LDA       7,10(5)      Salto incodicional al main
*      -> asignacion
*      -> Operacion: mas
*      -> constante
10:       LDC       0,2(0)      cargar constante: 2
*      <- constante
11:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
12:       LDC       0,3(0)      cargar constante: 3
*      <- constante
13:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
14:       ADD       0,1,0      op: +
*      <- Operacion: mas
15:       ST       0,0(5)      llamado: guarda el valor del argumento
*      -> constante
16:       LDC       0,7(0)      cargar constante: 7
*      <- constante
17:       ST       0,1(5)      llamado: guarda el valor del argumento
*      -> CALLFUNC
18:       LDA       4,1(7)      
19:       LDA       7,3(5)      Salto a la primera linea de la funcion
20:       ST       0,2(5)      asignacion: almaceno el valor para el id c
*      <- asignacion
*      -> escribir
*      -> identificador
21:       LD       0,2(5)      cargar valor de identificador: c
*      -> identificador
22:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
23:       HALT       0,0,0