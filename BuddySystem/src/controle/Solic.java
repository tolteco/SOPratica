/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

/**
 *
 * @author Maycon
 */
public class Solic {
    boolean in; //Indica se e solicitacao de memoria
    int qtde; //Indica a quantidade de memoria solicitada
    int pid; //Indica processo pedindo memoria ou a liberacao

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
    
}
