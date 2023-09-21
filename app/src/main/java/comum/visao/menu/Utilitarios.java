package comum.visao.menu;

import comum.visao.MyIO;

public class Utilitarios {

    public static void imprimirCabecalho(String titulo) {
        for (int i = 0; i < 80; i++) {
            System.out.print("#");
        }
        System.out.println();
        System.out.println(titulo);
        for (int i = 0; i < 80; i++) {
            System.out.print("#");
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Obtém do usuário a confirmação de uma ação.
     * Copiado e adaptado de outro projeto de autoria própria https://github.com/Henriquemcc/Dynamic_DNS_Update_Client/blob/main/app/src/main/kotlin/dynamic/dns/update/client/console/common/GetConfirmationConsole.kt
     *
     * @param comando Requisicao que instruirá o usuário sobre o que ele deve confirmar.
     * @return Confirmação do usuário.
     */
    public static boolean obterConfirmacao(String comando) {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append(comando).append("\n");
        mensagem.append("S - Sim").append("\n");
        mensagem.append("N - Não").append("\n");
        mensagem.append("> ");

        String entrada = null;
        while (entrada == null || entrada.isEmpty() || (entrada.charAt(0) != 'S' && entrada.charAt(0) != 'N')) {
            entrada = MyIO.readString(mensagem.toString()).toUpperCase();
        }

        return entrada.charAt(0) == 'S';
    }
}
