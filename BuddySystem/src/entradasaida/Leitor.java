/*
 * Le randomicos gerados previamente
 */
package entradasaida;

import controle.Solic;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Maycon
 */
public class Leitor {

    public int qtde;
    public int pid;
    public boolean in = false;
    public ArrayList<Solic> H = new ArrayList<>();

    public ArrayList<Solic> Solicitacoes() throws FileNotFoundException, IOException {
        FileReader arq = new FileReader("Solicitacoes.txt");
        BufferedReader lerArq = new BufferedReader(arq);

        String linha = lerArq.readLine();
        if (linha == null) {
            System.out.println("Erro na abertura do arquivo");
            return null;
        }
        if (Integer.parseInt(linha.substring(0, 1)) == 1){
            in = true;
        }
        /**
        qtde = Integer.parseInt(linha.substring(1, ));
        */
        Solic S = new Solic(true, 0, 0);
        H.add(S);
        while (linha != null) {
            linha = lerArq.readLine(); //Le
            
            
        }
        return H;
    }
}
