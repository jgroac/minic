void prueba() {
    puts(3);
}

void sum() {
    prueba();
}

void sum2(){
    sum();
}

void sum3(){
    sum2();
}

void main(){
	sum3();
}