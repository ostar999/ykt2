package net.polyv.danmaku.danmaku.loader;

import java.io.InputStream;
import net.polyv.danmaku.danmaku.parser.IDataSource;

/* loaded from: classes9.dex */
public interface ILoader {
    IDataSource<?> getDataSource();

    void load(InputStream inputStream) throws IllegalDataException;

    void load(String str) throws IllegalDataException;
}
