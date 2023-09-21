package comum.visao;

import comum.modelo.IntRange;

import java.io.IOException;

/**
 * Classe que contém os métodos de input e output.
 */
public class MyIO {

    /**
     * Lê uma String da entrada do usuário.
     * @param instrucao Explicação para o usuário o que ele deve digitar.
     * @return String lida da entrada.
     */
    public static String readString(String instrucao) {
        if (instrucao != null)
            System.out.print(instrucao);

        return readString();
    }

    /**
     * Realiza a leitura da entrada do usuário.
     * @return String lida da entrada.
     */
    private static String readLine() {
        StringBuilder string = new StringBuilder();
        char tmp;
        try {
            do {
                tmp = (char) System.in.read();
                if (tmp != '\n' && tmp != 13) {
                    string.append(tmp);
                }
            } while (tmp != '\n');
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return string.toString();
    }

    /**
     * Lê uma String da entrada do usuário.
     * @return String lida da entrada.
     */
    public static String readString() {
        return readLine();
    }

    public static int readInt(String instrucao, IntRange intervalo) {
        Integer numero = null;
        while (numero == null || (intervalo != null && (numero < intervalo.from() || numero > intervalo.to()))) {
            final String stringLida = readString(instrucao);
            try {
                numero = new Integer(stringLida);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (intervalo != null && numero != null && (numero < intervalo.from() || numero > intervalo.to())) {
                System.out.printf("%d não está entre %d e %d\n", numero, intervalo.from(), intervalo.to());
            }
        }

        return numero;
    }

    /**
     * Lê um número inteiro da entrada do usuário.
     * @return Número inteiro lido da entrada.
     */
    public static int readInt() {
        return readInt(null, null);
    }

}
