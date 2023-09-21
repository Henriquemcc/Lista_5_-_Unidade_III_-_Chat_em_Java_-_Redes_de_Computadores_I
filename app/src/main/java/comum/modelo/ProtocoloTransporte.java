package comum.modelo;

/**
 * Enum dos protocolos de transporte.
 */
public enum ProtocoloTransporte {
    TCP(6, "Transmission Control Protocol"),
    UDP(17, "User Datagram Protocol");

    /**
     * Número do protocolo.
     */
    private final int numeroProtocolo;

    /**
     * Descrição do protocolo.
     */
    private final String descricao;

    /**
     * Constrói uma nova instância da classe ProtocoloTransporte.
     * @param numeroProtocolo Número do protocolo de transporte.
     * @param descricao Descrição do protocolo de transporte.
     */
    ProtocoloTransporte(int numeroProtocolo, String descricao) {
        this.numeroProtocolo = numeroProtocolo;
        this.descricao = descricao;
    }

    /**
     * Obtém o número do protocolo de transporte.
     * @return Número do protocolo de transporte.
     */
    public int getNumeroProtocolo() {
        return numeroProtocolo;
    }

    /**
     * Obtém a descrição do protocolo de transporte.
     * @return Descrição do protocolo de transporte.
     */
    public String getDescricao() {
        return descricao;
    }
}
