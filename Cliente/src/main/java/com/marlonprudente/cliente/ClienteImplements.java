/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.cliente;

import com.marlonprudente.interfaces.Cliente;
import com.marlonprudente.interfaces.ClienteCoordenador;
import com.marlonprudente.interfaces.ServidorCoordenador;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class ClienteImplements extends UnicastRemoteObject implements Cliente{
    
    protected ClienteImplements() throws RemoteException{
        super();
    }

    @Override
    public void CosultarPassagens(ServidorCoordenador coordenador) throws RemoteException {
        coordenador.ConsultarPassagens();
    }

    @Override
    public void CosultarHoteis() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String ComprarPassagem(int passagemId, int quantidade) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String ReservarHospedagem(int hospedagemId, int quantidade) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String ComprarPacote(int passagemId, int hospedagemId, int quantidade) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
