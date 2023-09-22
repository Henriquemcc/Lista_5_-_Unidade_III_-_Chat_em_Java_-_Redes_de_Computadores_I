package servidor.controlador;/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import comum.modelo.ProtocoloTransporte;
import servidor.visao.MenuServidor;

/**
 * Classe principal do servidor.
 */
public class Servidor {

    /**
     * Porta do servidor.
     */
    public static Integer portaServidor = null;

    /**
     * Protocolo da camada de transporte utilizado na comunicação com os clientes.
     */
    public static ProtocoloTransporte protocoloTransporte = null;

    /**
     * Indica se o programa está em execução.
     */
    public static boolean programaEmExecucao = true;

    /**
     * Controlador de mensagens.
     */
    public static final ControladorMensagens controladorMensagens =  new ControladorMensagens();

    /**
     * Thread responsável por se comunicar com os clientes.
     */
    public static ThreadComunicacaoClientes threadComunicacaoClientes = null;

    /**
     * Realiza o processo de finalização do cliente.
     */
    private static void finalizar() {
        programaEmExecucao = false;
        threadComunicacaoClientes.finalizar();
    }

    private static void configurarInterrupcao() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            finalizar();
        }));
    }

    /**
     * Método principal do servidor.
     */
    public static void main(String[] args) {
        configurarInterrupcao();
        MenuServidor.configuracaoInicial();
        System.out.println("Executando o servidor");
        threadComunicacaoClientes.start();
        MenuServidor.menuPrincipal();
        System.out.println("Finalizando o servidor");
        finalizar();
    }
}
