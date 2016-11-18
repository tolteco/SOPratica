#include <stdio.h>
#include <string.h>

int main (){
  long long int ts=4294967296;
  int ts2=4294967296;
  unsigned int ts3=4294967296;
  unsigned short int ts4=65535;

  printf("int    = %d\n", ts2);
  printf("u int  = %ld\n", ts3);
  printf("ll int = %lld\n", ts);
  printf("short  = %hu\n", ts4);

  return 0;
}
