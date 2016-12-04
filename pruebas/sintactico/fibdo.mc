void  fibonacci(int n){
  
  int i = 0, j = 1, k=0, t=0;

  do {
      t = i + j;
      i = j;  
      j = t;
        puts(j);
        k = k + 1;
   } while( k < n);
}

void main(){
  fibonacci(7); 
}