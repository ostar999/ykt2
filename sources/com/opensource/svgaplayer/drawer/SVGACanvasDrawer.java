package com.opensource.svgaplayer.drawer;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.media.SoundPool;
import android.text.BoringLayout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import com.opensource.svgaplayer.BuildConfig;
import com.opensource.svgaplayer.IClickAreaListener;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGASoundManager;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.opensource.svgaplayer.drawer.SGVADrawer;
import com.opensource.svgaplayer.entities.SVGAAudioEntity;
import com.opensource.svgaplayer.entities.SVGAPathEntity;
import com.opensource.svgaplayer.entities.SVGAVideoShapeEntity;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001:\u000267B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J$\u0010\u0019\u001a\u00020\u001a2\n\u0010\u001b\u001a\u00060\u001cR\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0002J \u0010!\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\"\u001a\u00020#H\u0016J\u001c\u0010$\u001a\u00020\u001a2\n\u0010\u001b\u001a\u00060\u001cR\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u001c\u0010%\u001a\u00020\u001a2\n\u0010\u001b\u001a\u00060\u001cR\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J$\u0010&\u001a\u00020\u001a2\n\u0010\u001b\u001a\u00060\u001cR\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0002J,\u0010'\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010(\u001a\u00020\u000e2\n\u0010\u001b\u001a\u00060\u001cR\u00020\u00012\u0006\u0010)\u001a\u00020*H\u0002J\"\u0010+\u001a\u00020\t2\u0006\u0010,\u001a\u00020 2\u0010\u0010-\u001a\f\u0012\b\u0012\u00060\u001cR\u00020\u00010.H\u0002J\"\u0010/\u001a\u00020\t2\u0006\u0010,\u001a\u00020 2\u0010\u0010-\u001a\f\u0012\b\u0012\u00060\u001cR\u00020\u00010.H\u0002J\u0010\u00100\u001a\u0002012\u0006\u00102\u001a\u00020*H\u0002J\u0010\u00103\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0010\u00104\u001a\u00020*2\u0006\u00105\u001a\u00020*H\u0002R\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\nR*\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fj\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\nR\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lcom/opensource/svgaplayer/drawer/SVGACanvasDrawer;", "Lcom/opensource/svgaplayer/drawer/SGVADrawer;", "videoItem", "Lcom/opensource/svgaplayer/SVGAVideoEntity;", "dynamicItem", "Lcom/opensource/svgaplayer/SVGADynamicEntity;", "(Lcom/opensource/svgaplayer/SVGAVideoEntity;Lcom/opensource/svgaplayer/SVGADynamicEntity;)V", "beginIndexList", "", "", "[Ljava/lang/Boolean;", "drawTextCache", "Ljava/util/HashMap;", "", "Landroid/graphics/Bitmap;", "Lkotlin/collections/HashMap;", "getDynamicItem", "()Lcom/opensource/svgaplayer/SVGADynamicEntity;", "endIndexList", "matrixScaleTempValues", "", "pathCache", "Lcom/opensource/svgaplayer/drawer/SVGACanvasDrawer$PathCache;", "sharedValues", "Lcom/opensource/svgaplayer/drawer/SVGACanvasDrawer$ShareValues;", "drawDynamic", "", "sprite", "Lcom/opensource/svgaplayer/drawer/SGVADrawer$SVGADrawerSprite;", "canvas", "Landroid/graphics/Canvas;", "frameIndex", "", "drawFrame", "scaleType", "Landroid/widget/ImageView$ScaleType;", "drawImage", "drawShape", "drawSprite", "drawTextOnBitmap", "drawingBitmap", "frameMatrix", "Landroid/graphics/Matrix;", "isMatteBegin", "spriteIndex", "sprites", "", "isMatteEnd", "matrixScale", "", "matrix", "playAudio", "shareFrameMatrix", "transform", "PathCache", "ShareValues", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGACanvasDrawer extends SGVADrawer {
    private Boolean[] beginIndexList;
    private final HashMap<String, Bitmap> drawTextCache;

    @NotNull
    private final SVGADynamicEntity dynamicItem;
    private Boolean[] endIndexList;
    private final float[] matrixScaleTempValues;
    private final PathCache pathCache;
    private final ShareValues sharedValues;

    @Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0005J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/opensource/svgaplayer/drawer/SVGACanvasDrawer$PathCache;", "", "()V", "cache", "Ljava/util/HashMap;", "Lcom/opensource/svgaplayer/entities/SVGAVideoShapeEntity;", "Landroid/graphics/Path;", "Lkotlin/collections/HashMap;", "canvasHeight", "", "canvasWidth", "buildPath", "shape", "onSizeChanged", "", "canvas", "Landroid/graphics/Canvas;", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public static final class PathCache {
        private final HashMap<SVGAVideoShapeEntity, Path> cache = new HashMap<>();
        private int canvasHeight;
        private int canvasWidth;

        @NotNull
        public final Path buildPath(@NotNull SVGAVideoShapeEntity shape) {
            Intrinsics.checkParameterIsNotNull(shape, "shape");
            if (!this.cache.containsKey(shape)) {
                Path path = new Path();
                path.set(shape.getShapePath());
                this.cache.put(shape, path);
            }
            Path path2 = this.cache.get(shape);
            if (path2 == null) {
                Intrinsics.throwNpe();
            }
            return path2;
        }

        public final void onSizeChanged(@NotNull Canvas canvas) {
            Intrinsics.checkParameterIsNotNull(canvas, "canvas");
            if (this.canvasWidth != canvas.getWidth() || this.canvasHeight != canvas.getHeight()) {
                this.cache.clear();
            }
            this.canvasWidth = canvas.getWidth();
            this.canvasHeight = canvas.getHeight();
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\bJ\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\f\u001a\u00020\u0006J\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u000f\u001a\u00020\u000eR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/opensource/svgaplayer/drawer/SVGACanvasDrawer$ShareValues;", "", "()V", "shareMatteCanvas", "Landroid/graphics/Canvas;", "shareMattePaint", "Landroid/graphics/Paint;", "sharedMatrix", "Landroid/graphics/Matrix;", "sharedMatrix2", "sharedMatteBitmap", "Landroid/graphics/Bitmap;", "sharedPaint", "sharedPath", "Landroid/graphics/Path;", "sharedPath2", "width", "", "height", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
    public static final class ShareValues {
        private Canvas shareMatteCanvas;
        private Bitmap sharedMatteBitmap;
        private final Paint sharedPaint = new Paint();
        private final Path sharedPath = new Path();
        private final Path sharedPath2 = new Path();
        private final Matrix sharedMatrix = new Matrix();
        private final Matrix sharedMatrix2 = new Matrix();
        private final Paint shareMattePaint = new Paint();

        @NotNull
        public final Canvas shareMatteCanvas(int width, int height) {
            if (this.shareMatteCanvas == null) {
                this.sharedMatteBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
            }
            return new Canvas(this.sharedMatteBitmap);
        }

        @NotNull
        public final Paint shareMattePaint() {
            this.shareMattePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            return this.shareMattePaint;
        }

        @NotNull
        public final Matrix sharedMatrix() {
            this.sharedMatrix.reset();
            return this.sharedMatrix;
        }

        @NotNull
        public final Matrix sharedMatrix2() {
            this.sharedMatrix2.reset();
            return this.sharedMatrix2;
        }

        @NotNull
        public final Bitmap sharedMatteBitmap() {
            Bitmap bitmap = this.sharedMatteBitmap;
            if (bitmap != null) {
                return bitmap;
            }
            throw new TypeCastException("null cannot be cast to non-null type android.graphics.Bitmap");
        }

        @NotNull
        public final Paint sharedPaint() {
            this.sharedPaint.reset();
            return this.sharedPaint;
        }

        @NotNull
        public final Path sharedPath() {
            this.sharedPath.reset();
            return this.sharedPath;
        }

        @NotNull
        public final Path sharedPath2() {
            this.sharedPath2.reset();
            return this.sharedPath2;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SVGACanvasDrawer(@NotNull SVGAVideoEntity videoItem, @NotNull SVGADynamicEntity dynamicItem) {
        super(videoItem);
        Intrinsics.checkParameterIsNotNull(videoItem, "videoItem");
        Intrinsics.checkParameterIsNotNull(dynamicItem, "dynamicItem");
        this.dynamicItem = dynamicItem;
        this.sharedValues = new ShareValues();
        this.drawTextCache = new HashMap<>();
        this.pathCache = new PathCache();
        this.matrixScaleTempValues = new float[16];
    }

    private final void drawDynamic(SGVADrawer.SVGADrawerSprite sprite, Canvas canvas, int frameIndex) {
        String str = sprite.get_imageKey();
        if (str != null) {
            Function2<Canvas, Integer, Boolean> function2 = this.dynamicItem.getDynamicDrawer$com_opensource_svgaplayer().get(str);
            if (function2 != null) {
                Matrix matrixShareFrameMatrix = shareFrameMatrix(sprite.getFrameEntity().getTransform());
                canvas.save();
                canvas.concat(matrixShareFrameMatrix);
                function2.invoke(canvas, Integer.valueOf(frameIndex));
                canvas.restore();
            }
            Function4<Canvas, Integer, Integer, Integer, Boolean> function4 = this.dynamicItem.getDynamicDrawerSized$com_opensource_svgaplayer().get(str);
            if (function4 != null) {
                Matrix matrixShareFrameMatrix2 = shareFrameMatrix(sprite.getFrameEntity().getTransform());
                canvas.save();
                canvas.concat(matrixShareFrameMatrix2);
                function4.invoke(canvas, Integer.valueOf(frameIndex), Integer.valueOf((int) sprite.getFrameEntity().getLayout().getWidth()), Integer.valueOf((int) sprite.getFrameEntity().getLayout().getHeight()));
                canvas.restore();
            }
        }
    }

    private final void drawImage(SGVADrawer.SVGADrawerSprite sprite, Canvas canvas) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        String strSubstring;
        String str = sprite.get_imageKey();
        if (str == null || Intrinsics.areEqual(this.dynamicItem.getDynamicHidden$com_opensource_svgaplayer().get(str), Boolean.TRUE)) {
            return;
        }
        if (StringsKt__StringsJVMKt.endsWith$default(str, ".matte", false, 2, null)) {
            strSubstring = str.substring(0, str.length() - 6);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        } else {
            strSubstring = str;
        }
        Bitmap bitmap = this.dynamicItem.getDynamicImage$com_opensource_svgaplayer().get(strSubstring);
        if (bitmap == null) {
            bitmap = getVideoItem().getImageMap$com_opensource_svgaplayer().get(strSubstring);
        }
        Bitmap bitmap2 = bitmap;
        if (bitmap2 != null) {
            Matrix matrixShareFrameMatrix = shareFrameMatrix(sprite.getFrameEntity().getTransform());
            Paint paintSharedPaint = this.sharedValues.sharedPaint();
            paintSharedPaint.setAntiAlias(getVideoItem().getAntiAlias());
            paintSharedPaint.setFilterBitmap(getVideoItem().getAntiAlias());
            paintSharedPaint.setAlpha((int) (sprite.getFrameEntity().getAlpha() * 255));
            if (sprite.getFrameEntity().getMaskPath() != null) {
                SVGAPathEntity maskPath = sprite.getFrameEntity().getMaskPath();
                if (maskPath == null) {
                    return;
                }
                canvas.save();
                Path pathSharedPath = this.sharedValues.sharedPath();
                maskPath.buildPath(pathSharedPath);
                pathSharedPath.transform(matrixShareFrameMatrix);
                canvas.clipPath(pathSharedPath);
                matrixShareFrameMatrix.preScale((float) (sprite.getFrameEntity().getLayout().getWidth() / bitmap2.getWidth()), (float) (sprite.getFrameEntity().getLayout().getHeight() / bitmap2.getHeight()));
                if (!bitmap2.isRecycled()) {
                    canvas.drawBitmap(bitmap2, matrixShareFrameMatrix, paintSharedPaint);
                }
                canvas.restore();
            } else {
                matrixShareFrameMatrix.preScale((float) (sprite.getFrameEntity().getLayout().getWidth() / bitmap2.getWidth()), (float) (sprite.getFrameEntity().getLayout().getHeight() / bitmap2.getHeight()));
                if (!bitmap2.isRecycled()) {
                    canvas.drawBitmap(bitmap2, matrixShareFrameMatrix, paintSharedPaint);
                }
            }
            IClickAreaListener iClickAreaListener = this.dynamicItem.getDynamicIClickArea$com_opensource_svgaplayer().get(str);
            if (iClickAreaListener != null) {
                float[] fArr = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
                matrixShareFrameMatrix.getValues(fArr);
                iClickAreaListener.onResponseArea(str, (int) fArr[2], (int) fArr[5], (int) ((bitmap2.getWidth() * fArr[0]) + fArr[2]), (int) ((bitmap2.getHeight() * fArr[4]) + fArr[5]));
            }
            drawTextOnBitmap(canvas, bitmap2, sprite, matrixShareFrameMatrix);
        }
    }

    private final void drawShape(SGVADrawer.SVGADrawerSprite sprite, Canvas canvas) throws NumberFormatException {
        float[] lineDash;
        String lineJoin;
        String lineCap;
        int fill;
        Matrix matrixShareFrameMatrix = shareFrameMatrix(sprite.getFrameEntity().getTransform());
        for (SVGAVideoShapeEntity sVGAVideoShapeEntity : sprite.getFrameEntity().getShapes()) {
            sVGAVideoShapeEntity.buildPath();
            if (sVGAVideoShapeEntity.getShapePath() != null) {
                Paint paintSharedPaint = this.sharedValues.sharedPaint();
                paintSharedPaint.reset();
                paintSharedPaint.setAntiAlias(getVideoItem().getAntiAlias());
                double d3 = 255;
                paintSharedPaint.setAlpha((int) (sprite.getFrameEntity().getAlpha() * d3));
                Path pathSharedPath = this.sharedValues.sharedPath();
                pathSharedPath.reset();
                pathSharedPath.addPath(this.pathCache.buildPath(sVGAVideoShapeEntity));
                Matrix matrixSharedMatrix2 = this.sharedValues.sharedMatrix2();
                matrixSharedMatrix2.reset();
                Matrix transform = sVGAVideoShapeEntity.getTransform();
                if (transform != null) {
                    matrixSharedMatrix2.postConcat(transform);
                }
                matrixSharedMatrix2.postConcat(matrixShareFrameMatrix);
                pathSharedPath.transform(matrixSharedMatrix2);
                SVGAVideoShapeEntity.Styles styles = sVGAVideoShapeEntity.getStyles();
                if (styles != null && (fill = styles.getFill()) != 0) {
                    paintSharedPaint.setStyle(Paint.Style.FILL);
                    paintSharedPaint.setColor(fill);
                    int iMin = Math.min(255, Math.max(0, (int) (sprite.getFrameEntity().getAlpha() * d3)));
                    if (iMin != 255) {
                        paintSharedPaint.setAlpha(iMin);
                    }
                    if (sprite.getFrameEntity().getMaskPath() != null) {
                        canvas.save();
                    }
                    SVGAPathEntity maskPath = sprite.getFrameEntity().getMaskPath();
                    if (maskPath != null) {
                        Path pathSharedPath2 = this.sharedValues.sharedPath2();
                        maskPath.buildPath(pathSharedPath2);
                        pathSharedPath2.transform(matrixShareFrameMatrix);
                        canvas.clipPath(pathSharedPath2);
                    }
                    canvas.drawPath(pathSharedPath, paintSharedPaint);
                    if (sprite.getFrameEntity().getMaskPath() != null) {
                        canvas.restore();
                    }
                }
                SVGAVideoShapeEntity.Styles styles2 = sVGAVideoShapeEntity.getStyles();
                if (styles2 != null) {
                    float f2 = 0;
                    if (styles2.getStrokeWidth() > f2) {
                        paintSharedPaint.setAlpha((int) (sprite.getFrameEntity().getAlpha() * d3));
                        paintSharedPaint.setStyle(Paint.Style.STROKE);
                        SVGAVideoShapeEntity.Styles styles3 = sVGAVideoShapeEntity.getStyles();
                        if (styles3 != null) {
                            paintSharedPaint.setColor(styles3.getStroke());
                            int iMin2 = Math.min(255, Math.max(0, (int) (sprite.getFrameEntity().getAlpha() * d3)));
                            if (iMin2 != 255) {
                                paintSharedPaint.setAlpha(iMin2);
                            }
                        }
                        float fMatrixScale = matrixScale(matrixShareFrameMatrix);
                        SVGAVideoShapeEntity.Styles styles4 = sVGAVideoShapeEntity.getStyles();
                        if (styles4 != null) {
                            paintSharedPaint.setStrokeWidth(styles4.getStrokeWidth() * fMatrixScale);
                        }
                        SVGAVideoShapeEntity.Styles styles5 = sVGAVideoShapeEntity.getStyles();
                        if (styles5 != null && (lineCap = styles5.getLineCap()) != null) {
                            if (StringsKt__StringsJVMKt.equals(lineCap, "butt", true)) {
                                paintSharedPaint.setStrokeCap(Paint.Cap.BUTT);
                            } else if (StringsKt__StringsJVMKt.equals(lineCap, "round", true)) {
                                paintSharedPaint.setStrokeCap(Paint.Cap.ROUND);
                            } else if (StringsKt__StringsJVMKt.equals(lineCap, "square", true)) {
                                paintSharedPaint.setStrokeCap(Paint.Cap.SQUARE);
                            }
                        }
                        SVGAVideoShapeEntity.Styles styles6 = sVGAVideoShapeEntity.getStyles();
                        if (styles6 != null && (lineJoin = styles6.getLineJoin()) != null) {
                            if (StringsKt__StringsJVMKt.equals(lineJoin, "miter", true)) {
                                paintSharedPaint.setStrokeJoin(Paint.Join.MITER);
                            } else if (StringsKt__StringsJVMKt.equals(lineJoin, "round", true)) {
                                paintSharedPaint.setStrokeJoin(Paint.Join.ROUND);
                            } else if (StringsKt__StringsJVMKt.equals(lineJoin, "bevel", true)) {
                                paintSharedPaint.setStrokeJoin(Paint.Join.BEVEL);
                            }
                        }
                        if (sVGAVideoShapeEntity.getStyles() != null) {
                            paintSharedPaint.setStrokeMiter(r8.getMiterLimit() * fMatrixScale);
                        }
                        SVGAVideoShapeEntity.Styles styles7 = sVGAVideoShapeEntity.getStyles();
                        if (styles7 != null && (lineDash = styles7.getLineDash()) != null && lineDash.length == 3 && (lineDash[0] > f2 || lineDash[1] > f2)) {
                            float[] fArr = new float[2];
                            float f3 = lineDash[0];
                            if (f3 < 1.0f) {
                                f3 = 1.0f;
                            }
                            fArr[0] = f3 * fMatrixScale;
                            float f4 = lineDash[1];
                            if (f4 < 0.1f) {
                                f4 = 0.1f;
                            }
                            fArr[1] = f4 * fMatrixScale;
                            paintSharedPaint.setPathEffect(new DashPathEffect(fArr, lineDash[2] * fMatrixScale));
                        }
                        if (sprite.getFrameEntity().getMaskPath() != null) {
                            canvas.save();
                        }
                        SVGAPathEntity maskPath2 = sprite.getFrameEntity().getMaskPath();
                        if (maskPath2 != null) {
                            Path pathSharedPath22 = this.sharedValues.sharedPath2();
                            maskPath2.buildPath(pathSharedPath22);
                            pathSharedPath22.transform(matrixShareFrameMatrix);
                            canvas.clipPath(pathSharedPath22);
                        }
                        canvas.drawPath(pathSharedPath, paintSharedPaint);
                        if (sprite.getFrameEntity().getMaskPath() != null) {
                            canvas.restore();
                        }
                    }
                }
            }
        }
    }

    private final void drawSprite(SGVADrawer.SVGADrawerSprite sprite, Canvas canvas, int frameIndex) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        drawImage(sprite, canvas);
        drawShape(sprite, canvas);
        drawDynamic(sprite, canvas, frameIndex);
    }

    private final void drawTextOnBitmap(Canvas canvas, Bitmap drawingBitmap, SGVADrawer.SVGADrawerSprite sprite, Matrix frameMatrix) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        int i2;
        TextPaint drawingTextPaint;
        if (this.dynamicItem.getIsTextDirty()) {
            this.drawTextCache.clear();
            this.dynamicItem.setTextDirty$com_opensource_svgaplayer(false);
        }
        String str = sprite.get_imageKey();
        if (str != null) {
            String str2 = this.dynamicItem.getDynamicText$com_opensource_svgaplayer().get(str);
            Bitmap bitmapCreateBitmap = null;
            if (str2 != null && (drawingTextPaint = this.dynamicItem.getDynamicTextPaint$com_opensource_svgaplayer().get(str)) != null && (bitmapCreateBitmap = this.drawTextCache.get(str)) == null) {
                bitmapCreateBitmap = Bitmap.createBitmap(drawingBitmap.getWidth(), drawingBitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Rect rect = new Rect(0, 0, drawingBitmap.getWidth(), drawingBitmap.getHeight());
                Canvas canvas2 = new Canvas(bitmapCreateBitmap);
                Intrinsics.checkExpressionValueIsNotNull(drawingTextPaint, "drawingTextPaint");
                drawingTextPaint.setAntiAlias(true);
                Paint.FontMetrics fontMetrics = drawingTextPaint.getFontMetrics();
                float f2 = 2;
                canvas2.drawText(str2, rect.centerX(), (rect.centerY() - (fontMetrics.top / f2)) - (fontMetrics.bottom / f2), drawingTextPaint);
                HashMap<String, Bitmap> map = this.drawTextCache;
                if (bitmapCreateBitmap == null) {
                    throw new TypeCastException("null cannot be cast to non-null type android.graphics.Bitmap");
                }
                map.put(str, bitmapCreateBitmap);
            }
            BoringLayout it = this.dynamicItem.getDynamicBoringLayoutText$com_opensource_svgaplayer().get(str);
            if (it != null && (bitmapCreateBitmap = this.drawTextCache.get(str)) == null) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                TextPaint paint = it.getPaint();
                Intrinsics.checkExpressionValueIsNotNull(paint, "it.paint");
                paint.setAntiAlias(true);
                bitmapCreateBitmap = Bitmap.createBitmap(drawingBitmap.getWidth(), drawingBitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas3 = new Canvas(bitmapCreateBitmap);
                canvas3.translate(0.0f, (drawingBitmap.getHeight() - it.getHeight()) / 2);
                it.draw(canvas3);
                HashMap<String, Bitmap> map2 = this.drawTextCache;
                if (bitmapCreateBitmap == null) {
                    throw new TypeCastException("null cannot be cast to non-null type android.graphics.Bitmap");
                }
                map2.put(str, bitmapCreateBitmap);
            }
            StaticLayout it2 = this.dynamicItem.getDynamicStaticLayoutText$com_opensource_svgaplayer().get(str);
            if (it2 != null && (bitmapCreateBitmap = this.drawTextCache.get(str)) == null) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                TextPaint paint2 = it2.getPaint();
                Intrinsics.checkExpressionValueIsNotNull(paint2, "it.paint");
                paint2.setAntiAlias(true);
                try {
                    Field field = StaticLayout.class.getDeclaredField("mMaximumVisibleLineCount");
                    Intrinsics.checkExpressionValueIsNotNull(field, "field");
                    field.setAccessible(true);
                    i2 = field.getInt(it2);
                } catch (Exception unused) {
                    i2 = Integer.MAX_VALUE;
                }
                StaticLayout layout = StaticLayout.Builder.obtain(it2.getText(), 0, it2.getText().length(), it2.getPaint(), drawingBitmap.getWidth()).setAlignment(it2.getAlignment()).setMaxLines(i2).setEllipsize(TextUtils.TruncateAt.END).build();
                Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(drawingBitmap.getWidth(), drawingBitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas4 = new Canvas(bitmapCreateBitmap2);
                int height = drawingBitmap.getHeight();
                Intrinsics.checkExpressionValueIsNotNull(layout, "layout");
                canvas4.translate(0.0f, (height - layout.getHeight()) / 2);
                layout.draw(canvas4);
                HashMap<String, Bitmap> map3 = this.drawTextCache;
                if (bitmapCreateBitmap2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type android.graphics.Bitmap");
                }
                map3.put(str, bitmapCreateBitmap2);
                bitmapCreateBitmap = bitmapCreateBitmap2;
            }
            if (bitmapCreateBitmap != null) {
                Paint paintSharedPaint = this.sharedValues.sharedPaint();
                paintSharedPaint.setAntiAlias(getVideoItem().getAntiAlias());
                paintSharedPaint.setAlpha((int) (sprite.getFrameEntity().getAlpha() * 255));
                if (sprite.getFrameEntity().getMaskPath() == null) {
                    paintSharedPaint.setFilterBitmap(getVideoItem().getAntiAlias());
                    canvas.drawBitmap(bitmapCreateBitmap, frameMatrix, paintSharedPaint);
                    return;
                }
                SVGAPathEntity maskPath = sprite.getFrameEntity().getMaskPath();
                if (maskPath != null) {
                    canvas.save();
                    canvas.concat(frameMatrix);
                    canvas.clipRect(0, 0, drawingBitmap.getWidth(), drawingBitmap.getHeight());
                    Shader.TileMode tileMode = Shader.TileMode.REPEAT;
                    paintSharedPaint.setShader(new BitmapShader(bitmapCreateBitmap, tileMode, tileMode));
                    Path pathSharedPath = this.sharedValues.sharedPath();
                    maskPath.buildPath(pathSharedPath);
                    canvas.drawPath(pathSharedPath, paintSharedPaint);
                    canvas.restore();
                }
            }
        }
    }

    private final boolean isMatteBegin(int spriteIndex, List<SGVADrawer.SVGADrawerSprite> sprites) {
        Boolean bool;
        String str;
        SGVADrawer.SVGADrawerSprite sVGADrawerSprite;
        if (this.beginIndexList == null) {
            int size = sprites.size();
            Boolean[] boolArr = new Boolean[size];
            for (int i2 = 0; i2 < size; i2++) {
                boolArr[i2] = Boolean.FALSE;
            }
            int i3 = 0;
            for (Object obj : sprites) {
                int i4 = i3 + 1;
                if (i3 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                SGVADrawer.SVGADrawerSprite sVGADrawerSprite2 = (SGVADrawer.SVGADrawerSprite) obj;
                String str2 = sVGADrawerSprite2.get_imageKey();
                if ((str2 == null || !StringsKt__StringsJVMKt.endsWith$default(str2, ".matte", false, 2, null)) && (str = sVGADrawerSprite2.get_matteKey()) != null && str.length() > 0 && (sVGADrawerSprite = sprites.get(i3 - 1)) != null) {
                    String str3 = sVGADrawerSprite.get_matteKey();
                    if (str3 == null || str3.length() == 0) {
                        boolArr[i3] = Boolean.TRUE;
                    } else if (!Intrinsics.areEqual(sVGADrawerSprite.get_matteKey(), sVGADrawerSprite2.get_matteKey())) {
                        boolArr[i3] = Boolean.TRUE;
                    }
                }
                i3 = i4;
            }
            this.beginIndexList = boolArr;
        }
        Boolean[] boolArr2 = this.beginIndexList;
        if (boolArr2 == null || (bool = boolArr2[spriteIndex]) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    private final boolean isMatteEnd(int spriteIndex, List<SGVADrawer.SVGADrawerSprite> sprites) {
        Boolean bool;
        String str;
        if (this.endIndexList == null) {
            List<SGVADrawer.SVGADrawerSprite> list = sprites;
            int size = list.size();
            Boolean[] boolArr = new Boolean[size];
            for (int i2 = 0; i2 < size; i2++) {
                boolArr[i2] = Boolean.FALSE;
            }
            int i3 = 0;
            for (Object obj : sprites) {
                int i4 = i3 + 1;
                if (i3 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                SGVADrawer.SVGADrawerSprite sVGADrawerSprite = (SGVADrawer.SVGADrawerSprite) obj;
                String str2 = sVGADrawerSprite.get_imageKey();
                if ((str2 == null || !StringsKt__StringsJVMKt.endsWith$default(str2, ".matte", false, 2, null)) && (str = sVGADrawerSprite.get_matteKey()) != null && str.length() > 0) {
                    if (i3 == list.size() - 1) {
                        boolArr[i3] = Boolean.TRUE;
                    } else {
                        SGVADrawer.SVGADrawerSprite sVGADrawerSprite2 = sprites.get(i4);
                        if (sVGADrawerSprite2 != null) {
                            String str3 = sVGADrawerSprite2.get_matteKey();
                            if (str3 == null || str3.length() == 0) {
                                boolArr[i3] = Boolean.TRUE;
                            } else if (!Intrinsics.areEqual(sVGADrawerSprite2.get_matteKey(), sVGADrawerSprite.get_matteKey())) {
                                boolArr[i3] = Boolean.TRUE;
                            }
                        }
                    }
                }
                i3 = i4;
            }
            this.endIndexList = boolArr;
        }
        Boolean[] boolArr2 = this.endIndexList;
        if (boolArr2 == null || (bool = boolArr2[spriteIndex]) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    private final float matrixScale(Matrix matrix) {
        matrix.getValues(this.matrixScaleTempValues);
        float[] fArr = this.matrixScaleTempValues;
        float f2 = fArr[0];
        if (f2 == 0.0f) {
            return 0.0f;
        }
        double d3 = f2;
        double d4 = fArr[3];
        double d5 = fArr[1];
        double d6 = fArr[4];
        if (d3 * d6 == d4 * d5) {
            return 0.0f;
        }
        double dSqrt = Math.sqrt((d3 * d3) + (d4 * d4));
        double d7 = d3 / dSqrt;
        double d8 = d4 / dSqrt;
        double d9 = (d7 * d5) + (d8 * d6);
        double d10 = d5 - (d7 * d9);
        double d11 = d6 - (d9 * d8);
        double dSqrt2 = Math.sqrt((d10 * d10) + (d11 * d11));
        if (d7 * (d11 / dSqrt2) < d8 * (d10 / dSqrt2)) {
            dSqrt = -dSqrt;
        }
        return Math.abs(getScaleInfo().getRatioX() ? (float) dSqrt : (float) dSqrt2);
    }

    private final void playAudio(int frameIndex) {
        Integer soundID;
        for (SVGAAudioEntity sVGAAudioEntity : getVideoItem().getAudioList$com_opensource_svgaplayer()) {
            if (sVGAAudioEntity.getStartFrame() == frameIndex) {
                SVGASoundManager sVGASoundManager = SVGASoundManager.INSTANCE;
                if (sVGASoundManager.isInit$com_opensource_svgaplayer()) {
                    Integer soundID2 = sVGAAudioEntity.getSoundID();
                    if (soundID2 != null) {
                        sVGAAudioEntity.setPlayID(Integer.valueOf(sVGASoundManager.play$com_opensource_svgaplayer(soundID2.intValue())));
                    }
                } else {
                    SoundPool soundPool = getVideoItem().getSoundPool();
                    if (soundPool != null && (soundID = sVGAAudioEntity.getSoundID()) != null) {
                        sVGAAudioEntity.setPlayID(Integer.valueOf(soundPool.play(soundID.intValue(), 1.0f, 1.0f, 1, 0, 1.0f)));
                    }
                }
            }
            if (sVGAAudioEntity.getEndFrame() <= frameIndex) {
                Integer playID = sVGAAudioEntity.getPlayID();
                if (playID != null) {
                    int iIntValue = playID.intValue();
                    SVGASoundManager sVGASoundManager2 = SVGASoundManager.INSTANCE;
                    if (sVGASoundManager2.isInit$com_opensource_svgaplayer()) {
                        sVGASoundManager2.stop$com_opensource_svgaplayer(iIntValue);
                    } else {
                        SoundPool soundPool2 = getVideoItem().getSoundPool();
                        if (soundPool2 != null) {
                            soundPool2.stop(iIntValue);
                        }
                    }
                }
                sVGAAudioEntity.setPlayID(null);
            }
        }
    }

    private final Matrix shareFrameMatrix(Matrix transform) {
        Matrix matrixSharedMatrix = this.sharedValues.sharedMatrix();
        matrixSharedMatrix.postScale(getScaleInfo().getScaleFx(), getScaleInfo().getScaleFy());
        matrixSharedMatrix.postTranslate(getScaleInfo().getTranFx(), getScaleInfo().getTranFy());
        matrixSharedMatrix.preConcat(transform);
        return matrixSharedMatrix;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a6  */
    @Override // com.opensource.svgaplayer.drawer.SGVADrawer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawFrame(@org.jetbrains.annotations.NotNull android.graphics.Canvas r21, int r22, @org.jetbrains.annotations.NotNull android.widget.ImageView.ScaleType r23) {
        /*
            Method dump skipped, instructions count: 243
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.opensource.svgaplayer.drawer.SVGACanvasDrawer.drawFrame(android.graphics.Canvas, int, android.widget.ImageView$ScaleType):void");
    }

    @NotNull
    public final SVGADynamicEntity getDynamicItem() {
        return this.dynamicItem;
    }
}
