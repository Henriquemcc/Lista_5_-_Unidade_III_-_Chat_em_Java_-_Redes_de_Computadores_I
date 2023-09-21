package servidor.controlador;

import comum.controlador.TCP;
import comum.modelo.Comunicacao;
import comum.modelo.Mensagem;
import comum.modelo.Requisicao;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Thread responsável por responder um cliente via TCP.
 */
public class TratadorClienteTcp extends Thread {

    /**
     * Socket TCP.
     */
    private final Socket conexaoCliente;

    /**
     * Constrói uma nova instância da classe TratadorClienteTcp.
     * @param conexaoCliente Socket TCP.
     */
    public TratadorClienteTcp(Socket conexaoCliente) {
        System.out.println("Conectado com: " + conexaoCliente.getInetAddress() + ":" + conexaoCliente.getPort());
        this.conexaoCliente = conexaoCliente;
    }

    /**
     * Executa a thread.
     */
    @Override
    public void run() {
        try {
            // Obtendo requisição
            Comunicacao requisicao = (Comunicacao) TCP.receberObjeto(conexaoCliente);
            System.out.println("requisicao = " + requisicao);

            // Tratando requisição
            if (requisicao != null) {

                // Enviando mensagens
                if (requisicao.requisicao == Requisicao.Enviar && requisicao.mensagens != null && !requisicao.mensagens.isEmpty()) {
                    for (Mensagem mensagem : requisicao.mensagens)
                        if (mensagem != null) Servidor.controladorMensagens.adicionarMensagem(mensagem);

                    // Respondendo ao cliente
                    Comunicacao resposta = Comunicacao.servidorRespondeSolicitacaoEnvioDeMensagemDoCliente();
                    TCP.enviarObjeto(conexaoCliente, resposta);
                    System.out.println("resposta = " + resposta);

                // Recebendo mensagens
                } else if (requisicao.requisicao == Requisicao.Receber && requisicao.usuario != null && !requisicao.usuario.isEmpty()) {
                    List<Mensagem> mensagens = Servidor.controladorMensagens.retirarMensagens(requisicao.usuario);

                    // Respondendo ao cliente
                    Comunicacao resposta = Comunicacao.servidorRespondeSolicitacaoRecebimentoDeMensagensDoCliente(mensagens);
                    TCP.enviarObjeto(conexaoCliente, resposta);
                    System.out.println("resposta = " + resposta);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
