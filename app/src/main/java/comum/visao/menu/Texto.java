package comum.visao.menu;

/**
 * Opção do console que será impressa no menu de console.
 * Copiado e adaptado de outro projeto de autoria própria https://github.com/Henriquemcc/Dynamic_DNS_Update_Client/blob/main/app/src/main/kotlin/dynamic/dns/update/client/console/common/menu/ConsoleOption.kt
 */
public class Texto extends Opcao {
    public final String texto;

    public Texto(String texto) {
        this.texto = texto;
    }
}
