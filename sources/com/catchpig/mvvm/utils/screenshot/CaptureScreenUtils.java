package com.catchpig.mvvm.utils.screenshot;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import com.catchpig.mvvm.utils.screenshot.UriObserver;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.umeng.analytics.pro.d;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bÇ\u0002\u0018\u00002\u00020\u0001:\u0001\u001eB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u000e\u0010\u001a\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u000eJ\u0006\u0010\u001d\u001a\u00020\u0015R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/catchpig/mvvm/utils/screenshot/CaptureScreenUtils;", "Lcom/catchpig/mvvm/utils/screenshot/UriObserver$UriChangeListener;", "()V", "KEYWORDS", "", "", "[Ljava/lang/String;", "contentResolver", "Landroid/content/ContentResolver;", d.R, "Landroid/content/Context;", "imageObserver", "Lcom/catchpig/mvvm/utils/screenshot/UriObserver;", "mListener", "Lcom/catchpig/mvvm/utils/screenshot/CaptureScreenUtils$OnCaptureClickListener;", "screenPicture", "", "screenTime", "", "videoObserver", "change", "", "selfChange", "", "uri", "Landroid/net/Uri;", MiPushClient.COMMAND_REGISTER, "setOnCaptureListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, MiPushClient.COMMAND_UNREGISTER, "OnCaptureClickListener", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SuppressLint({"StaticFieldLeak"})
/* loaded from: classes2.dex */
public final class CaptureScreenUtils implements UriObserver.UriChangeListener {

    @NotNull
    public static final CaptureScreenUtils INSTANCE;

    @NotNull
    private static final String[] KEYWORDS;

    @Nullable
    private static ContentResolver contentResolver;

    @Nullable
    private static Context context;

    @NotNull
    private static UriObserver imageObserver;

    @Nullable
    private static OnCaptureClickListener mListener;

    @NotNull
    private static final Set<String> screenPicture;
    private static long screenTime;

    @NotNull
    private static UriObserver videoObserver;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/catchpig/mvvm/utils/screenshot/CaptureScreenUtils$OnCaptureClickListener;", "", "onCaptureListener", "", "path", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnCaptureClickListener {
        void onCaptureListener(@NotNull String path);
    }

    static {
        CaptureScreenUtils captureScreenUtils = new CaptureScreenUtils();
        INSTANCE = captureScreenUtils;
        KEYWORDS = new String[]{"screenshot", "screenshots", "screen_shot", "screen-shot", "screen shot", "screencapture", "screen_capture", "screen-capture", "screen capture", "screencap", "screen_cap", "screen-cap", "screen cap", "screenrecorder"};
        screenPicture = new LinkedHashSet();
        HandlerThread handlerThread = new HandlerThread("uriobserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        UriObserver uriObserver = new UriObserver(handler);
        videoObserver = uriObserver;
        uriObserver.setOnUriChangeListener(captureScreenUtils);
        UriObserver uriObserver2 = new UriObserver(handler);
        imageObserver = uriObserver2;
        uriObserver2.setOnUriChangeListener(captureScreenUtils);
    }

    private CaptureScreenUtils() {
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0029  */
    @Override // com.catchpig.mvvm.utils.screenshot.UriObserver.UriChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void change(boolean r17, @org.jetbrains.annotations.NotNull android.net.Uri r18) {
        /*
            Method dump skipped, instructions count: 405
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.utils.screenshot.CaptureScreenUtils.change(boolean, android.net.Uri):void");
    }

    public final void register(@NotNull Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        context = context2;
        ContentResolver contentResolver2 = context2.getContentResolver();
        if (contentResolver2 != null) {
            contentResolver = contentResolver2;
            contentResolver2.registerContentObserver(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, true, videoObserver);
            contentResolver2.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, imageObserver);
        }
    }

    public final void setOnCaptureListener(@NotNull OnCaptureClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        mListener = listener;
    }

    public final void unregister() {
        ContentResolver contentResolver2 = contentResolver;
        if (contentResolver2 != null) {
            contentResolver2.unregisterContentObserver(videoObserver);
            contentResolver2.unregisterContentObserver(imageObserver);
        }
        contentResolver = null;
        context = null;
    }
}
