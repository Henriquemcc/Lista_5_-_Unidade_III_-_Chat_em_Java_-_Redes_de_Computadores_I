package comum.modelo;

public enum ProtocoloTransporte {
    TCP(6, "Transmission Control Protocol"),
    UDP(17, "User Datagram Protocol");

    private final int numeroProtocolo;
    private final String descricao;

    ProtocoloTransporte(int numeroProtocolo, String descricao) {
        this.numeroProtocolo = numeroProtocolo;
        this.descricao = descricao;
    }

    public int getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public String getDescricao() {
        return descricao;
    }
}
