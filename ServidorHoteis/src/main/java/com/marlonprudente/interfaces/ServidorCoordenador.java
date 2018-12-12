/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public interface ServidorCoordenador extends Remote{    
    public int ConsultarTransacao(int transacaoId) throws RemoteException;
    public void setServidorHoteis(ServidorHoteis hoteis) throws RemoteException;
}
