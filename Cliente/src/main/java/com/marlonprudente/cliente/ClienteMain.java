/*
 * Este sotfware foi feito para a UTFPR - Campus Curitiba;
 * O Código é livre para uso não comercial;
 * Desenvolvido através do Netbeans IDE.
 */
package com.marlonprudente.cliente;

import com.marlonprudente.interfaces.*;
import java.rmi.NotBoundException;
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
public class ClienteMain {
    
private static final Logger log = Logger.getLogger(ClienteMain.class.getName());

    public static void main(String[] args) {
        Cliente cliente = null;
        Registry servicoNomesRMI;

        try {
            Handler fileHandler = new FileHandler("./logClienteMain.log");
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            log.addHandler(fileHandler);
            log.info("Logger name: " + log.getName());
            cliente = new ClienteImplements();
        } catch (Exception e) {
            log.severe("Erro ao usar ClienteImplements()" + e);
        }
        
        try {
            servicoNomesRMI = LocateRegistry.getRegistry(2001);
            log.log(Level.INFO, "Registro 2001: {0}", servicoNomesRMI.toString());
            ServidorCoordenador servidor = (ServidorCoordenador)servicoNomesRMI.lookup("coordenador");
            log.log(Level.INFO, "lookup coordenador: {0}", servidor.toString());
            //servicoNomesRMI.rebind("cliente", cliente); 
            log.info("Conectou ao Coordenador");
            cliente.setServidorCoordenador(servidor);
            
            while (true) {
                System.out.println("1 - Consultar Passagens: ");
                System.out.println("2 - Comprar Passagens: ");
                System.out.println("3 - Consultar Hoteis: ");
                System.out.println("4 - Comprar Hoteis: ");
                System.out.println("5 - Comprar Pacote: ");                
                Scanner scanner = new Scanner(System.in);
                Integer op = scanner.nextInt();
                String input, resultado;
                int idPassagem, quantidadePessoas, idHotel;
                switch (op) {
                    case 1:
                        System.out.println("Consultando passagem...");
                        cliente.CosultarPassagens();
                        break;
                    case 2:
                        System.out.println("Comprando passagem...");
                        System.out.println("Digite o ID da passagem:");
                        input = scanner.next();
                        idPassagem = Integer.parseInt(input);
                        System.out.println("Digite a quantidade de poltronas: ");
                        input = scanner.next();
                        quantidadePessoas = Integer.parseInt(input);
                        resultado = cliente.ComprarPassagem(idPassagem, quantidadePessoas);
                        System.out.println("Resultado: " + resultado);
                        break;
                    case 3:
                        System.out.println("Consultando Hoteis...");
                        cliente.CosultarHoteis();
                        break;
                    case 4:
                        System.out.println("Comprando Hoteis...");
                        System.out.println("Digite o ID do Hotel:");
                        input = scanner.next();
                        idHotel = Integer.parseInt(input);
                        System.out.println("Digite a quantidade de pessoas: ");
                        input = scanner.next();
                        quantidadePessoas = Integer.parseInt(input);
                        resultado = cliente.ReservarHospedagem(idHotel, quantidadePessoas);
                        System.out.println("Resultado: " + resultado);
                        cliente.ReservarHospedagem(1, 2);
                        break;
                    case 5:
                        System.out.println("Comprando Pacote...");
                        System.out.println("Digite o ID da passagem:");
                        input = scanner.next();
                        idPassagem = Integer.parseInt(input);
                        System.out.println("Digite o ID do Hotel:");
                        input = scanner.next();
                        idHotel = Integer.parseInt(input);
                        System.out.println("Digite a quantidade de pessoas: ");
                        input = scanner.next();
                        quantidadePessoas = Integer.parseInt(input);
                        resultado = cliente.ComprarPacote(idPassagem, idHotel, quantidadePessoas);
                        System.out.println("Resultado: " + resultado);
                        break;
                    default:
                        System.out.println("Opção Inválida!");
                        break;
                }
            }

        }catch(RemoteException e){
            log.severe("Main cliente: " + e);
        }catch(NotBoundException e){
            log.severe("Main cliente: " + e);
        }
    }

}
