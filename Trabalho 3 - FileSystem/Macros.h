#include <stdlib.h>
#include <stdio.h>
#include <string.h>

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
#define STRANGE_PATH -5 //Caminho ate arquivo e estranho (Falta alguma / ou etc)

/**
* Valor inteiro, quantia de bits, ponteiro de retorno.
* Ponteiro tem que estar alocado como array, ou ha explosoes.
*/
void toBinaryString(int value, int bitsCount, char* output){
    int i;
    output[bitsCount];

    for (i = bitsCount - 1; i >= 0; --i, value >>= 1)
        output[i] = (value & 1) + '0';
}

/**
* String binaria (BigEndian) para
* unsigned int.
*/
unsigned int toUnsignedInt(char* bits){
    unsigned int val;

    for (val = 0; *bits; bits++) {
        if (*bits == '1')
            val = (val << 1) | 1;
        else if (*bits == '0' )
            val <<= 1;
        else
            break;
    }

    return val;
}

void stringChopBegin(char* str, int sizeToChop){
    int len = strlen(str);
    memmove(str, str+sizeToChop, len-sizeToChop+1);
}

#endif



