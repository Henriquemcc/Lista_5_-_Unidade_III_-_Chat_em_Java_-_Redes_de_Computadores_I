package cliente.visao;

import cliente.controlador.Cliente;
import comum.modelo.IntRange;
import comum.modelo.ProtocoloTransporte;
import comum.visao.*;
import comum.visao.menu.Botao;
import comum.visao.menu.Menu;
import comum.visao.menu.Opcao;
import comum.visao.menu.Utilitarios;

import java.util.ArrayList;

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
            System.out.println("Ainda não foi implementado");
        //TODO()
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
        System.out.println("comum.modelo.Mensagem recebida: " + !Cliente.mensagens.isEmpty());
    }

    private static void menuLerMensagemRecebida() {
        Utilitarios.imprimirCabecalho("Menu de exibição de mensagem recebida");
        System.out.println("Mensagens recebidas: " + Cliente.mensagens.size());
        for (int i = 0; i < Cliente.mensagens.size(); i++) {
            System.out.println("De: " + Cliente.mensagens.get(i).nomeUsuarioRemetente);
            System.out.println("Para: " + Cliente.mensagens.get(i).nomeUsuarioDestinatario);
            System.out.println("Data de envio: " + Cliente.mensagens.get(i).dataEnvio);
            System.out.println("comum.modelo.Mensagem: " + Cliente.mensagens.get(i).mensagem);
        }
        boolean apagarMensagens = Utilitarios.obterConfirmacao("Deseja apagar todas as mensagens recebidas?");
        if (apagarMensagens)
            Cliente.mensagens.clear();
    }

    public static void menuPrincipal() {

        ArrayList<Opcao> opcoes = new ArrayList<>();
        if (!Cliente.mensagens.isEmpty())
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