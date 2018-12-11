/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.servidorpassagens;

import com.marlonprudente.interfaces.ServidorCoordenador;
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
    ServidorCoordenador coordenador;

    protected ServidorPassagensImplements() throws RemoteException {
        super();
    }

    @Override
    public List<String> ConsultarPassagens() throws RemoteException {
        System.out.println("Consulta de passagens solicitada.");
        List<String> listaPassagens = new ArrayList<>();

        for (Passagem p : passagens) {
            listaPassagens.add("Id: " + p.getId() + " De: " + p.getDe() + " Para: " + p.getPara() + " Valor: " + p.getValor() + " Disponiveis: " + p.getPoltronas());
        }

        return listaPassagens;
    }

    @Override
    public void AdicionarPassagem(Integer id, Integer poltronas, String para, String de, Date data, Integer valor) throws RemoteException {
        System.out.println("Adicionando nova passagem");
        Passagem p = new Passagem(id, poltronas, para, de, data, valor);
        passagens.add(p);
    }

    @Override
    public synchronized boolean ComprarPassagemUnitaria(int id, int quantidade) throws RemoteException {
        System.out.println("Compra de passagem solicitada.");
        for (Passagem p : passagens) {
            if (p.getId().equals(id)) {
                if (p.getPoltronas() < quantidade) {
                    return false;
                }
                p.setPoltronas(p.getPoltronas() - quantidade);
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean ComprarPassagemPacote(int id, int quantidade, int transacaoId) throws RemoteException {
        System.out.println("Compra de pacote solicitada");
        for (Passagem p : passagens) {
            if (p.getId().equals(id)) {
                if (p.getPoltronas() < quantidade) {
                    System.out.println("Não existe poltronas disponíveis para essa quantidade.");
                    return false;
                }
                p.setPoltronas(p.getPoltronas() - quantidade);
                Transacao t = new Transacao();
                t.TransacaoPassagem(transacaoId, quantidade, id);
                transacoes.add(t);
                salvarTransacoes();
                System.out.println("Pacote passagem vendido.");
                return true;
            }
        }
        return false;
    }

    @Override
    public void VerificarTransacoesPendentes() throws RemoteException {
        System.out.println("Verificando Transacoes pendentes");
        if (transacoes.isEmpty()) {
            recuperarTransacoes();
        }
        int index = 0;
        for (Transacao t : transacoes) {
            System.out.println("Transacao Id: " + t.getId() + " Status: " + t.getStatusTransacao());
            if (t.getStatusTransacao() == 1) {
                int statusTransacao = this.coordenador.ConsultarTransacao(t.getId());
                if (statusTransacao == 2) {
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
    public void setServidorCoordenador(ServidorCoordenador coordenador) throws RemoteException {
        this.coordenador = coordenador;
        VerificarTransacoesPendentes();
    }

}
