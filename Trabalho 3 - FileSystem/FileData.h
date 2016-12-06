#include "Macros.h"

#ifndef FILEDATA_H
#define FILEDATA_H

#define NEW_FILEDATA ALLOC_STRUCT(FileAttribs)

#define TIPO_DIRETORIO 0x00
#define TIPO_ARQUIVO   0x01

typedef struct FileAttribs {
    unsigned char  nome_arquivo[13];
    unsigned char  extensao_arquivo[4];
    unsigned char  tipo; //Vide constantes acima
    unsigned int   tam_arquivo;
    unsigned int   tempo_criacao;
    unsigned int   tempo_modif;
    unsigned short first_blk_ptr;
}__attribute__((packed));

/**
* Cria um bloco de arquivos vazio
*/
FileAttribs* newFileAttribs(void){
    FileAttribs* data = NEW_FILEDATA;
    return data;
}

#endif

