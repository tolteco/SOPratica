/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barbearia;

import java.util.Random;

/**
 *
 * @author Juliano_Felipe
 */
public class Cliente {
    private static final String[] NAMES = {"Paulo", "Bob", "El Capone", "Bodhi", "Nabucodonosor",
                                           "Dam Son Jr.", "Leif", "Mathias", "Saad Maan", "Steve Rogers",
                                           "Cap Rogers", "W. W. W.", "Eddie", "Bone Saw", "BJORN"};
    private static final int MAX = NAMES.length;
    
    private final String nome;
    private final int id;
    private double tempoAtendimento;
    
    private String gerarNome(){
        Random rand = new Random();
        int orientation = rand.nextInt(5)+1;
        if ((orientation == 1) && id<MAX) return NAMES[id];
        else if ((orientation == 1) && id<MAX) return NAMES[MAX-id];
        else return NAMES[rand.nextInt(MAX)];
    }
    
    public Cliente(int id) {
        this.id = id;
        nome = gerarNome();
        tempoAtendimento = 0.0;
    }

    public double getTempoAtendimento() {
        return tempoAtendimento;
    }

    public void setTempoAtendimento(double tempoAtendimento) {
        this.tempoAtendimento = tempoAtendimento;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }
    
    /**
     * Para inserir na tabela de filas.
     * @return Array de 2 pos.
     */
    public Object[] getFila(){
        Object[] ret = {id, nome};
        return ret;
    }
    
    /**
     * Para inserir na tabela de nao atendidos.
     * @return Array de 2 pos.
     */
    public Object[] getNaoAtendido(){
        return getFila();
    }
    
    /**
     * Para inserir na tabela de atendidos.
     * @return Array de 3 pos.
     */
    public Object[] getAtendido(){
        Object[] ret = {id, nome, tempoAtendimento};
        return ret;
    }

    @Override
    public String toString() {
        return "Cliente{" + "nome=" + nome + ", id=" + id + ", tempoAtendimento=" + tempoAtendimento + '}';
    }
}
