#include "Macros.h"
#include "BitMap.h"
#include "InitBlock.h"
#include "FileData.h"
#include <string.h>

#ifndef CUMULO_H
#define CUMULO_H

typedef struct FileSystem {
    InitBlock* initBlock;
    BitMap* bitMap;
};

/**
* Estrutura com informacoes
* principais do sistema.
*/
FileSystem* sistema;
/**
* Ponteiro para o arquivo
* de imagem a ser usado
* pelo sistema.
*/
FILE* pointer;

//Prototipos
size_t getBlockSize(FileSystem* sistema);
int    newEmptyFile(char* path);
int    newFile(char* nome, char* ext, char* conteudo);
int    writeData(char* conteudo, FILE* seek);
int    deleteFile(char* path);
char*  openFile(char* path);
int    copyFile(char* nomeCompletoDaOrigem, char* nomeDestino, char* extDestino);
int    exportFile(char* nomeOrigem, char* extOrigem, char* nomeCompletoDoDestino);
size_t locateBeginingOfFile(char* path);
void   flushDataStructures();

/// Implementacoes
/**
* Metodo para calculo do numero de bytes
* de cada bloco do sistema.
*
* Inputs:
* ---------
* sistema: Ponteiro para informações do
*          sistema.
*
* Outputs:
* ---------
* size_t: Tamanho em Bytes.
*/
size_t getBlockSize(FileSystem* sistema){
    return sistema->initBlock->setores_por_bloco * STD_SECTOR_SIZE;
}

/**
* Metodo para criacao de arquivos
* novos vazios.
*
* Inputs:
* ---------
* path: String com caminho para o arquivo.
*
* Outputs:
* ---------
* ERROR_LEVEL: Erro de acordo com os
*              definidos em MACROS_H.
*/
int newEmptyFile(char* path){
    return newFile(nome, ext, NULL);
}

/**
* Metodo para criacao de arquivos
* novos com conteudo passado via
* parametro.
*
* Inputs:
* ---------
* path:     String com caminho para o arquivo;
* conteudo: String com o conteudo do arquivo
*           (Se passado NULL, arquivo vazio e
*           criado).
*
* Outputs:
* ---------
* ERROR_LEVEL: Erro de acordo com os definidos
*              em MACROS_H.
*/
int newFile(char* path, char* conteudo){
    FILE* seek = pointer;
    int subDirs = countNumberOfSubDirs(path);

    if(subDirs == 1){

        size_t tableOffset = sizeof(InitBlock) + sizeof(BitMap);
        FSEEK(seek, tableOffset,SEEK_SET);

        FileAttribs* file = NEW_FILEDATA;
        removeFirstSlash(path);
        char f1[strlen(path)];
        strcpy(f1, path);
        char f2[strlen(path)];
        strcpy(f2, path);

        strcpy(file->nome_arquivo, splitFileName(f1));
        strcpy(file->extensao_arquivo, splitFileExt(f2));
        file->tam_arquivo = 0;
        file->tempo_criacao = tempoAtual();
        file->tempo_modif = tempoAtual();

        if (conteudo == NULL) return NO_ERROR;
        file->tam_arquivo = strlen(conteudo);

        file->first_blk_ptr = findEmptyCluster(sistema->bitMap);
        markAsOccupiedCluster(file->first_blk_ptr);
        FSEEK(seek, file->first_blk_ptr, SEEK_CUR);

        return writeData(conteudo, seek);
    }



    return STRANGE_PATH;
}

/**
* conteudo; ponteiroNoPrimeiroBloco da imagem
*/
int writeData(char* conteudo, FILE* seek){
    int blkSize = sistema->initBlock->setores_por_bloco * STD_SECTOR_SIZE;

    if (strlen(conteudo) < blkSize){
        fwrite(conteudo, 1, sizeof(conteudo), seek);
    } else {
        int noBlks = (strlen(conteudo) + blkSize - 1) / blkSize;
        int i=0;
        unsigned short next;
        while(i< noBlks-1){
            fwrite(conteudo, 1, blkSize-sizeof(short), seek);
            next = findEmptyCluster(sistema->bitMap);
            fwrite(next, 1, sizeof(short), seek);
            markAsOccupiedCluster(next);
            FSEEK(seek, next, SEEK_CUR);

            i++;
        } //Se nao for blocos completos?
        fwrite(conteudo, 1, blkSize-sizeof(short), seek);
        fwrite(next, 1, sizeof(short), seek);
    }
}

/**
* Metodo para exclusao de arquivos.
*
* Inputs:
* ---------
* path: String com caminho para o arquivo.
*
* Outputs:
* ---------
* ERROR_LEVEL: Erro de acordo com os
*              definidos em MACROS_H.
*/
int deleteFile(char* path){
    FILE* seek = pointer;
    int subDirs = countNumberOfSubDirs(path);

    if(subDirs == 1){

        size_t tableOffset = sizeof(InitBlock) + sizeof(BitMap);
        FSEEK(seek, tableOffset,SEEK_SET);

        FileAttribs* file = NEW_FILEDATA;
        removeFirstSlash(path);
        char f1[strlen(path)];
        strcpy(f1, path);
        char f2[strlen(path)];
        strcpy(f2, path);

        FileAttribs* searching = NEW_FILEDATA;
        FILE* seek2 = seek;
        int i=0;
        do{
            fread(&searching, sizeof(searching), 1, seek2);
            i++; //Number of structs
        }while(strcmp(searchin,input) != 0); //Ler enquanto nao acha o nome

        FSEEK(seek, sizeof(searching)*(i-1), SEEK_CUR);

        fwrite(file, 1, sizeof(file), seek);

        markAsFreeCluster(searching->first_blk_ptr);

        return NO_ERROR;
    }

}

/**
* Metodo para abertura de arquivos
* (Retorna String com conteudo).
*
* Inputs:
* ---------
* path: String com caminho para o arquivo.
*
* Outputs:
* ---------
* Conteudo*
* FileAttribs* data: Ponteiro pra retorno.
* char* com conteudo/info do arquivo.
*/
char* openFile(char* path, FileAttribs* meta){
    FILE* seek = pointer;
    int subDirs = countNumberOfSubDirs(path);

    if(subDirs == 1){

        size_t tableOffset = sizeof(InitBlock) + sizeof(BitMap);
        FSEEK(seek, tableOffset,SEEK_SET);

        FileAttribs* file = NEW_FILEDATA;
        removeFirstSlash(path);
        char f1[strlen(path)];
        strcpy(f1, path);
        char f2[strlen(path)];
        strcpy(f2, path);

        FileAttribs* searching = NEW_FILEDATA;
        FILE* seek2 = seek;
        int i=0;
        do{
            fread(&searching, sizeof(searching), 1, seek2);
            i++; //Number of structs
        }while(strcmp(searchin,input) != 0) //Ler enquanto nao acha o nome

        seek2 = seek;
        int blkSize = sistema->initBlock->setores_por_bloco * STD_SECTOR_SIZE;
        int noBlks = (searching->tam_arquivo + blkSize - 1) / blkSize;
        char* str = malloc(sizeof(char) * searching->tam_arquivo);
        do{
            FSEEK(seek, blkSize*searching->first_blk_ptr, SEEK_CUR); //So lida com blocos inteiros, nao "meio blco", por enquanto
            fread(str, blkSize-sizeof(short),1,seek);
            fread(searching->first_blk_ptr, sizeof(short),1,seek);
        }while(noBlks--);

        memmove(meta, searching, sizeof(searching));
        return str;
    }
}

/**
* Metodo para copia de arquivos
* PARA o sistema Cumulo.
*
* Inputs:
* ---------
* nomeCompletoDaOrigem: String com o nome do arquivo
*                       fora do sistema Cumulo;
* path:                 String com caminho para o arquivo
*                       destino.
*
* Outputs:
* ---------
* ERROR_LEVEL: Erro de acordo com os
*              definidos em MACROS_H.
*/
int copyFile(char* nomeCompletoDaOrigem, char* path){
}

/**
* Metodo para exportacao de arquivos
* DO sistema Cumulo.
*
* Inputs:
* ---------
* path:                  String com caminho do arquivo
*                        para extrair dados;
* nomeCompletoDoDestino: String com o nome do arquivo
*                        fora do sistema Cumulo, para
*                        ser criado com o conteudo da
*                        Origem.
* Outputs:
* ---------
* ERROR_LEVEL: Erro de acordo com os
*              definidos em MACROS_H.
*/
int exportFile(char* path, char* nomeCompletoDoDestino){
    FileAttribs* toExport = NEW_FILEDATA;
    char* str = openFile(path, toExport);

    //int noOfSubDirs = countNumberOfSubDirs(path);

    removeFirstSlash(path)

    FILE *fp = fopen(path, "ab+");

    fwrite(str, sizeof(str), 1, fp);
    fflush(fp);

    return NO_ERROR;
}

/**
* Metodo para localizacao a posicao
* de metadatos de um arquivo especifico.
*
* Inputs:
* ---------
* path:     String com caminho para o arquivo;
*
* Outputs:
* ---------
* size_t:   Offset, em bytes, da posicao onde
*           estara o primeiro byte.
*/
size_t locateBeginingOfFile(char* path){
    int subDirs = countNumberOfSubDirs(path);
    removeFirstSlash(path);
    if (subDirs==STRANGE_PATH){
        if (DISPLAY_INFO) printf("Caminho ate arquivo inconsistente.");
        return STRANGE_PATH;
    }

    while (subDirs--){ //subDirs > 0
        char* subDir = getFirstDir(path);
        if (subDir == NULL){
            getData();
        } else {
            proceedDownToNextNod();
        }
    }
}

size_t locateFileWithinDir(char* path, char* name){
}

/**
* Conta o numero de dirs. Se = 1, root.
*/
int countNumberOfSubDirs(char* path){
    int len = strlen(path);
    char t[len];
    strcpy(t, path);
    int i=0;
    int j=0;
    while (i < len){
        if (t[i] == '/'){
            j++;
        }
        i++;
    }

    if (j == 0) return STRANGE_PATH;
    else        return j;
}

char* getFirstDir(char* path){
    int len = strlen(path);
    char t[len];
    strcpy(t, path);
    int i=1;
    while (i < len){
        if (t[i] == '/'){
            break;
        }
        i++;
    }
    //i++; //Para pegar a barra
    if(i>=len || i<=1) return NULL; //Nao encontrou '/', nao corta

    char* ext = malloc (sizeof (char) * i);
    memmove(ext, path, i);
    removeFirstSlash(path);
    return ext;
}

/**
* Remove ate a primeira ocorrencia
* de '/'. Se nao ocorrer, nao
* remove.
*/
void removeFirstSlash(char* path){
    int len = strlen(path);
    char t[len];
    strcpy(t, path);
    int i=0;

    while (i < len){
        if (t[i] == '/'){
            i++;
            break;
        }
        i++;
    }
    if(i >= len) i=0; //Nao encontrou '/', nao corta

    memmove(path, path + i, len-i+1);
}

void flushDataStructures(){
    FILE* seek = pointer;
    FSEEK(seek, sizeof(InitBlock),SEEK_SET);
    fwrite(sistema->bitMap, 1, sizeof(BitMap), seek);
    fflush();
}

#endif

