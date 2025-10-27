package org.apache.commons.compress.archivers.sevenz;

/* loaded from: classes9.dex */
public class SevenZMethodConfiguration {
    private final SevenZMethod method;
    private final Object options;

    public SevenZMethodConfiguration(SevenZMethod sevenZMethod) {
        this(sevenZMethod, null);
    }

    public SevenZMethod getMethod() {
        return this.method;
    }

    public Object getOptions() {
        return this.options;
    }

    public SevenZMethodConfiguration(SevenZMethod sevenZMethod, Object obj) {
        this.method = sevenZMethod;
        this.options = obj;
        if (obj == null || Coders.findByMethod(sevenZMethod).canAcceptOptions(obj)) {
            return;
        }
        throw new IllegalArgumentException("The " + sevenZMethod + " method doesn't support options of type " + obj.getClass());
    }
}
