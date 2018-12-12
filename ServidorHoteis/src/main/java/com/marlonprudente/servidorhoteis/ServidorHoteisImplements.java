/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.servidorhoteis;

import com.marlonprudente.interfaces.ServidorCoordenador;
import com.marlonprudente.interfaces.ServidorHoteis;
import com.marlonprudente.transacoesutils.Hoteis;
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
public class ServidorHoteisImplements extends UnicastRemoteObject implements ServidorHoteis{
    
    List<Hoteis> hoteis = new ArrayList<>();
    List<Transacao> transacoes = new ArrayList<>();
    ServidorCoordenador coordenador;
    
    protected ServidorHoteisImplements() throws RemoteException {
        super();
    }
    
    @Override
    public List<String> ConsultarHoteis() throws RemoteException {
        System.out.println("Consulta de hoteis solicitada!");
        List<String> listaHoteis = new ArrayList<>();        
        for(Hoteis h : hoteis){
            listaHoteis.add("Id: " + h.getId() + " Localização: " + h.getLocalizacao() + " Quartos Disponiveis: " + h.getQuartosDisponiveis());
        }
        
        return listaHoteis;
    }

    @Override
    public boolean ReservarHotelUnitario(int id, int quantidade) throws RemoteException {
        System.out.println("Venda de hotel solicitada!");
        for (Hoteis h : hoteis) {
            if (h.getId() == id) {
                if (h.getQuartosDisponiveis() < quantidade) {
                    return false;
                }
                h.setQuartosDisponiveis(h.getQuartosDisponiveis() - quantidade);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean ReservarHotelPacote(int id, int quantidade, int transacaoId) throws RemoteException {
        System.out.println("Venda de Pacote solicitada!");
        for (Hoteis h : hoteis) {
            if (h.getId() == id) {
                if (h.getQuartosDisponiveis() < quantidade) {
                    System.out.println("Não existe quartos com a quantidade solicitada.");
                    return false;
                }
                h.setQuartosDisponiveis(h.getQuartosDisponiveis() - quantidade);
                Transacao t = new Transacao();
                t.TransacaoPassagem(transacaoId, quantidade, id);
                transacoes.add(t);
                salvarTransacoes();
                System.out.println("Hotel para pacote vendido");
                return true;
            }
        }
        return false;
    }

    @Override
    public void AdicionarHotel(Integer id, String nome, String localizacao, Integer quartos, Integer pessoasPorQuarto, Integer valorQuarto) throws RemoteException {
        System.out.println("Adicionando Hoteis");
        Hoteis h = new Hoteis(id,nome,localizacao,quartos,pessoasPorQuarto, valorQuarto);
        hoteis.add(h);
    }

    @Override
    public void VerificarTransacoesPendentes() throws RemoteException {
        if (transacoes.isEmpty()) {
            recuperarTransacoes();
        }
        int index = 0;
        for (Transacao t : transacoes) {
            System.out.println("Transacao Id: " + t.getId() + " Status: " + t.getStatusTransacao());
            if (t.getStatusTransacao() == 1) {
                if (this.coordenador.ConsultarTransacao(t.getId()) == 2) {
                    //confirmar
                    System.out.println("Confirmando Transacao Id: " + t.getId());
                    t.confirmarTransacao();
                    transacoes.set(index, t);

                }
            }
            index++;
        }
    }

    @Override
    public void ConfirmarTransacaoPendente(int transacaoId) throws RemoteException {
        System.out.println("Confirmando transacao de ID: " + transacaoId);
        if (transacoes.isEmpty()) {
            recuperarTransacoes();
        }
        int index = 0;
        for (Transacao t : transacoes) {
            if (t.getId() == transacaoId) {
                if (t.getStatusTransacao() == 1) {
                    //confirmar
                    t.confirmarTransacao();
                    transacoes.set(index, t);
                    salvarTransacoes();
                }
            }
            index++;
        }
    }

    @Override
    public void setServidorCoordenador(ServidorCoordenador coordenador) throws RemoteException {
        this.coordenador = coordenador;
        VerificarTransacoesPendentes();
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
