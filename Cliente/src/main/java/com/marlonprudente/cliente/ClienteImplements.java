/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.cliente;

import com.marlonprudente.interfaces.Cliente;
import com.marlonprudente.interfaces.ServidorCoordenador;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class ClienteImplements extends UnicastRemoteObject implements Cliente{
    ServidorCoordenador coordenador;
    protected ClienteImplements() throws RemoteException{
        super();
    }

    @Override
    public void CosultarPassagens() throws RemoteException {
        List<String> passagens = coordenador.ConsultarPassagens();
        
        for(String p : passagens){
            System.out.println(">" + p);
        }
    }

    @Override
    public void CosultarHoteis() throws RemoteException {
        List<String> hoteis = coordenador.ConsultarHoteis();
        for(String h : hoteis){
            System.out.println(">" + h);
        }
    }

    @Override
    public String ComprarPassagem(int passagemId, int quantidade) throws RemoteException {
        if(this.coordenador.ComprarPassagem(passagemId, quantidade)){
            return "Comprado com sucesso";
        }else{
            return "Não foi possivel realizar a compra!";
        }
    }

    @Override
    public String ReservarHospedagem(int hospedagemId, int quantidade) throws RemoteException {
        if(this.coordenador.ReservarHotel(hospedagemId, quantidade)){
            return "Comprado com sucesso";
        }else{
            return "Não foi possivel realizar a compra!";
        }
    }

    @Override
    public String ComprarPacote(int passagemId, int hospedagemId, int quantidade) throws RemoteException {
        if(this.coordenador.ComprarPacote(passagemId, hospedagemId, quantidade)){
            return "Comprado com sucesso";
        }else{
            return "Não foi possivel realizar a compra!";
        }
    }

    @Override
    public void setServidorCoordenador(ServidorCoordenador coordenador) throws RemoteException {
        this.coordenador = coordenador;
    }
    
}
