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
    private static final int MAGICAL_TIME=2; //Tempo de espera
    private static final Logger LOG = MainFrame.LOG;
    private final boolean logToStream;
    
    private final int MAX_TIME;
    private final int MIN_TIME;
    private final boolean useMilis;
    
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
        this(b, null, 1, false);
    }

    public GeradorClientes(Barbearia b, MainFrame frame, int mode, boolean useMilis){
        barbearia = b;
        logToStream = frame != null; 
        /*PaneHandler hnd = new PaneHandler(new BufferedPaneOutputStream(console));
        LOG.addHandler(hnd);
        LOG.setUseParentHandlers(false);*/
        
        if (mode==2 || mode==4 || mode==6){                //Modo 2: Barbeiro rápido:
            MAX_TIME = MAGICAL_TIME*3; //Ao invés do barbeiro ser mais rápido,
        } else {                       //o gerador é mais lento.
            MAX_TIME = MAGICAL_TIME;
        }
        
        if (mode==4 || mode==6) MIN_TIME = MAGICAL_TIME;
        else MIN_TIME=0;
        this.useMilis = useMilis;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < barbearia.clientes; i++) {
            //int randomInt = 0; //Descomentar essa linha e comentar a abaixo se for pra fazer tudo correndo
            int randomInt = (randomGenerator.nextInt(MAX_TIME)) + MIN_TIME;
            try { //Escolhe um tempo para a entrada entre um cliente e outro
                if (useMilis)
                    TimeUnit.MILLISECONDS.sleep(randomInt); //Para milisegundos
                else
                    TimeUnit.SECONDS.sleep(randomInt);
            } catch (InterruptedException ex) {
                Logger.getLogger(GeradorClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
            barbearia.mexefila(1, i);
            Thread.yield(); //Forca a troca de contexto
        }
        barbearia.fechar = true; //Indica ao barbeiro que terminou de gerar clientes
    }
}
