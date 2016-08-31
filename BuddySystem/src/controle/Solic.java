/*
 * Estrutura para solicitacoes de memoria
 */
package controle;


public class Solic {

    private boolean in; //Indica se e solicitacao de memoria
    private int qtde; //Indica a quantidade de memoria solicitada
    private int pid; //Indica processo pedindo memoria ou a liberacao

    /**
     * Construtor default de solicitacoes
     *
     * @param in : true se e solicitacao
     * @param qtde : quantidade de memoria a ser alocada (deve ser multiplo de
     * 4096)
     * @param pid : pid do processo requisitante
     */
    public Solic(boolean in, int qtde, int pid) {
        this.in = in;
        this.qtde = qtde;
        this.pid = pid;
    }

    public boolean isIn() {
        return in;
    }

    public void setIn(boolean in) {
        this.in = in;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Solicitacao: \n" + "Requisicao de memoria: " + in + "\nQuantidade Solicitada: " + qtde + "\nPID: " + pid;
    }
}
