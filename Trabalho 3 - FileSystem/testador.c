#include <stdio.h>
#include <stdlib.h>
#include "Macros.h"
#include <string.h>
#include "FileData.h"
#include "InitBlock.h"


void delimit(){
    char *token;
    char line[] = "AAAAA.txt";
    char *search = ".";


    // Token will point to "SEVERAL".
    token = strtok(line, search);
    printf("%s,", token);

    // Token will point to "WORDS".
    token = strtok(NULL, search);
    printf("\n%s,", token);
}

int main (){
    printf("LKJ\n\n");
    char f[]= "AAABCDDD.txtaaaaaaaaa";
printf("LKJ\n\n");
    stringChopBegin(f, 3);
    printf("%s, ", f);

    /*FileAttribs* file = newFileAttribs();
    strcpy(file->nome_arquivo, "0000000000000");
    int i = isFileEmpty(file);

    printf("%d\n\n\n", i);

    tempoAtual();*/

    return 0; //Error Level
}

