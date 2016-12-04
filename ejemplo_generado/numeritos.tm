*      Compilacion TINY para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> leer
2:       IN       0,0,0      leer: lee un valor entero 
3:       ST       0,0(5)      leer: almaceno el valor entero leido en el id paso
*      <- leer
*      -> asignacion
*      -> constante
4:       LDC       0,0(0)      cargar constante: 0
*      <- constante
5:       ST       0,1(5)      asignacion: almaceno el valor para el id c
*      <- asignacion
*      -> repeat
*      repeat: el salto hacia el final (luego del cuerpo) del repeat debe estar aqui
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
6:       LD       0,1(5)      cargar valor de identificador: c
*      -> identificador
7:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> identificador
8:       LD       0,0(5)      cargar valor de identificador: paso
*      -> identificador
9:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
10:       ADD       0,1,0      op: +
*      <- Operacion: mas
11:       ST       0,1(5)      asignacion: almaceno el valor para el id c
*      <- asignacion
*      -> escribir
*      -> identificador
12:       LD       0,1(5)      cargar valor de identificador: c
*      -> identificador
13:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      -> Operacion: menor
*      -> constante
14:       LDC       0,100(0)      cargar constante: 100
*      <- constante
15:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> identificador
16:       LD       0,1(5)      cargar valor de identificador: c
*      -> identificador
17:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
18:       SUB       0,1,0      op: <
19:       JLT       0,2(7)      voy dos instrucciones mas alla if verdadero (AC<0)
20:       LDC       0,0(0)      caso de falso (AC=0)
21:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
22:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: menor
23:       JEQ       0,-18(7)      repeat: jmp hacia el inicio del cuerpo
*      <- repeat
*      -> escribir
*      -> constante
24:       LDC       0,1000(0)      cargar constante: 1000
*      <- constante
25:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
26:       HALT       0,0,0  