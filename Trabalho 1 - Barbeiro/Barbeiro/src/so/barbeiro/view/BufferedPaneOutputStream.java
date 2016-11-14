/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.barbeiro.view;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * @author Juliano Felipe da Silva
 */
public class BufferedPaneOutputStream extends OutputStream {
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final JTextPane pane;
    private final StyledDocument doc;
    private final String encoding;
    
    /**
     * Array de atributos usados pela stream.
     * As posições são delimitadas pelas constantes
     * de atributos.
     */
    private AttributeSet[] attributes = new AttributeSet[4];
    /**
     * Constante que localiza posição dos
     * atributos usados normalmente pela
     * stream.
     */
    public final int NORMAL=0;
    /**
     * Constante que localiza posição dos
     * atributos usados na impressão de
     * informações pela stream.
     */
    public final int INFO=1;
    /**
     * Constante que localiza posição dos
     * atributos usados na impressão de
     * warnings pela stream.
     */
    public final int WARNING=2;
    /**
     * Constante que localiza posição dos
     * atributos usados na impressão de
     * erros pela stream.
     */
    public final int SEVERE=3;
    /**
     * Variável que indica qual deve ser o atributo
     * usado para imprimir (NORMAL, INFO, etc).
     */
    private int flushSwitch;
    
    /*
    Por enquanto, não há como especificar o tamanho do Buffer.
    O padrão, inicial, do "ByteArrayOutputStream" é 32 Bytes
    */
    
    /**
     * Cria uma OutputStream para o "StyledDocument" de um
     * JTextPane, usando atributos padrão de cores e "UTF-8"
     * como a codificação padrão.
     * @param pane Para ser destino da Stream.
     */
    public BufferedPaneOutputStream(JTextPane pane) {
        this(pane, "UTF-8", null, null, null, null);
        /*
        É deselegante passar null e fazer a verificação no outro
        construtor, mas não consigo pensar em como criar um attributeset
        anonimamente para passar para o construtor "Geral".
        Talvez, fazer um attributeset do sistema?
        */
    }
    
    /**
     * Cria uma OutputStream para o "StyledDocument" de um
     * JTextPane, usando atributos padrão de cores para
     * info, warning e error.
     * @param pane     Para ser destino da Stream.
     * @param encoding Codificação a ser usada.
     * @param normal   Padrão de atributos para o write
     *                  sobrecarregado de OutputStream.
     */
    public BufferedPaneOutputStream(JTextPane pane, String encoding, AttributeSet normal) {
        this(pane, encoding, normal, null, null, null);
    }
    
    /**
     * Constrói um OutputStream para o "StyledDocument"
     * de um determinado "JTextPane".
     * @param pane    Pane para ser destino da Stream.
     * @param Charset Codificação a ser usada nos bytes.
     * @param attribs Attributos para saida normal, se nulo
     *                fundo é branco e fonte é preta.
     * @param info    Attributos para saida pelo método:
     *                {@link PaneOutputStream.writeInfo(int)}.
     *                Se nulo, fundo branco e fonte azul.
     * @param warn    Attributos para saida pelo método:
     *                {@link PaneOutputStream.writeWarning(int)}.
     *                Se nulo, fundo branco e fonte laranja.
     * @param err     Attributos para saida pelo método:
     *                {@link PaneOutputStream.writeErr(int)}.
     *                Se nulo, fundo branco e fonte vermelha.
     */
    public BufferedPaneOutputStream(JTextPane pane, String Charset, AttributeSet attribs, AttributeSet info, AttributeSet warn, AttributeSet err){
        super();
        this.pane = pane;
        doc = pane.getStyledDocument();
        encoding = Charset;
        
        
        if (attribs == null){
            SimpleAttributeSet standard = new SimpleAttributeSet();
            StyleConstants.setForeground(standard, Color.BLACK);
            StyleConstants.setBackground(standard, Color.WHITE);
            StyleConstants.setBold(standard, false);
            attributes[NORMAL] = standard;
        }
        if (info == null){
            SimpleAttributeSet standard = new SimpleAttributeSet();
            StyleConstants.setForeground(standard, Color.BLUE);
            StyleConstants.setBackground(standard, Color.WHITE);
            StyleConstants.setBold(standard, false);
            attributes[INFO] = standard;
        }
        if (warn == null){
            SimpleAttributeSet standard = new SimpleAttributeSet();
            StyleConstants.setForeground(standard, new Color(255, 119, 0));
            StyleConstants.setBackground(standard, Color.WHITE);
            StyleConstants.setBold(standard, false);
            attributes[WARNING] = standard;
        }
        if (err == null){
            SimpleAttributeSet standard = new SimpleAttributeSet();
            StyleConstants.setForeground(standard, Color.RED);
            StyleConstants.setBackground(standard, Color.WHITE);
            StyleConstants.setBold(standard, false);
            attributes[SEVERE] = standard;
        }
        
        flushSwitch=NORMAL;
    } 

    /**
     * Sobrecarrega flush da classe pai, para usar
     * o atributo {@link PaneOutputStream.normal} e a
     * codificação {@link PaneOutputStream.encoding}.
     * @throws IOException Em erro.
     */
    @Override
    public void flush() throws IOException {
        try {
            doc.insertString(doc.getLength(), buffer.toString(encoding), attributes[flushSwitch]);
        } catch (BadLocationException ex) {
            Logger.getLogger(BufferedPaneOutputStream.class.getName()).log(Level.SEVERE, null, ex);
        }
        buffer.reset();
    }
    
    @Override
    public void write(int b) throws IOException {
        buffer.write(b);
        pane.setCaretPosition(doc.getLength());
    }
    
    /**
     * Escreve na Stream usando os atributos
     * {@link PaneOutputStream.info}
     * @param b Byte a escrever.
     * @throws IOException Em erro.
     */
    public void writeInfo(int b) throws IOException {
        flushSwitch = INFO;
        write(b);
        flushSwitch = NORMAL;
    }
    
    /**
     * Escreve na Stream usando os atributos
     * {@link PaneOutputStream.warn}
     * @param b Byte a escrever.
     * @throws IOException Em erro.
     */
    public void writeWarning(int b) throws IOException {
        flushSwitch = WARNING;
        write(b);
        flushSwitch = NORMAL;
    }
    
    /**
     * Escreve na Stream usando os atributos
     * {@link PaneOutputStream.err}
     * @param b Byte a escrever.
     * @throws IOException Em erro.
     */
    public void writeErr(int b) throws IOException {
        flushSwitch = SEVERE;
        write(b);
        flushSwitch = NORMAL;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters de atributos">
    /**
     * Retorna os atributos usados
     * para a escrita padrão.
     * @return AttributeSet padrão.
     */ 
    public AttributeSet getNormal() {
        return attributes[NORMAL];
    }
    
    /**
     * Altera os atributos para escrita de
     * warnings.
     * @param normal AttributeSet para warnings.
     */
    public void setNormal(AttributeSet normal) {
        attributes[NORMAL] = normal;
    }
    
    /**
     * Retorna os atributos usados
     * para a escrita de informações.
     * @return AttributeSet de infos..
     */
    public AttributeSet getInfo() {
        return attributes[INFO];
    }
    
    /**
     * Altera os atributos para escrita de
     * informações.
     * @param info AttributeSet para infos..
     */
    public void setInfo(AttributeSet info) {
        attributes[INFO] = info;
    }
    
    /**
     * Retorna os atributos usados
     * para a escrita de erros.
     * @return AttributeSet de erros.
     */
    public AttributeSet getSevere() {
        return attributes[SEVERE];
    }
    
    /**
     * Altera os atributos para escrita de
     * erros.
     * @param err AttributeSet para erros.
     */
    public void setSevere(AttributeSet err) {
        attributes[SEVERE] = err;
    }
    
    /**
     * Retorna os atributos usados
     * para a escrita de warnings.
     * @return AttributeSet de warnings.
     */
    public AttributeSet getWarn() {
        return attributes[WARNING];
    }
    
    /**
     * Altera os atributos para escrita de
     * warnings.
     * @param warn AttributeSet para warnings.
     */
    public void setWarn(AttributeSet warn) {
        attributes[WARNING] = warn;
    }
    
    /**
     * Retorna o tamanho do Buffer interno.
     * @return tamanho em inteiro.
     */
    public int getBufferSize() {
        return buffer.size();
    }
    
    /**
     * Retorna o pane destino da Stream.
     * @return Instância do JTextPane.
     */
    public JTextPane getPane() {
        return pane;
    }
    
    /**
     * Retorna o nome do charset
     * em formato de String.
     * @return nome.
     */
    public String getCharsetName() {
        return encoding;
    }
    //</editor-fold>

    /**
     * Troca o {@link import javax.swing.text.AttributeSet}
     * usado na impressão. Para tornar a chamada mais
     * persistente à mudanças, use as constantes de atributos.
     * @param attributeSet para ser usado na impressão. 
     */
    public void setCurrentAttributeSet(int attributeSet){
        if ((attributeSet > attributes.length)  || attributeSet<0)
            throw new ArrayIndexOutOfBoundsException("Não existe atributo na posição: "+attributeSet);
        
        flushSwitch = attributeSet;
    }
}
