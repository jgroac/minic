void main(){
	int x,*p;
	x = 40;
	p = &x;
	*p = 23;
	puts(x);
}