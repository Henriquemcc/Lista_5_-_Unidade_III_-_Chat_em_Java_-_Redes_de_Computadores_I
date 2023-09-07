package cliente.visao;

import cliente.controlador.Cliente;
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

        Cliente.protocoloTransporte = protocolo;
    }

    private static void menuDestinatario() {
        Utilitarios.imprimirCabecalho("Menu de alteração do destinatário");
        String novoDestinatario = MyIO.readString("Digite o nome de usuário do destinatario: ");
        boolean confirmacao = Utilitarios.obterConfirmacao("Deseja alterar o destinatário para \"" + novoDestinatario + "\"?");
        if (confirmacao)
            Cliente.destinatario = novoDestinatario;
    }

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


    public static void menuNomeUsuario() {
        Utilitarios.imprimirCabecalho("Menu de alteração do nome de usuário");
        String novoNomeUsuario = MyIO.readString("Digite o seu nome de usuário: ");
        boolean confirmacao = Utilitarios.obterConfirmacao("Deseja alterar o seu nome de usuário para \"" + novoNomeUsuario + "\"?");
        if (confirmacao)
            Cliente.nomeUsuario = novoNomeUsuario;
    }

    private static void menuMostrarInformacoes() {
        Utilitarios.imprimirCabecalho("Menu de exibição das informações");
        System.out.println("Endereço do servidor: " + Cliente.enderecoServidor);
        System.out.println("Porta do servidor: " + Cliente.portaServidor);
        System.out.println("Protocolo de transporte: " + Cliente.protocoloTransporte);
        System.out.println("Nome de usuário: " + Cliente.nomeUsuario);
        System.out.println("Destinatário: " + Cliente.destinatario);
        System.out.println("Mensagens recebidas: " + Cliente.controladorMensagens.novasMensagens());
    }

    private static void menuLerMensagemRecebida() {
        Utilitarios.imprimirCabecalho("Menu de exibição de mensagem recebida");
        List<Mensagem> mensagensRecebidas = Cliente.controladorMensagens.retirarMensagens();
        System.out.println("Mensagens recebidas: " + mensagensRecebidas.size());
        for (int i = 0; i < mensagensRecebidas.size(); i++) {
            System.out.println("De: " + mensagensRecebidas.get(i).nomeUsuarioRemetente);
            System.out.println("Para: " + mensagensRecebidas.get(i).nomeUsuarioDestinatario);
            System.out.println("Data de envio: " + mensagensRecebidas.get(i).dataEnvio);
            System.out.println("comum.modelo.Mensagem: " + mensagensRecebidas.get(i).mensagem);
        }
    }

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
        Menu menuPrincipal = new Menu("Menu principal do cliente", opcoes);
    }
}
