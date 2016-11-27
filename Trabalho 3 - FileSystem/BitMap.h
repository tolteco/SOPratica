#include "Macros.h"

#ifndef BITMAP_H
#define BITMAP_H

#define NEW_BITMAP ALLOC_STRUCT(BitMap)

typedef struct BitMap {
    unsigned char bitmap[8192];
}__attribute__((packed));

/**
* Cria um bitmap vazio.
*/
BitMap* newBitMap(void){
    BitMap* bitMap = NEW_BITMAP;
    return bitMap;
}

/**
* Retorna o "ponteiro" para o
* proximo bloco livre
*/
unsigned short findEmptyCluster(BitMap* bitMap){

}

#endif

