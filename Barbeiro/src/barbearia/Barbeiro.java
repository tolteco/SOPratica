/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barbearia;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maycon
 */
public class Barbeiro extends Thread implements Runnable {

    Barbearia barbearia;

    Barbeiro(Barbearia b) {
        barbearia = b;
    }

    @Override
    public void run() {
        while (barbearia.aberto == true) {
            barbearia.mexefila(2);
            atendimento();
            if (barbearia.fechar == true && barbearia.fila == 0) {
                notifyAll();
                barbearia.aberto = false;
            }
        }
    }

    public void atendimento(){
        int randomInt = (Main.randomGenerator.nextInt(5)) + 1;
        try {
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException ex) {
            Logger.getLogger(Barbeiro.class.getName()).log(Level.SEVERE, null, ex);
        }
        barbearia.t = false;
    }
}
