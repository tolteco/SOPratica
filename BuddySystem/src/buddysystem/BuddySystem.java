/*
 * Alocacao de paginas utilizando um sistema buddy
 */
package buddysystem;

import controle.Arvore;
import controle.Solic;
import entradasaida.EscritorRelatorios;
import entradasaida.Leitor;
import entradasaida.RootSize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BuddySystem {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        ArrayList<Solic> S = new ArrayList<>(); //Array de solicitações (vem pelo arquivo)
        Solic So; //Solicitacao
        Arvore a = new Arvore(); //Arvore do Buddy
        int Op; //Controlador de tipo de entrada a ser realizada
        int b = 1; //Controlador de if de leitura do teclado
        boolean i = false; //boolean para ver se e requisicao ou liberacao de memoria
        int qtde = 0, pid = 0; //Porque tem que ser declarado E inicializado aqui!
        int req = 0, lib = 0, reqat = 0;
        Scanner in = new Scanner(System.in); //Inicializacao de leitor do teclado
        EscritorRelatorios ER = new EscritorRelatorios();
        System.out.println("SIMULADOR DE SISTEMA BUDDY");
        System.out.println("Memoria maxima: " + RootSize.ROOTSIZE);
        System.out.println("Quantidade maxima de niveis: " + RootSize.MAXLEVELS);
        System.out.println("Tamanho do bloco de menor nivel: " + RootSize.BLOCKSIZE);
        System.out.println("Tamanho da pagina: " + RootSize.PAGESIZE);
        System.out.println("Paginas por bloco: " + RootSize.PAGESPERBLOCK);
        System.out.println("-------------------------------------------------------");
        System.out.println("Informe o metodo de solicitacoes (1 Teclado, 2 Arquivo)");
        Op = in.nextInt();
        if (Op == 1) { //Entrada pelo teclado
            while (b == 1) {
                System.out.println("Escolha entre 1 para requisicao 0 para liberacao ou 2 para estado da memoria");
                int s = in.nextInt();
                if (s == 1 || s == 0) {
                    System.out.println("Insira o pid do processo solicitante");
                    pid = in.nextInt();
                }
                if (s == 1) {
                    i = true;
                    System.out.println("Insira a quantidade de memoria que deseja alocar");
                    qtde = in.nextInt();
                }
                if (qtde == 0 && i) {
                    System.out.println("ERRO. NAO E POSSIVEL ALOCAR 0 BYTES NESSE SISTEMA");
                    return;
                }
                if (s == 1 || s == 0) {
                    So = new Solic(i, qtde, pid);
                    if (So.isIn()) { //Se e uma solicitacao
                        req++;
                        if (a.pedeMemoria(So)) {
                            System.out.println("Memoria alocada");
                            reqat++;
                        } else {
                            System.out.println("Erro ao alocar memoria");
                        }
                    } else { //Se e uma liberacao de memoria
                        lib++;
                        if (a.liberaMemoria(So)) {
                            System.out.println("Memoria liberada");
                        } else {
                            System.out.println("Erro ao liberar memoria");
                        }
                    }
                    a.imprimeArvore(a.getRoot());
                }
                if (s == 2) {
                    System.out.println("Memoria Alocada = " + a.getOcupada());
                    a.imprimeMemoria(a.getRoot());
                    System.out.println("Requisicoes ate o momento: " + req);
                    System.out.println("Requisicoes atendidas ate o momento: " + reqat);
                    System.out.println("Liberacoes ate o momento: " + lib);
                }
                System.out.println("Deseja fazer outra solicitacao? 0 Nao ou 1 Sim");
                b = in.nextInt();
                i = false;
            }

        } else if (Op == 2) { //Entrada por arquivo
            Leitor L = new Leitor();
            S = L.Solicitacoes(); //Primeiro le tudo
            System.out.println("Iniciando requisicoes");
            long tI = System.currentTimeMillis();
            for (Solic s : S) {
                System.out.println(s);
                if (s.isIn()) { //Se e uma solicitacao
                    if (a.pedeMemoria(s))
                        reqat++;
                    req++;
                } else { //Se e uma liberacao de memoria
                    a.liberaMemoria(s);
                    lib++;
                }
                System.out.println("Memoria Alocada = " + a.getOcupada());
                a.imprimeMemoria(a.getRoot());
                //a.imprimeArvore(a.getRoot());
            }
            long tF = System.currentTimeMillis();
            ER.escreveRelatorio(tF - tI, req, lib, req, reqat, lib);
            System.out.println("------------Situacao final------------");
            System.out.println("Memoria Alocada = " + a.getOcupada());
            a.imprimeMemoria(a.getRoot());
            System.out.println("Requisicoes Realizadas: " + req);
            System.out.println("Requisicoes Atendidas : " + reqat);
            System.out.println("Liberacoes            : " + lib);

        } else {
            System.out.println("Opcao Invalida");
        }
    }

}

/**
 * RETIRAR QUANDO FOR ENTREGAR Projeto de arvore do buddy baseado em:
 * https://github.com/utkarshsimha/buddy_system/tree/master/src
 */
