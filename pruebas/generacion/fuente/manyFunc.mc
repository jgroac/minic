void sum3(){
    sum2();
}
void sum2(){
    sum();
}

void sum() {
    prueba();
}

void prueba() {
    puts(3);
}

void main(){
	sum3();
}