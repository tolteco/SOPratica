/*
 * Le randomicos gerados previamente
 */
package entradasaida;

import controle.Solic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Leitor {

    public int qtde;
    public int pid;
    public boolean in = false;
    public ArrayList<Solic> H = new ArrayList<>();

    public ArrayList<Solic> Solicitacoes() throws FileNotFoundException, IOException {
        FileReader arq = new FileReader("Solicitacoes.txt");
        BufferedReader lerArq = new BufferedReader(arq);
        Scanner rfile = new Scanner(new File("Solicitacoes.txt"));

        int s = rfile.nextInt();
        int pid = rfile.nextInt();
        int qtde = rfile.nextInt();
        System.out.println("In: " + s + " " + pid + " " + qtde);
        if (s == 1) {
            in = true;
        }
        Solic S = new Solic(in, qtde, pid);
        H.add(S);
        in = false;
        while (rfile.hasNextInt()) {
            s = rfile.nextInt();
            pid = rfile.nextInt();
            qtde = rfile.nextInt();
            if (s == 1) {
                in = true;
            }
            S = new Solic(in, qtde, pid);
            H.add(S);
            System.out.println("In: " + s + " " + pid + " " + qtde);
            in = false;
        }
        return H;
    }
}
