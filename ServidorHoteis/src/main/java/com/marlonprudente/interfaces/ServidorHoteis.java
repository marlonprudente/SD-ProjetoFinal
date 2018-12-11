/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public interface ServidorHoteis extends Remote{
    public List<String> ConsultarHoteis() throws RemoteException;
    public boolean ReservarHotelUnitario(int id, int quantidade) throws RemoteException;
    public boolean ReservarHotelPacote(int id, int quantidade, int transacaoId) throws RemoteException;
    public void AdicionarHotel(Integer id, String nome, String localizacao, Integer quartos, Integer pessoasPorQuarto, Integer valorQuarto) throws RemoteException;
    public void VerificarTransacoesPendentes() throws RemoteException;
    public void ConfirmarTransacaoPendente(int transacaoId) throws RemoteException;
    public void setServidorCoordenador(ServidorCoordenador coordenador) throws RemoteException;
}
