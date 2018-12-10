/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.servidorpassagens;

import com.marlonprudente.transacoesutils.Passagem;
import com.marlonprudente.interfaces.ServidorPassagens;
import com.marlonprudente.transacoesutils.Transacao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class ServidorPassagensImplements extends UnicastRemoteObject implements ServidorPassagens {

    List<Passagem> passagens = new ArrayList<>();
    List<Transacao> transacoes = new ArrayList<>();
    

    protected ServidorPassagensImplements() throws RemoteException {
        super();
    }

    @Override
    public List<String> ConsultarPassagens() throws RemoteException {
        List<String> listaPassagens = new ArrayList<>();
        
        for(Passagem p : passagens){
            listaPassagens.add("Id: " + p.getId() + " De: " + p.getDe() + " Para: " + p.getPara() + " Valor: " + p.getValor());
        }
        
        return listaPassagens;
    }
    
    @Override
    public void AdicionarPassagem(Integer id, Integer poltronas, String para, String de, Date data, Integer valor) throws RemoteException {
        Passagem p = new Passagem(id,poltronas,para,de,data,valor);
        passagens.add(p);
    }

    @Override
    public synchronized boolean ComprarPassagemUnitaria(int id, int quantidade) throws RemoteException {
        for(Passagem p : passagens){
            if(p.getId().equals(id)){
                if(p.getPoltronas() < quantidade){
                    return false;
                }
                p.setPoltronas(p.getPoltronas() - quantidade);
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean ComprarPassagemPacote(int id, int quantidade) throws RemoteException {
                for(Passagem p : passagens){
            if(p.getId().equals(id)){
                if(p.getPoltronas() < quantidade){
                    return false;
                }
                p.setPoltronas(p.getPoltronas() - quantidade);
                Transacao t = new Transacao(transacoes.size(),quantidade,id);
                transacoes.add(t);
                salvarTransacoes();
                return true;
            }
        }
        return false;
    }

    @Override
    public void VerificarTransacoesPendentes() throws RemoteException {
        recuperarTransacoes();
        
        for(Transacao t : transacoes){
            System.out.println("Id: " + t.getIdPassagem());
        }
        
    }

    @Override
    public void ConfirmarTransacaoPendente(int transacaoId) throws RemoteException {
        System.out.println("ID: " + transacaoId);//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void salvarTransacoes() {
        try {
            FileOutputStream f = new FileOutputStream(new File("transacoes.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            for (Transacao t : transacoes) {
                o.writeObject(t);
            }
            o.close();
            f.close();

        } catch (Exception e) {

        }
    }

    public void recuperarTransacoes() {

        boolean cont = true;
        try {
            FileInputStream fi = new FileInputStream(new File("transacoes.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            while (cont) {  
                Transacao transacao = (Transacao) oi.readObject();
                if (transacao != null) {
                    if(!transacoes.contains(transacao))                    
                        transacoes.add((Transacao)transacao);
                } else {
                    cont = false;
                }
                
            }            
            oi.close();
            fi.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro: " + e);
        }

    }

    

}
