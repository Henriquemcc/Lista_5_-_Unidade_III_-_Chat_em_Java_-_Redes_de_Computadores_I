package comum.visao.menu;

import java.util.function.Supplier;

/**
 * Botão do console, que contém o texto dele e a ação a ser executada quando ele é selecionado.
 */
public class Botao extends Opcao {

    /**
     * Texto do botão.
     */
    final String texto;

    /**
     * Ação a ser executada quando o botão é pressionado.
     */
    final Supplier<Void> quandoPressionado;

    /**
     * Constrói uma nova instância da classe Botao.
     * @param texto Texto do botão.
     * @param quandoPressionado Ação a ser executada quando o botão é pressionado.
     */
    public Botao(String texto, Supplier<Void> quandoPressionado) {
        this.texto = texto;
        this.quandoPressionado = quandoPressionado;
    }
}
