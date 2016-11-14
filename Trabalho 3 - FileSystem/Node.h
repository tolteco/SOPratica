#include "Macros.h"

#ifndef NODE_H
#define NODE_H

#define NEWNODE ALLOC_STRUCT(Node)

typedef struct node Node;

/**
* Define a estrutura basica de
* um nodo.
*/
struct node{
    int info;
    Node* next;
    Node* prev;
};

/**
* Cria um nodo vazio
*/
Node* create(void){
    return NULL;
}

#endif



