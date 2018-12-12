/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.servidorhoteis;

import com.marlonprudente.interfaces.ServidorCoordenador;
import com.marlonprudente.interfaces.ServidorHoteis;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class HoteisMain {

    public static void main(String[] args) {
        ServidorHoteis servidorHoteis = null;
        ServidorCoordenador servidorCoordenador = null;

        try {
            servidorHoteis = new ServidorHoteisImplements();

        } catch (RemoteException e) {
            System.out.println("Main: " + e);
        }

        try {
            Registry servicoNomesRMI = LocateRegistry.createRegistry(2003);
            servicoNomesRMI.rebind("hoteis", servidorHoteis);
            while (true) {
                System.out.println("1 - Cadastrar Hoteis");
                System.out.println("2 - Verificar Transacoes Pendentes");
                Scanner scanner = new Scanner(System.in);
                Integer op = scanner.nextInt();
                String input;
                switch (op) {
                    case 1:
                        DateFormat df = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
                        servidorHoteis.AdicionarHotel(1, "Hotel Maria", "CWB", 10, 10, 100);
                        servidorHoteis.AdicionarHotel(2, "Hotel Jose", "SP", 10, 15, 1000);
                        servidorHoteis.AdicionarHotel(3, "Hotel Joana", "MG", 10, 5, 180);
                        servidorHoteis.AdicionarHotel(4, "Hotel Joaquina", "SC", 10, 1, 150);
                        break;
                    case 2:
                        Registry servicoNomesCoordenador = LocateRegistry.getRegistry(2001);
                        ServidorCoordenador coordenador = (ServidorCoordenador) servicoNomesCoordenador.lookup("coordenador");
                        servidorHoteis.setServidorCoordenador(coordenador);
                        servidorHoteis.VerificarTransacoesPendentes();
                        break;
                    default:
                        System.out.println("Opção Inválida!");
                        break;
                }
            }

        } catch (Exception ex) {

        }
    }
}
