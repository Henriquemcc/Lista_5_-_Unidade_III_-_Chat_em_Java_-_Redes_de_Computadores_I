package servidor.controlador;/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import comum.modelo.ProtocoloTransporte;
import servidor.visao.MenuConsoleServidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;

public class Servidor {

    /**
     * Porta do servidor.
     */
    public static Integer portaServidor = null;

    /**
     * Protocolo da camada de transporte utilizado na comunicação com os clientes.
     */
    public static ProtocoloTransporte protocoloTransporte = null;

    public static boolean programaEmExecucao = true;

    public static final ControladorMensagens controladorMensagens =  new ControladorMensagens();

    public static final Thread threadComunicacaoClientes = new Thread() {

        private void comunicacaoClienteTcp() {
            try{
                ServerSocket serverSocket = new ServerSocket(portaServidor);
                while (programaEmExecucao) {
                    TratadorClienteTcp tratadorClienteTcp = new TratadorClienteTcp(serverSocket.accept());
                    tratadorClienteTcp.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void comunicacaoClienteUdp() {
            try(DatagramSocket socket = new DatagramSocket(portaServidor)) {
                while (programaEmExecucao) {
                    byte[] bufferRecebimento = new byte[1024];
                    DatagramPacket pacoteRecebido = new DatagramPacket(bufferRecebimento, bufferRecebimento.length);
                    socket.receive(pacoteRecebido);
                    TratadorClienteUdp tratadorClienteUdp = new TratadorClienteUdp(socket, pacoteRecebido);
                    tratadorClienteUdp.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            if (protocoloTransporte == ProtocoloTransporte.TCP)
                comunicacaoClienteTcp();
            else if (protocoloTransporte == ProtocoloTransporte.UDP)
                comunicacaoClienteUdp();
        }
    };

    public static void configuracaoInicial() {
        while (portaServidor == null)
            MenuConsoleServidor.menuPortaServidor();
        while (protocoloTransporte == null)
            MenuConsoleServidor.menuProtocoloTransporte();
    }

    public static void main(String[] args) {
        configuracaoInicial();
        System.out.println("Executando o servidor");
        threadComunicacaoClientes.start();
    }
}
