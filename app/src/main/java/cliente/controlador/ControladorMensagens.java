package cliente.controlador;


import comum.controlador.TCP;
import comum.controlador.UDP;
import comum.modelo.Comunicacao;
import comum.modelo.Mensagem;
import comum.modelo.ProtocoloTransporte;
import comum.modelo.Resposta;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controlador responsável pelo envio e recepção de mensagens.
 */
public class ControladorMensagens {

    /**
     * Endereço do servidor.
     */
    private final String enderecoServidor;

    /**
     * Porta do servidor.
     */
    private final int portaServidor;

    /**
     * Protocolo de transporte utilizado para comunicar com o servidor.
     */
    private final ProtocoloTransporte protocoloTransporte;

    /**
     * Lista de mensagens recebidas do servidor.
     */
    private final List<Mensagem> listaRecebimento = Collections.synchronizedList(new ArrayList<>());

    /**
     * Nome de usuário do cliente.
     */
    private final String nomeUsuario;

    /**
     * Socket TCP utilizado para comunicar com o servidor.
     */
    private Socket socketTcp = null;

    /**
     * Socket UDP utilizado para comunicar com o servidor.
     */
    private DatagramSocket socketUdp = null;

    /**
     * Thread responsável por receber mensagens do servidor.
     */
    private final Thread threadRecebimento = new Thread() {

        /**
         * Recebe mensagens do servidor, utilizando o protocolo TCP.
         */
        private void receberTcp() {
            while (Cliente.programaEmExecucao) {
                try {
                    socketTcp = new Socket(InetAddress.getByName(enderecoServidor), portaServidor);

                    // Enviando pedido para receber mensagens
                    Comunicacao requisicao = Comunicacao.cilenteSolicitaRecebimentoDeMensagens(nomeUsuario);
                    TCP.enviarObjeto(socketTcp, requisicao);

                    // Obtendo resposta
                    Comunicacao resposta = (Comunicacao) TCP.receberObjeto(socketTcp);
                    if (resposta != null && resposta.mensagens != null && !resposta.mensagens.isEmpty()) {
                        System.out.println("\nNovas mensagens recebidas!");
                        for (Mensagem mensagem : resposta.mensagens)
                            if (mensagem != null) listaRecebimento.add(mensagem);
                    }

                    Thread.sleep(Duration.ofMinutes(1).toMillis());

                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (socketTcp != null)
                        try {
                            socketTcp.close();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                }
            }
        }

        /**
         * Recebe mensagens do servidor, utilizando o protocolo UDP.
         */
        private void receberUdp() {
            while (Cliente.programaEmExecucao) {
                try {
                    InetAddress ipServidor = InetAddress.getByName(enderecoServidor);
                    socketUdp = new DatagramSocket();

                    // Enviando pedido para receber mensagens
                    Comunicacao requisicao = Comunicacao.cilenteSolicitaRecebimentoDeMensagens(nomeUsuario);
                    UDP.enviarObjeto(requisicao, socketUdp, ipServidor, portaServidor);

                    // Obtendo resposta
                    UDP.UdpObjetoRecebido udpObjetoRecebido = UDP.receberObjeto(socketUdp);
                    Comunicacao resposta = (Comunicacao) udpObjetoRecebido.object;
                    if (resposta != null && resposta.mensagens != null && !resposta.mensagens.isEmpty()) {
                        System.out.println("\nNovas mensagens recebidas!");
                        for (Mensagem mensagem : resposta.mensagens)
                            if (mensagem != null) listaRecebimento.add(mensagem);
                    }

                    Thread.sleep(Duration.ofMinutes(1).toMillis());

                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (socketUdp != null)
                        socketUdp.close();
                }
            }
        }

        /**
         * Inicia a execução da thread para recebimento de mensagens do servidor.
         */
        @Override
        public void run() {
            if (protocoloTransporte == ProtocoloTransporte.TCP) receberTcp();
            else if (protocoloTransporte == ProtocoloTransporte.UDP) receberUdp();
        }
    };

    /**
     * Constrói uma instância da classe ControladorMensagens.
     * @param enderecoServidor Endereço do servidor.
     * @param portaServidor Porta do servidor.
     * @param protocoloTransporte Protocolo de transporte utilizado para comunicar com o servidor.
     * @param nomeUsuario Nome de usuário do cliente.
     */
    public ControladorMensagens(String enderecoServidor, int portaServidor, ProtocoloTransporte protocoloTransporte, String nomeUsuario) {
        this.enderecoServidor = enderecoServidor;
        this.portaServidor = portaServidor;
        this.protocoloTransporte = protocoloTransporte;
        this.nomeUsuario = nomeUsuario;

        threadRecebimento.start();
    }

    /**
     * Verifica se existem novas mensagens recebidas.
     * @return Valor booleano indicando se há novas mensagens recebidas.
     */
    public boolean novasMensagens() {
        return !listaRecebimento.isEmpty();
    }

    /**
     * Retira mensagens recebidas da lista de recebimento.
     * @return Lista de mensagens recebidas.
     */
    public synchronized List<Mensagem> retirarMensagens() {
        List<Mensagem> mensagensRetiradas = new ArrayList<>();
        while (!listaRecebimento.isEmpty()) {
            mensagensRetiradas.add(listaRecebimento.remove(listaRecebimento.size() - 1));
        }

        return mensagensRetiradas;
    }

    /**
     * Envia uma mensagem para o servidor, utilizando o TCP.
     * @param mensagem Mensagem a ser enviada para o servidor.
     */
    private void enviarMensagemTcp(Mensagem mensagem) {
        System.out.println("Enviando mensagem: " + mensagem);
        List<Mensagem> mensagens = new ArrayList<>();
        mensagens.add(mensagem);

        try (Socket socket = new Socket(InetAddress.getByName(enderecoServidor), portaServidor)) {
            boolean erro = false;
            do {
                // Enviando comunicação com a mensagem a ser enviada
                Comunicacao requisicao = Comunicacao.clienteEnviaMensagem(mensagens);
                TCP.enviarObjeto(socket, requisicao);
                System.out.println("requisicao = " + requisicao);

                // Recebendo resposta
                Comunicacao resposta = (Comunicacao) TCP.receberObjeto(socket);
                System.out.println("resposta = " + resposta);
                erro = resposta == null || resposta.resposta != Resposta.Sucesso;
            } while (erro);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Envia uma mensagem para o servidor.
     * @param mensagem Mensagem a ser enviada para o servidor.
     */
    public void enviarMensagem(Mensagem mensagem) {
        if (Cliente.protocoloTransporte == ProtocoloTransporte.TCP) enviarMensagemTcp(mensagem);
        else if (Cliente.protocoloTransporte == ProtocoloTransporte.UDP) enviarMensagemUdp(mensagem);

    }

    /**
     * Envia uma mensagem para o servidor, utilizando o UDP.
     * @param mensagem Mensagem a ser enviada para o servidor.
     */
    private void enviarMensagemUdp(Mensagem mensagem) {
        System.out.println("Enviando mensagem: " + mensagem);
        List<Mensagem> mensagens = new ArrayList<>();
        mensagens.add(mensagem);

        DatagramSocket datagramSocket = null;
        try {
            InetAddress ipServidor = InetAddress.getByName(enderecoServidor);
            datagramSocket = new DatagramSocket();
            boolean erro = false;
            do {
                // Enviando comunicação com a mensagem a ser enviada
                Comunicacao requisicao = Comunicacao.clienteEnviaMensagem(mensagens);
                UDP.enviarObjeto(requisicao, datagramSocket, ipServidor, portaServidor);
                System.out.println("requisicao = " + requisicao);

                // Recebendo resposta
                UDP.UdpObjetoRecebido udpObjetoRecebido = UDP.receberObjeto(datagramSocket);
                Comunicacao resposta = (Comunicacao) udpObjetoRecebido.object;
                System.out.println("resposta = " + resposta);
                erro = resposta == null || resposta.resposta != Resposta.Sucesso;
            } while (erro);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (datagramSocket != null)
                datagramSocket.close();
        }
    }

    /**
     * Finaliza a execução do controlador de mensagens.
     */
    public void finalizar() {
        if (socketTcp != null)
            try {
                socketTcp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (socketUdp != null)
            socketUdp.close();
    }
}
