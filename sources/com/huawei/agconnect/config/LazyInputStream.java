package com.huawei.agconnect.config;

import android.content.Context;
import com.huawei.agconnect.config.impl.Utils;
import java.io.IOException;
import java.io.InputStream;

@Deprecated
/* loaded from: classes4.dex */
public abstract class LazyInputStream {
    private final Context mContext;
    private InputStream mInput;

    public LazyInputStream(Context context) {
        this.mContext = context;
    }

    public final void close() throws IOException {
        Utils.closeQuietly(this.mInput);
    }

    public abstract InputStream get(Context context);

    public InputStream loadInputStream() {
        if (this.mInput == null) {
            this.mInput = get(this.mContext);
        }
        return this.mInput;
    }
}
