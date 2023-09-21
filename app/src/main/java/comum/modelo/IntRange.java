package comum.modelo;

import java.lang.annotation.Annotation;

public class IntRange {

    private final int from;
    private final int to;

    public IntRange(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public long from() {
        return from;
    }

    public long to() {
        return to;
    }

    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
