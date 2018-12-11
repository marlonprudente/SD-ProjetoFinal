package com.marlonprudente.transacoesutils;

/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */


import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class Passagem extends UnicastRemoteObject implements Serializable{

    Integer id = -1;
    String de = "";
    String para = "";    
    Date data;    
    Integer valor;
    Integer poltronasDisponiveis;

    public Passagem(Integer id, Integer poltronas, String para, String de, Date data, Integer valor) throws RemoteException {
        this.id = id;        
        this.data = data;        
        this.de = de;
        this.para = para;
        this.poltronasDisponiveis = poltronas;
        this.valor = valor;

    }
    
    public Date getData() {
        return this.data;
    }

    public void setData(String data) {
        try {
            DateFormat df = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
            this.data = df.parse(data);
        } catch (ParseException e) {
            System.out.println(e);
        }
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer novoId) {
        this.id = novoId;
    }

    public Integer getValor() {
        return this.valor;
    }

    public void setValor(Integer novoValor) {
        this.valor = novoValor;
    }

    public String getDe() {
        return this.de;
    }

    public void setDe(String novaCidadeOrigem) {
        this.de = novaCidadeOrigem;
    }

    public String getPara() {
        return this.para;
    }

    public void setPara(String novaCidadeDestino) {
        this.para = novaCidadeDestino;
    }
    public Integer getPoltronas() {
        return this.poltronasDisponiveis;
    }

    public void setPoltronas(Integer novasPoltronas) {
        this.poltronasDisponiveis = novasPoltronas;
    }
}
