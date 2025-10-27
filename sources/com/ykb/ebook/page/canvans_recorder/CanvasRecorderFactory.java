package com.ykb.ebook.page.canvans_recorder;

import android.os.Build;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorderFactory;", "", "()V", "atLeastApi29", "", "create", "Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;", "locked", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class CanvasRecorderFactory {

    @NotNull
    public static final CanvasRecorderFactory INSTANCE = new CanvasRecorderFactory();
    private static final boolean atLeastApi29;

    static {
        atLeastApi29 = Build.VERSION.SDK_INT >= 29;
    }

    private CanvasRecorderFactory() {
    }

    public static /* synthetic */ CanvasRecorder create$default(CanvasRecorderFactory canvasRecorderFactory, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        return canvasRecorderFactory.create(z2);
    }

    @NotNull
    public final CanvasRecorder create(boolean locked) {
        CanvasRecorder canvasRecorderApi29Impl = atLeastApi29 ? new CanvasRecorderApi29Impl() : new CanvasRecorderImpl();
        return locked ? new CanvasRecorderLocked(canvasRecorderApi29Impl) : canvasRecorderApi29Impl;
    }
}
