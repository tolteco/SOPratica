/**
 * Guarda arvore de nos para estrutura
 */
package controle;

import entradasaida.RootSize;

public class Arvore {

    private No root;
    private int ocupada = 0;

    public No getRoot() {
        return root;
    }

    public int getOcupada() {
        return ocupada;
    }

    public Arvore() {
        root = createNo(null);
    }

    private No createNo(No pai) {
        No n = new No(pai);
        return n;
    }

    /**
     * Funcao para solicitacao de memoria
     *
     * @param s : Estrutura da solicitacao a ser alocada
     * @return : true se foi possivel alocar o no
     */
    public boolean pedeMemoria(Solic s) {
        if (s.getQtde() > RootSize.ROOTSIZE) {
            //System.out.println("Tamanho exigido maior que a memoria disponivel");
            return false;
        }
        No n = achaLivre(root, s.getQtde());
        if (n == null) {
            //System.out.println("Tamanho exigido indisponivel");
            return false;
        } else {
            n.alocar(s.getPid());
            ocupada = ocupada + n.getSize();
            return true;
        }
    }

    /**
     * Libera a memoria do pid inserido na solicitacao
     *
     * @param So : Solicitacao de liberacao de memoria
     * @return : true se o no foi desalocado
     */
    public boolean liberaMemoria(Solic So) {
        if (So.getQtde() > RootSize.ROOTSIZE) {
            //System.out.println("Tamanho exigido maior que a memoria disponivel");
            return false;
        }
        No n = achaOcupado(root, So.getPid());
        System.out.println(n);
        if (n == null) {
            //System.out.println("PID informado nao encontrado na arvore");
            return false;
        } else {
            n.desalocar(); //Desaloca o no
            ocupada = ocupada - n.getSize();
            procuraVazios(n); //Procura ramos da arvore que nao estejam alocados para reduzi-los a um no menor
            return true;
        }
    }

    /**
     * Procura o no que o processo esta alocando
     *
     * @param n : No base para consulta
     * @param pid : pid do processo
     * @param ni : nivel da memoria a ser desalocada
     * @return : No encontrado que deve ser desalocado, retorna null se nao for
     * encontrado
     */
    private No achaOcupado(No n, int pid) {
        No temp = null;
        if (n.ocupado == true || n.isSplit()) {
            if (pid == n.pid && n.ocupado == true) {
                return n;
            } else if (n.isSplit()) {
                temp = achaOcupado(n.esquerda, pid);
                if (temp != null) {
                    return temp;
                }
                temp = achaOcupado(n.direita, pid);
                if (temp != null) {
                    return temp;
                }
            } else {
                return null;
            }
        }
        return temp;
    }

    /**
     * Procura um no com espaco suficiente a partir do no entregue
     *
     * @param n : no a ser usado como base (geralmente usado root)
     * @param tam : tamanho solicitado
     * @return : no que deve ser alocado (ja dividido e tudo)
     */
    private No achaLivre(No n, int tam) {
        if (n.ocupado == true) {
            return null;
        }
        No temp = null;
        //Se o no nao esta quebrado, se o tamanho solicitado e pelo o menos o que o 
        //no tem e se o tamanho e maior que a metade desse no (nao divide, tem que alocar ele)
        if (!n.isSplit() && tam <= n.getSize() && tam > n.getSize() / 2) {
            if (n.ocupado == false) {
                return n;
            } else {
                return null;
            }
        } else if (tam <= n.getSize() / 2) { //Se ele tem que ser quebrado
            if ((n.nivel + 1) == RootSize.MAXLEVELS) {
                //System.out.println("Erro. Tentativa de quebra de memoria em excesso, retornando bloco de nivel maximo");
                return n;
            }
            if (!n.isSplit()) {
                splitNo(n);
            }
            temp = achaLivre(n.esquerda, tam);
            if (temp != null) {
                return temp;
            }
            temp = achaLivre(n.direita, tam);
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
     * TENTA imprimir a arvore como esta; comeca pelo raiz, entao percorre a
     * subarvore esquerda para cada no e depois direita
     *
     * @param n : No (recebe inicialmente root)
     */
    public void imprimeArvore(No n) {
        System.out.println("No:");
        if (n.ocupado) {
            System.out.println("Alocado");
            System.out.println("PID: " + n.pid);
        } else {
            System.out.println("Nao alocado");
        }
        System.out.println("Nivel: " + n.nivel);
        if (n.esquerda != null) {
            System.out.println("=====Nos a esquerda=====");
            imprimeArvore(n.esquerda);
        }
        if (n.direita != null) {
            System.out.println("=====Nos a direita=====");
            imprimeArvore(n.direita);
        }
    }

    /**
     * Procura nos vazios para junta-los (Eita funcao chata de programar)
     *
     * @param n : No para buscar (comeca com root)
     */
    private void procuraVazios(No n) {
        if (n.pai != null) {
            if ((!(n.pai.direita.ocupado) && !(n.pai.esquerda.ocupado)) && (!n.pai.direita.isSplit() && !n.pai.esquerda.isSplit())) { //Se nem o no da direita nem o da esquerda estao ocupados
                n = n.pai;
                mataFilhos(n);
                procuraVazios(n);
            }
        }
    }

    /**
     * Imprime estado da memoria
     *
     * @param n : No para funcao (comeca com root)
     */
    public void imprimeMemoria(No n) {
        if (n.isSplit()) {
            imprimeMemoria(n.esquerda);
            imprimeMemoria(n.direita);
        } else if (n.ocupado) {
            System.out.println("PID = " + n.pid + "; Memoria alocada = " + n.getSize());
        }
    }

    /**
     * Mata os filhos de um no
     * @param n : no pai
     */
    private void mataFilhos(No n) {
        n.esquerda = null;
        n.direita = null;
    }
}