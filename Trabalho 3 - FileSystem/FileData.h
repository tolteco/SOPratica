#include "Macros.h"
#include <stdlib.h>
#include <string.h>
#include <time.h>

#ifndef FILEDATA_H
#define FILEDATA_H

#define NEW_FILEDATA ALLOC_STRUCT(FileAttribs)

#define YEAR_BITS   7
#define MONTH_BITS  4
#define DAY_BITS    5
#define HOUR_BITS   5
#define MINS_BITS   6
#define SECS_BITS   5

#define TIPO_DIRETORIO 0x00
#define TIPO_ARQUIVO   0x01

typedef struct FileAttribss {
    unsigned char  nome_arquivo[13];
    unsigned char  extensao_arquivo[4];
    unsigned char  tipo; //Vide constantes acima
    unsigned int   tam_arquivo;
    unsigned int   tempo_criacao;
    unsigned int   tempo_modif;
    unsigned short first_blk_ptr;
}__attribute__((packed)) FileAttribs;

void zeroFill(FileAttribs* filed){
    int len = 13;
    char nome[len];
    int i=0;
    while(i<len){
        nome[i] = 0; i++;
    }
    memmove(filed->nome_arquivo, nome, len);
}

/**
* Cria um bloco de arquivos vazio
*/
FileAttribs* newFileAttribs(void){
    FileAttribs* data = malloc(32);
    zeroFill(data);
    return data;
}



/**
* Arquivo vazio se todos os bytes do
* nome sao zerados.
*/
int isFileEmpty(FileAttribs* filed){
    int len = 13;
    char nome[len];
    memmove(nome, filed->nome_arquivo,len);

    int i=0;
    while (i < len){
        if (nome[i] |0x00 != 0x00) return -1; //IS NOT EMPTY
        i++;
    }

    return 1; //IS EMPTY
}

char* splitFileName(char fullname[]){
    char* name;
    char* ext;

    char *look = ".";
    name = strtok(fullname, look);
    ext = strtok(NULL, look);

    if (strlen(name) > 13){
        memmove(name, name, 13);
        name[13] = '\0';
    }

    return name;
}

char* splitFileExt(char fullname[]){
    char* name;
    char* ext;

    char *look = ".";
    name = strtok(fullname, look);
    ext = strtok(NULL, look);

    if (strlen(ext) > 4){
        memmove(name, name, 4);
        ext[4] = '\0';
    }

    return ext;
}

int tempoAtual(){
    time_t rawtime;
    struct tm * timeinfo;

    time ( &rawtime );
    timeinfo = localtime ( &rawtime );

    char year[YEAR_BITS+1]; year[YEAR_BITS] = '\0';
    toBinaryString(timeinfo->tm_year-80, YEAR_BITS, year); //Anos sao -1900, entao so reduz 80

    char month[MONTH_BITS+1]; month[MONTH_BITS] = '\0';
    toBinaryString(timeinfo->tm_mon+1, MONTH_BITS, month); //Meses aumenta 1

    char day[DAY_BITS+1]; day[DAY_BITS] = '\0';
    toBinaryString(timeinfo->tm_mday, DAY_BITS, day);

    char hour[HOUR_BITS+1]; hour[HOUR_BITS] = '\0';
    toBinaryString(timeinfo->tm_hour, HOUR_BITS, hour);

    char mins[MINS_BITS+1]; mins[MINS_BITS] = '\0';
    toBinaryString(timeinfo->tm_min, MINS_BITS, mins);

    char secs[SECS_BITS+1]; secs[SECS_BITS] = '\0';
    toBinaryString(timeinfo->tm_sec/2, SECS_BITS, secs); //Segs / 2

    strcat(year, month);
    strcat(year, day);
    strcat(year, hour);
    strcat(year, mins);
    strcat(year, secs);

    return toUnsignedInt(year);
}

#endif

