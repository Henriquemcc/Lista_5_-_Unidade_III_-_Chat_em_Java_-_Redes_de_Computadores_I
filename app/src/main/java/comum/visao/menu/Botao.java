package comum.visao.menu;

import java.util.function.Supplier;

/**
 * Botão do console, que contém o texto dele e a ação a ser executada quando ele é selecionado.
 * Copiado e adaptado de outro projeto de autoria própria https://github.com/Henriquemcc/Dynamic_DNS_Update_Client/blob/main/app/src/main/kotlin/dynamic/dns/update/client/console/common/menu/ConsoleButton.kt
 */
public class Botao extends Opcao {
    final String texto;
    final Supplier<Void> quandoPressionado;

    public Botao(String texto, Supplier<Void> quandoPressionado) {
        this.texto = texto;
        this.quandoPressionado = quandoPressionado;
    }
}
