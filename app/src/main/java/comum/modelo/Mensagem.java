package comum.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Mensagem implements Serializable {
    public String mensagem;
    public String nomeUsuarioRemetente;
    public LocalDateTime dataEnvio;
    public String nomeUsuarioDestinatario;

    public Mensagem(String mensagem, String nomeUsuarioRemetente, LocalDateTime dataEnvio, String nomeUsuarioDestinatario) {
        this.mensagem = mensagem;
        this.nomeUsuarioRemetente = nomeUsuarioRemetente;
        this.dataEnvio = dataEnvio;
        this.nomeUsuarioDestinatario = nomeUsuarioDestinatario;
    }

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
