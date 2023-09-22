package servidor.controlador;

import comum.modelo.ProtocoloTransporte;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Thread responsável por comunicar com os clientes.
 */
public class ThreadComunicacaoClientes extends Thread{

    /**
     * Porta a qual será utilizada para se comunicar com os clientes
     */
    private final int portaServidor;

    /**
     * Protocolo de transporte utilizado para se comunicar com os clientes.
     */
    private final ProtocoloTransporte protocoloTransporte;

    /**
     * Indica se o programa está em execução.
     */
    private boolean programaEmExecucao = true;

    /**
     * Socket TCP responsável por comunicar com os clientes.
     */
    private ServerSocket serverSocket = null;

    /**
     * Socket UDP responsável por comunicar com os clientes.
     */
    private DatagramSocket datagramSocket = null;

    /**
     * Lista que armazenará os tratadores de cliente TCP.
     */
    private final List<TratadorClienteTcp> tratadoresClienteTcp = Collections.synchronizedList(new ArrayList<>());

    /**
     * Lista que armazenará os tratadores de cliente UDP.
     */
    private final List<TratadorClienteUdp> tratadoresClienteUdp = Collections.synchronizedList(new ArrayList<>());

    /**
     * Constrói uma instância da classe ThreadComunicacaoClientes.
     * @param portaServidor Porta utilizada para se comunicar com os clientes.
     * @param protocoloTransporte Protocolo de transporte utilizado para se comunicar com os clientes.
     */
    public ThreadComunicacaoClientes(int portaServidor, ProtocoloTransporte protocoloTransporte) {
        this.portaServidor = portaServidor;
        this.protocoloTransporte = protocoloTransporte;
    }

    /**
     * Comunica com os clientes usando TCP.
     */
    private void comunicacaoClienteTcp() {
        try{
            serverSocket = new ServerSocket(portaServidor);
            while (programaEmExecucao) {
                TratadorClienteTcp tratadorClienteTcp = new TratadorClienteTcp(serverSocket.accept());
                tratadoresClienteTcp.add(tratadorClienteTcp);
                tratadorClienteTcp.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Comunica com os clientes usando UDP.
     */
    private void comunicacaoClienteUdp() {
        try{
            datagramSocket = new DatagramSocket(portaServidor);
            while (programaEmExecucao) {
                byte[] bufferRecebimento = new byte[1024];
                DatagramPacket pacoteRecebido = new DatagramPacket(bufferRecebimento, bufferRecebimento.length);
                datagramSocket.receive(pacoteRecebido);
                TratadorClienteUdp tratadorClienteUdp = new TratadorClienteUdp(datagramSocket, pacoteRecebido);
                tratadoresClienteUdp.add(tratadorClienteUdp);
                tratadorClienteUdp.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (datagramSocket != null)
                datagramSocket.close();
        }
    }

    /**
     * Executa a thread.
     */
    @Override
    public void run() {
        if (protocoloTransporte == ProtocoloTransporte.TCP)
            comunicacaoClienteTcp();
        else if (protocoloTransporte == ProtocoloTransporte.UDP)
            comunicacaoClienteUdp();
    }

    /**
     * Finaliza a execução da thread.
     */
    public void finalizar() {
        programaEmExecucao = false;
        for (TratadorClienteTcp tratadorClienteTcp: tratadoresClienteTcp) {
            tratadorClienteTcp.finalizar();
        }
        for (TratadorClienteUdp tratadorClienteUdp: tratadoresClienteUdp) {
            tratadorClienteUdp.finalizar();
        }
        if (serverSocket != null) try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (datagramSocket != null)
            datagramSocket.close();
    }
}
