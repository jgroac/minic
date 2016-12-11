*      Compilacion MINIC para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
2:       LDA       7,3(5)      Salto incodicional al main
*      -> asignacion
*      -> constante
3:       LDC       0,20(0)      cargar constante: 20
*      <- constante
4:       ST       0,1(5)      asignacion: almaceno el valor para el id a
*      <- asignacion
*      -> For
*      -> asignacion
*      -> constante
5:       LDC       0,20(0)      cargar constante: 20
*      <- constante
6:       ST       0,0(5)      asignacion: almaceno el valor para el id i
*      <- asignacion
*      for: el salto hacia el final (luego del cuerpo) del for debe estar aqui
*      -> Operacion: mayor
*      -> identificador
7:       LD       0,0(5)      cargar valor de identificador: i
*      -> identificador
8:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
9:       LDC       0,1(0)      cargar constante: 1
*      <- constante
10:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
11:       SUB       0,1,0      op: >
12:       JGT       0,2(7)      voy dos instrucciones mas alla if verdadero AC>0
13:       LDC       0,0(0)      caso de falso AC=0
14:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 {es falso evito colocarlo verdadero}
15:       LDC       0,1(0)      caso de verdadero AC=1
*      <- Operacion: mayor
*      -> escribir
*      -> identificador
17:       LD       0,0(5)      cargar valor de identificador: i
*      -> identificador
18:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      -> asignacion
*      -> constante
19:       LDC       0,2(0)      cargar constante: 2
*      <- constante
20:       ST       0,1(5)      asignacion: almaceno el valor para el id a
*      <- asignacion
*      -> asignacion
*      -> Operacion: menos
*      -> identificador
21:       LD       0,0(5)      cargar valor de identificador: i
*      -> identificador
22:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
23:       LDC       0,1(0)      cargar constante: 1
*      <- constante
24:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
25:       SUB       0,1,0      op: -
*      <- Operacion: menos
26:       ST       0,0(5)      asignacion: almaceno el valor para el id i
*      <- asignacion
27:       LDA       7,-21(7)      if: vamos hacia el inicio
16:       JEQ       0,11(7)      for: jmp hacia el fin del cuerpo
*      -> escribir
*      -> identificador
28:       LD       0,1(5)      cargar valor de identificador: a
*      -> identificador
29:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
30:       HALT       0,0,0  