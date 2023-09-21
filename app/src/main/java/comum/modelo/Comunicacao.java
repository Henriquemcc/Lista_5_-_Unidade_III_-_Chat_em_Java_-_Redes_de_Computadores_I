package comum.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Classe do objeto que será recebido e enviado entre o cliente e o servidor.
 */
public class Comunicacao implements Serializable {

    /**
     * Requisição do cliente para o servidor.
     */
    public Requisicao requisicao;

    /**
     * Resposta do servidor para o cliente.
     */
    public Resposta resposta;

    /**
     * Nome de usuário do cliente.
     */
    public String usuario;

    /**
     * Mensagem a ser trocada entre o cliente e o servidor, e o servidor e o cliente.
     */
    public List<Mensagem> mensagens;

    /**
     * Constrói uma instância da classe Comunicação específica para o cliente solicitar o recebimento de mensagens.
     * @param nomeUsuario Nome de usuário do cliente.
     * @return Instância da classe Comunicação construída.
     */
    public static Comunicacao cilenteSolicitaRecebimentoDeMensagens(String nomeUsuario) {
        Comunicacao comunicacao = new Comunicacao();
        comunicacao.requisicao = Requisicao.Receber;
        comunicacao.usuario = nomeUsuario;
        return comunicacao;
    }

    /**
     * Constrói uma instância da classe Comunicação específica para o cliente enviar mensagens para o servidor.
     * @param mensagens Lista de mensagens a serem enviadas para o servidor.
     * @return Instância da classe Comunicação construída.
     */
    public static Comunicacao clienteEnviaMensagem(List<Mensagem> mensagens) {
        Comunicacao comunicacao = new Comunicacao();
        comunicacao.requisicao = Requisicao.Enviar;
        comunicacao.mensagens = mensagens;
        return comunicacao;
    }

    /**
     * Constrói uma instância da classe Comunicação específica para o servidor responder à solicitação do cliente para receber mensagens.
     * @param mensagens Lista de mensagens para o cliente.
     * @return Instância da classe Comunicação construída.
     */
    public static Comunicacao servidorRespondeSolicitacaoRecebimentoDeMensagensDoCliente(List<Mensagem> mensagens) {
        Comunicacao comunicacao = new Comunicacao();
        comunicacao.resposta = Resposta.Sucesso;
        comunicacao.mensagens = mensagens;
        return comunicacao;
    }

    /**
     * Constrói uma instância da classe Comunicação específica para o servidor responder à solicitação do cliente de envio de mensagem.
     * @return Instância da classe Comunicação construída.
     */
    public static Comunicacao servidorRespondeSolicitacaoEnvioDeMensagemDoCliente() {
        Comunicacao comunicacao = new Comunicacao();
        comunicacao.resposta = Resposta.Sucesso;
        return comunicacao;
    }

    /**
     * Constrói uma instância da classe Comunicação específica para o servidor ou o cliente enviarem uma mensagem de erro entre eles.
     * @return Instância da classe Comunicação construída.
     */
    public static Comunicacao erro() {
        Comunicacao comunicacao = new Comunicacao();
        comunicacao.resposta = Resposta.Erro;
        return comunicacao;
    }

    /**
     * Converte uma instância dessa classe em uma string.
     * @return String criada a partir de uma instância desta classe.
     */
    @Override
    public String toString() {
        return "Comunicacao{" +
                "requisicao=" + requisicao +
                ", resposta=" + resposta +
                ", usuario='" + usuario + '\'' +
                ", mensagens=" + mensagens +
                '}';
    }
}
