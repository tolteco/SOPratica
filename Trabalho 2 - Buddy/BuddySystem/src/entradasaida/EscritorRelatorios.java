/*
 * Escreve relatorios sobre desempenho do programa
 */
package entradasaida;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EscritorRelatorios {

    private final StringBuilder QUERY = new StringBuilder();

    private String namefile = "Relatorio(";
    private String B = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    public boolean escreveRelatorio(long tempo, int requisicoes, int liberacoes, int req, int reqat, int lib) {

        namefile = namefile + B;
        namefile = namefile + ").txt";

        final File file = new File(namefile);
        try {
            file.createNewFile();
        } catch (IOException ex) {
            return false;
        } //Finalização de criação de arquivo

        PrintWriter writer;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        } catch (IOException ex) {
            Logger.getLogger(EscritorRelatorios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }//Finalização de inicialização de escritor

        QUERY.append(tempo)
                .append(req)
                .append(reqat)
                .append(lib);

        writer.println(QUERY.toString());
        writer.close();

        return true;
    }
}
