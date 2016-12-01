#include "Macros.h"

#ifndef BITMAP_H
#define BITMAP_H

#define NEW_BITMAP ALLOC_STRUCT(BitMap)

#define BIT_MAP_SIZE 8192

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
    int i = 0;
    while (i < BIT_MAP_SIZE){
        char eightBlks = bitMap->bitmap[i];
        if (eightBlks & 0x80 == 0x00) return i;
        i++;
        if (eightBlks & 0x40 == 0x00) return i;
        i++;
        if (eightBlks & 0x20 == 0x00) return i;
        i++;
        if (eightBlks & 0x10 == 0x00) return i;
        i++;
        if (eightBlks & 0x08 == 0x00) return i;
        i++;
        if (eightBlks & 0x04 == 0x00) return i;
        i++;
        if (eightBlks & 0x02 == 0x00) return i;
        i++;
        if (eightBlks & 0x01 == 0x00) return i;
        i++;
    }

    return NO_SPACE;
}

int markAsOccupiedCluster(BitMap* bit, unsigned short ptr){
    int i = 0;
    while (i < BIT_MAP_SIZE){
        if (i == ptr) bit->bitmap[i] |= 0x80;
        i++;
        if (i == ptr) bit->bitmap[i] |= 0x40;
        i++;
        if (i == ptr) bit->bitmap[i] |= 0x20;
        i++;
        if (i == ptr) bit->bitmap[i] |= 0x10;
        i++;
        if (i == ptr) bit->bitmap[i] |= 0x08;
        i++;
        if (i == ptr) bit->bitmap[i] |= 0x04;
        i++;
        if (i == ptr) bit->bitmap[i] |= 0x02;
        i++;
        if (i == ptr) bit->bitmap[i] |= 0x01;
        i++;
    }
}

int markAsFreeCluster(BitMap* bit, unsigned short ptr){
    int i = 0;
    while (i < BIT_MAP_SIZE){
        if (i == ptr) bit->bitmap[i] &= 0x7F;
        i++;
        if (i == ptr) bit->bitmap[i] &= 0xBF;
        i++;
        if (i == ptr) bit->bitmap[i] &= 0xDF;
        i++;
        if (i == ptr) bit->bitmap[i] &= 0xF7;
        i++;
        if (i == ptr) bit->bitmap[i] &= 0xFB;
        i++;
        if (i == ptr) bit->bitmap[i] &= 0x04;
        i++;
        if (i == ptr) bit->bitmap[i] &= 0xFD;
        i++;
        if (i == ptr) bit->bitmap[i] &= 0xFE;
        i++;
    }
}

#endif

