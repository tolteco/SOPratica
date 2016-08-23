/*
 * Guarda situacao da memoria para cada pagina
 */
package controle;

import java.util.Arrays;

public class Memoria {
    /**
     * Vetor que indica se as posicoes estao ocupadas ou nao
     */
    public boolean mem[] = new boolean[2048];

    public boolean[] getMem() {
        return mem;
    }

    public void setMem(boolean[] mem) {
        this.mem = mem;
    }

    public Memoria() {
        Arrays.fill(mem, false);
    }
}
