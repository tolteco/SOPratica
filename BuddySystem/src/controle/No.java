/*
 * No da arvore
 */
package controle;

import entradasaida.RootSize;

public class No {

    public No pai, esquerda, direita;
    public boolean ocupado;
    public int pid;
    public byte nivel;

    /**
     * Para criar um novo no
     *
     * @param pai : Requer o no pai para saber o nivel desse no
     */
    public No(No pai) {
        this.pai = pai;
        ocupado = false;
        pid = 0;
        esquerda = null;
        direita = null;
        if (pai == null) {
            nivel = 0;
        } else {
            nivel = (byte) (pai.nivel + 1);
        }
    }

    /**
     * Tamanho do no atual
     *
     * @return : Tamanho do no em bytes (inteiro)
     */
    public int getSize() {
        return (int) (RootSize.ROOTSIZE / (Math.pow(2, nivel)));
    }

    /**
     * Funcao de alocacao de memoria, recebe o no como referencia
     *
     * @param pid : pid do processo
     * @return true se alocou com sucesso a memoria
     */
    public boolean alocar(int pid) {
        if (ocupado) {
            return false;
        }
        this.pid = pid;
        ocupado = true;
        return true;
    }

    public void desalocar() {
        this.ocupado = false;
    }

    /**
     * Retorna se o no esta dividido em subniveis ou nao
     *
     * @return : true se o no esta dividido em outros nos
     */
    public boolean isSplit() {
        return !(esquerda == null || direita == null);
    }

    @Override
    public String toString() {
        return "No{" + "ocupado=" + ocupado + "\n pid=" + pid + "\n nivel=" + nivel + '}';
    }

}

/**
 * Exemplo de codigo de no; disponivel em:
 * https://github.com/utkarshsimha/buddy_system/blob/master/src/Node.java
 */
