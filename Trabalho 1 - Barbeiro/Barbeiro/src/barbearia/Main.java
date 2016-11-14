/**
 * Pacote Main, inicia Threads, escritor de relatorio e random
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Thread {

    public static String namefile = "Relatorio(" + (new SimpleDateFormat("dd-MM-yyyy").format(new Date())) + ").txt" /**
             * @param namefile Usado para criar arquivo com o nome do relatorio
             */
            ;
    public static File file /**
             * @param file Ponteiro para o arquivo
             */
            ;
    public static PrintWriter writer /**
             * @param writer Ponteiro para escritor java em arquivo
             */
            ;
    public static Random randomGenerator /**
             * @param randomGenerator Gerador aleatorio usado nos tempos de
             * atendimento e entre criacao de clientes
             */
            ;

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
            //Escreve cabecalho em arquivo
            System.out.println("Abrindo Barbearia");
            writer.println("Abrindo Barbearia\n");
            writer.println("Cadeiras de espera: " + barbearia.cadeiras + "\n");
            writer.println("Clientes ate finalizacao: " + barbearia.clientes + "\n");
            writer.println("-----------------------------\n");
            Thread b = new Thread(new Barbeiro(barbearia));
            Thread g = new Thread(new GeradorClientes(barbearia));
            b.start(); //Inicia Threads
            g.start();
        }
    }

    /**
     * Inicia os atributos para escrever relatorio em txt
     * @return Se nenhum erro ocorreu
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
