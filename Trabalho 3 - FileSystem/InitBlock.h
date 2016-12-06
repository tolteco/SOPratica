#include "Macros.h"

#ifndef INITBLOCK_H
#define INITBLOCK_H

#define NEW_INITBLOCK ALLOC_STRUCT(InitBlock)

/**
* Tamanho de um setor.
*/
#define STD_SECTOR_SIZE       512
/**
* Numero padrao (e maximo)
* de setores.
*/
#define STD_SECTOR_COUNT    65535 ///0xFFFF
/**
* Numero padrao de setores
* por bloco.
*/
#define STD_SECTORS_PER_BLK   0x1 ///1 - Dec

typedef struct InitBlock {
    unsigned char  nome_unidade[13];
    unsigned short qtde_blocos;
    unsigned char  setores_por_bloco;
}__attribute__((packed));

/**
* Cria um bloco inicial padrao.
*/
InitBlock* newInitBlk(void){
    InitBlock* blk = NEW_INITBLOCK;
    blk->qtde_blocos = STD_SECTOR_COUNT;
    blk->setores_por_bloco = STD_SECTORS_PER_BLK;
    return blk;
}

#endif

