package comum.modelo;

import java.io.Serializable;

public enum Requisicao implements Serializable {
    Enviar("Enviar mensagem"),
    Receber("Receber mensagem"),
    Terminar("Fechar conexao"),
    Resposta("Respondendo a requisição");

    private final String descricao;

    Requisicao(String descricao) {
        this.descricao = descricao;
    }
}
