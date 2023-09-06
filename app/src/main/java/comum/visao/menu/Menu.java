package comum.visao.menu;

import comum.modelo.IntRange;
import comum.visao.MyIO;

import java.util.ArrayList;
import java.util.List;

/**
 * Um menu de console cuja mensagem não muda durante a execução.
 * Copiado e adaptado de outro projeto de autoria própria https://github.com/Henriquemcc/Dynamic_DNS_Update_Client/blob/12e62a8f86179d441c22baab107bb20d27bef8c9/app/src/main/kotlin/dynamic/dns/update/client/console/common/menu/StaticConsoleMenu.kt
 */
public class Menu {
    private final String titulo;
    private final List<Opcao> opcoes;
    private final Texto textoOpcoes;
    private final Texto textoSaida;

    private List<Botao> obterBotoes() {
        final List<Botao> botoesConsole = new ArrayList<>();
        for (Opcao opcao: opcoes) {
            if (opcao instanceof Botao)
                botoesConsole.add((Botao) opcao);

        }
        return botoesConsole;
    }

    private int obterNumeroBotoes() {
        return obterBotoes().size();
    }

    private String obterMensagem() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(textoOpcoes.texto).append("\n");
        stringBuilder.append("0 - ").append(textoSaida.texto).append("\n");

        int indiceBotao = 1;
        for (Opcao opcao: opcoes) {
            if (opcao instanceof Texto)
                stringBuilder.append(((Texto) opcao).texto).append("\n");
            else if(opcao instanceof Botao)
                stringBuilder.append(indiceBotao++).append(" - ").append(((Botao) opcao).texto).append("\n");
        }

        stringBuilder.append("> ");

        return stringBuilder.toString();
    }

    private int obterEntrada() {
        return MyIO.readInt(obterMensagem(), new IntRange(0, obterNumeroBotoes()));
    }

    public Menu(String titulo, List<Opcao> opcoes, Texto textoOpcoes, Texto textoSaida) {
        this.titulo = titulo;
        this.opcoes = opcoes;
        this.textoOpcoes = textoOpcoes;
        this.textoSaida = textoSaida;

        int botaoSelecionado = -1;
        while (botaoSelecionado != 0) {
            if (titulo != null) {
                Utilitarios.imprimirCabecalho(titulo);
            }

            botaoSelecionado = obterEntrada();

            if (botaoSelecionado != 0) {
                obterBotoes().get(botaoSelecionado - 1).quandoPressionado.get();
            }
        }
    }

    public Menu(String titulo, List<Opcao> opcoes) {
        this(titulo, opcoes, new Texto("Opções: "), new Texto("Sair"));
    }

    public Menu(List<Opcao> opcoes) {
        this(null, opcoes, new Texto("Opções: "), new Texto("Sair"));
    }




}
