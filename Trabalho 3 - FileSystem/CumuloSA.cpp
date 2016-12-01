#include <stdio.h>
#include <string.h>
#include <time.h>

#define SVER 1
#define MVER 0

char comando[100], dir[102], com[5], com2[100];
int exit = 0;

void tabela(){//Esta fucao deixa a tabela de informacoes bonita
  if (tam>=0 &&tam<10){
      printf("%s %d         .     %d %s", data, hora, tam, nome); //nao sera impresso assim
  }else if(tam>9 && tam<100){
      printf("%s %d         .    %d %s", data, hora, tam, nome);
  }else if(tam>99 && tam<1000){
      printf("%s %d         .   %d %s", data, hora, tam, nome);
  }else if(tam>999 && tam<10000){
      printf("%s %d         .  %d %s", data, hora, tam, nome);
  }else if(tam>9999 && tam<100000){
      printf("%s %d         . %d %s", data, hora, tam, nome);
  }else if(tam>99999 && tam<1000000){
      printf("%s %d         .%d %s", data, hora, tam, nome);
  }
  return 0;
}

int main(int argc, char *op[]){
  if (argc == 2){ //Verifica se a quantidade de argumentos e valida
    if ((strcmp(op[1], "-h") == 0) || (strcmp(op[1], "/h") == 0) || (strcmp(op[1], "/help") == 0) || (strcmp(op[1], "-help") == 0)){ //Opcao Help
      printf("Sistema de arquivos Cumulo V%d.%d\n\n Chamada:\n\n", SVER, MVER);
      printf("	NOME.x [unidade]\n");
      printf("  [unidade]    Unidade separada para a unidade (sda, sdb1, ...)\n");
      return 0;
    }
    /*strcpy(dir, "/dev/");
		strcat(dir, op[1]); //recebe o parametro e adiciona ao fim da string, que ficara como "/dev/???*"
		d = fopen(dir, "w"); //Abre o diretorio para escrita*/ //Isolado para testes com arquivos de imagem
		d = fopen(op[1], "w");
    while (exit == 0){ //Ate que o comando de saida seja dado
      printf("%s : ", dir);
      fgets (comando, 98, stdin);
      com = strtok(comando, " "); //com recebe a primeira parte do comando, ate o primeiro espaco
      if (strcmp(com, "help") == 0 || strcmp(com, "HELP") == 0){ //Opcao de ajuda
        com2 = strtok(NULL, " ");
        if(strcmp(com, "help") == 0 || strcmp(com, "HELP") == 0){ //Help sobre a funcao help
          printf("Fornece informacoes de ajuda sobre comandos do Cumulo\n\n Chamada:\n\n", SVER, MVER);
          printf("	HELP [comando]\n");
          printf("  [comando]    Comando a ter ajuda informada\n");
        }
        else if(strcmp(com, "cd") == 0 || strcmp(com, "cd") == 0){ //Help sobre a funcao help
          printf("Altera a pasta atual\n\n Chamada:\n\n", SVER, MVER);
          printf("	CD [caminho | ..]\n");
          printf("  [caminho]    Diretorio a se adentrar\n");
          printf("  ..           Ir a pasta pai");
        }
      }
      if else(strcmp(com, "exit") == 0 || strcmp(com, "EXIT") == 0){ //Opcao de saida
        exit = 1;
      }
    }
    else if (strcmp(com, "cd") == 0 || strcmp(com, "CD") == 0){
        com2 = strtok(NULL, " ");

        while(strcmp(com2, NOME.nome) != 0){
          fread(&NOME, 1, sizeof(NOME), d);//"d" e o ponteiro para o arquivo
        }
        if (NOME.tipo==0){
          fseek//ver com o juliano
        }
      }
      else if(strcmp(com, "dir") == 0 || strcmp(com, "DIR") == 0){
        while(strcmp(com2, "") != 0){
          fread(&NOME, 1, sizeof(NOME), d);//"d" e o ponteiro para o arquivo, PRECISO DO FSEEK AQUI E PRECISO SABER TBEM AS VARIAVEIS Q VIRAM DE VC PRA EU COLOCAR AQUI, MAS FAREI AMANHA ISSO
          if(NOME.tipo==0){
            printf("%s %d   <DIR>       %s", data, hora, nome);
          }else{
            tabela();
          }
        }
      }
  } else {
    printf("Erro, quantidade de argumentos invalido.\n");
		return -1;
  }
  return 0;
}
