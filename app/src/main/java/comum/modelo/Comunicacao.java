package comum.modelo;

import java.io.Serializable;
import java.util.List;

public class Comunicacao implements Serializable {
    public Requisicao requisicao;
    public Resposta resposta;
    public String usuario;
    public List<Mensagem> mensagens;

    public static Comunicacao cilenteSolicitaRecebimentoDeMensagens(String nomeUsuario) {
        Comunicacao comunicacao = new Comunicacao();
        comunicacao.requisicao = Requisicao.Receber;
        comunicacao.usuario = nomeUsuario;
        return comunicacao;
    }

    public static Comunicacao clienteEnviaMensagem(List<Mensagem> mensagens) {
        Comunicacao comunicacao = new Comunicacao();
        comunicacao.requisicao = Requisicao.Enviar;
        comunicacao.mensagens = mensagens;
        return comunicacao;
    }

    public static Comunicacao servidorRespondeSolicitacaoRecebimentoDeMensagensDoCliente(List<Mensagem> mensagens) {
        Comunicacao comunicacao = new Comunicacao();
        comunicacao.resposta = Resposta.Sucesso;
        comunicacao.mensagens = mensagens;
        return comunicacao;
    }

    public static Comunicacao servidorRespondeSolicitacaoEnvioDeMensagemDoCliente() {
        Comunicacao comunicacao = new Comunicacao();
        comunicacao.resposta = Resposta.Sucesso;
        return comunicacao;
    }

    public static Comunicacao erro() {
        Comunicacao comunicacao = new Comunicacao();
        comunicacao.resposta = Resposta.Erro;
        return comunicacao;
    }

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
