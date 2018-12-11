/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public interface Cliente extends Remote{
    public void CosultarPassagens() throws RemoteException;
    public void CosultarHoteis() throws RemoteException;
    public String ComprarPassagem(int passagemId, int quantidade) throws RemoteException;
    public String ReservarHospedagem(int hospedagemId, int quantidade) throws RemoteException;
    public String ComprarPacote(int passagemId, int hospedagemId, int quantidade) throws RemoteException;
    public void setServidorCoordenador(ServidorCoordenador coordenador) throws RemoteException;
    
}
