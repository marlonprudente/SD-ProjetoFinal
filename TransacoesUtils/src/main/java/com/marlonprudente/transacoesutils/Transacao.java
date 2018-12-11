package com.marlonprudente.transacoesutils;

import java.io.Serializable;

/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */


/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class Transacao implements Serializable{
    int Id;
    int quantidadePoltrona;
    int idPassagem;
    int idHotel;
    int status; //2 - Realizado // 1 - Pendente de Confirmacao
    public void TransacaoPassagem(int id, int poltronas, int idPassagem){
        this.Id = id;
        this.quantidadePoltrona = poltronas;
        this.idPassagem = idPassagem;
        this.status = 1;
    }
    
    public void TransacaoHotel(int id, int poltronas, int idHotel){
    this.Id = id;
    this.quantidadePoltrona = poltronas;
    this.idHotel = idHotel;
    this.status = 1;
}
    
public void Transacao(int id, int poltronas,int idPassagem, int idHotel){
    this.Id = id;
    this.quantidadePoltrona = poltronas;
    this.idPassagem = idPassagem;
    this.idHotel = idHotel;
    this.status = 1;
}
    
    public int getId(){
        return this.Id;
    }
    
    public void confirmarTransacao(){
        this.status = 2;
    }
    
    public int getStatusTransacao(){
        return this.status;
    }
    
    public int getIdPassagem(){
        return this.idPassagem;
    }
    
    public int getIdHotel(){
        return this.idHotel;
    }
    
    
    
}
