*      Compilacion MINIC para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
2:       LDA       7,3(5)      Salto incodicional al main
*      -> asignacion
*      -> constante
3:       LDC       0,40(0)      cargar constante: 40
*      <- constante
4:       ST       0,0(5)      asignacion: almaceno el valor para el id x
*      <- asignacion
*      -> asignacion
*      -> identificador : &id
5:       LDC       0,0(0)      cargar direccion de memoria: 0
*      -> identificador
6:       ST       0,1(5)      asignacion: almaceno el valor para el id p
*      <- asignacion
*      -> asignacion tipo puntero: *id = exp
*      -> constante
7:       LDC       0,23(0)      cargar constante: 23
*      <- constante
8:       ST       0,0(5)      asignacion: almaceno el valor en la direccion que apunta p
*      <- asignacion
*      -> escribir
*      -> identificador
9:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
10:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
11:       HALT       0,0,0