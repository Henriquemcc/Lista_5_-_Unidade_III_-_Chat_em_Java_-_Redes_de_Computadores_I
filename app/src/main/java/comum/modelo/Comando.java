package comum.modelo;

public enum Comando {
    Enviar("Enviar mensagem"),
    Receber("Receber mensagem"),
    Terminar("Fechar conexao");

    private final String descricao;

    Comando(String descricao) {
        this.descricao = descricao;
    }
}
