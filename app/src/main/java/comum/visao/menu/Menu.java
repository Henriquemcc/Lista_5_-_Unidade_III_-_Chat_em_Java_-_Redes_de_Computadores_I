package comum.visao.menu;

import comum.modelo.IntRange;
import comum.visao.MyIO;

import java.util.ArrayList;
import java.util.List;

/**
 * Um menu de console cuja mensagem não muda durante a execução.
 */
public class Menu {

    /**
     * Título do menu.
     */
    private final String titulo;

    /**
     * Opções do menu.
     */
    private final List<Opcao> opcoes;

    /**
     * Texto para apresentar as opções disponíveis.
     */
    private final Texto textoOpcoes;

    /**
     * Texto a ser mostrado na opção para sair do menu.
     */
    private final Texto textoSaida;

    /**
     * Obtém uma lista com os botões do menu.
     * @return Lista com os botões do menu.
     */
    private List<Botao> obterBotoes() {
        final List<Botao> botoesConsole = new ArrayList<>();
        for (Opcao opcao: opcoes) {
            if (opcao instanceof Botao)
                botoesConsole.add((Botao) opcao);

        }
        return botoesConsole;
    }

    /**
     * Obtém o número de botões.
     * @return Número de botões.
     */
    private int obterNumeroBotoes() {
        return obterBotoes().size();
    }

    /**
     * Obtém mensagem a ser impressa na tela do usuário.
     * @return Mensagem a ser impressa na tela do usuário.
     */
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

    /**
     * Obtém opção digitada pelo usuário.
     * @return Opção digitada pelo usuário.
     */
    private int obterEntrada() {
        return MyIO.readInt(obterMensagem(), new IntRange(0, obterNumeroBotoes()));
    }

    /**
     * Constrói uma nova instância da classe Menu.
     * @param titulo Título do menu.
     * @param opcoes Opções do menu.
     * @param textoOpcoes Texto das opções do menu.
     * @param textoSaida Texto da opção de sair do menu.
     */
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

    /**
     * Constrói uma nova instância da classe Menu.
     * @param titulo Título do menu.
     * @param opcoes Opções do menu.
     */
    public Menu(String titulo, List<Opcao> opcoes) {
        this(titulo, opcoes, new Texto("Opções: "), new Texto("Sair"));
    }

    /**
     * Constrói uma nova instância da classe Menu.
     * @param opcoes Opções do menu.
     */
    public Menu(List<Opcao> opcoes) {
        this(null, opcoes, new Texto("Opções: "), new Texto("Sair"));
    }




}
