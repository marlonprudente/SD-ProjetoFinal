/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.servidorcoordenador;

import com.marlonprudente.interfaces.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Marlon Prudente <marlon.oliveira at alunos.utfpr.edu.br>
 */
public class CoordenadorMain {
private static final Logger log = Logger.getLogger(CoordenadorMain.class.getName());
    public static void main(String[] args) {
        ServidorCoordenador coordenador = null;       

        try {
            Handler fileHandler = new FileHandler("./logClienteMain.log");
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            log.addHandler(fileHandler);
            log.info("Logger name: " + log.getName());
            coordenador = new ServidorCoordenadorImplements();            
        } catch (Exception e) {
            log.severe("Main: " + e);
        }

        try {
            //Registro para Clientes:
            Registry servicoNomesCoordenador = LocateRegistry.createRegistry(2001);
            servicoNomesCoordenador.rebind("coordenador", coordenador);
            log.info("Criado registro 2001 para Clientes.");
            //Registro para os Servidores Passagem e Hoteis
            Registry servicoNomesPassagens = LocateRegistry.getRegistry(2002);            
            ServidorPassagens passagens = (ServidorPassagens)servicoNomesPassagens.lookup("passagens");
            coordenador.setServidorPassagens(passagens);
            passagens.setServidorCoordenador(coordenador);
            log.info("Conectado ao registro 2002 ServidorPassagens.");
            Registry servicoNomesHoteis = LocateRegistry.getRegistry(2003);            
            ServidorHoteis hoteis = (ServidorHoteis)servicoNomesHoteis.lookup("hoteis");
            coordenador.setServidorHoteis(hoteis);
            hoteis.setServidorCoordenador(coordenador);
            log.info("Conectado ao registro 2003 ServidorHoteis.");
            while (true) {
                System.out.println("1 - Verificar Transacoes");
                Scanner scanner = new Scanner(System.in);
                Integer op = scanner.nextInt();
                switch (op) {
                    case 1:
                        coordenador.ConsultarTransacoes();
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
