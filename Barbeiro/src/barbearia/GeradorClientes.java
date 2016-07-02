/**
 * Pacote do Gerador de clientes, gera clientes ate a quantidade informada e poe
 * na fila se for possivel
 */
package barbearia;

import static barbearia.Main.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeradorClientes extends Thread implements Runnable {

    Barbearia barbearia /**
             * @param barbearia E construido com a barbearia para que possa
             * acessar atributos e mexer com funcoes
             */
            ;

    /**
     * Construtor de Gerador
     * @param b Barbearia para construcao
     */
    GeradorClientes(Barbearia b) {
        barbearia = b;
    }

    @Override
    public void run() {
        for (int i = 0; i < barbearia.clientes; i++) {
            //int randomInt = 0; //Descomentar essa linha e comentar a abaixo se for pra fazer tudo correndo
            int randomInt = (randomGenerator.nextInt(5)) + 1; //Tempo. Atualmente 1 a 6 segundos
            try { //Escolhe um tempo para a entrada entre um cliente e outro
                TimeUnit.SECONDS.sleep(randomInt);
                //TimeUnit.MILLISECONDS.sleep(randomInt); //Para milisegundos
            } catch (InterruptedException ex) {
                Logger.getLogger(GeradorClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
            barbearia.mexefila(1, i);
            Thread.yield(); //Forca a troca de contexto
        }
        barbearia.fechar = true; //Indica ao barbeiro que terminou de gerar clientes
    }
}
