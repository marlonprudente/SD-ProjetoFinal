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
public interface ServidorPassagens extends Remote{
    public List<String> ConsultarPassagens() throws RemoteException;
    public boolean ComprarPassagemUnitaria(int id, int quantidade) throws RemoteException;
    public boolean ComprarPassagemPacote(int id, int quantidade) throws RemoteException;
    public void VerificarTransacoesPendentes() throws RemoteException;
    public void ConfirmarTransacaoPendente(int transacaoId) throws RemoteException;
    
}
