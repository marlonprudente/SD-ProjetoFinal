/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.servidorpassagens;

import com.marlonprudente.interfaces.ServidorCoordenador;
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

        try {
            servidorPassagens = new ServidorPassagensImplements();
            
        } catch (RemoteException e) {
            System.out.println("Main: " + e);
        }

        try {
            Registry servicoNomesRMI = LocateRegistry.createRegistry(2002);
            servicoNomesRMI.rebind("passagens", servidorPassagens);            
                while (true) { 
                System.out.println("1 - Cadastrar Passagens");
                System.out.println("2 - Verificar Transacoes Pendentes");
                Scanner scanner = new Scanner(System.in);
                Integer op = scanner.nextInt();
                String input;
                switch (op) {
                    case 1:
                        DateFormat df = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);
                        servidorPassagens.AdicionarPassagem(1, 10, "CWB", "SP", df.parse("09/12/2018"), 100);
                        servidorPassagens.AdicionarPassagem(2, 15, "SP", "CWB", df.parse("12/12/2018"), 150);
                        servidorPassagens.AdicionarPassagem(3, 3, "CWB", "MG", df.parse("14/12/2018"), 200);
                        servidorPassagens.AdicionarPassagem(4, 11, "MG", "CWB", df.parse("19/12/2018"), 210);
                        servidorPassagens.AdicionarPassagem(5, 45, "CWB", "Lisboa", df.parse("23/12/2018"), 1000);
                        break;
                    case 2:
                        Registry servicoNomesCoordenador = LocateRegistry.getRegistry(2001);            
                        ServidorCoordenador coordenador = (ServidorCoordenador)servicoNomesCoordenador.lookup("coordenador");
                        servidorPassagens.setServidorCoordenador(coordenador);
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
