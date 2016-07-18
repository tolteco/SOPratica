/**
 * Pacote do Gerador de clientes, gera clientes ate a quantidade informada e poe
 * na fila se for possivel
 */
package barbearia;

import static barbearia.Main.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import so.barbeiro.view.MainFrame;

public class GeradorClientes extends Thread implements Runnable {
    private static final Logger LOG = MainFrame.LOG;
    private final boolean logToStream;
    
    Barbearia barbearia /**
             * @param barbearia E construido com a barbearia para que possa
             * acessar atributos e mexer com funcoes
             */
            ;

    /**
     * Construtor de Gerador
     * @param b Barbearia para construcao
     */
    public GeradorClientes(Barbearia b) {
        this(b, null);
    }

    public GeradorClientes(Barbearia b, MainFrame frame){
        barbearia = b;
        logToStream = frame != null; /*PaneHandler hnd = new PaneHandler(new BufferedPaneOutputStream(console));
        LOG.addHandler(hnd);
        LOG.setUseParentHandlers(false);*/
    }
    
    @Override
    public void run() {
        for (int i = 0; i < barbearia.clientes; i++) {
            //int randomInt = 0; //Descomentar essa linha e comentar a abaixo se for pra fazer tudo correndo
            int randomInt = (randomGenerator.nextInt(2)) + 0; //Tempo. Atualmente 0 a 2 segundos
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
