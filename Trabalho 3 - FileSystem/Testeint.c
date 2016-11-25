#include <stdio.h>
#include <string.h>
#include <time.h>

int main (){
  long long int ts=4294967296;
  int ts2=4294967296;
  unsigned int ts3=4294967296;
  unsigned short int ts4=65535;

	int B = 6;

	printf("A  = %d\n", B);
	B = B << 1;
	printf("AN = %d\n", B);
	time_t rawtime;
  struct tm * timeinfo;

  time ( &rawtime );
  timeinfo = localtime ( &rawtime );
	char A[50];
	strcpy(A, asctime (timeinfo));
  printf ( "Current local time and date: %s\n", A );

	time_t now = time(0); //Hora do sistema

  printf("int    = %d\n", ts2);
  printf("u int  = %ld\n", ts3);
  printf("ll int = %lld\n", ts);
  printf("short  = %hu\n", ts4);
	printf("time now = %s\n", time);

  return 0;
}
