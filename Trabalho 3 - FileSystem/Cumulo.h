#include "Macros.h"
#include "BitMap.h"
#include "InitBlock.h"
#include "FileData.h"

#ifndef CUMULO_H
#define CUMULO_H

typedef struct FileSystem {
    InitBlock* initBlock;
    BitMap* bitMap;
};

/**
* Ponteiro para o arquivo
* de imagem a ser usado
* pelo sistema.
*/
FileSystem* sistema;

//Prototipos
FileSystem* initFileSystem(char* systemName){
}

int newEmptyFile(char* nome, char* ext);
int newFile(char* nome, char* ext, char* conteudo);
int deleteFile(char* nome, char* ext);
char* openFile(char* nome, char* ext);
int copyFile(char* nomeCompletoDaOrigem, char* nomeDestino, char* extDestino);
int exportFile(char* nomeOrigem, char* extOrigem, char* nomeCompletoDoDestino);


/// Implementacoes

/**
* Metodo para criacao de arquivos
* novos vazios.
*
* Inputs:
* ---------
* nome: String com o nome do arquivo;
* ext:  String com a extensao do arquivo.
*
* Outputs:
* ---------
* ERROR_LEVEL: Erro de acordo com os
*              definidos em MACROS_H.
*/
int newEmptyFile(char* nome, char* ext){
    return newFile(nome, ext, NULL);
}

/**
* Metodo para criacao de arquivos
* novos com conteudo passado via
* parametro.
*
* Inputs:
* ---------
* nome:     String com o nome do arquivo;
* ext:      String com a extensao do arquivo;
* conteudo: String com o conteudo do arquivo
*           (Se passado NULL, arquivo vazio e
*           criado).
*
* Outputs:
* ---------
* ERROR_LEVEL: Erro de acordo com os definidos
*              em MACROS_H.
*/
int newFile(char* nome, char* ext, char* conteudo){
    return newFile(nome, ext, NULL);
}

/**
* Metodo para exclusao de arquivos.
*
* Inputs:
* ---------
* nome: String com o nome do arquivo;
* ext:  String com a extensao do arquivo.
*
* Outputs:
* ---------
* ERROR_LEVEL: Erro de acordo com os
*              definidos em MACROS_H.
*/
int deleteFile(char* nome, char* ext){
}

/**
* Metodo para abertura de arquivos
* (Retorna String com conteudo).
*
* Inputs:
* ---------
* nome: String com o nome do arquivo;
* ext:  String com a extensao do arquivo.
*
* Outputs:
* ---------
* char* com conteudo do arquivo.
*/
char* openFile(char* nome, char* ext){
}

/**
* Metodo para copia de arquivos
* PARA o sistema Cumulo.
*
* Inputs:
* ---------
* nomeCompletoDaOrigem: String com o nome do arquivo
*                       fora do sistema Cumulo;
* nomeDestino:          String com o nome do arquivo
*                       a ser criado no sistema Cumulo;
* extDestino:           String com a extensao do arquivo.
*
* Outputs:
* ---------
* ERROR_LEVEL: Erro de acordo com os
*              definidos em MACROS_H.
*/
int copyFile(char* nomeCompletoDaOrigem, char* nomeDestino, char* extDestino){
}

/**
* Metodo para exportacao de arquivos
* DO sistema Cumulo.
*
* Inputs:
* ---------
* nomeOrigem:            String com o nome do arquivo
*                        para extrair dados;
* extOrigem:             String com a extensao do arquivo;
* nomeCompletoDoDestino: String com o nome do arquivo
*                        fora do sistema Cumulo, para
*                        ser criado com o conteudo da
*                        Origem.
* Outputs:
* ---------
* ERROR_LEVEL: Erro de acordo com os
*              definidos em MACROS_H.
*/
int exportFile(char* nomeOrigem, char* extOrigem, char* nomeCompletoDoDestino){
}

#endif

