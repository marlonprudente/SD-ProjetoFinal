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
public interface ClienteCoordenador extends Remote{
    public List<String> ConsultarPassagens() throws RemoteException;
    public List<String> ConsultarHoteis() throws RemoteException;
    public boolean ComprarPassagem(int id, int quantidade) throws RemoteException;
    public boolean ReservarHotel(int id, int quantidade) throws RemoteException;
    public boolean ComprarPacote(int PassagemId, int hotelId, int quantidade) throws RemoteException;
}
