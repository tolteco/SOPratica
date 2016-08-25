/*
 * Guarda arvore de nos para estrutura
 */
package controle;

import java.util.ArrayList;

public class Arvore {

    private No root;
    private int index = 0;
    public ArrayList<No> list = new ArrayList<>();
    private final int MAX_LEVELS = 12;
    private final int ROOT_SIZE = 8388608; //4096 * 2048

    public Arvore() {
        root = null;
    }

    private No createNo(No pai) {
        No n = new No(pai);
        list.add(index, n);
        index++;
        return n;
    }

    public boolean requestMem(int tam) {
        if (tam > ROOT_SIZE) {
            System.err.println("Tamanho exigido maior que a memoria disponivel");
            return false;
        }
        No n = findNo(root, tam);
        if (n == null) {
            System.err.println("Tamanho exigido indisponivel");
            return false;
        } else {
            n.alocar();
        }
        return true;
    }

    /**
     * Procura um no com espaco suficiente a partir do no entregue
     * @param n : no a ser usado como base (geralmente usado root)
     * @param tam : tamanho solicitado
     * @return : no que deve ser alocado (ja divide e tudo)
     */
    private No findNo(No n, int tam) {
        if (n.ocupado == true) {
            return null;
        }
        No temp = null;
        //Se o no nao esta quebrado, se o tamanho solicitado e pelo o menos o que o 
        //no tem e se o tamanho e maior que a metade desse no (nao divide, tem que alocar ele)
        if (!isSplit(n) && tam <= n.getSize() && tam > n.getSize() / 2) {
            if (n.ocupado == false) {
                return n;
            } else {
                return null;
            }
        } else if (tam <= n.getSize() / 2) { //Se ele tem que ser quebrado
            if (!isSplit(n)) {
                splitNo(n);
            }
            temp = findNo(n.esquerda, tam);
            if (temp != null) {
                return temp;
            }
            temp = findNo(n.direita, tam);
            if (temp != null) {
                return temp;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Quebra o no em metade da direita e metade da esquerda
     *
     * @param n : No a ser dividido
     */
    private void splitNo(No n) {
        n.esquerda = createNo(n);
        n.direita = createNo(n);
    }

    /**
     * Retorna se o no esta dividido em subniveis ou nao
     *
     * @param n : No a ser verificado
     * @return : true se o no esta dividido em outros nos
     */
    private boolean isSplit(No n) {
        return !(n.esquerda == null || n.direita == null);
    }
}

/**
 * Exemplo de codigo de arvore; disponivel em:
 * https://github.com/utkarshsimha/buddy_system/blob/master/src/Tree.java
 */
