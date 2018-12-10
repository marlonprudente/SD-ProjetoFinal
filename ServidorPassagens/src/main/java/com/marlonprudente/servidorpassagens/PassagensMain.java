/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.servidorpassagens;

import com.marlonprudente.interfaces.ServidorPassagens;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class PassagensMain {
    public static void main(String[] args) {
                ServidorPassagens servidorPassagens = null;
        //ServidorCoordenador servidorCoordenador = null;

        try {
            servidorPassagens = new ServidorPassagensImplements();
            //servidorCoordenador = new ServidorCoordenadorImplements();
        } catch (RemoteException e) {
            System.out.println("Main: " + e);
        }

        try {
            Registry servicoNomesRMI = LocateRegistry.createRegistry(2002);
            servicoNomesRMI.rebind("passagens", servidorPassagens);            
            
                while (true) {                
                Scanner scanner = new Scanner(System.in);
                Integer op = scanner.nextInt();
                String input;
                switch (op) {
                    case 1:
                        DateFormat df = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
                        servidorPassagens.AdicionarPassagem(1, 10, "", "", df.parse("09/12/2018"), 100);
                        servidorPassagens.ComprarPassagemPacote(1, 2);
                        servidorPassagens.ComprarPassagemPacote(1, 2);
                        servidorPassagens.ComprarPassagemPacote(1, 2);
                        break;
                    case 2:
                        servidorPassagens.VerificarTransacoesPendentes();
                        break;
                    default:
                        System.out.println("Opção Inválida!");
                        break;
                }
            }
                        
        }catch (Exception ex) {

        }
    }
    
}
