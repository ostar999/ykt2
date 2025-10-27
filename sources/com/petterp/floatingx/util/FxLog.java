package com.petterp.floatingx.util;

import android.util.Log;
import cn.hutool.core.text.CharPool;
import com.aliyun.vod.log.struct.AliyunLogKey;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0003J\u000e\u0010\b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0003J\u000e\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/petterp/floatingx/util/FxLog;", "", "tag", "", "(Ljava/lang/String;)V", "d", "", "message", AliyunLogKey.KEY_EVENT, "v", "Companion", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class FxLog {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static String TAG = "FloatingX";

    @NotNull
    private final String tag;

    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/petterp/floatingx/util/FxLog$Companion;", "", "()V", "TAG", "", "builder", "Lcom/petterp/floatingx/util/FxLog;", "tag", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final FxLog builder(@NotNull String tag) {
            Intrinsics.checkNotNullParameter(tag, "tag");
            return new FxLog(FxLog.TAG + CharPool.DASHED + tag, null);
        }
    }

    private FxLog(String str) {
        this.tag = str;
    }

    public /* synthetic */ FxLog(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    public final void d(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        Log.d(this.tag, message);
    }

    public final void e(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        Log.e(this.tag, message);
    }

    public final void v(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        Log.v(this.tag, message);
    }
}
