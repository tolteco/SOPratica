#include <stdio.h>
#include <string.h>
#include <time.h>


/*typedef struct Centry
{
	unsigned char  nome[13];
	unsigned char  extensao[4];
	unsigned char  tipo;
	unsigned int   tamanho;
	unsigned int   criacao;
	unsigned int   modificacao;
	unsigned short primeiro_setor;
}__attribute__((packed)) Centry_t;

Centry_t root;
Centry_t root2;
Centry_t root3;*/

char comando;//, dir[513], data1[21], temp[5], cc[102];
FILE *f;

/*void IntpData(unsigned int A){
	int i;
	sprintf(temp, "%d", (A & 31)); //Horas
	strcpy(data1, temp);
	strcat(data1, ":");
	A = A >> 5;
	sprintf(temp, "%d", (A & 63)); //Minutos
	strcat(data1, temp);
	strcat(data1, ":");
	A = A >> 6;
	sprintf(temp, "%d", ((A & 31)*2)); //Segundos
	strcat(data1, temp);
	strcat(data1, " ");
	A = A >> 5;
	sprintf(temp, "%d", (A & 31)); //Dias
	strcat(data1, temp);
	strcat(data1, "/");
	A = A >> 5;
	sprintf(temp, "%d", (A & 15)); //Meses
	strcat(data1, temp);
	strcat(data1, "/");
	A = A >> 4;
	sprintf(temp, "%d", ((A & 127) + 2000)); //Anos
	strcat(data1, temp);
	A = A >> 7;
}*/

int main (){
	//char *G;
	//char H[50] = "Primeiro segundo terceiro quarto quinto";
	//char J[20];
	//unsigned int criacao;
  /*long long int ts=4294967296;
  int ts2=4294967296;
  unsigned int ts3=4294967296;
  unsigned short int ts4=65535;*/

	f = fopen("/home/administrador/Downloads/R.txt", "r");
	while (!feof(f)) {
		fread(&comando, 1, sizeof(comando), f);
		printf("-%c-", comando);
	}
	//G = strtok(H, " ");
	//G = strtok(NULL, " ");
	//G = strtok(NULL, " ");

	/*printf("\n---------\n%s\n---------\n", G);
	strcpy(J, G);
	printf("%s\n", J);



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
	printf ( "Ano = %d\n", (timeinfo->tm_year - 100));
	printf ( "Mes = %d\n", (timeinfo->tm_mon + 1));
	printf ( "Dia = %d\n", (timeinfo->tm_mday));
	printf ( "Hor = %d\n", (timeinfo->tm_hour));
	printf ( "Min = %d\n", (timeinfo->tm_min));
	printf ( "Seg = %d\n", (timeinfo->tm_sec / 2));
	criacao = (timeinfo->tm_year - 100) << 4;
	criacao = (criacao + (timeinfo->tm_mon + 1)) << 5;
	criacao = (criacao + (timeinfo->tm_mday)) << 5;
	criacao = (criacao + (timeinfo->tm_sec / 2)) << 6;
	criacao = (criacao + (timeinfo->tm_min)) << 5;
	criacao = criacao + timeinfo->tm_hour;
	printf ( "Shift = %u\n", criacao);

	IntpData(criacao);
	printf("Data e hora retornados : %s", data1);*/

	/*time_t now = time(0); //Hora do sistema

  printf("int    = %d\n", ts2);
  printf("u int  = %ld\n", ts3);
  printf("ll int = %lld\n", ts);
  printf("short  = %hu\n", ts4);
	printf("time now = %s\n", time);*/
/*char dir[11], buffer[513], r1[34], r2[34];
time_t rawtime;
  struct tm * timeinfo;

	strcpy(root.nome, ".");
			strcpy(root2.nome, "..");
			root.tipo = 0;
			root2.tipo = 0;
			root.tamanho = 0;
			root2.tamanho = 0;
			time ( &rawtime );
  		timeinfo = localtime (&rawtime);
			/*struct tm {
               int tm_sec;     Seconds (0-60)
               int tm_min;     Minutes (0-59)
               int tm_hour;    Hours (0-23)
               int tm_mday;    Day of the month (1-31)
               int tm_mon;     Month (0-11)
               int tm_year;    Year - 1900
               int tm_wday;    Day of the week (0-6, Sunday = 0)
               int tm_yday;    Day in the year (0-365, 1 Jan = 0)
               int tm_isdst;   Daylight saving time
        };
			root.criacao = (timeinfo->tm_year - 100) << 4;
			root.criacao = root.criacao + ((timeinfo->tm_mon + 1) << 5);
			root.criacao = root.criacao + ((timeinfo->tm_mday) << 5);
			root.criacao = root.criacao + ((timeinfo->tm_hour) << 6);
			root.criacao = root.criacao + ((timeinfo->tm_min) << 5);
			root.criacao = root.criacao + (timeinfo->tm_sec / 2);
			root2.criacao = root.criacao;
			root.modificacao = root.criacao;
			root2.modificacao = root.criacao;
			root.primeiro_setor = 0;
			root2.primeiro_setor = 0;

			memcpy(r1, &root, 32);
			memcpy(r2, &root2, 32);

			puts(r1);
			puts(r2);*/
  return 0;
}
