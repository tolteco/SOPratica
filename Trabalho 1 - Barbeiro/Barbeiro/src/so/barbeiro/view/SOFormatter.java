package so.barbeiro.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Formatador de logs usado 
 * na "MainFrame".
 * @author Juliano Felipe da Silva
 */
public class SOFormatter extends Formatter{
    private final Locale locale; 

    /**
     * Constrói um formatador de loggers
     * no padrão SIGMA com locale PT-BR.
     */
    public SOFormatter() {
        this(new Locale("pt", "BR")); //Locale para o Brasil
    }

    /**
     * Constrói um formatador de loggers
     * no padrão SIGMA com locale passado.
     * @param locale Usado na formatação.
     */
    public SOFormatter(Locale locale) {
        this.locale = locale;
    }

    /*
        Quick reference:
        - record.getClass: java.util.logging.LogRecord.
        - record.getThreadID: int
        - record.getLoggerName(): Telas.TelaPrincipal
        - record.getResourceBundleName():
        - getSourceClassName: Telas.TelaPrincipal
        - getSourceMethodName: <init>
        - 
    */
    @Override
    public String format(LogRecord record) {
        Date date=new Date(record.getMillis()); //HH para 0-23 hrs
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS dd/MM/yyyy", locale); ///.SSS para milisegundos
        
        /*String ret = formatter.format(date) + " - " + record.getSourceClassName() +
               " " + record.getSourceMethodName() + 
              "\n" + record.getLevel() + ": " + record.getMessage() + "\n";*/
        
        String ret = formatter.format(date) +
              "\n" + record.getLevel() + ": " + record.getMessage() + "\n\n";
        
        return ret;
    }
    
}

