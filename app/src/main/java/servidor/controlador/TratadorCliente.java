package servidor.controlador;

import comum.modelo.Comunicacao;
import comum.modelo.Mensagem;
import comum.modelo.Requisicao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TratadorCliente extends Thread {

    private final Socket conexaoCliente;

    public TratadorCliente(Socket conexaoCliente) {
        System.out.println("Conectado com: " + conexaoCliente.getInetAddress() + ":" + conexaoCliente.getPort());
        this.conexaoCliente = conexaoCliente;
    }


    @Override
    public void run() {
        Comunicacao requisicao = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(conexaoCliente.getInputStream());
            requisicao = (Comunicacao) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("requisicao = " + requisicao);

        if (requisicao != null)
        {
            if (requisicao.requisicao == Requisicao.Enviar && requisicao.mensagens != null && !requisicao.mensagens.isEmpty()) {
                for (Mensagem mensagem: requisicao.mensagens)
                    if (mensagem != null)
                        Servidor.controladorMensagens.adicionarMensagem(mensagem);

                // Respondendo ao cliente
                Comunicacao resposta = Comunicacao.servidorRespondeSolicitacaoEnvioDeMensagemDoCliente();
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(conexaoCliente.getOutputStream());
                    objectOutputStream.writeObject(resposta);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("resposta = " + resposta);
            } else if (requisicao.requisicao == Requisicao.Receber && requisicao.usuario != null && !requisicao.usuario.isEmpty()){
                List<Mensagem> mensagens = Servidor.controladorMensagens.retirarMensagens(requisicao.usuario);

                // Respondendo ao cliente
                Comunicacao resposta = Comunicacao.servidorRespondeSolicitacaoRecebimentoDeMensagensDoCliente(mensagens);
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(conexaoCliente.getOutputStream());
                    objectOutputStream.writeObject(resposta);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("resposta = " + resposta);
            }
        }
    }

}
