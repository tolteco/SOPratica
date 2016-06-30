/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barbearia;

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
public class Main extends Thread{

    static String namefile = "Relatorio(" + (new SimpleDateFormat("dd-MM-yyyy").format(new Date())) + ").txt";
    static File file;
    static PrintWriter writer;
    static Random randomGenerator;
    static ExecutorService executor;

    public static void main(String[] args) throws InterruptedException {
        Barbearia barbearia = new Barbearia();
        boolean H = EscreveRelatorio();
        if (!H) {
            System.out.println("Erro na criacao do arquivo\n");
        } else {
            randomGenerator = new Random();
            Scanner input = new Scanner(System.in);
            System.out.println("Quantas cadeiras a barbearia devera ter?");
            barbearia.cadeiras = input.nextInt();
            System.out.println("Quantos clientes deverao aparecer para cortar o cabelo?");
            barbearia.clientes = input.nextInt();
            //executor = Executors.newCachedThreadPool();
            System.out.println("Abrindo Barbearia");
            writer.println("Abrindo Barbearia\n");
            writer.println("Cadeiras de espera: " + barbearia.cadeiras + "\n");
            writer.println("Clientes ate finalizacao: " + barbearia.clientes + "\n");
            writer.println("-----------------------------\n");
            /**
             * for (int i = 0; i < 2; i++) { executor.execute(new
             * BarbeiroDorminhoco()); }
            *
             */
            //Barbeiro b = new Barbeiro(barbearia);
            //GeradorClientes g = new GeradorClientes(barbearia);
            Thread b = new Thread (new Barbeiro(barbearia));
            Thread g = new Thread (new GeradorClientes(barbearia));
            b.start();
            g.start();
            while (barbearia.aberto == true) {
                System.out.print("");
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
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Finalização de inicialização de escritor
        return true;
    }
    
}
