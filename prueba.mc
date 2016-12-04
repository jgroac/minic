void  fibonacci(int n){
  
  int i = 0, j = 1, k=0, t=0;

  for (k = 1; k <= n; k = k + 1 ) {
      t = i + j;
      i = j;  
      j = t;
       puts(j);
   }
}

void main(){
  fibonacci(7); 
}