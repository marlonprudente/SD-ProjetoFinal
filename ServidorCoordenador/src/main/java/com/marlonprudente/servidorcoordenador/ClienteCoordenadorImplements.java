/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.servidorcoordenador;

import com.marlonprudente.interfaces.ClienteCoordenador;
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
import java.util.List;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class ClienteCoordenadorImplements extends UnicastRemoteObject implements ClienteCoordenador {
    ServidorPassagens passagens;
     List<Transacao> transacoes = new ArrayList<>();
     
    protected ClienteCoordenadorImplements() throws RemoteException{
        
        super();
    }

    @Override
    public List<String> ConsultarPassagens() throws RemoteException {
        System.out.println("Consultando Passagem");
        List<String> teste = new ArrayList<String>();
        this.passagens.ConfirmarTransacaoPendente(1);
        teste.add("teste");
        
        return teste;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> ConsultarHoteis() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean ComprarPassagem(int id, int quantidade) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean ReservarHotel(int id, int quantidade) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean ComprarPacote(int PassagemId, int hotelId, int quantidade) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setServidorPassagens(ServidorPassagens passagens) throws RemoteException {
        this.passagens = passagens;
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
                    if (!transacoes.contains(transacao)) {
                        transacoes.add((Transacao) transacao);
                    }
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
