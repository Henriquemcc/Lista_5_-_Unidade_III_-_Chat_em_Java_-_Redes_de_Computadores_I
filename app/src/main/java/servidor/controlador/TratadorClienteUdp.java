package servidor.controlador;

import comum.controlador.TCP;
import comum.controlador.UDP;
import comum.modelo.Comunicacao;
import comum.modelo.Mensagem;
import comum.modelo.Requisicao;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

public class TratadorClienteUdp extends Thread {
    private DatagramSocket socket;
    private DatagramPacket pacoteRecebido;

    public TratadorClienteUdp(DatagramSocket socket, DatagramPacket pacoteRecebido)
    {
        this.socket = socket;
        this.pacoteRecebido = pacoteRecebido;
    }

    @Override
    public void run() {
        try {
            // Obtendo requisição
            UDP.UdpObjetoRecebido udpObjetoRecebido = UDP.receberObjeto(pacoteRecebido);
            Comunicacao requisicao = (Comunicacao) udpObjetoRecebido.object;
            System.out.println("requisicao = " + requisicao);

            // Tratando requisição
            if (requisicao != null) {

                // Enviando mensagens
                if (requisicao.requisicao == Requisicao.Enviar && requisicao.mensagens != null && !requisicao.mensagens.isEmpty()) {
                    for (Mensagem mensagem : requisicao.mensagens)
                        if (mensagem != null) Servidor.controladorMensagens.adicionarMensagem(mensagem);

                    // Respondendo ao cliente
                    Comunicacao resposta = Comunicacao.servidorRespondeSolicitacaoEnvioDeMensagemDoCliente();
                    UDP.enviarObjeto(resposta, socket,  udpObjetoRecebido.ipOrigem, udpObjetoRecebido.portaOrigem);
                    System.out.println("resposta = " + resposta);

                    // Recebendo mensagens
                } else if (requisicao.requisicao == Requisicao.Receber && requisicao.usuario != null && !requisicao.usuario.isEmpty()) {
                    List<Mensagem> mensagens = Servidor.controladorMensagens.retirarMensagens(requisicao.usuario);

                    // Respondendo ao cliente
                    Comunicacao resposta = Comunicacao.servidorRespondeSolicitacaoRecebimentoDeMensagensDoCliente(mensagens);
                    UDP.enviarObjeto(resposta, socket, udpObjetoRecebido.ipOrigem, udpObjetoRecebido.portaOrigem);
                    System.out.println("resposta = " + resposta);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
