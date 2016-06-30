/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barbearia;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maycon
 */
public class Barbearia {

    public int cadeiras = 0, fila = 0, clientes = 0;
    public boolean aberto = true, fechar = false, t = false, bd = false;
    public Random randomGenerator;

    public synchronized void mexefila(int k) {
        if (k == 1) { //Gerador de clientes
            if (fila == cadeiras) {
                System.out.println("Fila cheia");
                Main.writer.println("Fila cheia");
            } else {
                fila++;
                if (bd){
                    notify();
                }
            }
        } else if (k == 2) { //Barbeiro
            if (fila > 0) {
                fila--;
                t = true;
                //System.out.println("Barbeiro em atendimento");
                System.out.println("Barbeiro atendendo");
                Main.writer.println("Barbeiro em atendimento");
            } else {
                try {
                    //System.out.println("Barbeiro dormindo");
                    Main.writer.println("Barbeiro dormindo");
                    bd = true;
                    wait();
                    //System.out.println("Barbeiro acordado");
                    Main.writer.println("Barbeiro acordado");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Imprimevetor(t);
    }

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
        if (T){
            System.out.println("  CB");
        } else {
            System.out.println("  B");
        }
    }
}
