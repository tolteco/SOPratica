#include <stdio.h>
#include <string.h>
#include <time.h>

#define secblc 33554432

/**
*	Formatador de unidades elaborado para o sistema Cumulo
*/

typedef struct Centry
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
Centry_t root3;
FILE *d;
char dir[11], buffer[513], r1[34], r2[34], r[65], buffer2[512];
int tipo = 0, setpblc = 0, totblc = 0;
long long int tamsolic;
unsigned short int blocostotal;
unsigned char setorespbloco;

int CalculaTamanho(){
	int i;
	if (tamsolic > 4294967296){ //Se o tamanho e maior que o maximo
		return -1;
	}
	if (tamsolic % secblc == 0){ //Se o tamanho encaixa em algum dos 32 em 32 mega perfeitamente
		setpblc = tamsolic/secblc; //Da a quantidade de setores por bloco
		totblc = 65536;
	} else {
		if (tamsolic < secblc){ //Se o tamanho for menor que 32 mega
			setpblc = 1;
			if (tamsolic % 512 == 0){ //Se for divisivel por 512 (encaixar em setores)
				totblc = tamsolic/512;
			} else {
				return -1;
			}
		} else { //Se for maior que 32 mega (ou igual)
			if (tamsolic % 512 != 0){ //Se o valor nao for divisivel por 512
				return -1;
			}
			setpblc = 1; //Pra comecar em um
			while(tamsolic%(512*setpblc) !=0 || tamsolic/(512*setpblc) > 65536){
				//Enquanto nao der um numero inteiro ou a quantidade de blocos ficar maior que 65536, coloca mais um setor
				//em cada bloco e tenta dividir ele pelo tamanho que foi pedido, se chegou nesse ponto e porque vai encaixar,
				//ainda assim tem o if pra verificar
				setpblc++;
				if (setpblc > 128){
					return -1;
				}
			}
			totblc = tamsolic/(512*setpblc); //Total de blocos na unidade
		}
	}

	if (totblc == 65536){ //Como 65536 nao e representavel em 16 bits, usamos o zero para o maior
		blocostotal = 0;
	} else if (totblc < 65536 && totblc > 0){ //Se for qualquer outro valor (nao pode ser zero)
		blocostotal = totblc;
	} else { //Deu zero ou alguma coisa maior que 65536
		return -1
	}

	if (setpblc > 0 && setpblc < 129){ //A quantidade de setores por bloco deve estar entre 1 e 128
		setorespbloco = setpblc;
	} else {
		return -1;
	}
	return 0;
}

int SugereTamanho(){
	int aux, tamsolicDw, tamsolicUp, D;

	aux = tamsolic%512;
	tamsolicDw = tamsolic-aux;
	tamsolicUp = tamsolic+(512-aux);

	printf("Escolha um tamanho permitido sugerido. Os tamanhos sao: %d e %d.\n", tamsolicDw, tamsolicUp);
	printf("Escolha a opcao 0 para retornar ao menu, opcao 1 para %d ou 2 para %d.", tamsolicDw, tamsolicUp);
	scanf("%d",&D);

	if(D == 0){
		return -1;
	}else if(D == 1){
		tamsolic=tamsolicDw;
		return 0;

	}else if(D == 2){
		tamsolic = tamsolicUp;
		return 0;
	}else{
		return -1;
	}

	return 0;
}

int main (int argc, char *op[]){
	char Time[30];
	time_t rawtime;
  struct tm * timeinfo;
	char *token; //Para separacao da data
	int A; //Variavel usada para verificacao de ero de funcoes
	if (argc == 5){ //Verifica se a quantidade de argumentos e valida
		strcpy(dir, "/dev/");
		strcat(dir, op[1]); //recebe o parametro e adiciona ao fim da string, que ficara como "/dev/???*"
		d = fopen(dir, "w"); //Abre o diretorio para escrita
		if (d != NULL){
			if (strlen(op[2]) > 13){ //Se a string for maior que o maximo permitido, para
				printf("Erro, quantidade de caracteres maior que o permitido para o nome da unidade.\n");
				return -1;
			}
			tamsolic = atoi(op[3]); //Converte o tamanho solicitado da unidade para inteiro
			if (op[4] == 't'){ //Se a opcao for tamanho total, o tipo e zero
				tipo = 0;
			} else if (op[4] == 'e'){ //Se for tamanho enderecavel, o tipo e um
				tipo = 1
			} else {
				printf("Erro, opcao invalida; argumento 4.\n");
				return -1;
			}
			A = CalculaTamanho();
			if (A != 0){
				printf("Erro, tamanho invalido.");
				B = SugereTamanho();
				if (B == -1){
					return -1;
				}else{
					A = CalculaTamanho();
					if(A != 0){
						printf("Erro.");
						return -1;
					}
				}
			}

			//Inicio da formatacao, trecho onde efetivamente se escrevem dados na unidade a ser formatada
			//Dados iniciais
			fwrite(op[2], 1, strlen(op[2]), d);
			fwrite(blocostotal, 1, strlen(blocostotal), d);
			fwrite(setorespbloco, 1, strlen(setorespbloco), d)

			//bitmap
			memset(buffer, 0, strlen(buffer));
			fwrite(buffer, sizeof(char), 8192, d); //Se esse nao imprimir 16 vezes testar com os comentados ai embaixo
			/*fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);
			fwrite(buffer, 1, sizeof(buffer), d);*/

			//Root
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
        };*/
			root.criacao = (timeinfo->tm_year - 100) << 4;
			root.criacao = root.criacao + ((timeinfo->tm_mon + 1) << 5);
			root.criacao = root.criacao + ((timeinfo->tm_mday) << 5);
			root.criacao = root.criacao + ((timeinfo->tm_hour) << 6);
			root.criacao = root.criacao + ((timeinfo->tm_min) << 5);
			root.criacao = root.criacao + (timeinfo->tm_sec / 2);
			root2.criacao = root.criacao;
			root.modificacao = root.criacao;
			root2.modificacao = root.criacao;
			root.primeirosetor = 0;
			root2.primeirosetor = 0;

			memcpy(r1, &root, 32);
			r1[32] = '\0';
			memcpy(r2, &root2, 32);
			r2[32] = '\0';
			strcat(r, r1);
			strcat(r, r2);
			strcat(buffer2, r);
			fwrite(buffer2, 1, sizeof(buffer2), d);

			//Zerar blocos
			while(totblc > 0){
				fwrite(buffer, 1, sizeof(buffer), d);
				totblc--;
			}
		} else {
			printf("Erro, ponteiro nulo para unidade solicitada.\n");
			return -1;
		}
	} else if (argc == 2){
		if ((strcmp(op[1], "-h") == 0) || (strcmp(op[1], "/h") == 0) || (strcmp(op[1], "/help") == 0) || (strcmp(op[1], "-help") == 0)){ //Opcao Help
			printf("Formatador de unidades elaborado para o sistema Cumulo\n\n Chamada:\n\n");
      printf("	NOME.x [unidade] [novo label] [tamanho] [t | e]\n");
      printf("  [unidade]    Unidade separada para a unidade (sda, sdb1, ...)\n");
      printf("  [novo label] Nome dado a unidade apos a formatacao (maximo de 13 caracteres)\n");
      printf("  [tamanho]    Tamanho em bytes para formatar a unidade\n");
      printf("  t            Tamanho total da unidade, incluindo dados iniciais e bitmap\n");
      printf("  e            Tamanho da unidade desconsiderando dados iniciais e bitmap\n");
		} else {
			printf("Erro, quantidade de argumentos invalido.\n");
			return -1;
		}
  }	else {
		printf("Erro, quantidade de argumentos invalido.\n");
		return -1;
	}
	return 0;
}
