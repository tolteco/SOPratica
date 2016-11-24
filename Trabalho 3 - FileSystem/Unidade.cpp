/*
   OAC 2016
   Teste para Ponto Flututane 
      Float e Double
   
   Compilado como: 
      gcc pftest.c -o pftest.x
*/
#include <stdio.h>
#include <string.h>		// cores
#include <stdlib.h> 		// atof
// ANSI Colors at https://en.wikipedia.org/wiki/ANSI_escape_code#Colors
#define C_RED			"\x1b[31;1m"	
#define C_GREEN		"\x1b[32;1m"
#define C_BLUE			"\x1b[34;1m"
#define C_RESET		"\x1b[0m"

#define size_f 4
#define size_d 8

int main(int argc, char *argv[]){

	float 		pf = 0.0;
	double 	db = 0.0;

	int *ppf, *pdb;

	if(argc < 2){
		printf("\tErro:\tÉ necessário inserir o float\n");
		printf("\tUso:\t%s [9]9.9[9]\n", argv[0]);
		printf("Finalizado\n");
		return -1;
	}


	pf = atof(argv[1]);
   db = atof(argv[1]);
	printf("Número: %f\n", pf);

	printf("Pointer size: %ld\n",sizeof(&ppf));

	//db = (double) pf;

	ppf = (int *) &pf;
	pdb = (int *) &db;

 	// OK para PF
	printf(C_BLUE "FLOAT\n" C_RESET);
	printf("\tSize:\t%ld bytes\n", sizeof(pf));
	printf("\tAddr:\t%p\n", ppf);
	printf("\tValue:\t" C_GREEN "0x%0X\n" C_RESET, *ppf);
   printf("\tValue:\t %.35f\n", pf);

	printf("\n");

	 // OK para DB
	printf(C_BLUE "DOUBLE\n" C_RESET);
	printf("\tSize:\t%ld bytes\n", sizeof(double));
	printf("\tAddr L:\t%p\n", pdb);
	printf("\tAddr H:\t%p\n", (pdb+1));
	printf("\tValue:\t" C_GREEN "0x%08X%08X\n" C_RESET, *(pdb+1), *pdb);	
   printf("\tValue:\t %.60lf\n", db);

	return 0;
}
