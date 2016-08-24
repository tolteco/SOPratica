/*
 * No da arvore
 */
package controle;

/**
 *
 * @author Maycon
 */
public class No {

    public No pai, esquerda, direita;
    public boolean ocupado;
    public byte nivel;

    /**
     * Para criar um novo no
     * @param pai : Requer o no pai para saber o nivel desse no
     */
    public No(No pai) {
        this.pai = pai;
        ocupado = false;
        esquerda = null;
        direita = null;
        if (pai == null){
            nivel = 0;
        } else{
            nivel = (byte) (pai.nivel + 1);
        }
    }
    
    /**
     * Funcao de alocacao de memoria, recebe o no como referencia
     * @return true se alocou com sucesso a memoria
     */
    public boolean alocar(){
        if (ocupado){
            return false;
        }
        ocupado = true;
        return true;
    }
}
