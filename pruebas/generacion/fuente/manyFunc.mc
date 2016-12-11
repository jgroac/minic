int sum3(){
    puts(3);
}

int sum2(){
    sum3();
}

int sum() {
    sum2();
}


void main(){
	sum();
}