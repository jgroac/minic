void  fibonacci(int n){
  
  int i = 0, j = 1, k=1, t=0;

  while( k < n) {
      t = i + j;
      i = j;  
      j = t;
        puts(j);
        k = k + 1;
   }
}

void main(){
  fibonacci(7); 
}