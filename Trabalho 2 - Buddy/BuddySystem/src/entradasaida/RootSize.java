/*
 * Informacoes sobre a base da memoria
 */
package entradasaida;

/**
 *
 * @author Maycon
 */
public class RootSize {

    public static final int ROOTSIZE = 8388608;
    public static final int MAXLEVELS = 12;
    public static final int BLOCKSIZE = ROOTSIZE / 2048;
    public static final int PAGESIZE = 4096;
    public static final int PAGESPERBLOCK = BLOCKSIZE / PAGESIZE;
}
