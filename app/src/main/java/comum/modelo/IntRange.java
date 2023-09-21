package comum.modelo;

import java.lang.annotation.Annotation;

/**
 * Intervalo de valores inteiros
 */
public class IntRange {

    /**
     * Valor mínimo.
     */
    private final int from;

    /**
     * Valor máximo.
     */
    private final int to;

    /**
     * Constrói uma nova instância da classe IntRange.
     * @param from Valor do valor mínimo.
     * @param to Valor do valor máximo.
     */
    public IntRange(int from, int to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Obtém o valor mínimo.
     * @return Valor mínimo do intervalo.
     */
    public long from() {
        return from;
    }

    /**
     * Obtém o valor máximo.
     * @return Valor máximo do intervalo.
     */
    public long to() {
        return to;
    }

    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
