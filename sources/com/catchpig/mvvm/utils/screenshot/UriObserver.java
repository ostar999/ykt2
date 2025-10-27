package com.catchpig.mvvm.utils.screenshot;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u000eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\u000e\u0010\r\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/catchpig/mvvm/utils/screenshot/UriObserver;", "Landroid/database/ContentObserver;", "handler", "Landroid/os/Handler;", "(Landroid/os/Handler;)V", "uriChangeListener", "Lcom/catchpig/mvvm/utils/screenshot/UriObserver$UriChangeListener;", "onChange", "", "selfChange", "", "uri", "Landroid/net/Uri;", "setOnUriChangeListener", "UriChangeListener", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UriObserver extends ContentObserver {
    private UriChangeListener uriChangeListener;

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lcom/catchpig/mvvm/utils/screenshot/UriObserver$UriChangeListener;", "", "change", "", "selfChange", "", "uri", "Landroid/net/Uri;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface UriChangeListener {
        void change(boolean selfChange, @NotNull Uri uri);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UriObserver(@NotNull Handler handler) {
        super(handler);
        Intrinsics.checkNotNullParameter(handler, "handler");
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean selfChange, @Nullable Uri uri) {
        super.onChange(selfChange, uri);
        Log.e("CaptureScreenUtils", "UriObserver===" + selfChange + ",  " + uri);
        UriChangeListener uriChangeListener = this.uriChangeListener;
        if (uriChangeListener == null || uri == null) {
            return;
        }
        if (uriChangeListener == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uriChangeListener");
            uriChangeListener = null;
        }
        uriChangeListener.change(selfChange, uri);
    }

    public final void setOnUriChangeListener(@NotNull UriChangeListener uriChangeListener) {
        Intrinsics.checkNotNullParameter(uriChangeListener, "uriChangeListener");
        this.uriChangeListener = uriChangeListener;
    }
}
