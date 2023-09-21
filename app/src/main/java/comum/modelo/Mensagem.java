package comum.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Uma mensagem entre clientes.
 */
public class Mensagem implements Serializable {

    /**
     * Conteúdo da mensagem.
     */
    public final String mensagem;

    /**
     * Nome do usuário remetente.
     */
    public final String nomeUsuarioRemetente;

    /**
     * Data de envio.
     */
    public final LocalDateTime dataEnvio;

    /**
     * Nome do usuário destinatário.
     */
    public final String nomeUsuarioDestinatario;

    /**
     * Constrói uma nova instância da classe Mensagem.
     * @param mensagem Conteúdo da mensagem a ser enviada.
     * @param nomeUsuarioRemetente Nome do usuário remetente da mensagem.
     * @param dataEnvio Data de envido da mensagem.
     * @param nomeUsuarioDestinatario Nome do usuário destinatário da mensagem.
     */
    public Mensagem(String mensagem, String nomeUsuarioRemetente, LocalDateTime dataEnvio, String nomeUsuarioDestinatario) {
        this.mensagem = mensagem;
        this.nomeUsuarioRemetente = nomeUsuarioRemetente;
        this.dataEnvio = dataEnvio;
        this.nomeUsuarioDestinatario = nomeUsuarioDestinatario;
    }

    /**
     * Converte uma instância desta classe em uma string.
     * @return String da instância desta classe.
     */
    @Override
    public String toString() {
        return "Mensagem{" +
                "mensagem='" + mensagem + '\'' +
                ", nomeUsuarioRemetente='" + nomeUsuarioRemetente + '\'' +
                ", dataEnvio=" + dataEnvio +
                ", nomeUsuarioDestinatario='" + nomeUsuarioDestinatario + '\'' +
                '}';
    }
}
