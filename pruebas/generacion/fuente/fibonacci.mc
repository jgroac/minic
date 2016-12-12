void main()
{
    
    int i, n, a, b, fib;
    
    a = 0;
    b = 1;
    fib = 0;
    
    gets(n);
    puts(a);
    puts (b);

    i=2;

	do{
	fib = a+b;
	a = b;
	b = fib;
	i = i+1;
	
	puts(fib);
	
	}while(i<n);

    
}