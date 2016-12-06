int suma(int num1, int num2){
int sum;
/* Argumentos usados aqui*/
sum = num1+num2;
/* Retorno
El valor */
return sum;
}

int suma2(int num1, int num2){
int sum;
/* Argumentos usados aqui*/
sum = num1+num2;
/* Retorno
El valor */
return sum;
}

int main()
{
    int var1, var2, *var3;
    gets(var1);
    gets(var2);
    var2 = *var1 * *var1;
    int salida;
    var3=&salida;
    *salida = suma (var1, var2);
    puts(var3);
}