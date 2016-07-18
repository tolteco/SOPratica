/**
 * Pacote do Barbeiro, chama funcao de barbearia para atender, se puder, atende;
 * senao, dorme; Responsavel por fechar a barbearia, pois deve atender todos os
 * que possam chegar e terminar a fila de atendimento
 */
package barbearia;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import so.barbeiro.view.MainFrame;

public class Barbeiro extends Thread implements Runnable {
    private static final Logger LOG = MainFrame.LOG;
    private final boolean logToStream;
    private final MainFrame MainF;
    
    public static double atendimentoTime;
    
    Barbearia barbearia /**
             * @param barbearia E construido com a barbearia para que possa
             * acessar atributos e mexer com funcoes
             */
            ;
    boolean SEBD /**
             * @param SEBD Se barbeiro acabou de acordar
             */
            ;

    /**
     * Construtor de barbeiro
     * @param b Barbearia para construcao
     */
    public Barbeiro(Barbearia b) {
        this(b, null);
    }

    public Barbeiro(Barbearia b, MainFrame frame){
        barbearia = b;
        this.MainF = frame;
        if (frame != null){
            logToStream = true;
            /*PaneHandler hnd = new PaneHandler(new BufferedPaneOutputStream(console));
            LOG.addHandler(hnd);
            LOG.setUseParentHandlers(false);*/
        } else {
            logToStream = false;
        }
    }
    
    @Override
    public void run() {
        while (barbearia.aberto == true) {
            SEBD = barbearia.mexefila(2, 0);
            if (!SEBD) { //Se o barbeiro estava dormindo nao gasta tempo de atendimento
                atendimento();
            }
            if (barbearia.fechar == true && barbearia.fila == 0) { //Se gerador de clientes ja parou e barbeiro ja atendeu toda a fila
                barbearia.aberto = false;
                if (logToStream)
                    java.awt.EventQueue.invokeLater(() -> { LOG.warning("Fechando Barbearia..."); });
                else
                    System.out.println("Fechando Barbearia");
                Main.writer.println("Fechando Barbearia\n");
                Main.writer.close();
            }
            Thread.yield(); //Forca a troca de contexto
        }
    }

    /**
     * Escolhe um tempo aleatorio para o atendimento e espera esse tempo (como
     * se estivesse atendendo)
     */
    public void atendimento() {
        //int randomInt = 0; //Descomentar essa linha e comentar a abaixo se for pra fazer tudo correndo
        int randomInt = (Main.randomGenerator.nextInt(5)) + 1; //Tempo. Atualmente 1 a 6 segundos
        atendimentoTime = randomInt;
        try {
            TimeUnit.SECONDS.sleep(randomInt);
            //TimeUnit.MILLISECONDS.sleep(randomInt); //Para milisegundos
        } catch (InterruptedException ex) {
            Logger.getLogger(Barbeiro.class.getName()).log(Level.SEVERE, null, ex);
        }
        barbearia.t = false; //Fim do atendimento
    }
}
