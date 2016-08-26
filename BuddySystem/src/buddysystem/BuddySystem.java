/*
 * Alocacao de paginas utilizando um sistema buddy
 */
package buddysystem;

import controle.Solic;
import entradasaida.Leitor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BuddySystem {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        ArrayList<Solic> S = new ArrayList<>();
        Solic So;
        int Op;
        int b=1;
        boolean i = false;
        Scanner in = new Scanner(System.in);
        System.err.println("Tamanho exigido maior que a memoria disponivel");

        System.out.println("Informe o metodo de solicitacoes (1 Teclado, 2 Arquivo)");
        Op = in.nextInt();
        if (Op == 1) {
            while (b==1) {
                System.out.println("Escolha 1 para requisicao ou 0 para liberacao");
                int s = in.nextInt();
                System.out.println("Insira o pid do processo solicitante");
                int pid = in.nextInt();
                System.out.println("Insira a quantidade");
                int qtde = in.nextInt();
                if (s == 1) {
                    i = true;
                }
                So = new Solic(i, qtde, pid);
                System.out.println("Deseja fazer outra solicitacao? 0 para nao ou 1 para sim");
                int a = in.nextInt();
                if(a==1){
                    b=1;
                }
                if(a==0){
                    b=0;
                }
            }

        } else if (Op == 2) {
            Leitor L = new Leitor();
            S = L.Solicitacoes();
        }
    }

}

/**
 * RETIRAR QUANDO FOR ENTREGAR
 * Projeto de arvore do buddy baseado em:
 * https://github.com/utkarshsimha/buddy_system/tree/master/src
 */
