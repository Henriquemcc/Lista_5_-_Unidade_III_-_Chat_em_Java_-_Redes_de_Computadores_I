package comum.modelo;

import java.lang.annotation.Annotation;

public class IntRange implements org.checkerframework.common.value.qual.IntRange {

    private final int from;
    private final int to;

    public IntRange(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public long from() {
        return from;
    }

    @Override
    public long to() {
        return to;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
