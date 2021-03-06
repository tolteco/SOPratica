#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

#define SVER 1
#define MVER 0

//Informacoes iniciais iniciais
typedef struct IICumulo
{
  unsigned char nome_unidade[13];
  unsigned short qtde_blocos;
  unsigned char setores_por_bloco;
}__attribute__((packed)) IICumulo_t;

//Estrutura de entradas de metadados
typedef struct IDCumulo
{
  unsigned char  nome[13];
  unsigned char  extensao[4];
  unsigned char  tipo;
  unsigned int   tamanho;
  unsigned int	 criacao;
  unsigned int	 modificacao;
  unsigned short primeiro_setor;
}__attribute__((packed)) IDCumulo_t;

//Estrutura de bitmap
typedef struct BCumulo
{
  unsigned char  bit[8192];
}__attribute__((packed)) BCumulo_t;

//Variaveis globais
char comando[100], dir[513], data1[21], temp[5], cc[102], buffer54[510], nome[13], ext[4];
char *com, *com2, *com3;
char buffer5;
unsigned short pont, l_p[65536];
unsigned char AA;
unsigned int BB;
long long int J, Q88, Us, TBlo;
int i = 0;
int ec = 0, T;
FILE *d;
FILE *f;
IICumulo_t II;
BCumulo_t Bitmap;
IDCumulo_t ID;
time_t rawtime;
struct tm * timeinfo;

//Pega um inteiro e converte em string de data
void IntpData(unsigned int A){
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
}

void printTamanho(){
  if (ID.tamanho < 10 && ID.tamanho > 0){
    printf("      %d", ID.tamanho);
  } else if (ID.tamanho < 100 && ID.tamanho > 10){
    printf("     %d", ID.tamanho);
  } else if (ID.tamanho < 1000 && ID.tamanho > 100){
    printf("    %d", ID.tamanho);
  } else if (ID.tamanho < 10000 && ID.tamanho > 1000){
    printf("   %d", ID.tamanho);
  } else if (ID.tamanho < 100000 && ID.tamanho > 10000){
    printf("  %d", ID.tamanho);
  } else if (ID.tamanho < 1000000 && ID.tamanho > 100000){
    printf(" %d", ID.tamanho);
  } else if (ID.tamanho < 10000000 && ID.tamanho > 1000000){
    printf("%d", ID.tamanho);
  } else {
    printf("  oxe  ");
  }
}

void escreveID(){
  IntpData(ID.criacao);
  if (ID.tipo == 0){
    printf("%s  <DIR>  %s\n", data1, ID.nome);
  }
  else {
    printf("%s ", data1);
    printTamanho();
    printf(" %s\n", ID.nome);
  }
}

int copiario(){ //Copiar de dentro pra fora
  strcpy(cc, com3);
  strcat(cc, com2);
  printf("cc = .%s.\n", cc);
  f = fopen(com3, "w");
  if (f == NULL){
    printf("Erro, Arquivo invalido");
    return -1;
  }
  fread(&ID, 1, sizeof(ID), d);
  while (ID.nome[0] != 0){
    fread(&ID, 1, sizeof(ID), d);
    printf("Lida entrada: %s - %u\n", ID.nome, ID.criacao);
    if (strcmp(ID.nome, com2) == 0){
      break;
    }
  }
  J = 8208 + (ID.primeiro_setor * II.setores_por_bloco * 512);
  if (strcmp(ID.nome, com2) == 0){
    fseek(d, J, SEEK_SET);
    fread(&buffer54, 1, sizeof(buffer54), d);
    fread(&pont, 1, sizeof(pont), d);
    fwrite(buffer54, 1, sizeof(buffer54), d);
    while(pont != 0){
      J = 8208 + (pont * II.setores_por_bloco * 512);
      fseek(d, J, SEEK_SET);
      fread(&buffer54, 1, sizeof(buffer54), d);
      fread(&pont, 1, sizeof(pont), d);
      fwrite(buffer54, 1, sizeof(buffer54), d);
    }
  }	else {
    printf("Erro\n");
  }
  return 0;
}

int buscaponteiro(long long A){
  unsigned char C;
  int i = 0, j = 0, k = 1;
  while(A > 0){ //Enquanto o valor solicitado for maior que 0
    C = Bitmap.bit[i] & (256 >> k);
    //printf("Cbp = %u", C);
    if (C == 0){
      A = A - Us;
      l_p[j] = (i+1) * (k-1);
      j++;
      Bitmap.bit[i] = Bitmap.bit[i] | (256 >> k);
    }
    if (k == 8){
      k = 0;
      i++;
    }
    if (i == TBlo){
      return 1;
    }
    k++;
  }
  fseek(f, 0, SEEK_SET);
  fwrite(&II, 1, sizeof(IICumulo_t), d);
  fwrite(&Bitmap, 1, sizeof(char), d);
  return 0;
}

int copiaroi(){ //Copiar de fora pra dentro
  int i , j, k, l, m, C;
  long long int tam;
  IDCumulo_t entr;
  J = 8208;
  strcpy(cc, com2);
  printf("cc = .%s.\n", cc);
  f = fopen(cc, "r");
  if (f == NULL){
    printf("Erro, Arquivo invalido");
    return 0;
  }
  i = strlen(cc);
  while(cc[i] != '/'){
    printf("i = %d - %c\n", i, cc[i]);
    i--;
  }
  printf("i = %d - %c\n", i, cc[i]);
  i++;
  j = strlen(cc);
  while(cc[j] != '.'){
    printf("j = %d - %c\n", j, cc[j]);
    j--;
  }
  printf("j = %d - %c\n", j, cc[j]);
  j++;
  l = 0;
  for (k = i; k < j-1; k++){
    if (l == 13)
    break;
    nome[l] = cc[k];
    l++;
  }
  l = 0;
  for (m = j; m < strlen(cc); m++){
    if (l == 4)
    break;
    ext[l] = cc[m];
    l++;
  }
  printf("Nome = .%s. - Ext = .%s.\n", nome, ext);
  fread(&ID, 1, sizeof(ID), d);
  while (ID.nome[0] != 0){
    fread(&ID, 1, sizeof(ID), d);
    printf("Nome = %s, ext = %s, Criacao = %u\n", ID.nome, ID.extensao, ID.criacao);
    J = J + 32;
  }
  printf("++J = %llu\n", J);
  fseek(d, J, SEEK_SET);
  strcpy(entr.nome, nome);
  strcpy(entr.extensao, ext);
  entr.tamanho = 0;
  time ( &rawtime );
  timeinfo = localtime (&rawtime);
  entr.criacao = (timeinfo->tm_year - 100) << 4;
  entr.criacao = (entr.criacao + (timeinfo->tm_mon + 1)) << 5;
  entr.criacao = (entr.criacao + (timeinfo->tm_mday)) << 5;
  entr.criacao = (entr.criacao + (timeinfo->tm_sec / 2)) << 6;
  entr.criacao = (entr.criacao + (timeinfo->tm_min)) << 5;
  entr.criacao = entr.criacao + timeinfo->tm_hour;
  entr.modificacao = entr.criacao;
  fseek(f, 0L, SEEK_END);
  entr.tamanho = ftell(f);
  printf("Nome = .%s. - Ext = .%s. - Tam = %u - Cri = %u - Mod = %u\n", entr.nome, entr.extensao, entr.tamanho, entr.criacao, entr.modificacao);
  C = buscaponteiro(entr.tamanho);
  if (C == 1) {
    printf("Erro, Espaco insuficiente\n");
    return -1;
  }
  entr.primeiro_setor = l_p[0];
  fwrite(&entr, 1, sizeof(entr), d);
  i = 0;
  fseek(f, 0, SEEK_SET);
  while (!feof(f)) {
    J = 8208 + (l_p[i] * (II.setores_por_bloco * 512));
    fseek(d, J, SEEK_SET);
    printf("J = %llu\n", J);
    for (j = 0; j < ((II.setores_por_bloco * 512) - 2); j++){
      fread(&buffer5, 1, sizeof(char), f);
      if (feof(f)){
        memset(&buffer5, 0, sizeof(char)) ;
      }
      //printf("Lido : .%c.\n", buffer5);
      fwrite(&buffer5, 1, sizeof(char), d);
    }
    fwrite(&l_p[i+1], 1, sizeof(short), d);
    //copy /home/administrador/Downloads/R.txt oxe
    i++;
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
    d = fopen(op[1], "r+");
    if (d == NULL){
      printf("Erro, Arquivo invalido");
      return 0;
    }

    //Le as estruturas...
    fread(&II, 1, sizeof(II), d);
    //printf("Nome: %s\n", II.nome_unidade);
    //printf("NBrk: .%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.\n", II.nome_unidade[0], II.nome_unidade[1], II.nome_unidade[2], II.nome_unidade[3], II.nome_unidade[4], II.nome_unidade[5], II.nome_unidade[6], II.nome_unidade[7], II.nome_unidade[8], II.nome_unidade[9], II.nome_unidade[10], II.nome_unidade[11], II.nome_unidade[12]);
    //printf("Bloc: %hu\n", II.qtde_blocos);
    //printf("Stre: %hhu\n", II.setores_por_bloco);
    fread(&Bitmap, 1, sizeof(Bitmap), d);

    //Seta Inicio
    strcat(dir, "\\");
    Q88 = 8208;

    if (II.setores_por_bloco > 1){
      Us = (512 * (II.setores_por_bloco - 1)) + 510;
    } else {
      Us = 510;
    }
    if (II.qtde_blocos == 0){
      TBlo = 65536;
    }	else {
      TBlo = II.qtde_blocos;
    }
    while (ec == 0){ //Ate que o comando de saida seja dado
      fseek(d, Q88, SEEK_SET);
      printf("%s%s : ", op[1] ,dir);
      fgets (comando, 98, stdin);
      com = strtok(comando, " "); //com recebe a primeira parte do comando, ate o primeiro espaco
      strcpy(cc, com);
      T = strlen(cc);
      if (cc[T-1] == '\n'){
        cc[T-1] = '\0';
      }
      //printf("CC = .%s.", cc);

      if (strcmp(cc, "copy") == 0 || strcmp(cc, "copy") == 0){
        com2 = strtok(NULL, " ");
        com3 = strtok(NULL, " ");
        printf("de.%s.-para.%s.\n", com2, com3);
        strcpy(cc, com3);
        T = strlen(cc);
        cc[T-1] = '\0';
        if (cc[0] == '/'){
          printf("Copia de denro para fora\n");
          T = copiario();
          if (T == 1){
            return -1;
          }
        }
        strcpy(cc, com2);
        if (cc[0] == '/'){
          printf("Copia de fora para dentro\n");
          T = copiaroi();
          if (T == 1){
            return -1;
          }
        } else {
          printf("Erro\n");
        }
      }

      if (strcmp(cc, "oxe") == 0 || strcmp(cc, "oxe") == 0){
        while (!feof(d)){
          i++;
          fread(&AA, 1, sizeof(AA), d);
          printf("for byte %i = .%hhu.\n", i, AA);
          system("sleep 500");
        }
      }
      if (strcmp(cc, "dir") == 0 || strcmp(cc, "DIR") == 0){
        fread(&ID, 1, sizeof(ID), d);
        while (ID.nome[0] != 0){
          /*printf("NBrk: .%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.-.%hhu.\n", ID.nome[0], ID.nome[1], ID.nome[2], ID.nome[3], ID.nome[4], ID.nome[5], ID.nome[6], ID.nome[7], ID.nome[8], ID.nome[9], ID.nome[10], ID.nome[11], ID.nome[12]);
          printf("\n\n.%hhu.-.%hhu.-.%hhu.-.%hhu.", ID.extensao[0], ID.extensao[1], ID.extensao[2], ID.extensao[3]);
          printf("--%u--", ID.tamanho);
          printf("++.%u.++", ID.criacao);
          printf("++.%u.++", ID.modificacao);*/
          escreveID();
          fread(&ID, 1, sizeof(ID), d);
        }
      }
      if (strcmp(cc, "help") == 0 || strcmp(cc, "HELP") == 0){ //Opcao de ajuda
        printf("Sistema de arquivos Cumulo\n Versao %d.%d\n\n", SVER, MVER);
        printf("-------------------------------------------\nListagem de arquivos do diretorio\n\n Chamada:\n\n");
        printf("  dir\n");
        printf("-------------------------------------------\n\nCopia de arquivos de e para a unidade\n\n Chamada:\n\n");
        printf("  copy [origem] [destino]\n");
        printf("  [origem]  Local de origem dos dados\n");
        printf("  [destino] Local de destino dos dados\n");
        printf("  Observacao: Espacos sao estritamente proibidos\n\n");
      }
      if (strcmp(cc, "exit") == 0 || strcmp(cc, "EXIT") == 0){ //Opcao de saida
        fclose(d);
        return 0;
      }
    }
  } else {
    printf("Erro, quantidade de argumentos invalido.\n");
    return -1;
  }
  return 0;
}
