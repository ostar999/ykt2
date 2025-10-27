package net.polyv.danmaku.danmaku.loader.android;

import android.net.Uri;
import java.io.InputStream;
import net.polyv.danmaku.danmaku.loader.ILoader;
import net.polyv.danmaku.danmaku.loader.IllegalDataException;
import net.polyv.danmaku.danmaku.parser.android.JSONSource;

/* loaded from: classes9.dex */
public class AcFunDanmakuLoader implements ILoader {
    private static volatile AcFunDanmakuLoader instance;
    private JSONSource dataSource;

    private AcFunDanmakuLoader() {
    }

    public static ILoader instance() {
        if (instance == null) {
            synchronized (AcFunDanmakuLoader.class) {
                if (instance == null) {
                    instance = new AcFunDanmakuLoader();
                }
            }
        }
        return instance;
    }

    @Override // net.polyv.danmaku.danmaku.loader.ILoader
    public void load(String str) throws IllegalDataException {
        try {
            this.dataSource = new JSONSource(Uri.parse(str));
        } catch (Exception e2) {
            throw new IllegalDataException(e2);
        }
    }

    @Override // net.polyv.danmaku.danmaku.loader.ILoader
    public JSONSource getDataSource() {
        return this.dataSource;
    }

    @Override // net.polyv.danmaku.danmaku.loader.ILoader
    public void load(InputStream inputStream) throws IllegalDataException {
        try {
            this.dataSource = new JSONSource(inputStream);
        } catch (Exception e2) {
            throw new IllegalDataException(e2);
        }
    }
}
