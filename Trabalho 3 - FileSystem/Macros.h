#include <stdlib.h>
#include <stdio.h>

#ifndef MACROS_H
#define MACROS_H

///Define uma funcao para limpeza de tela
#ifdef WIN32 //Win
    #define CLR system("cls\n");
#else //Unix
    #define CLR system ("clear\n");
#endif

///Display de mensagem, espera por enter para continuar
#define continuar printf(" - Enter para continuar...\n");getchar();

///Imprimir todas as infos. Se 0, imprime so o essencial
#define DISPLAY_INFO 1

///Aloca o tamanho de uma determinada struct. Retorna ponteiro
#define ALLOC_STRUCT( Struct ) ((Struct *)malloc( sizeof( Struct )))

///Error Level
#define EMPTY_FILE    2 //Arquivo vazio
#define EMPTY_FOLDER  1 //Pasta Vazia
#define NO_ERROR      0 //Sem erro
#define FAILURE      -1 //Falha geral
#define NO_SPACE     -2 //Espaço insuficiente
#define FILE_NOT_FND -3 //Arquivo nao encontrado
#define COMN_NOT_FND -4 //Comando nao encontrado

#endif



