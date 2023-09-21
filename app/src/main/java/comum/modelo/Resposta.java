package comum.modelo;

import java.io.Serializable;

/**
 * Enum das respostas que o servidor pode enviar ao cliente.
 */
public enum Resposta implements Serializable {

    Sucesso("Operação bem sucedida"),
    Erro("Operação mal sucedida");

    /**
     * Descrição da resposta.
     */
    private final String descricao;

    /**
     * Constrói uma nova instância da classe Resposta.
     * @param descricao Descrição da resposta.
     */
    Resposta(String descricao) {
        this.descricao = descricao;
    }
}
