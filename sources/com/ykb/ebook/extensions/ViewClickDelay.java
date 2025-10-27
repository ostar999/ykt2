package com.ykb.ebook.extensions;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\b¨\u0006\u0012"}, d2 = {"Lcom/ykb/ebook/extensions/ViewClickDelay;", "", "()V", "SPACE_TIME", "", "getSPACE_TIME", "()J", "setSPACE_TIME", "(J)V", "hash", "", "getHash", "()I", "setHash", "(I)V", "lastClickTime", "getLastClickTime", "setLastClickTime", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ViewClickDelay {

    @NotNull
    public static final ViewClickDelay INSTANCE = new ViewClickDelay();
    private static long SPACE_TIME = 1000;
    private static int hash;
    private static long lastClickTime;

    private ViewClickDelay() {
    }

    public final int getHash() {
        return hash;
    }

    public final long getLastClickTime() {
        return lastClickTime;
    }

    public final long getSPACE_TIME() {
        return SPACE_TIME;
    }

    public final void setHash(int i2) {
        hash = i2;
    }

    public final void setLastClickTime(long j2) {
        lastClickTime = j2;
    }

    public final void setSPACE_TIME(long j2) {
        SPACE_TIME = j2;
    }
}
