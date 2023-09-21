package comum.modelo;

import java.io.Serializable;

/**
 * Enum das requisições que o cliente pode fazer ao servidor.
 */
public enum Requisicao implements Serializable {
    Enviar("Enviar mensagem"),
    Receber("Receber mensagem"),
    Terminar("Fechar conexao"),
    Resposta("Respondendo a requisição");

    /**
     * Descrição da requisição.
     */
    private final String descricao;

    /**
     * Constrói uma nova instância da classe Requisição.
     * @param descricao Descrição da requisição.
     */
    Requisicao(String descricao) {
        this.descricao = descricao;
    }
}
