package cn.hutool.core.text.finder;

/* loaded from: classes.dex */
public interface Finder {
    public static final int INDEX_NOT_FOUND = -1;

    int end(int i2);

    Finder reset();

    int start(int i2);
}
