package cliente.visao;

import cliente.controlador.Cliente;
import cliente.controlador.ControladorMensagens;
import comum.modelo.IntRange;
import comum.modelo.Mensagem;
import comum.modelo.ProtocoloTransporte;
import comum.visao.MyIO;
import comum.visao.menu.Botao;
import comum.visao.menu.Menu;
import comum.visao.menu.Opcao;
import comum.visao.menu.Utilitarios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Menus utilizados para a interação do usuário com o programa.
 */
public class MenuCliente {
    /**
     * Obtém do usuário o endereço do servidor.
     */
    public static void menuEnderecoServidor() {
        Utilitarios.imprimirCabecalho("Menu de alteração do endereço do servidor");
        String novoEnderecoServidor = MyIO.readString("Digite o endereço do servidor: ");
        boolean confirmacao = Utilitarios.obterConfirmacao("Deseja alterar o endereço do servidor para " + novoEnderecoServidor + "?");
        if (confirmacao)
            Cliente.enderecoServidor = novoEnderecoServidor;
    }

    /**
     * Obtém do usuário a porta do servidor.
     */
    public static void menuPortaServidor() {
        Utilitarios.imprimirCabecalho("Menu de alteração da porta do servidor");
        int novaPortaServidor = MyIO.readInt("Digite a porta do servidor: ", new IntRange(1, 65535));
        boolean confirmacao = Utilitarios.obterConfirmacao("Deseja alterar a porta do servidor para " + novaPortaServidor + "?");
        if (confirmacao)
            Cliente.portaServidor = novaPortaServidor;
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
            Cliente.protocoloTransporte = protocolo;
    }

    /**
     * Obtém do usuário o destinatário para o qual se deseja enviar uma mensagem.
     */
    private static void menuDestinatario() {
        Utilitarios.imprimirCabecalho("Menu de alteração do destinatário");
        String novoDestinatario = MyIO.readString("Digite o nome de usuário do destinatario: ");
        boolean confirmacao = Utilitarios.obterConfirmacao("Deseja alterar o destinatário para \"" + novoDestinatario + "\"?");
        if (confirmacao)
            Cliente.destinatario = novoDestinatario;
    }

    /**
     * Menu que realiza o envio de mensagens.
     */
    private static void menuEnviarMensagem() {
        Utilitarios.imprimirCabecalho("Menu de envio de mensagem");

        if (Cliente.destinatario == null) {
            menuDestinatario();
        }

        String mensagem = MyIO.readString("Digite a mensagem a ser enviada:\n");
        boolean confirmacao = Utilitarios.obterConfirmacao("Deseja enviar a mensagem: \"" + mensagem + "\" para \"" + Cliente.destinatario + "\"?");
        if (confirmacao)
            Cliente.controladorMensagens.enviarMensagem(new Mensagem(mensagem, Cliente.nomeUsuario, LocalDateTime.now(), Cliente.destinatario));
    }

    /**
     * Obtém do usuário o nome de usuário que ele deseja utilizar.
     */
    public static void menuNomeUsuario() {
        Utilitarios.imprimirCabecalho("Menu de alteração do nome de usuário");
        String novoNomeUsuario = MyIO.readString("Digite o seu nome de usuário: ");
        boolean confirmacao = Utilitarios.obterConfirmacao("Deseja alterar o seu nome de usuário para \"" + novoNomeUsuario + "\"?");
        if (confirmacao)
            Cliente.nomeUsuario = novoNomeUsuario;
    }

    /**
     * Mostra informações sobre a execução do programa.
     */
    private static void menuMostrarInformacoes() {
        Utilitarios.imprimirCabecalho("Menu de exibição das informações");
        System.out.println("Endereço do servidor: " + Cliente.enderecoServidor);
        System.out.println("Porta do servidor: " + Cliente.portaServidor);
        System.out.println("Protocolo de transporte: " + Cliente.protocoloTransporte);
        System.out.println("Nome de usuário: " + Cliente.nomeUsuario);
        System.out.println("Destinatário: " + Cliente.destinatario);
        System.out.println("Mensagens recebidas: " + Cliente.controladorMensagens.novasMensagens());
    }

    /**
     * Mostra as mensagens recebidas.
     */
    private static void menuLerMensagemRecebida() {
        Utilitarios.imprimirCabecalho("Menu de exibição de mensagem recebida");
        if (Cliente.controladorMensagens != null) {
            List<Mensagem> mensagensRecebidas = Cliente.controladorMensagens.retirarMensagens();
            System.out.println("Mensagens recebidas: " + mensagensRecebidas.size());
            for (int i = 0; i < mensagensRecebidas.size(); i++) {
                System.out.println("De: " + mensagensRecebidas.get(i).nomeUsuarioRemetente);
                System.out.println("Para: " + mensagensRecebidas.get(i).nomeUsuarioDestinatario);
                System.out.println("Data de envio: " + mensagensRecebidas.get(i).dataEnvio);
                System.out.println("Mensagem: " + mensagensRecebidas.get(i).mensagem);
            }
        }
    }

    /**
     * Realiza a configuração inicial do cliente.
     */
    public static void configuracaoInicial() {
        Utilitarios.imprimirCabecalho("Menu de configuração inicial");
        while (Cliente.enderecoServidor == null) MenuCliente.menuEnderecoServidor();
        while (Cliente.portaServidor == null) MenuCliente.menuPortaServidor();
        while (Cliente.protocoloTransporte == null) MenuCliente.menuProtocoloTransporte();
        while (Cliente.nomeUsuario == null) MenuCliente.menuNomeUsuario();
    }

    /**
     * Mostra o menu principal.
     */
    public static void menuPrincipal() {

        ArrayList<Opcao> opcoes = new ArrayList<>();
        opcoes.add(new Botao("Ler mensagem recebida", () -> {
            menuLerMensagemRecebida();
            return null;
        }));
        opcoes.add(new Botao("Enviar mensagem", () -> {
            menuEnviarMensagem();
            return null;
        }));
        opcoes.add(new Botao("Mostrar informações", () -> {
            menuMostrarInformacoes();
            return null;
        }));
        opcoes.add(new Botao("Alterar destinatário", () -> {
            menuDestinatario();
            return null;
        }));
        opcoes.add(new Botao("Alterar nome de usuário", () -> {
            menuNomeUsuario();
            return null;
        }));
        opcoes.add(new Botao("Alterar endereço do servidor", () -> {
            menuEnderecoServidor();
            return null;
        }));
        opcoes.add(new Botao("Alterar porta do servidor", () -> {
            menuPortaServidor();
            return null;
        }));
        opcoes.add(new Botao("Alterar protocolo da camada de transporte", () -> {
            menuProtocoloTransporte();
            return null;
        }));
        opcoes.add(new Botao("Aplicar alterações", () -> {
            if (Cliente.controladorMensagens != null) {
                Cliente.controladorMensagens.finalizar();
                Cliente.controladorMensagens = new ControladorMensagens(Cliente.enderecoServidor, Cliente.portaServidor, Cliente.protocoloTransporte, Cliente.nomeUsuario);
            }
            return null;
        }));
        Menu menuPrincipal = new Menu("Menu principal do cliente", opcoes);
    }
}
