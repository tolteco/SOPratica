/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barbeirodorminhoco;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maycon
 */
public class BarbeiroDorminhoco extends Thread implements Runnable {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    static int cadeiras = 0, fila = 0, clientes = 0;
    static boolean aberto = true, fechar = false;
    static String namefile = "Relatorio(" + (new SimpleDateFormat("dd-MM-yyyy").format(new Date())) + ").txt";
    static File file;
    static PrintWriter writer;
    static Random randomGenerator;
    static ExecutorService executor;

    public static void main(String[] args) throws InterruptedException {
        boolean H = EscreveRelatorio();
        if (!H) {
            System.out.println("Erro na criacao do arquivo\n");
        } else {
            randomGenerator = new Random();
            Scanner input = new Scanner(System.in);
            System.out.println("Quantas cadeiras a barbearia devera ter?");
            cadeiras = input.nextInt();
            System.out.println("Quantos clientes deverao aparecer para cortar o cabelo?");
            clientes = input.nextInt();
            //executor = Executors.newCachedThreadPool();
            System.out.println("Abrindo Barbearia");
            writer.println("Abrindo Barbearia\n");
            writer.println("Cadeiras de espera: " + cadeiras + "\n");
            writer.println("Clientes ate finalizacao: " + clientes + "\n");
            writer.println("-----------------------------\n");
            /**
            for (int i = 0; i < 2; i++) {
                executor.execute(new BarbeiroDorminhoco());
            }
            **/
            BarbeiroDorminhoco a = new BarbeiroDorminhoco();
            Thread t1 = new Thread (new BarbeiroDorminhoco(a));
            t1.start();
            Thread t2 = new Thread (new BarbeiroDorminhoco(a));
            t2.start();
            while (aberto == true) {
                //Nao faz nada
            }
            //executor.shutdown();
            System.out.println("Fechando Barbearia");
            writer.println("Fechando Barbearia\n");
            writer.close();
        }
    }

    /**
     * Inicia os atributos para escrever relatorio em txt
     *
     * @return
     */
    public static boolean EscreveRelatorio() {
        String A = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        file = new File(namefile);
        try {
            file.createNewFile();
        } catch (IOException ex) {
            return false;
        } //Finalização de criacao de arquivo para relatorio
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        } catch (IOException ex) {
            Logger.getLogger(BarbeiroDorminhoco.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Finalização de inicialização de escritor
        return true;
    }

    private BarbeiroDorminhoco(BarbeiroDorminhoco a) {
    }

    private BarbeiroDorminhoco() {
    }

    /**
     * Mexe na fila de acordo com a opcao solicitada
     *
     * @param k - opcao (1. Coloca cliente na fila; 2. Checa situacao e retira
     * cliente da fila)
     */
    public synchronized void mexefila(int k) {
        if (k == 1) {
            if (fila == cadeiras) {
                System.out.println("Fila cheia");
                writer.println("Fila cheia\n");
            } else {
                fila++;
                notify();
            }
        } else if (k == 2) {
            if (fila > 0) {
                fila--;
            } else {
                try {
                    System.out.println("Barbeiro dormindo");
                    writer.println("Barbeiro dormindo\n");
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(BarbeiroDorminhoco.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void atendimento() {
        int randomInt = (randomGenerator.nextInt(5)) + 1;
        try {
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException ex) {
            Logger.getLogger(BarbeiroDorminhoco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        int T = Integer.parseInt((Thread.currentThread().getName()).substring(Thread.currentThread().getName().length() - 1, Thread.currentThread().getName().length()));
        System.out.println("Thread[" + Thread.currentThread().getName() + "]\n");
        if (T == 1) { //Criador de clientes
            for (int i = 0; i < clientes; i++) {
                int randomInt2 = (randomGenerator.nextInt(5)) + 1;
                try {
                    TimeUnit.SECONDS.sleep(randomInt2);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BarbeiroDorminhoco.class.getName()).log(Level.SEVERE, null, ex);
                }
                mexefila(1);
                System.out.println("Cliente criado[" + i + "]\n");
                writer.println("Cliente criado[" + i + "]\n");
                //Forca troca de contexto
                Thread.yield();
            }
            fechar = true;
        } else if (T == 2) { //Barbeiro
            while (aberto == true) {
                mexefila(2);
                System.out.println("Cliente em atendimento");
                writer.println("Cliente em atendimento\n");
                atendimento();
                if (fechar == true && fila == 0) {
                    aberto = false;
                }
                //Forca troca de contexto
                Thread.yield();
            }
        } else {
            //Nao faz nada caso seja uma Thread invalida (por qualquer motivo que possa ser)
        }
    }
}
