/**
 * Pacote da Barbearia, faz todas as acoes envolvendo a fila em funcao
 * sincronizada para evitar acesso concorrente do gerador de clientes e do
 * barbeiro a fila
 */
package barbearia;

import static barbearia.Main.writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import so.barbeiro.view.MainFrame;

public class Barbearia {
    private static final Logger LOG = MainFrame.LOG;
    private final boolean logToStream;
    private final MainFrame MainF;
    
    private ArrayList<Cliente> clientesNaoAtendidos = new ArrayList();
    private ArrayList<Cliente> filaC = new ArrayList();
    private ArrayList<Cliente> atendidos = new ArrayList();
    
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

    public Barbearia(){
        this(null);
    }
    
    public Barbearia(MainFrame frame) {
        this.MainF = frame;
        if (frame != null){
            logToStream = true;
            //PaneHandler hnd = new PaneHandler(new BufferedPaneOutputStream(console));
            //LOG.addHandler(hnd);
            //LOG.setUseParentHandlers(false);
        } else {
            logToStream = false;
        }
    }

    
    
    /**
     * @param k Opcao (1 Gerador de clientes acessando; 2 Barbeiro acessando)
     * @param i Cliente que esta sendo gerado 
     *          (caso de o barbeiro estar entrando recebe zero, nao sera usado)
     * @return  Se o barbeiro acabou de acordar
     */
    public synchronized boolean mexefila(int k, int i) {
        sebd = false;
        Cliente cliente = null;
        if (k == 1) { //Gerador de clientes
            cliente = new Cliente(i);
            if (fila == cadeiras) {
                if (logToStream)
                    java.awt.EventQueue.invokeLater(() -> { LOG.severe("Fila cheia"); });
                else
                    System.out.println("Fila cheia");
                Main.writer.println("Fila cheia");
                clientesNaoAtendidos.add(cliente);
            } else {
                fila++;
                StringBuilder sb = new StringBuilder();
                sb.append("Cliente criado[").append(i).append("]");
                if (logToStream)
                    java.awt.EventQueue.invokeLater(() -> {LOG.info(sb.toString());});
                else
                    System.out.println(sb.toString());
                writer.println(sb.toString());
                filaC.add(cliente);
                if (bd) {
                    notify(); //Acorda barbeiro
                }
            }
        } else if (k == 2) { //Barbeiro
            if (fila > 0) {
                fila--;
                t = true;
                if (logToStream)
                    java.awt.EventQueue.invokeLater(() -> {
                        LOG.info("Barbeiro atendendo");
                    });
                    //LOG.info("Barbeiro atendendo");
                else
                    System.out.println("Barbeiro atendendo");
                Main.writer.println("Barbeiro em atendimento");
                MainF.setBarberState(1);
                if (filaC.size() > 0){
                    cliente = filaC.remove(0);
                    cliente.setTempoAtendimento(Barbeiro.atendimentoTime); //Add para o último cliente, o último tempo.
                    atendidos.add(cliente);
                }
            } else {
                try {
                    MainF.setBarberState(0);
                    Main.writer.println("Barbeiro dormindo");
                    bd = true; //Barbeiro dormiu
                    wait();
                                       
                    bd = false;
                    sebd = true;
                    if (logToStream)
                        java.awt.EventQueue.invokeLater(() -> { LOG.info("Barbeiro acordado"); });
                    else
                        System.out.println("Barbeiro acordado");
                    Main.writer.println("Barbeiro acordado");
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (logToStream)
            revalidateMainFrame();
        else
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
    
    private void revalidateMainFrame(){
        MainF.fillAtendidos(atendidos);
        MainF.fillFila(filaC);
        MainF.fillNaoAtendidos(clientesNaoAtendidos);
        /*if (t == true){
            MainF.setBarberState(1);
        } else {
            MainF.setBarberState(0);
        }*/
    }
}
