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
        int Op;
        Scanner in = new Scanner(System.in);
        System.err.println("Tamanho exigido maior que a memoria disponivel");
        
        System.out.println("Informe o metodo de solicitacoes (1 Teclado, 2 Arquivo)");
        Op = in.nextInt();
        if (Op == 1){
            //Fazer
        } else if (Op == 2){
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