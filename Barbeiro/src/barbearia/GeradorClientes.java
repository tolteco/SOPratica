/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barbearia;

import static barbearia.Main.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maycon
 */
public class GeradorClientes extends Thread implements Runnable {

    Barbearia barbearia;

    GeradorClientes(Barbearia b) {
        barbearia = b;
    }

    public void run() {
        //System.out.println("Quantidade de clientes recebida: " + barbearia.clientes);
        for (int i = 0; i < barbearia.clientes; i++) {
            int randomInt2 = (randomGenerator.nextInt(5)) + 1;
            //System.out.println("Cliente criado[" + i + "]");
            try {
                TimeUnit.SECONDS.sleep(randomInt2);
            } catch (InterruptedException ex) {
                Logger.getLogger(GeradorClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
            writer.println("Cliente criado[" + i + "]");
            System.out.println("Cliente criado[" + i + "]");
            barbearia.mexefila(1);
        }
        barbearia.fechar = true;
    }
}
