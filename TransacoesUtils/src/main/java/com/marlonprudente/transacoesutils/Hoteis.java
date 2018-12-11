/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.transacoesutils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class Hoteis implements Serializable{
    Integer id = -1;    
    Integer valorQuarto;
    String localizacao;
    String nome;
    Integer quartosDisponiveis;
    Integer quantidadePessoasPorQuarto;
    
    public Hoteis(Integer id, String nome, String localizacao, Integer quartos, Integer pessoasPorQuarto, Integer valorQuarto){
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.quartosDisponiveis = quartos;
        this.valorQuarto = valorQuarto;
        this.quantidadePessoasPorQuarto = pessoasPorQuarto;       
    }
    
    public String getLocalizacao(){
        return this.localizacao;
    }
    
    public int getId(){
        return this.id;
    }
    public void setLocalizacao(String novaLocalizacao){
        this.localizacao = novaLocalizacao;
    }
    public String getNome(){
        return this.nome;
    }
    public void setNome(String novoNome){
        this.nome = novoNome;
    }
    public int getQuartosDisponiveis(){
        return this.quartosDisponiveis;
    }
    public void setQuartosDisponiveis(Integer novaQuantidade){
        this.quartosDisponiveis = novaQuantidade;
    }  
    public void setQuantidadePessoasDisponiveis(Integer novaQuantidade){
        this.quantidadePessoasPorQuarto = novaQuantidade;
    }
}
