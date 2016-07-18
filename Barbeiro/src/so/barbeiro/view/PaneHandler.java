/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.barbeiro.view;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

/**
 *
 * @author Juliano Felipe da Silva
 */
public class PaneHandler extends StreamHandler {
    private final BufferedPaneOutputStream stream;
    
    /**
     * Constrói um Handler usando a stream direcionada
     * à JTextPanes, com um {@link java.util.logging.SimpleFormatter}.
     * @param outStream Stream já instanciada.
     */
    public PaneHandler(BufferedPaneOutputStream outStream) {
        this(outStream, new SimpleFormatter());
    }

    /**
     * Constrói um Handler usando a stream direcionada
     * à JTextPanes.
     * @param outStream Stream já instanciada.
     * @param formatter Formatador a ser usado nos "LogRecords".
     */
    public PaneHandler(BufferedPaneOutputStream outStream, Formatter formatter) {
        super(outStream, formatter);
        stream = outStream;
    }
    
    @Override
    public synchronized void close() throws SecurityException {
        flush();
    }

    @Override
    public synchronized void publish(LogRecord record) {
        if (record.getLevel() == Level.INFO)
            stream.setCurrentAttributeSet( stream.INFO );
        if (record.getLevel() == Level.WARNING)
            stream.setCurrentAttributeSet( stream.WARNING );
        if (record.getLevel() == Level.SEVERE)
            stream.setCurrentAttributeSet( stream.SEVERE );
        super.publish(record); 
        flush();
        stream.setCurrentAttributeSet( stream.NORMAL );
    }
    
}
