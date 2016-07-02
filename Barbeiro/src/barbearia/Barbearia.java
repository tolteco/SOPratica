/**
 * Pacote da Barbearia, faz todas as acoes envolvendo a fila em funcao
 * sincronizada para evitar acesso concorrente do gerador de clientes e do
 * barbeiro a fila
 */
package barbearia;

import static barbearia.Main.writer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Barbearia {

    public int cadeiras = 0, fila = 0, clientes = 0 /**
             * @param cadeiras  Quantidade de cadeiras na barbearia
             * @param fila      Tamanho da fila (cadeiras ocupadas)
             * @param clientes  Quantidade de clientes que serao gerados
             */
            ;

    public boolean aberto = true, fechar = false, t = false, bd = false, sebd = false
            /**
             * @param aberto    Barbearia aberta
             * @param fechar    Se a barbearia deve fechar 
             *                  (usado em gerador de clientes 
             *                  para indicar fim da criacao)
             * @param t         Barbeiro em atendimento
             * @param bd        Barbeiro dormindo
             * @param sebd      Se barbeiro acadou de acordar
             */
            ;

    /**
     * @param k Opcao (1 Gerador de clientes acessando; 2 Barbeiro acessando)
     * @param i Cliente que esta sendo gerado 
     *          (caso de o barbeiro estar entrando recebe zero, nao sera usado)
     * @return  Se o barbeiro acabou de acordar
     */
    public synchronized boolean mexefila(int k, int i) {
        sebd = false;
        if (k == 1) { //Gerador de clientes
            if (fila == cadeiras) {
                System.out.println("Fila cheia");
                Main.writer.println("Fila cheia");
            } else {
                fila++;
                writer.println("Cliente criado[" + (i + 1) + "]");
                System.out.println("Cliente criado[" + (i + 1) + "]");
                if (bd) {
                    notify(); //Acorda barbeiro
                }
            }
        } else if (k == 2) { //Barbeiro
            if (fila > 0) {
                fila--;
                t = true;
                System.out.println("Barbeiro atendendo");
                Main.writer.println("Barbeiro em atendimento");
            } else {
                try {
                    Main.writer.println("Barbeiro dormindo");
                    bd = true; //Barbeiro dormiu
                    wait();
                    bd = false;
                    sebd = true;
                    System.out.println("Barbeiro acordado");
                    Main.writer.println("Barbeiro acordado");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Imprimevetor(t);
        return sebd;
    }

    /**
     * Imprime "Como estaria organizado se fosse um vetor"
     * @param T Se o barbeiro esta em atendimento
     */
    private void Imprimevetor(boolean T) {
        for (int i = 0; i < cadeiras; i++) {
            System.out.print("-");
        }
        System.out.println("  -");
        for (int q = 0; q < cadeiras; q++) {
            if (q < fila) {
                System.out.print("C");
            } else {
                System.out.print("-");
            }
        }
        if (T) {
            System.out.println("  CB");
        } else {
            System.out.println("  B");
        }
    }
}
