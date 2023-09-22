package servidor.visao;

import comum.modelo.IntRange;
import comum.modelo.ProtocoloTransporte;
import comum.visao.*;
import comum.visao.menu.Botao;
import comum.visao.menu.Menu;
import comum.visao.menu.Opcao;
import comum.visao.menu.Utilitarios;
import servidor.controlador.Servidor;
import servidor.controlador.ThreadComunicacaoClientes;

import java.util.ArrayList;

/**
 * Menu do servidor.
 */
public class MenuServidor {
    /**
     * Obtém do usuário a porta do servidor.
     */
    public static void menuPortaServidor() {
        Utilitarios.imprimirCabecalho("Menu de alteração da porta do servidor");
        int novaPortaServidor = MyIO.readInt("Digite a porta do servidor: ", new IntRange(1, 65535));
        boolean confirmacao = Utilitarios.obterConfirmacao("Deseja alterar a porta do servidor para " + novaPortaServidor + "?");
        if (confirmacao)
            Servidor.portaServidor = novaPortaServidor;
    }

    /**
     * Obtém do usuário o protocolo da camada de transporte.
     */
    public static void menuProtocoloTransporte() {
        Utilitarios.imprimirCabecalho("Menu de alteração do protocolo da camada de transporte");
        String entrada = null;
        while (entrada == null || ((!entrada.toUpperCase().equals("TCP")) && (!entrada.toUpperCase().equals("UDP")))) {
            entrada = MyIO.readString("Digite o protocolo a ser utilizado na camada de transporte (TCP/UDP): ");
            if (entrada == null || ((!entrada.toUpperCase().equals("TCP")) && (!entrada.toUpperCase().equals("UDP")))) {
                System.out.println("O entrada deve ser TCP ou UDP.");
            }
        }

        ProtocoloTransporte protocolo;
        if (entrada.toUpperCase().equals("TCP"))
            protocolo = ProtocoloTransporte.TCP;
        else
            protocolo = ProtocoloTransporte.UDP;

        boolean confirmacao = Utilitarios.obterConfirmacao("Deseja alterar o protocolo de transporte para " + protocolo + "?");
        if (confirmacao)
            Servidor.protocoloTransporte = protocolo;
    }

    /**
     * Realiza a configuração inicial do servidor.
     */
    public static void configuracaoInicial() {
        while (Servidor.portaServidor == null)
            MenuServidor.menuPortaServidor();
        while (Servidor.protocoloTransporte == null)
            MenuServidor.menuProtocoloTransporte();
        Servidor.threadComunicacaoClientes = new ThreadComunicacaoClientes(Servidor.portaServidor, Servidor.protocoloTransporte);
    }

    /**
     * Mostra o menu principal do servidor.
     */
    public static void menuPrincipal() {
        ArrayList<Opcao> opcoes = new ArrayList<>();
        opcoes.add(new Botao("Alterar porta do servidor", () -> {
            menuPortaServidor();
            return null;
        }));
        opcoes.add(new Botao("Alterar protocolo da camada de transporte", () -> {
            menuProtocoloTransporte();
            return null;
        }));
        opcoes.add(new Botao("Aplicar alterações", () -> {
            if (Servidor.threadComunicacaoClientes != null) {
                Servidor.threadComunicacaoClientes.finalizar();
                Servidor.threadComunicacaoClientes = new ThreadComunicacaoClientes(Servidor.portaServidor, Servidor.protocoloTransporte);
                Servidor.threadComunicacaoClientes.start();
            }
            return null;
        }));
        Menu menuPrincipal = new Menu("Menu principal do servidor", opcoes);
    }
}
