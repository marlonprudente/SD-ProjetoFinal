/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.servidorcoordenador;

import com.marlonprudente.interfaces.ServidorCoordenador;
import com.marlonprudente.interfaces.ServidorHoteis;
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
public class ServidorCoordenadorImplements extends UnicastRemoteObject implements ServidorCoordenador {

    ServidorPassagens passagens;
    ServidorHoteis hoteis;
    List<Transacao> transacoes = new ArrayList<>();

    protected ServidorCoordenadorImplements() throws RemoteException {
        super();
    }

    @Override
    public List<String> ConsultarPassagens() throws RemoteException {
        System.out.println("Consultando Passagem");
        List<String> listaPassagens = new ArrayList<String>();
        listaPassagens = this.passagens.ConsultarPassagens();
        return listaPassagens;
    }

    @Override
    public List<String> ConsultarHoteis() throws RemoteException {
       System.out.println("Consultando Hoteis");
        List<String> listaHoteis = new ArrayList<String>();
        listaHoteis = this.hoteis.ConsultarHoteis();
        return listaHoteis;
    }

    @Override
    public boolean ComprarPassagem(int id, int quantidade) throws RemoteException {
        return this.passagens.ComprarPassagemUnitaria(id, quantidade);
    }

    @Override
    public boolean ReservarHotel(int id, int quantidade) throws RemoteException {
        return this.hoteis.ReservarHotelUnitario(id, quantidade);
    }

    @Override
    public boolean ComprarPacote(int PassagemId, int hotelId, int quantidade) throws RemoteException {
        if (transacoes.isEmpty()) {
            recuperarTransacoes();
        }
        Transacao t = new Transacao();
        int index = transacoes.size() + 1;
        System.out.println("Id nova Transacao: " + index);
        t.Transacao(index, quantidade, PassagemId, hotelId);
        transacoes.add(t);
        boolean passagemRetorno = this.passagens.ComprarPassagemPacote(PassagemId, quantidade, index);
        System.out.println("Passagem: " + passagemRetorno);
        boolean hoteisRetorno = this.hoteis.ReservarHotelPacote(hotelId, quantidade, index);
        System.out.println("Hoteis: " + hoteisRetorno);
        boolean confirmacaoPacote = (passagemRetorno && hoteisRetorno);
        Transacao aux = null;
        if (confirmacaoPacote) {
            for (Transacao t2 : transacoes) {
                if (t2.getId() == index) {
                    t2.confirmarTransacao();
                    aux = t2;
                }
            }
            transacoes.set(index - 1, aux);
            salvarTransacoes();
            this.passagens.ConfirmarTransacaoPendente(index);
            this.hoteis.ConfirmarTransacaoPendente(index);
        }


        return confirmacaoPacote;
    }

    @Override
    public int ConsultarTransacao(int transacaoId) throws RemoteException {
        System.out.println("Consulta de status da transacao ID: " + transacaoId);
        if (transacoes.isEmpty()) {
            recuperarTransacoes();
        }
        int transacaoStatus = 0;
        for (Transacao t : transacoes) {
            if (t.getId() == transacaoId) {
                transacaoStatus = t.getStatusTransacao();
                System.out.println("Status: " + transacaoStatus);
            }
        }
        return transacaoStatus;
    }

    @Override
    public void setServidorPassagens(ServidorPassagens passagens) throws RemoteException {
        this.passagens = passagens;
    }
    
    @Override
    public void setServidorHoteis(ServidorHoteis hoteis) throws RemoteException {
        this.hoteis = hoteis;
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

    @Override
    public void ConsultarTransacoes() throws RemoteException {
        if (transacoes.isEmpty()) {
            recuperarTransacoes();
        }
        for(Transacao t : transacoes){
            System.out.println("Id: " + t.getId() + " PassagemId: " + t.getIdPassagem() + " HotelId: " + t.getIdHotel() + " Status: " + t.getStatusTransacao());
        }
    }
}
