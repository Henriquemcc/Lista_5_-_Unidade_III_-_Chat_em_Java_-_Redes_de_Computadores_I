package cliente.controlador;


import comum.controlador.TCP;
import comum.modelo.Comunicacao;
import comum.modelo.Mensagem;
import comum.modelo.ProtocoloTransporte;
import comum.modelo.Resposta;

import java.io.IOException;
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
    private final String enderecoServidor;
    private final int portaServidor;
    private final ProtocoloTransporte protocoloTransporte;
    private final List<Mensagem> listaRecebimento = Collections.synchronizedList(new ArrayList<>());
    private final String nomeUsuario;

    private final Thread threadRecebimento = new Thread() {

        private void receberTcp() {
            while (true) {
                try (Socket socket = new Socket(InetAddress.getByName(enderecoServidor), portaServidor)) {
                    // Enviando pedido para receber mensagens
                    Comunicacao requisicao = Comunicacao.cilenteSolicitaRecebimentoDeMensagens(nomeUsuario);
                    TCP.enviarObjeto(socket, requisicao);

                    // Obtendo resposta
                    Comunicacao resposta = (Comunicacao) TCP.receberObjeto(socket);
                    if (resposta != null && resposta.mensagens != null && !resposta.mensagens.isEmpty()) {
                        System.out.println("\nNovas mensagens recebidas!");
                        for (Mensagem mensagem : resposta.mensagens)
                            if (mensagem != null) listaRecebimento.add(mensagem);
                    }

                    Thread.sleep(Duration.ofMinutes(1).toMillis());

                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void receberUdp() {

        }

        @Override
        public void run() {
            if (Cliente.protocoloTransporte == ProtocoloTransporte.TCP) receberTcp();
            else if (Cliente.protocoloTransporte == ProtocoloTransporte.UDP) receberUdp();

        }
    };

    public ControladorMensagens(String enderecoServidor, int portaServidor, ProtocoloTransporte protocoloTransporte, String nomeUsuario) {
        this.enderecoServidor = enderecoServidor;
        this.portaServidor = portaServidor;
        this.protocoloTransporte = protocoloTransporte;
        this.nomeUsuario = nomeUsuario;

        threadRecebimento.start();
    }

    public boolean novasMensagens() {
        return !listaRecebimento.isEmpty();
    }

    public synchronized List<Mensagem> retirarMensagens() {
        List<Mensagem> mensagensRetiradas = new ArrayList<>();
        while (!listaRecebimento.isEmpty()) {
            mensagensRetiradas.add(listaRecebimento.remove(listaRecebimento.size() - 1));
        }

        return mensagensRetiradas;
    }

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

    public void enviarMensagem(Mensagem mensagem) {
        if (Cliente.protocoloTransporte == ProtocoloTransporte.TCP) enviarMensagemTcp(mensagem);
        else if (Cliente.protocoloTransporte == ProtocoloTransporte.UDP) enviarMensagemUdp(mensagem);

    }

    private void enviarMensagemUdp(Mensagem mensagem) {

    }
}
