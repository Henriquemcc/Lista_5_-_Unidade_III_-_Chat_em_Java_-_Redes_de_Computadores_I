package comum.modelo;

import java.io.Serializable;

public enum Resposta implements Serializable {

    Sucesso("Operação bem sucedida"),
    Erro("Operação mal sucedida");


    private final String descricao;
    Resposta(String descricao) {
        this.descricao = descricao;
    }
}
