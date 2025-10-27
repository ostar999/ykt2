package com.opensource.svgaplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.text.BoringLayout;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.opensource.svgaplayer.SVGADynamicEntity;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010<\u001a\u00020=J\u000e\u0010>\u001a\u00020=2\u0006\u0010?\u001a\u00020\u0005J\u0014\u0010>\u001a\u00020=2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00050@JF\u0010A\u001a\u00020=26\u0010B\u001a2\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u00140\r2\u0006\u0010C\u001a\u00020\u0005Jp\u0010D\u001a\u00020=2`\u0010B\u001a\\\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0013\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0019\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00140\u00182\u0006\u0010C\u001a\u00020\u0005J\u0016\u0010E\u001a\u00020=2\u0006\u0010F\u001a\u00020%2\u0006\u0010C\u001a\u00020\u0005J\u0016\u0010E\u001a\u00020=2\u0006\u0010G\u001a\u00020\u00052\u0006\u0010C\u001a\u00020\u0005J\u0016\u0010H\u001a\u00020=2\u0006\u0010I\u001a\u00020\u00062\u0006\u0010C\u001a\u00020\u0005J\u0016\u0010H\u001a\u00020=2\u0006\u0010I\u001a\u00020)2\u0006\u0010C\u001a\u00020\u0005J\u001e\u0010H\u001a\u00020=2\u0006\u0010J\u001a\u00020\u00052\u0006\u0010K\u001a\u0002002\u0006\u0010C\u001a\u00020\u0005J\u0016\u0010L\u001a\u00020=2\u0006\u0010M\u001a\u00020\u00142\u0006\u0010C\u001a\u00020\u0005R6\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0096\u0001\u0010\f\u001a~\u0012\u0004\u0012\u00020\u0005\u00124\u00122\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u00140\r0\u0004j>\u0012\u0004\u0012\u00020\u0005\u00124\u00122\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u00140\r`\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\t\"\u0004\b\u0016\u0010\u000bRë\u0001\u0010\u0017\u001aÒ\u0001\u0012\u0004\u0012\u00020\u0005\u0012^\u0012\\\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0013\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0019\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00140\u00180\u0004jh\u0012\u0004\u0012\u00020\u0005\u0012^\u0012\\\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0013\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0019\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00140\u0018`\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\t\"\u0004\b\u001c\u0010\u000bR6\u0010\u001d\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00140\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0014`\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\t\"\u0004\b\u001f\u0010\u000bR6\u0010 \u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020!0\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020!`\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\t\"\u0004\b#\u0010\u000bR6\u0010$\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020%0\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020%`\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\t\"\u0004\b'\u0010\u000bR6\u0010(\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020)0\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020)`\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\t\"\u0004\b+\u0010\u000bR6\u0010,\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\t\"\u0004\b.\u0010\u000bR6\u0010/\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002000\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u000200`\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\t\"\u0004\b2\u0010\u000bR\u001a\u00103\u001a\u00020\u0014X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R6\u00108\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002090\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u000209`\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\t\"\u0004\b;\u0010\u000b¨\u0006N"}, d2 = {"Lcom/opensource/svgaplayer/SVGADynamicEntity;", "", "()V", "dynamicBoringLayoutText", "Ljava/util/HashMap;", "", "Landroid/text/BoringLayout;", "Lkotlin/collections/HashMap;", "getDynamicBoringLayoutText$com_opensource_svgaplayer", "()Ljava/util/HashMap;", "setDynamicBoringLayoutText$com_opensource_svgaplayer", "(Ljava/util/HashMap;)V", "dynamicDrawer", "Lkotlin/Function2;", "Landroid/graphics/Canvas;", "Lkotlin/ParameterName;", "name", "canvas", "", "frameIndex", "", "getDynamicDrawer$com_opensource_svgaplayer", "setDynamicDrawer$com_opensource_svgaplayer", "dynamicDrawerSized", "Lkotlin/Function4;", "width", "height", "getDynamicDrawerSized$com_opensource_svgaplayer", "setDynamicDrawerSized$com_opensource_svgaplayer", "dynamicHidden", "getDynamicHidden$com_opensource_svgaplayer", "setDynamicHidden$com_opensource_svgaplayer", "dynamicIClickArea", "Lcom/opensource/svgaplayer/IClickAreaListener;", "getDynamicIClickArea$com_opensource_svgaplayer", "setDynamicIClickArea$com_opensource_svgaplayer", "dynamicImage", "Landroid/graphics/Bitmap;", "getDynamicImage$com_opensource_svgaplayer", "setDynamicImage$com_opensource_svgaplayer", "dynamicStaticLayoutText", "Landroid/text/StaticLayout;", "getDynamicStaticLayoutText$com_opensource_svgaplayer", "setDynamicStaticLayoutText$com_opensource_svgaplayer", "dynamicText", "getDynamicText$com_opensource_svgaplayer", "setDynamicText$com_opensource_svgaplayer", "dynamicTextPaint", "Landroid/text/TextPaint;", "getDynamicTextPaint$com_opensource_svgaplayer", "setDynamicTextPaint$com_opensource_svgaplayer", "isTextDirty", "isTextDirty$com_opensource_svgaplayer", "()Z", "setTextDirty$com_opensource_svgaplayer", "(Z)V", "mClickMap", "", "getMClickMap$com_opensource_svgaplayer", "setMClickMap$com_opensource_svgaplayer", "clearDynamicObjects", "", "setClickArea", "clickKey", "", "setDynamicDrawer", "drawer", "forKey", "setDynamicDrawerSized", "setDynamicImage", "bitmap", "url", "setDynamicText", "layoutText", "text", "textPaint", "setHidden", "value", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGADynamicEntity {
    private boolean isTextDirty;

    @NotNull
    private HashMap<String, Boolean> dynamicHidden = new HashMap<>();

    @NotNull
    private HashMap<String, Bitmap> dynamicImage = new HashMap<>();

    @NotNull
    private HashMap<String, String> dynamicText = new HashMap<>();

    @NotNull
    private HashMap<String, TextPaint> dynamicTextPaint = new HashMap<>();

    @NotNull
    private HashMap<String, StaticLayout> dynamicStaticLayoutText = new HashMap<>();

    @NotNull
    private HashMap<String, BoringLayout> dynamicBoringLayoutText = new HashMap<>();

    @NotNull
    private HashMap<String, Function2<Canvas, Integer, Boolean>> dynamicDrawer = new HashMap<>();

    @NotNull
    private HashMap<String, int[]> mClickMap = new HashMap<>();

    @NotNull
    private HashMap<String, IClickAreaListener> dynamicIClickArea = new HashMap<>();

    @NotNull
    private HashMap<String, Function4<Canvas, Integer, Integer, Integer, Boolean>> dynamicDrawerSized = new HashMap<>();

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "run"}, k = 3, mv = {1, 1, 15})
    /* renamed from: com.opensource.svgaplayer.SVGADynamicEntity$setDynamicImage$1, reason: invalid class name and case insensitive filesystem */
    public static final class RunnableC05511 implements Runnable {
        final /* synthetic */ String $forKey;
        final /* synthetic */ Handler $handler;
        final /* synthetic */ String $url;

        public RunnableC05511(String str, Handler handler, String str2) {
            this.$url = str;
            this.$handler = handler;
            this.$forKey = str2;
        }

        @Override // java.lang.Runnable
        public final void run() throws IOException {
            URLConnection uRLConnectionOpenConnection = new URL(this.$url).openConnection();
            if (!(uRLConnectionOpenConnection instanceof HttpURLConnection)) {
                uRLConnectionOpenConnection = null;
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            if (httpURLConnection != null) {
                try {
                    try {
                        httpURLConnection.setConnectTimeout(20000);
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.connect();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        try {
                            final Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStream);
                            if (bitmapDecodeStream != null) {
                                this.$handler.post(new Runnable() { // from class: com.opensource.svgaplayer.SVGADynamicEntity$setDynamicImage$1$$special$$inlined$let$lambda$2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SVGADynamicEntity.RunnableC05511 runnableC05511 = this;
                                        SVGADynamicEntity.this.setDynamicImage(bitmapDecodeStream, runnableC05511.$forKey);
                                    }
                                });
                            }
                            CloseableKt.closeFinally(inputStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(inputStream, th);
                                throw th2;
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Unit unit = Unit.INSTANCE;
                    }
                } finally {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable unused) {
                    }
                }
            }
        }
    }

    public final void clearDynamicObjects() {
        this.isTextDirty = true;
        this.dynamicHidden.clear();
        this.dynamicImage.clear();
        this.dynamicText.clear();
        this.dynamicTextPaint.clear();
        this.dynamicStaticLayoutText.clear();
        this.dynamicBoringLayoutText.clear();
        this.dynamicDrawer.clear();
        this.dynamicIClickArea.clear();
        this.mClickMap.clear();
        this.dynamicDrawerSized.clear();
    }

    @NotNull
    public final HashMap<String, BoringLayout> getDynamicBoringLayoutText$com_opensource_svgaplayer() {
        return this.dynamicBoringLayoutText;
    }

    @NotNull
    public final HashMap<String, Function2<Canvas, Integer, Boolean>> getDynamicDrawer$com_opensource_svgaplayer() {
        return this.dynamicDrawer;
    }

    @NotNull
    public final HashMap<String, Function4<Canvas, Integer, Integer, Integer, Boolean>> getDynamicDrawerSized$com_opensource_svgaplayer() {
        return this.dynamicDrawerSized;
    }

    @NotNull
    public final HashMap<String, Boolean> getDynamicHidden$com_opensource_svgaplayer() {
        return this.dynamicHidden;
    }

    @NotNull
    public final HashMap<String, IClickAreaListener> getDynamicIClickArea$com_opensource_svgaplayer() {
        return this.dynamicIClickArea;
    }

    @NotNull
    public final HashMap<String, Bitmap> getDynamicImage$com_opensource_svgaplayer() {
        return this.dynamicImage;
    }

    @NotNull
    public final HashMap<String, StaticLayout> getDynamicStaticLayoutText$com_opensource_svgaplayer() {
        return this.dynamicStaticLayoutText;
    }

    @NotNull
    public final HashMap<String, String> getDynamicText$com_opensource_svgaplayer() {
        return this.dynamicText;
    }

    @NotNull
    public final HashMap<String, TextPaint> getDynamicTextPaint$com_opensource_svgaplayer() {
        return this.dynamicTextPaint;
    }

    @NotNull
    public final HashMap<String, int[]> getMClickMap$com_opensource_svgaplayer() {
        return this.mClickMap;
    }

    /* renamed from: isTextDirty$com_opensource_svgaplayer, reason: from getter */
    public final boolean getIsTextDirty() {
        return this.isTextDirty;
    }

    public final void setClickArea(@NotNull List<String> clickKey) {
        Intrinsics.checkParameterIsNotNull(clickKey, "clickKey");
        Iterator<String> it = clickKey.iterator();
        while (it.hasNext()) {
            this.dynamicIClickArea.put(it.next(), new IClickAreaListener() { // from class: com.opensource.svgaplayer.SVGADynamicEntity.setClickArea.1
                @Override // com.opensource.svgaplayer.IClickAreaListener
                public void onResponseArea(@NotNull String key, int x02, int y02, int x12, int y12) {
                    Intrinsics.checkParameterIsNotNull(key, "key");
                    HashMap<String, int[]> mClickMap$com_opensource_svgaplayer = SVGADynamicEntity.this.getMClickMap$com_opensource_svgaplayer();
                    if (mClickMap$com_opensource_svgaplayer.get(key) == null) {
                        mClickMap$com_opensource_svgaplayer.put(key, new int[]{x02, y02, x12, y12});
                        return;
                    }
                    int[] iArr = mClickMap$com_opensource_svgaplayer.get(key);
                    if (iArr != null) {
                        iArr[0] = x02;
                        iArr[1] = y02;
                        iArr[2] = x12;
                        iArr[3] = y12;
                    }
                }
            });
        }
    }

    public final void setDynamicBoringLayoutText$com_opensource_svgaplayer(@NotNull HashMap<String, BoringLayout> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.dynamicBoringLayoutText = map;
    }

    public final void setDynamicDrawer(@NotNull Function2<? super Canvas, ? super Integer, Boolean> drawer, @NotNull String forKey) {
        Intrinsics.checkParameterIsNotNull(drawer, "drawer");
        Intrinsics.checkParameterIsNotNull(forKey, "forKey");
        this.dynamicDrawer.put(forKey, drawer);
    }

    public final void setDynamicDrawer$com_opensource_svgaplayer(@NotNull HashMap<String, Function2<Canvas, Integer, Boolean>> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.dynamicDrawer = map;
    }

    public final void setDynamicDrawerSized(@NotNull Function4<? super Canvas, ? super Integer, ? super Integer, ? super Integer, Boolean> drawer, @NotNull String forKey) {
        Intrinsics.checkParameterIsNotNull(drawer, "drawer");
        Intrinsics.checkParameterIsNotNull(forKey, "forKey");
        this.dynamicDrawerSized.put(forKey, drawer);
    }

    public final void setDynamicDrawerSized$com_opensource_svgaplayer(@NotNull HashMap<String, Function4<Canvas, Integer, Integer, Integer, Boolean>> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.dynamicDrawerSized = map;
    }

    public final void setDynamicHidden$com_opensource_svgaplayer(@NotNull HashMap<String, Boolean> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.dynamicHidden = map;
    }

    public final void setDynamicIClickArea$com_opensource_svgaplayer(@NotNull HashMap<String, IClickAreaListener> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.dynamicIClickArea = map;
    }

    public final void setDynamicImage(@NotNull Bitmap bitmap, @NotNull String forKey) {
        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
        Intrinsics.checkParameterIsNotNull(forKey, "forKey");
        this.dynamicImage.put(forKey, bitmap);
    }

    public final void setDynamicImage$com_opensource_svgaplayer(@NotNull HashMap<String, Bitmap> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.dynamicImage = map;
    }

    public final void setDynamicStaticLayoutText$com_opensource_svgaplayer(@NotNull HashMap<String, StaticLayout> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.dynamicStaticLayoutText = map;
    }

    public final void setDynamicText(@NotNull String text, @NotNull TextPaint textPaint, @NotNull String forKey) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(textPaint, "textPaint");
        Intrinsics.checkParameterIsNotNull(forKey, "forKey");
        this.isTextDirty = true;
        this.dynamicText.put(forKey, text);
        this.dynamicTextPaint.put(forKey, textPaint);
    }

    public final void setDynamicText$com_opensource_svgaplayer(@NotNull HashMap<String, String> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.dynamicText = map;
    }

    public final void setDynamicTextPaint$com_opensource_svgaplayer(@NotNull HashMap<String, TextPaint> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.dynamicTextPaint = map;
    }

    public final void setHidden(boolean value, @NotNull String forKey) {
        Intrinsics.checkParameterIsNotNull(forKey, "forKey");
        this.dynamicHidden.put(forKey, Boolean.valueOf(value));
    }

    public final void setMClickMap$com_opensource_svgaplayer(@NotNull HashMap<String, int[]> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.mClickMap = map;
    }

    public final void setTextDirty$com_opensource_svgaplayer(boolean z2) {
        this.isTextDirty = z2;
    }

    public final void setDynamicImage(@NotNull String url, @NotNull String forKey) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(forKey, "forKey");
        SVGAParser.INSTANCE.getThreadPoolExecutor$com_opensource_svgaplayer().execute(new RunnableC05511(url, new Handler(), forKey));
    }

    public final void setClickArea(@NotNull String clickKey) {
        Intrinsics.checkParameterIsNotNull(clickKey, "clickKey");
        this.dynamicIClickArea.put(clickKey, new IClickAreaListener() { // from class: com.opensource.svgaplayer.SVGADynamicEntity.setClickArea.2
            @Override // com.opensource.svgaplayer.IClickAreaListener
            public void onResponseArea(@NotNull String key, int x02, int y02, int x12, int y12) {
                Intrinsics.checkParameterIsNotNull(key, "key");
                HashMap<String, int[]> mClickMap$com_opensource_svgaplayer = SVGADynamicEntity.this.getMClickMap$com_opensource_svgaplayer();
                if (mClickMap$com_opensource_svgaplayer.get(key) == null) {
                    mClickMap$com_opensource_svgaplayer.put(key, new int[]{x02, y02, x12, y12});
                    return;
                }
                int[] iArr = mClickMap$com_opensource_svgaplayer.get(key);
                if (iArr != null) {
                    iArr[0] = x02;
                    iArr[1] = y02;
                    iArr[2] = x12;
                    iArr[3] = y12;
                }
            }
        });
    }

    public final void setDynamicText(@NotNull StaticLayout layoutText, @NotNull String forKey) {
        Intrinsics.checkParameterIsNotNull(layoutText, "layoutText");
        Intrinsics.checkParameterIsNotNull(forKey, "forKey");
        this.isTextDirty = true;
        this.dynamicStaticLayoutText.put(forKey, layoutText);
    }

    public final void setDynamicText(@NotNull BoringLayout layoutText, @NotNull String forKey) {
        Intrinsics.checkParameterIsNotNull(layoutText, "layoutText");
        Intrinsics.checkParameterIsNotNull(forKey, "forKey");
        this.isTextDirty = true;
        if (BoringLayout.isBoring(layoutText.getText(), layoutText.getPaint()) != null) {
            this.dynamicBoringLayoutText.put(forKey, layoutText);
        }
    }
}
