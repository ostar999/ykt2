package com.davemorrissey.labs.subscaleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import com.davemorrissey.labs.subscaleview.decoder.CompatDecoderFactory;
import com.davemorrissey.labs.subscaleview.decoder.DecoderFactory;
import com.davemorrissey.labs.subscaleview.decoder.ImageDecoder;
import com.davemorrissey.labs.subscaleview.decoder.ImageRegionDecoder;
import com.davemorrissey.labs.subscaleview.decoder.SkiaImageDecoder;
import com.davemorrissey.labs.subscaleview.decoder.SkiaImageRegionDecoder;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes3.dex */
public class SubsamplingScaleImageView extends View {
    public static final int EASE_IN_OUT_QUAD = 2;
    public static final int EASE_OUT_QUAD = 1;
    private static final int MESSAGE_LONG_CLICK = 1;
    public static final int ORIENTATION_0 = 0;
    public static final int ORIENTATION_180 = 180;
    public static final int ORIENTATION_270 = 270;
    public static final int ORIENTATION_90 = 90;
    public static final int ORIENTATION_USE_EXIF = -1;
    public static final int ORIGIN_ANIM = 1;
    public static final int ORIGIN_DOUBLE_TAP_ZOOM = 4;
    public static final int ORIGIN_FLING = 3;
    public static final int ORIGIN_TOUCH = 2;
    public static final int PAN_LIMIT_CENTER = 3;
    public static final int PAN_LIMIT_INSIDE = 1;
    public static final int PAN_LIMIT_OUTSIDE = 2;
    public static final int SCALE_TYPE_CENTER_CROP = 2;
    public static final int SCALE_TYPE_CENTER_INSIDE = 1;
    public static final int SCALE_TYPE_CUSTOM = 3;
    public static final int SCALE_TYPE_START = 4;
    private static final String TAG = "SubsamplingScaleImageView";
    public static final int TILE_SIZE_AUTO = Integer.MAX_VALUE;
    public static final int ZOOM_FOCUS_CENTER = 2;
    public static final int ZOOM_FOCUS_CENTER_IMMEDIATE = 3;
    public static final int ZOOM_FOCUS_FIXED = 1;
    private static Bitmap.Config preferredBitmapConfig;
    private Anim anim;
    private Bitmap bitmap;
    private DecoderFactory<? extends ImageDecoder> bitmapDecoderFactory;
    private boolean bitmapIsCached;
    private boolean bitmapIsPreview;
    private Paint bitmapPaint;
    private boolean debug;
    private Paint debugLinePaint;
    private Paint debugTextPaint;
    private ImageRegionDecoder decoder;
    private final ReadWriteLock decoderLock;
    private final float density;
    private GestureDetector detector;
    private int doubleTapZoomDuration;
    private float doubleTapZoomScale;
    private int doubleTapZoomStyle;
    private final float[] dstArray;
    private boolean eagerLoadingEnabled;
    private Executor executor;
    private int fullImageSampleSize;
    private final Handler handler;
    private boolean imageLoadedSent;
    private boolean isPanning;
    private boolean isQuickScaling;
    private boolean isZooming;
    private Matrix matrix;
    private float maxScale;
    private int maxTileHeight;
    private int maxTileWidth;
    private int maxTouchCount;
    private float minScale;
    private int minimumScaleType;
    private int minimumTileDpi;
    private OnImageEventListener onImageEventListener;
    private View.OnLongClickListener onLongClickListener;
    private OnStateChangedListener onStateChangedListener;
    private int orientation;
    private Rect pRegion;
    private boolean panEnabled;
    private int panLimit;
    private Float pendingScale;
    private boolean quickScaleEnabled;
    private float quickScaleLastDistance;
    private boolean quickScaleMoved;
    private PointF quickScaleSCenter;
    private final float quickScaleThreshold;
    private PointF quickScaleVLastPoint;
    private PointF quickScaleVStart;
    private boolean readySent;
    private DecoderFactory<? extends ImageRegionDecoder> regionDecoderFactory;
    private int sHeight;
    private int sOrientation;
    private PointF sPendingCenter;
    private RectF sRect;
    private Rect sRegion;
    private PointF sRequestedCenter;
    private int sWidth;
    private ScaleAndTranslate satTemp;
    private float scale;
    private float scaleStart;
    private GestureDetector singleDetector;
    private final float[] srcArray;
    private Paint tileBgPaint;
    private Map<Integer, List<Tile>> tileMap;
    private Uri uri;
    private PointF vCenterStart;
    private float vDistStart;
    private PointF vTranslate;
    private PointF vTranslateBefore;
    private PointF vTranslateStart;
    private boolean zoomEnabled;
    private static final List<Integer> VALID_ORIENTATIONS = Arrays.asList(0, 90, 180, 270, -1);
    private static final List<Integer> VALID_ZOOM_STYLES = Arrays.asList(1, 2, 3);
    private static final List<Integer> VALID_EASING_STYLES = Arrays.asList(2, 1);
    private static final List<Integer> VALID_PAN_LIMITS = Arrays.asList(1, 2, 3);
    private static final List<Integer> VALID_SCALE_TYPES = Arrays.asList(2, 1, 3, 4);

    public static class Anim {
        private long duration;
        private int easing;
        private boolean interruptible;
        private OnAnimationEventListener listener;
        private int origin;
        private PointF sCenterEnd;
        private PointF sCenterEndRequested;
        private PointF sCenterStart;
        private float scaleEnd;
        private float scaleStart;
        private long time;
        private PointF vFocusEnd;
        private PointF vFocusStart;

        private Anim() {
            this.duration = 500L;
            this.interruptible = true;
            this.easing = 2;
            this.origin = 1;
            this.time = System.currentTimeMillis();
        }
    }

    public static class BitmapLoadTask extends AsyncTask<Void, Void, Integer> {
        private Bitmap bitmap;
        private final WeakReference<Context> contextRef;
        private final WeakReference<DecoderFactory<? extends ImageDecoder>> decoderFactoryRef;
        private Exception exception;
        private final boolean preview;
        private final Uri source;
        private final WeakReference<SubsamplingScaleImageView> viewRef;

        public BitmapLoadTask(SubsamplingScaleImageView subsamplingScaleImageView, Context context, DecoderFactory<? extends ImageDecoder> decoderFactory, Uri uri, boolean z2) {
            this.viewRef = new WeakReference<>(subsamplingScaleImageView);
            this.contextRef = new WeakReference<>(context);
            this.decoderFactoryRef = new WeakReference<>(decoderFactory);
            this.source = uri;
            this.preview = z2;
        }

        @Override // android.os.AsyncTask
        public Integer doInBackground(Void... voidArr) {
            try {
                String string = this.source.toString();
                Context context = this.contextRef.get();
                DecoderFactory<? extends ImageDecoder> decoderFactory = this.decoderFactoryRef.get();
                SubsamplingScaleImageView subsamplingScaleImageView = this.viewRef.get();
                if (context == null || decoderFactory == null || subsamplingScaleImageView == null) {
                    return null;
                }
                subsamplingScaleImageView.debug("BitmapLoadTask.doInBackground", new Object[0]);
                this.bitmap = decoderFactory.make().decode(context, this.source);
                return Integer.valueOf(subsamplingScaleImageView.getExifOrientation(context, string));
            } catch (Exception e2) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to load bitmap", e2);
                this.exception = e2;
                return null;
            } catch (OutOfMemoryError e3) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to load bitmap - OutOfMemoryError", e3);
                this.exception = new RuntimeException(e3);
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Integer num) {
            SubsamplingScaleImageView subsamplingScaleImageView = this.viewRef.get();
            if (subsamplingScaleImageView != null) {
                Bitmap bitmap = this.bitmap;
                if (bitmap != null && num != null) {
                    if (this.preview) {
                        subsamplingScaleImageView.onPreviewLoaded(bitmap);
                        return;
                    } else {
                        subsamplingScaleImageView.onImageLoaded(bitmap, num.intValue(), false);
                        return;
                    }
                }
                if (this.exception == null || subsamplingScaleImageView.onImageEventListener == null) {
                    return;
                }
                if (this.preview) {
                    subsamplingScaleImageView.onImageEventListener.onPreviewLoadError(this.exception);
                } else {
                    subsamplingScaleImageView.onImageEventListener.onImageLoadError(this.exception);
                }
            }
        }
    }

    public static class DefaultOnAnimationEventListener implements OnAnimationEventListener {
        @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnAnimationEventListener
        public void onComplete() {
        }

        @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnAnimationEventListener
        public void onInterruptedByNewAnim() {
        }

        @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnAnimationEventListener
        public void onInterruptedByUser() {
        }
    }

    public static class DefaultOnImageEventListener implements OnImageEventListener {
        @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
        public void onImageLoadError(Exception exc) {
        }

        @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
        public void onImageLoaded() {
        }

        @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
        public void onPreviewLoadError(Exception exc) {
        }

        @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
        public void onPreviewReleased() {
        }

        @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
        public void onReady() {
        }

        @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnImageEventListener
        public void onTileLoadError(Exception exc) {
        }
    }

    public static class DefaultOnStateChangedListener implements OnStateChangedListener {
        @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnStateChangedListener
        public void onCenterChanged(PointF pointF, int i2) {
        }

        @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnStateChangedListener
        public void onScaleChanged(float f2, int i2) {
        }
    }

    public interface OnAnimationEventListener {
        void onComplete();

        void onInterruptedByNewAnim();

        void onInterruptedByUser();
    }

    public interface OnImageEventListener {
        void onImageLoadError(Exception exc);

        void onImageLoaded();

        void onPreviewLoadError(Exception exc);

        void onPreviewReleased();

        void onReady();

        void onTileLoadError(Exception exc);
    }

    public interface OnStateChangedListener {
        void onCenterChanged(PointF pointF, int i2);

        void onScaleChanged(float f2, int i2);
    }

    public static class ScaleAndTranslate {
        private float scale;
        private final PointF vTranslate;

        private ScaleAndTranslate(float f2, PointF pointF) {
            this.scale = f2;
            this.vTranslate = pointF;
        }
    }

    public static class Tile {
        private Bitmap bitmap;
        private Rect fileSRect;
        private boolean loading;
        private Rect sRect;
        private int sampleSize;
        private Rect vRect;
        private boolean visible;

        private Tile() {
        }
    }

    public static class TileLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private final WeakReference<ImageRegionDecoder> decoderRef;
        private Exception exception;
        private final WeakReference<Tile> tileRef;
        private final WeakReference<SubsamplingScaleImageView> viewRef;

        public TileLoadTask(SubsamplingScaleImageView subsamplingScaleImageView, ImageRegionDecoder imageRegionDecoder, Tile tile) {
            this.viewRef = new WeakReference<>(subsamplingScaleImageView);
            this.decoderRef = new WeakReference<>(imageRegionDecoder);
            this.tileRef = new WeakReference<>(tile);
            tile.loading = true;
        }

        @Override // android.os.AsyncTask
        public Bitmap doInBackground(Void... voidArr) {
            try {
                SubsamplingScaleImageView subsamplingScaleImageView = this.viewRef.get();
                ImageRegionDecoder imageRegionDecoder = this.decoderRef.get();
                Tile tile = this.tileRef.get();
                if (imageRegionDecoder == null || tile == null || subsamplingScaleImageView == null || !imageRegionDecoder.isReady() || !tile.visible) {
                    if (tile == null) {
                        return null;
                    }
                    tile.loading = false;
                    return null;
                }
                subsamplingScaleImageView.debug("TileLoadTask.doInBackground, tile.sRect=%s, tile.sampleSize=%d", tile.sRect, Integer.valueOf(tile.sampleSize));
                subsamplingScaleImageView.decoderLock.readLock().lock();
                try {
                    if (!imageRegionDecoder.isReady()) {
                        tile.loading = false;
                        subsamplingScaleImageView.decoderLock.readLock().unlock();
                        return null;
                    }
                    subsamplingScaleImageView.fileSRect(tile.sRect, tile.fileSRect);
                    if (subsamplingScaleImageView.sRegion != null) {
                        tile.fileSRect.offset(subsamplingScaleImageView.sRegion.left, subsamplingScaleImageView.sRegion.top);
                    }
                    return imageRegionDecoder.decodeRegion(tile.fileSRect, tile.sampleSize);
                } finally {
                    subsamplingScaleImageView.decoderLock.readLock().unlock();
                }
            } catch (Exception e2) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to decode tile", e2);
                this.exception = e2;
                return null;
            } catch (OutOfMemoryError e3) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to decode tile - OutOfMemoryError", e3);
                this.exception = new RuntimeException(e3);
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Bitmap bitmap) {
            SubsamplingScaleImageView subsamplingScaleImageView = this.viewRef.get();
            Tile tile = this.tileRef.get();
            if (subsamplingScaleImageView == null || tile == null) {
                return;
            }
            if (bitmap != null) {
                tile.bitmap = bitmap;
                tile.loading = false;
                subsamplingScaleImageView.onTileLoaded();
            } else {
                if (this.exception == null || subsamplingScaleImageView.onImageEventListener == null) {
                    return;
                }
                subsamplingScaleImageView.onImageEventListener.onTileLoadError(this.exception);
            }
        }
    }

    public static class TilesInitTask extends AsyncTask<Void, Void, int[]> {
        private final WeakReference<Context> contextRef;
        private ImageRegionDecoder decoder;
        private final WeakReference<DecoderFactory<? extends ImageRegionDecoder>> decoderFactoryRef;
        private Exception exception;
        private final Uri source;
        private final WeakReference<SubsamplingScaleImageView> viewRef;

        public TilesInitTask(SubsamplingScaleImageView subsamplingScaleImageView, Context context, DecoderFactory<? extends ImageRegionDecoder> decoderFactory, Uri uri) {
            this.viewRef = new WeakReference<>(subsamplingScaleImageView);
            this.contextRef = new WeakReference<>(context);
            this.decoderFactoryRef = new WeakReference<>(decoderFactory);
            this.source = uri;
        }

        @Override // android.os.AsyncTask
        public int[] doInBackground(Void... voidArr) {
            try {
                String string = this.source.toString();
                Context context = this.contextRef.get();
                DecoderFactory<? extends ImageRegionDecoder> decoderFactory = this.decoderFactoryRef.get();
                SubsamplingScaleImageView subsamplingScaleImageView = this.viewRef.get();
                if (context == null || decoderFactory == null || subsamplingScaleImageView == null) {
                    return null;
                }
                subsamplingScaleImageView.debug("TilesInitTask.doInBackground", new Object[0]);
                ImageRegionDecoder imageRegionDecoderMake = decoderFactory.make();
                this.decoder = imageRegionDecoderMake;
                Point pointInit = imageRegionDecoderMake.init(context, this.source);
                int iWidth = pointInit.x;
                int iHeight = pointInit.y;
                int exifOrientation = subsamplingScaleImageView.getExifOrientation(context, string);
                if (subsamplingScaleImageView.sRegion != null) {
                    subsamplingScaleImageView.sRegion.left = Math.max(0, subsamplingScaleImageView.sRegion.left);
                    subsamplingScaleImageView.sRegion.top = Math.max(0, subsamplingScaleImageView.sRegion.top);
                    subsamplingScaleImageView.sRegion.right = Math.min(iWidth, subsamplingScaleImageView.sRegion.right);
                    subsamplingScaleImageView.sRegion.bottom = Math.min(iHeight, subsamplingScaleImageView.sRegion.bottom);
                    iWidth = subsamplingScaleImageView.sRegion.width();
                    iHeight = subsamplingScaleImageView.sRegion.height();
                }
                return new int[]{iWidth, iHeight, exifOrientation};
            } catch (Exception e2) {
                Log.e(SubsamplingScaleImageView.TAG, "Failed to initialise bitmap decoder", e2);
                this.exception = e2;
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(int[] iArr) {
            SubsamplingScaleImageView subsamplingScaleImageView = this.viewRef.get();
            if (subsamplingScaleImageView != null) {
                ImageRegionDecoder imageRegionDecoder = this.decoder;
                if (imageRegionDecoder != null && iArr != null && iArr.length == 3) {
                    subsamplingScaleImageView.onTilesInited(imageRegionDecoder, iArr[0], iArr[1], iArr[2]);
                } else {
                    if (this.exception == null || subsamplingScaleImageView.onImageEventListener == null) {
                        return;
                    }
                    subsamplingScaleImageView.onImageEventListener.onImageLoadError(this.exception);
                }
            }
        }
    }

    public SubsamplingScaleImageView(Context context, AttributeSet attributeSet) {
        int resourceId;
        String string;
        super(context, attributeSet);
        this.orientation = 0;
        this.maxScale = 2.0f;
        this.minScale = minScale();
        this.minimumTileDpi = -1;
        this.panLimit = 1;
        this.minimumScaleType = 1;
        this.maxTileWidth = Integer.MAX_VALUE;
        this.maxTileHeight = Integer.MAX_VALUE;
        this.executor = AsyncTask.THREAD_POOL_EXECUTOR;
        this.eagerLoadingEnabled = true;
        this.panEnabled = true;
        this.zoomEnabled = true;
        this.quickScaleEnabled = true;
        this.doubleTapZoomScale = 1.0f;
        this.doubleTapZoomStyle = 1;
        this.doubleTapZoomDuration = 500;
        this.decoderLock = new ReentrantReadWriteLock(true);
        this.bitmapDecoderFactory = new CompatDecoderFactory(SkiaImageDecoder.class);
        this.regionDecoderFactory = new CompatDecoderFactory(SkiaImageRegionDecoder.class);
        this.srcArray = new float[8];
        this.dstArray = new float[8];
        this.density = getResources().getDisplayMetrics().density;
        setMinimumDpi(160);
        setDoubleTapZoomDpi(160);
        setMinimumTileDpi(320);
        setGestureDetector(context);
        this.handler = new Handler(new Handler.Callback() { // from class: com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message.what == 1 && SubsamplingScaleImageView.this.onLongClickListener != null) {
                    SubsamplingScaleImageView.this.maxTouchCount = 0;
                    SubsamplingScaleImageView subsamplingScaleImageView = SubsamplingScaleImageView.this;
                    SubsamplingScaleImageView.super.setOnLongClickListener(subsamplingScaleImageView.onLongClickListener);
                    SubsamplingScaleImageView.this.performLongClick();
                    SubsamplingScaleImageView.super.setOnLongClickListener(null);
                }
                return true;
            }
        });
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.SubsamplingScaleImageView);
            int i2 = R.styleable.SubsamplingScaleImageView_assetName;
            if (typedArrayObtainStyledAttributes.hasValue(i2) && (string = typedArrayObtainStyledAttributes.getString(i2)) != null && string.length() > 0) {
                setImage(ImageSource.asset(string).tilingEnabled());
            }
            int i3 = R.styleable.SubsamplingScaleImageView_src;
            if (typedArrayObtainStyledAttributes.hasValue(i3) && (resourceId = typedArrayObtainStyledAttributes.getResourceId(i3, 0)) > 0) {
                setImage(ImageSource.resource(resourceId).tilingEnabled());
            }
            int i4 = R.styleable.SubsamplingScaleImageView_panEnabled;
            if (typedArrayObtainStyledAttributes.hasValue(i4)) {
                setPanEnabled(typedArrayObtainStyledAttributes.getBoolean(i4, true));
            }
            int i5 = R.styleable.SubsamplingScaleImageView_zoomEnabled;
            if (typedArrayObtainStyledAttributes.hasValue(i5)) {
                setZoomEnabled(typedArrayObtainStyledAttributes.getBoolean(i5, true));
            }
            int i6 = R.styleable.SubsamplingScaleImageView_quickScaleEnabled;
            if (typedArrayObtainStyledAttributes.hasValue(i6)) {
                setQuickScaleEnabled(typedArrayObtainStyledAttributes.getBoolean(i6, true));
            }
            int i7 = R.styleable.SubsamplingScaleImageView_tileBackgroundColor;
            if (typedArrayObtainStyledAttributes.hasValue(i7)) {
                setTileBackgroundColor(typedArrayObtainStyledAttributes.getColor(i7, Color.argb(0, 0, 0, 0)));
            }
            typedArrayObtainStyledAttributes.recycle();
        }
        this.quickScaleThreshold = TypedValue.applyDimension(1, 20.0f, context.getResources().getDisplayMetrics());
    }

    private int calculateInSampleSize(float f2) {
        int iRound;
        if (this.minimumTileDpi > 0) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            f2 *= this.minimumTileDpi / ((displayMetrics.xdpi + displayMetrics.ydpi) / 2.0f);
        }
        int iSWidth = (int) (sWidth() * f2);
        int iSHeight = (int) (sHeight() * f2);
        if (iSWidth == 0 || iSHeight == 0) {
            return 32;
        }
        int i2 = 1;
        if (sHeight() > iSHeight || sWidth() > iSWidth) {
            iRound = Math.round(sHeight() / iSHeight);
            int iRound2 = Math.round(sWidth() / iSWidth);
            if (iRound >= iRound2) {
                iRound = iRound2;
            }
        } else {
            iRound = 1;
        }
        while (true) {
            int i3 = i2 * 2;
            if (i3 >= iRound) {
                return i2;
            }
            i2 = i3;
        }
    }

    private boolean checkImageLoaded() {
        boolean zIsBaseLayerReady = isBaseLayerReady();
        if (!this.imageLoadedSent && zIsBaseLayerReady) {
            preDraw();
            this.imageLoadedSent = true;
            onImageLoaded();
            OnImageEventListener onImageEventListener = this.onImageEventListener;
            if (onImageEventListener != null) {
                onImageEventListener.onImageLoaded();
            }
        }
        return zIsBaseLayerReady;
    }

    private boolean checkReady() {
        boolean z2 = getWidth() > 0 && getHeight() > 0 && this.sWidth > 0 && this.sHeight > 0 && (this.bitmap != null || isBaseLayerReady());
        if (!this.readySent && z2) {
            preDraw();
            this.readySent = true;
            onReady();
            OnImageEventListener onImageEventListener = this.onImageEventListener;
            if (onImageEventListener != null) {
                onImageEventListener.onReady();
            }
        }
        return z2;
    }

    private void createPaints() {
        if (this.bitmapPaint == null) {
            Paint paint = new Paint();
            this.bitmapPaint = paint;
            paint.setAntiAlias(true);
            this.bitmapPaint.setFilterBitmap(true);
            this.bitmapPaint.setDither(true);
        }
        if ((this.debugTextPaint == null || this.debugLinePaint == null) && this.debug) {
            Paint paint2 = new Paint();
            this.debugTextPaint = paint2;
            paint2.setTextSize(px(12));
            this.debugTextPaint.setColor(-65281);
            this.debugTextPaint.setStyle(Paint.Style.FILL);
            Paint paint3 = new Paint();
            this.debugLinePaint = paint3;
            paint3.setColor(-65281);
            this.debugLinePaint.setStyle(Paint.Style.STROKE);
            this.debugLinePaint.setStrokeWidth(px(1));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @AnyThread
    public void debug(String str, Object... objArr) {
        if (this.debug) {
            Log.d(TAG, String.format(str, objArr));
        }
    }

    private float distance(float f2, float f3, float f4, float f5) {
        float f6 = f2 - f3;
        float f7 = f4 - f5;
        return (float) Math.sqrt((f6 * f6) + (f7 * f7));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doubleTapZoom(PointF pointF, PointF pointF2) {
        if (!this.panEnabled) {
            PointF pointF3 = this.sRequestedCenter;
            if (pointF3 != null) {
                pointF.x = pointF3.x;
                pointF.y = pointF3.y;
            } else {
                pointF.x = sWidth() / 2;
                pointF.y = sHeight() / 2;
            }
        }
        float fMin = Math.min(this.maxScale, this.doubleTapZoomScale);
        float f2 = this.scale;
        boolean z2 = ((double) f2) <= ((double) fMin) * 0.9d || f2 == this.minScale;
        if (!z2) {
            fMin = minScale();
        }
        float f3 = fMin;
        int i2 = this.doubleTapZoomStyle;
        if (i2 == 3) {
            setScaleAndCenter(f3, pointF);
        } else if (i2 == 2 || !z2 || !this.panEnabled) {
            new AnimationBuilder(f3, pointF).withInterruptible(false).withDuration(this.doubleTapZoomDuration).withOrigin(4).start();
        } else if (i2 == 1) {
            new AnimationBuilder(f3, pointF, pointF2).withInterruptible(false).withDuration(this.doubleTapZoomDuration).withOrigin(4).start();
        }
        invalidate();
    }

    private float ease(int i2, long j2, float f2, float f3, long j3) {
        if (i2 == 1) {
            return easeOutQuad(j2, f2, f3, j3);
        }
        if (i2 == 2) {
            return easeInOutQuad(j2, f2, f3, j3);
        }
        throw new IllegalStateException("Unexpected easing type: " + i2);
    }

    private float easeInOutQuad(long j2, float f2, float f3, long j3) {
        float f4;
        float f5 = j2 / (j3 / 2.0f);
        if (f5 < 1.0f) {
            f4 = (f3 / 2.0f) * f5;
        } else {
            float f6 = f5 - 1.0f;
            f4 = (-f3) / 2.0f;
            f5 = (f6 * (f6 - 2.0f)) - 1.0f;
        }
        return (f4 * f5) + f2;
    }

    private float easeOutQuad(long j2, float f2, float f3, long j3) {
        float f4 = j2 / j3;
        return ((-f3) * f4 * (f4 - 2.0f)) + f2;
    }

    private void execute(AsyncTask<Void, Void, ?> asyncTask) {
        asyncTask.executeOnExecutor(this.executor, new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @AnyThread
    public void fileSRect(Rect rect, Rect rect2) {
        if (getRequiredRotation() == 0) {
            rect2.set(rect);
            return;
        }
        if (getRequiredRotation() == 90) {
            int i2 = rect.top;
            int i3 = this.sHeight;
            rect2.set(i2, i3 - rect.right, rect.bottom, i3 - rect.left);
        } else if (getRequiredRotation() != 180) {
            int i4 = this.sWidth;
            rect2.set(i4 - rect.bottom, rect.left, i4 - rect.top, rect.right);
        } else {
            int i5 = this.sWidth;
            int i6 = i5 - rect.right;
            int i7 = this.sHeight;
            rect2.set(i6, i7 - rect.bottom, i5 - rect.left, i7 - rect.top);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fitToBounds(boolean z2, ScaleAndTranslate scaleAndTranslate) {
        float fMax;
        int iMax;
        float fMax2;
        if (this.panLimit == 2 && isReady()) {
            z2 = false;
        }
        PointF pointF = scaleAndTranslate.vTranslate;
        float fLimitedScale = limitedScale(scaleAndTranslate.scale);
        float fSWidth = sWidth() * fLimitedScale;
        float fSHeight = sHeight() * fLimitedScale;
        if (this.panLimit == 3 && isReady()) {
            pointF.x = Math.max(pointF.x, (getWidth() / 2) - fSWidth);
            pointF.y = Math.max(pointF.y, (getHeight() / 2) - fSHeight);
        } else if (z2) {
            pointF.x = Math.max(pointF.x, getWidth() - fSWidth);
            pointF.y = Math.max(pointF.y, getHeight() - fSHeight);
        } else {
            pointF.x = Math.max(pointF.x, -fSWidth);
            pointF.y = Math.max(pointF.y, -fSHeight);
        }
        float paddingLeft = (getPaddingLeft() > 0 || getPaddingRight() > 0) ? getPaddingLeft() / (getPaddingLeft() + getPaddingRight()) : 0.5f;
        float paddingTop = (getPaddingTop() > 0 || getPaddingBottom() > 0) ? getPaddingTop() / (getPaddingTop() + getPaddingBottom()) : 0.5f;
        if (this.panLimit == 3 && isReady()) {
            fMax = Math.max(0, getWidth() / 2);
            iMax = Math.max(0, getHeight() / 2);
        } else {
            if (z2) {
                fMax = Math.max(0.0f, (getWidth() - fSWidth) * paddingLeft);
                fMax2 = Math.max(0.0f, (getHeight() - fSHeight) * paddingTop);
                pointF.x = Math.min(pointF.x, fMax);
                pointF.y = Math.min(pointF.y, fMax2);
                scaleAndTranslate.scale = fLimitedScale;
            }
            fMax = Math.max(0, getWidth());
            iMax = Math.max(0, getHeight());
        }
        fMax2 = iMax;
        pointF.x = Math.min(pointF.x, fMax);
        pointF.y = Math.min(pointF.y, fMax2);
        scaleAndTranslate.scale = fLimitedScale;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @AnyThread
    public int getExifOrientation(Context context, String str) {
        int i2;
        int i3 = 0;
        if (!str.startsWith("content")) {
            if (!str.startsWith("file:///") || str.startsWith("file:///android_asset/")) {
                return 0;
            }
            try {
                int attributeInt = new ExifInterface(str.substring(7)).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                if (attributeInt != 1 && attributeInt != 0) {
                    if (attributeInt == 6) {
                        i2 = 90;
                    } else if (attributeInt == 3) {
                        i2 = 180;
                    } else {
                        if (attributeInt != 8) {
                            Log.w(TAG, "Unsupported EXIF orientation: " + attributeInt);
                            return 0;
                        }
                        i2 = 270;
                    }
                    return i2;
                }
                return 0;
            } catch (Exception unused) {
                Log.w(TAG, "Could not get EXIF orientation of image");
                return 0;
            }
        }
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = context.getContentResolver().query(Uri.parse(str), new String[]{"orientation"}, null, null, null);
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    int i4 = cursorQuery.getInt(0);
                    if (!VALID_ORIENTATIONS.contains(Integer.valueOf(i4)) || i4 == -1) {
                        Log.w(TAG, "Unsupported orientation: " + i4);
                    } else {
                        i3 = i4;
                    }
                }
                if (cursorQuery == null) {
                    return i3;
                }
            } catch (Exception unused2) {
                Log.w(TAG, "Could not get orientation of image from media store");
                if (cursorQuery == null) {
                    return 0;
                }
            }
            cursorQuery.close();
            return i3;
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    @NonNull
    private Point getMaxBitmapDimensions(Canvas canvas) {
        return new Point(Math.min(canvas.getMaximumBitmapWidth(), this.maxTileWidth), Math.min(canvas.getMaximumBitmapHeight(), this.maxTileHeight));
    }

    public static Bitmap.Config getPreferredBitmapConfig() {
        return preferredBitmapConfig;
    }

    @AnyThread
    private int getRequiredRotation() {
        int i2 = this.orientation;
        return i2 == -1 ? this.sOrientation : i2;
    }

    private synchronized void initialiseBaseLayer(@NonNull Point point) {
        debug("initialiseBaseLayer maxTileDimensions=%dx%d", Integer.valueOf(point.x), Integer.valueOf(point.y));
        ScaleAndTranslate scaleAndTranslate = new ScaleAndTranslate(0.0f, new PointF(0.0f, 0.0f));
        this.satTemp = scaleAndTranslate;
        fitToBounds(true, scaleAndTranslate);
        int iCalculateInSampleSize = calculateInSampleSize(this.satTemp.scale);
        this.fullImageSampleSize = iCalculateInSampleSize;
        if (iCalculateInSampleSize > 1) {
            this.fullImageSampleSize = iCalculateInSampleSize / 2;
        }
        if (this.fullImageSampleSize != 1 || this.sRegion != null || sWidth() >= point.x || sHeight() >= point.y) {
            initialiseTileMap(point);
            Iterator<Tile> it = this.tileMap.get(Integer.valueOf(this.fullImageSampleSize)).iterator();
            while (it.hasNext()) {
                execute(new TileLoadTask(this, this.decoder, it.next()));
            }
            refreshRequiredTiles(true);
        } else {
            this.decoder.recycle();
            this.decoder = null;
            execute(new BitmapLoadTask(this, getContext(), this.bitmapDecoderFactory, this.uri, false));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void initialiseTileMap(Point point) {
        int i2 = 0;
        int i3 = 1;
        debug("initialiseTileMap maxTileDimensions=%dx%d", Integer.valueOf(point.x), Integer.valueOf(point.y));
        this.tileMap = new LinkedHashMap();
        int i4 = this.fullImageSampleSize;
        int i5 = 1;
        int i6 = 1;
        while (true) {
            int iSWidth = sWidth() / i5;
            int iSHeight = sHeight() / i6;
            int i7 = iSWidth / i4;
            int i8 = iSHeight / i4;
            while (true) {
                if (i7 + i5 + i3 <= point.x && (i7 <= getWidth() * 1.25d || i4 >= this.fullImageSampleSize)) {
                    break;
                }
                i5++;
                iSWidth = sWidth() / i5;
                i7 = iSWidth / i4;
                i3 = i3;
                i2 = i2;
            }
            while (true) {
                if (i8 + i6 + i3 <= point.y && (i8 <= getHeight() * 1.25d || i4 >= this.fullImageSampleSize)) {
                    break;
                }
                i6++;
                iSHeight = sHeight() / i6;
                i8 = iSHeight / i4;
                i3 = i3;
                i2 = i2;
            }
            ArrayList arrayList = new ArrayList(i5 * i6);
            int i9 = i2;
            while (i9 < i5) {
                int i10 = i2;
                while (i10 < i6) {
                    Tile tile = new Tile();
                    tile.sampleSize = i4;
                    tile.visible = i4 == this.fullImageSampleSize ? i3 : i2;
                    tile.sRect = new Rect(i9 * iSWidth, i10 * iSHeight, i9 == i5 + (-1) ? sWidth() : (i9 + 1) * iSWidth, i10 == i6 + (-1) ? sHeight() : (i10 + 1) * iSHeight);
                    tile.vRect = new Rect(0, 0, 0, 0);
                    tile.fileSRect = new Rect(tile.sRect);
                    arrayList.add(tile);
                    i10++;
                    i2 = 0;
                    i3 = 1;
                }
                i9++;
                i3 = 1;
            }
            int i11 = i2;
            this.tileMap.put(Integer.valueOf(i4), arrayList);
            if (i4 == 1) {
                return;
            }
            i4 /= 2;
            i3 = 1;
            i2 = i11;
        }
    }

    private boolean isBaseLayerReady() {
        boolean z2 = true;
        if (this.bitmap != null && !this.bitmapIsPreview) {
            return true;
        }
        Map<Integer, List<Tile>> map = this.tileMap;
        if (map == null) {
            return false;
        }
        for (Map.Entry<Integer, List<Tile>> entry : map.entrySet()) {
            if (entry.getKey().intValue() == this.fullImageSampleSize) {
                for (Tile tile : entry.getValue()) {
                    if (tile.loading || tile.bitmap == null) {
                        z2 = false;
                    }
                }
            }
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NonNull
    public PointF limitedSCenter(float f2, float f3, float f4, @NonNull PointF pointF) {
        PointF pointFVTranslateForSCenter = vTranslateForSCenter(f2, f3, f4);
        pointF.set(((getPaddingLeft() + (((getWidth() - getPaddingRight()) - getPaddingLeft()) / 2)) - pointFVTranslateForSCenter.x) / f4, ((getPaddingTop() + (((getHeight() - getPaddingBottom()) - getPaddingTop()) / 2)) - pointFVTranslateForSCenter.y) / f4);
        return pointF;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float limitedScale(float f2) {
        return Math.min(this.maxScale, Math.max(minScale(), f2));
    }

    private float minScale() {
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int i2 = this.minimumScaleType;
        if (i2 == 2 || i2 == 4) {
            return Math.max((getWidth() - paddingLeft) / sWidth(), (getHeight() - paddingBottom) / sHeight());
        }
        if (i2 == 3) {
            float f2 = this.minScale;
            if (f2 > 0.0f) {
                return f2;
            }
        }
        return Math.min((getWidth() - paddingLeft) / sWidth(), (getHeight() - paddingBottom) / sHeight());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onImageLoaded(Bitmap bitmap, int i2, boolean z2) {
        OnImageEventListener onImageEventListener;
        debug("onImageLoaded", new Object[0]);
        int i3 = this.sWidth;
        if (i3 > 0 && this.sHeight > 0 && (i3 != bitmap.getWidth() || this.sHeight != bitmap.getHeight())) {
            reset(false);
        }
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 != null && !this.bitmapIsCached) {
            bitmap2.recycle();
        }
        if (this.bitmap != null && this.bitmapIsCached && (onImageEventListener = this.onImageEventListener) != null) {
            onImageEventListener.onPreviewReleased();
        }
        this.bitmapIsPreview = false;
        this.bitmapIsCached = z2;
        this.bitmap = bitmap;
        this.sWidth = bitmap.getWidth();
        this.sHeight = bitmap.getHeight();
        this.sOrientation = i2;
        boolean zCheckReady = checkReady();
        boolean zCheckImageLoaded = checkImageLoaded();
        if (zCheckReady || zCheckImageLoaded) {
            invalidate();
            requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onPreviewLoaded(Bitmap bitmap) {
        debug("onPreviewLoaded", new Object[0]);
        if (this.bitmap == null && !this.imageLoadedSent) {
            Rect rect = this.pRegion;
            if (rect != null) {
                this.bitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), this.pRegion.height());
            } else {
                this.bitmap = bitmap;
            }
            this.bitmapIsPreview = true;
            if (checkReady()) {
                invalidate();
                requestLayout();
            }
            return;
        }
        bitmap.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onTileLoaded() {
        Bitmap bitmap;
        debug("onTileLoaded", new Object[0]);
        checkReady();
        checkImageLoaded();
        if (isBaseLayerReady() && (bitmap = this.bitmap) != null) {
            if (!this.bitmapIsCached) {
                bitmap.recycle();
            }
            this.bitmap = null;
            OnImageEventListener onImageEventListener = this.onImageEventListener;
            if (onImageEventListener != null && this.bitmapIsCached) {
                onImageEventListener.onPreviewReleased();
            }
            this.bitmapIsPreview = false;
            this.bitmapIsCached = false;
        }
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onTilesInited(ImageRegionDecoder imageRegionDecoder, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        debug("onTilesInited sWidth=%d, sHeight=%d, sOrientation=%d", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(this.orientation));
        int i8 = this.sWidth;
        if (i8 > 0 && (i7 = this.sHeight) > 0 && (i8 != i2 || i7 != i3)) {
            reset(false);
            Bitmap bitmap = this.bitmap;
            if (bitmap != null) {
                if (!this.bitmapIsCached) {
                    bitmap.recycle();
                }
                this.bitmap = null;
                OnImageEventListener onImageEventListener = this.onImageEventListener;
                if (onImageEventListener != null && this.bitmapIsCached) {
                    onImageEventListener.onPreviewReleased();
                }
                this.bitmapIsPreview = false;
                this.bitmapIsCached = false;
            }
        }
        this.decoder = imageRegionDecoder;
        this.sWidth = i2;
        this.sHeight = i3;
        this.sOrientation = i4;
        checkReady();
        if (!checkImageLoaded() && (i5 = this.maxTileWidth) > 0 && i5 != Integer.MAX_VALUE && (i6 = this.maxTileHeight) > 0 && i6 != Integer.MAX_VALUE && getWidth() > 0 && getHeight() > 0) {
            initialiseBaseLayer(new Point(this.maxTileWidth, this.maxTileHeight));
        }
        invalidate();
        requestLayout();
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001f, code lost:
    
        if (r1 != 262) goto L137;
     */
    /* JADX WARN: Removed duplicated region for block: B:135:0x03a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean onTouchEventInternal(@androidx.annotation.NonNull android.view.MotionEvent r13) {
        /*
            Method dump skipped, instructions count: 1176
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.onTouchEventInternal(android.view.MotionEvent):boolean");
    }

    private void preDraw() {
        Float f2;
        if (getWidth() == 0 || getHeight() == 0 || this.sWidth <= 0 || this.sHeight <= 0) {
            return;
        }
        if (this.sPendingCenter != null && (f2 = this.pendingScale) != null) {
            this.scale = f2.floatValue();
            if (this.vTranslate == null) {
                this.vTranslate = new PointF();
            }
            this.vTranslate.x = (getWidth() / 2) - (this.scale * this.sPendingCenter.x);
            this.vTranslate.y = (getHeight() / 2) - (this.scale * this.sPendingCenter.y);
            this.sPendingCenter = null;
            this.pendingScale = null;
            fitToBounds(true);
            refreshRequiredTiles(true);
        }
        fitToBounds(false);
    }

    private int px(int i2) {
        return (int) (this.density * i2);
    }

    private void refreshRequiredTiles(boolean z2) {
        if (this.decoder == null || this.tileMap == null) {
            return;
        }
        int iMin = Math.min(this.fullImageSampleSize, calculateInSampleSize(this.scale));
        Iterator<Map.Entry<Integer, List<Tile>>> it = this.tileMap.entrySet().iterator();
        while (it.hasNext()) {
            for (Tile tile : it.next().getValue()) {
                if (tile.sampleSize < iMin || (tile.sampleSize > iMin && tile.sampleSize != this.fullImageSampleSize)) {
                    tile.visible = false;
                    if (tile.bitmap != null) {
                        tile.bitmap.recycle();
                        tile.bitmap = null;
                    }
                }
                if (tile.sampleSize == iMin) {
                    if (tileVisible(tile)) {
                        tile.visible = true;
                        if (!tile.loading && tile.bitmap == null && z2) {
                            execute(new TileLoadTask(this, this.decoder, tile));
                        }
                    } else if (tile.sampleSize != this.fullImageSampleSize) {
                        tile.visible = false;
                        if (tile.bitmap != null) {
                            tile.bitmap.recycle();
                            tile.bitmap = null;
                        }
                    }
                } else if (tile.sampleSize == this.fullImageSampleSize) {
                    tile.visible = true;
                }
            }
        }
    }

    private void requestDisallowInterceptTouchEvent(boolean z2) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z2);
        }
    }

    private void reset(boolean z2) {
        OnImageEventListener onImageEventListener;
        debug("reset newImage=" + z2, new Object[0]);
        this.scale = 0.0f;
        this.scaleStart = 0.0f;
        this.vTranslate = null;
        this.vTranslateStart = null;
        this.vTranslateBefore = null;
        this.pendingScale = Float.valueOf(0.0f);
        this.sPendingCenter = null;
        this.sRequestedCenter = null;
        this.isZooming = false;
        this.isPanning = false;
        this.isQuickScaling = false;
        this.maxTouchCount = 0;
        this.fullImageSampleSize = 0;
        this.vCenterStart = null;
        this.vDistStart = 0.0f;
        this.quickScaleLastDistance = 0.0f;
        this.quickScaleMoved = false;
        this.quickScaleSCenter = null;
        this.quickScaleVLastPoint = null;
        this.quickScaleVStart = null;
        this.anim = null;
        this.satTemp = null;
        this.matrix = null;
        this.sRect = null;
        if (z2) {
            this.uri = null;
            this.decoderLock.writeLock().lock();
            try {
                ImageRegionDecoder imageRegionDecoder = this.decoder;
                if (imageRegionDecoder != null) {
                    imageRegionDecoder.recycle();
                    this.decoder = null;
                }
                this.decoderLock.writeLock().unlock();
                Bitmap bitmap = this.bitmap;
                if (bitmap != null && !this.bitmapIsCached) {
                    bitmap.recycle();
                }
                if (this.bitmap != null && this.bitmapIsCached && (onImageEventListener = this.onImageEventListener) != null) {
                    onImageEventListener.onPreviewReleased();
                }
                this.sWidth = 0;
                this.sHeight = 0;
                this.sOrientation = 0;
                this.sRegion = null;
                this.pRegion = null;
                this.readySent = false;
                this.imageLoadedSent = false;
                this.bitmap = null;
                this.bitmapIsPreview = false;
                this.bitmapIsCached = false;
            } catch (Throwable th) {
                this.decoderLock.writeLock().unlock();
                throw th;
            }
        }
        Map<Integer, List<Tile>> map = this.tileMap;
        if (map != null) {
            Iterator<Map.Entry<Integer, List<Tile>>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                for (Tile tile : it.next().getValue()) {
                    tile.visible = false;
                    if (tile.bitmap != null) {
                        tile.bitmap.recycle();
                        tile.bitmap = null;
                    }
                }
            }
            this.tileMap = null;
        }
        setGestureDetector(getContext());
    }

    private void restoreState(ImageViewState imageViewState) {
        if (imageViewState == null || !VALID_ORIENTATIONS.contains(Integer.valueOf(imageViewState.getOrientation()))) {
            return;
        }
        this.orientation = imageViewState.getOrientation();
        this.pendingScale = Float.valueOf(imageViewState.getScale());
        this.sPendingCenter = imageViewState.getCenter();
        invalidate();
    }

    private int sHeight() {
        int requiredRotation = getRequiredRotation();
        return (requiredRotation == 90 || requiredRotation == 270) ? this.sWidth : this.sHeight;
    }

    private int sWidth() {
        int requiredRotation = getRequiredRotation();
        return (requiredRotation == 90 || requiredRotation == 270) ? this.sHeight : this.sWidth;
    }

    private void sendStateChanged(float f2, PointF pointF, int i2) {
        OnStateChangedListener onStateChangedListener = this.onStateChangedListener;
        if (onStateChangedListener != null) {
            float f3 = this.scale;
            if (f3 != f2) {
                onStateChangedListener.onScaleChanged(f3, i2);
            }
        }
        if (this.onStateChangedListener == null || this.vTranslate.equals(pointF)) {
            return;
        }
        this.onStateChangedListener.onCenterChanged(getCenter(), i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setGestureDetector(final Context context) {
        this.detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (!SubsamplingScaleImageView.this.zoomEnabled || !SubsamplingScaleImageView.this.readySent || SubsamplingScaleImageView.this.vTranslate == null) {
                    return super.onDoubleTapEvent(motionEvent);
                }
                SubsamplingScaleImageView.this.setGestureDetector(context);
                if (!SubsamplingScaleImageView.this.quickScaleEnabled) {
                    SubsamplingScaleImageView subsamplingScaleImageView = SubsamplingScaleImageView.this;
                    subsamplingScaleImageView.doubleTapZoom(subsamplingScaleImageView.viewToSourceCoord(new PointF(motionEvent.getX(), motionEvent.getY())), new PointF(motionEvent.getX(), motionEvent.getY()));
                    return true;
                }
                SubsamplingScaleImageView.this.vCenterStart = new PointF(motionEvent.getX(), motionEvent.getY());
                SubsamplingScaleImageView.this.vTranslateStart = new PointF(SubsamplingScaleImageView.this.vTranslate.x, SubsamplingScaleImageView.this.vTranslate.y);
                SubsamplingScaleImageView subsamplingScaleImageView2 = SubsamplingScaleImageView.this;
                subsamplingScaleImageView2.scaleStart = subsamplingScaleImageView2.scale;
                SubsamplingScaleImageView.this.isQuickScaling = true;
                SubsamplingScaleImageView.this.isZooming = true;
                SubsamplingScaleImageView.this.quickScaleLastDistance = -1.0f;
                SubsamplingScaleImageView subsamplingScaleImageView3 = SubsamplingScaleImageView.this;
                subsamplingScaleImageView3.quickScaleSCenter = subsamplingScaleImageView3.viewToSourceCoord(subsamplingScaleImageView3.vCenterStart);
                SubsamplingScaleImageView.this.quickScaleVStart = new PointF(motionEvent.getX(), motionEvent.getY());
                SubsamplingScaleImageView.this.quickScaleVLastPoint = new PointF(SubsamplingScaleImageView.this.quickScaleSCenter.x, SubsamplingScaleImageView.this.quickScaleSCenter.y);
                SubsamplingScaleImageView.this.quickScaleMoved = false;
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
                if (!SubsamplingScaleImageView.this.panEnabled || !SubsamplingScaleImageView.this.readySent || SubsamplingScaleImageView.this.vTranslate == null || motionEvent == null || motionEvent2 == null || ((Math.abs(motionEvent.getX() - motionEvent2.getX()) <= 50.0f && Math.abs(motionEvent.getY() - motionEvent2.getY()) <= 50.0f) || ((Math.abs(f2) <= 500.0f && Math.abs(f3) <= 500.0f) || SubsamplingScaleImageView.this.isZooming))) {
                    return super.onFling(motionEvent, motionEvent2, f2, f3);
                }
                PointF pointF = new PointF(SubsamplingScaleImageView.this.vTranslate.x + (f2 * 0.25f), SubsamplingScaleImageView.this.vTranslate.y + (f3 * 0.25f));
                new AnimationBuilder(new PointF(((SubsamplingScaleImageView.this.getWidth() / 2) - pointF.x) / SubsamplingScaleImageView.this.scale, ((SubsamplingScaleImageView.this.getHeight() / 2) - pointF.y) / SubsamplingScaleImageView.this.scale)).withEasing(1).withPanLimited(false).withOrigin(3).start();
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                SubsamplingScaleImageView.this.performClick();
                return true;
            }
        });
        this.singleDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.3
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                SubsamplingScaleImageView.this.performClick();
                return true;
            }
        });
    }

    private void setMatrixArray(float[] fArr, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        fArr[0] = f2;
        fArr[1] = f3;
        fArr[2] = f4;
        fArr[3] = f5;
        fArr[4] = f6;
        fArr[5] = f7;
        fArr[6] = f8;
        fArr[7] = f9;
    }

    public static void setPreferredBitmapConfig(Bitmap.Config config) {
        preferredBitmapConfig = config;
    }

    private void sourceToViewRect(@NonNull Rect rect, @NonNull Rect rect2) {
        rect2.set((int) sourceToViewX(rect.left), (int) sourceToViewY(rect.top), (int) sourceToViewX(rect.right), (int) sourceToViewY(rect.bottom));
    }

    private float sourceToViewX(float f2) {
        PointF pointF = this.vTranslate;
        if (pointF == null) {
            return Float.NaN;
        }
        return (f2 * this.scale) + pointF.x;
    }

    private float sourceToViewY(float f2) {
        PointF pointF = this.vTranslate;
        if (pointF == null) {
            return Float.NaN;
        }
        return (f2 * this.scale) + pointF.y;
    }

    private boolean tileVisible(Tile tile) {
        return viewToSourceX(0.0f) <= ((float) tile.sRect.right) && ((float) tile.sRect.left) <= viewToSourceX((float) getWidth()) && viewToSourceY(0.0f) <= ((float) tile.sRect.bottom) && ((float) tile.sRect.top) <= viewToSourceY((float) getHeight());
    }

    @NonNull
    private PointF vTranslateForSCenter(float f2, float f3, float f4) {
        int paddingLeft = getPaddingLeft() + (((getWidth() - getPaddingRight()) - getPaddingLeft()) / 2);
        int paddingTop = getPaddingTop() + (((getHeight() - getPaddingBottom()) - getPaddingTop()) / 2);
        if (this.satTemp == null) {
            this.satTemp = new ScaleAndTranslate(0.0f, new PointF(0.0f, 0.0f));
        }
        this.satTemp.scale = f4;
        this.satTemp.vTranslate.set(paddingLeft - (f2 * f4), paddingTop - (f3 * f4));
        fitToBounds(true, this.satTemp);
        return this.satTemp.vTranslate;
    }

    private float viewToSourceX(float f2) {
        PointF pointF = this.vTranslate;
        if (pointF == null) {
            return Float.NaN;
        }
        return (f2 - pointF.x) / this.scale;
    }

    private float viewToSourceY(float f2) {
        PointF pointF = this.vTranslate;
        if (pointF == null) {
            return Float.NaN;
        }
        return (f2 - pointF.y) / this.scale;
    }

    @Nullable
    public AnimationBuilder animateCenter(PointF pointF) {
        if (isReady()) {
            return new AnimationBuilder(pointF);
        }
        return null;
    }

    @Nullable
    public AnimationBuilder animateScale(float f2) {
        if (isReady()) {
            return new AnimationBuilder(f2);
        }
        return null;
    }

    @Nullable
    public AnimationBuilder animateScaleAndCenter(float f2, PointF pointF) {
        if (isReady()) {
            return new AnimationBuilder(f2, pointF);
        }
        return null;
    }

    public final int getAppliedOrientation() {
        return getRequiredRotation();
    }

    @Nullable
    public final PointF getCenter() {
        return viewToSourceCoord(getWidth() / 2, getHeight() / 2);
    }

    public float getMaxScale() {
        return this.maxScale;
    }

    public final float getMinScale() {
        return minScale();
    }

    public final int getOrientation() {
        return this.orientation;
    }

    public final void getPanRemaining(RectF rectF) {
        if (isReady()) {
            float fSWidth = this.scale * sWidth();
            float fSHeight = this.scale * sHeight();
            int i2 = this.panLimit;
            if (i2 == 3) {
                rectF.top = Math.max(0.0f, -(this.vTranslate.y - (getHeight() / 2)));
                rectF.left = Math.max(0.0f, -(this.vTranslate.x - (getWidth() / 2)));
                rectF.bottom = Math.max(0.0f, this.vTranslate.y - ((getHeight() / 2) - fSHeight));
                rectF.right = Math.max(0.0f, this.vTranslate.x - ((getWidth() / 2) - fSWidth));
                return;
            }
            if (i2 == 2) {
                rectF.top = Math.max(0.0f, -(this.vTranslate.y - getHeight()));
                rectF.left = Math.max(0.0f, -(this.vTranslate.x - getWidth()));
                rectF.bottom = Math.max(0.0f, this.vTranslate.y + fSHeight);
                rectF.right = Math.max(0.0f, this.vTranslate.x + fSWidth);
                return;
            }
            rectF.top = Math.max(0.0f, -this.vTranslate.y);
            rectF.left = Math.max(0.0f, -this.vTranslate.x);
            rectF.bottom = Math.max(0.0f, (fSHeight + this.vTranslate.y) - getHeight());
            rectF.right = Math.max(0.0f, (fSWidth + this.vTranslate.x) - getWidth());
        }
    }

    public final int getSHeight() {
        return this.sHeight;
    }

    public final int getSWidth() {
        return this.sWidth;
    }

    public final float getScale() {
        return this.scale;
    }

    @Nullable
    public final ImageViewState getState() {
        if (this.vTranslate == null || this.sWidth <= 0 || this.sHeight <= 0) {
            return null;
        }
        return new ImageViewState(getScale(), getCenter(), getOrientation());
    }

    public boolean hasImage() {
        return (this.uri == null && this.bitmap == null) ? false : true;
    }

    public final boolean isImageLoaded() {
        return this.imageLoadedSent;
    }

    public final boolean isPanEnabled() {
        return this.panEnabled;
    }

    public final boolean isQuickScaleEnabled() {
        return this.quickScaleEnabled;
    }

    public final boolean isReady() {
        return this.readySent;
    }

    public final boolean isZoomEnabled() {
        return this.zoomEnabled;
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x0487  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onDraw(android.graphics.Canvas r32) {
        /*
            Method dump skipped, instructions count: 1872
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.onDraw(android.graphics.Canvas):void");
    }

    public void onImageLoaded() {
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        boolean z2 = mode != 1073741824;
        boolean z3 = mode2 != 1073741824;
        if (this.sWidth > 0 && this.sHeight > 0) {
            if (z2 && z3) {
                size = sWidth();
                size2 = sHeight();
            } else if (z3) {
                size2 = (int) ((sHeight() / sWidth()) * size);
            } else if (z2) {
                size = (int) ((sWidth() / sHeight()) * size2);
            }
        }
        setMeasuredDimension(Math.max(size, getSuggestedMinimumWidth()), Math.max(size2, getSuggestedMinimumHeight()));
    }

    public void onReady() {
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        debug("onSizeChanged %dx%d -> %dx%d", Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i2), Integer.valueOf(i3));
        PointF center = getCenter();
        if (!this.readySent || center == null) {
            return;
        }
        this.anim = null;
        this.pendingScale = Float.valueOf(this.scale);
        this.sPendingCenter = center;
    }

    @Override // android.view.View
    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        GestureDetector gestureDetector;
        Anim anim = this.anim;
        if (anim != null && !anim.interruptible) {
            requestDisallowInterceptTouchEvent(true);
            return true;
        }
        Anim anim2 = this.anim;
        if (anim2 != null && anim2.listener != null) {
            try {
                this.anim.listener.onInterruptedByUser();
            } catch (Exception e2) {
                Log.w(TAG, "Error thrown by animation listener", e2);
            }
        }
        this.anim = null;
        if (this.vTranslate == null) {
            GestureDetector gestureDetector2 = this.singleDetector;
            if (gestureDetector2 != null) {
                gestureDetector2.onTouchEvent(motionEvent);
            }
            return true;
        }
        if (!this.isQuickScaling && ((gestureDetector = this.detector) == null || gestureDetector.onTouchEvent(motionEvent))) {
            this.isZooming = false;
            this.isPanning = false;
            this.maxTouchCount = 0;
            return true;
        }
        if (this.vTranslateStart == null) {
            this.vTranslateStart = new PointF(0.0f, 0.0f);
        }
        if (this.vTranslateBefore == null) {
            this.vTranslateBefore = new PointF(0.0f, 0.0f);
        }
        if (this.vCenterStart == null) {
            this.vCenterStart = new PointF(0.0f, 0.0f);
        }
        float f2 = this.scale;
        this.vTranslateBefore.set(this.vTranslate);
        boolean zOnTouchEventInternal = onTouchEventInternal(motionEvent);
        sendStateChanged(f2, this.vTranslateBefore, 2);
        return zOnTouchEventInternal || super.onTouchEvent(motionEvent);
    }

    public void recycle() {
        reset(true);
        this.bitmapPaint = null;
        this.debugTextPaint = null;
        this.debugLinePaint = null;
        this.tileBgPaint = null;
    }

    public final void resetScaleAndCenter() {
        this.anim = null;
        this.pendingScale = Float.valueOf(limitedScale(0.0f));
        if (isReady()) {
            this.sPendingCenter = new PointF(sWidth() / 2, sHeight() / 2);
        } else {
            this.sPendingCenter = new PointF(0.0f, 0.0f);
        }
        invalidate();
    }

    public final void setBitmapDecoderClass(@NonNull Class<? extends ImageDecoder> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("Decoder class cannot be set to null");
        }
        this.bitmapDecoderFactory = new CompatDecoderFactory(cls);
    }

    public final void setBitmapDecoderFactory(@NonNull DecoderFactory<? extends ImageDecoder> decoderFactory) {
        if (decoderFactory == null) {
            throw new IllegalArgumentException("Decoder factory cannot be set to null");
        }
        this.bitmapDecoderFactory = decoderFactory;
    }

    public final void setDebug(boolean z2) {
        this.debug = z2;
    }

    public final void setDoubleTapZoomDpi(int i2) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        setDoubleTapZoomScale(((displayMetrics.xdpi + displayMetrics.ydpi) / 2.0f) / i2);
    }

    public final void setDoubleTapZoomDuration(int i2) {
        this.doubleTapZoomDuration = Math.max(0, i2);
    }

    public final void setDoubleTapZoomScale(float f2) {
        this.doubleTapZoomScale = f2;
    }

    public final void setDoubleTapZoomStyle(int i2) {
        if (VALID_ZOOM_STYLES.contains(Integer.valueOf(i2))) {
            this.doubleTapZoomStyle = i2;
            return;
        }
        throw new IllegalArgumentException("Invalid zoom style: " + i2);
    }

    public void setEagerLoadingEnabled(boolean z2) {
        this.eagerLoadingEnabled = z2;
    }

    public void setExecutor(@NonNull Executor executor) {
        if (executor == null) {
            throw new NullPointerException("Executor must not be null");
        }
        this.executor = executor;
    }

    public final void setImage(@NonNull ImageSource imageSource) {
        setImage(imageSource, null, null);
    }

    public final void setMaxScale(float f2) {
        this.maxScale = f2;
    }

    public void setMaxTileSize(int i2) {
        this.maxTileWidth = i2;
        this.maxTileHeight = i2;
    }

    public final void setMaximumDpi(int i2) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        setMinScale(((displayMetrics.xdpi + displayMetrics.ydpi) / 2.0f) / i2);
    }

    public final void setMinScale(float f2) {
        this.minScale = f2;
    }

    public final void setMinimumDpi(int i2) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        setMaxScale(((displayMetrics.xdpi + displayMetrics.ydpi) / 2.0f) / i2);
    }

    public final void setMinimumScaleType(int i2) {
        if (!VALID_SCALE_TYPES.contains(Integer.valueOf(i2))) {
            throw new IllegalArgumentException("Invalid scale type: " + i2);
        }
        this.minimumScaleType = i2;
        if (isReady()) {
            fitToBounds(true);
            invalidate();
        }
    }

    public void setMinimumTileDpi(int i2) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.minimumTileDpi = (int) Math.min((displayMetrics.xdpi + displayMetrics.ydpi) / 2.0f, i2);
        if (isReady()) {
            reset(false);
            invalidate();
        }
    }

    public void setOnImageEventListener(OnImageEventListener onImageEventListener) {
        this.onImageEventListener = onImageEventListener;
    }

    @Override // android.view.View
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        this.onStateChangedListener = onStateChangedListener;
    }

    public final void setOrientation(int i2) {
        if (!VALID_ORIENTATIONS.contains(Integer.valueOf(i2))) {
            throw new IllegalArgumentException("Invalid orientation: " + i2);
        }
        this.orientation = i2;
        reset(false);
        invalidate();
        requestLayout();
    }

    public final void setPanEnabled(boolean z2) {
        PointF pointF;
        this.panEnabled = z2;
        if (z2 || (pointF = this.vTranslate) == null) {
            return;
        }
        pointF.x = (getWidth() / 2) - (this.scale * (sWidth() / 2));
        this.vTranslate.y = (getHeight() / 2) - (this.scale * (sHeight() / 2));
        if (isReady()) {
            refreshRequiredTiles(true);
            invalidate();
        }
    }

    public final void setPanLimit(int i2) {
        if (!VALID_PAN_LIMITS.contains(Integer.valueOf(i2))) {
            throw new IllegalArgumentException("Invalid pan limit: " + i2);
        }
        this.panLimit = i2;
        if (isReady()) {
            fitToBounds(true);
            invalidate();
        }
    }

    public final void setQuickScaleEnabled(boolean z2) {
        this.quickScaleEnabled = z2;
    }

    public final void setRegionDecoderClass(@NonNull Class<? extends ImageRegionDecoder> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("Decoder class cannot be set to null");
        }
        this.regionDecoderFactory = new CompatDecoderFactory(cls);
    }

    public final void setRegionDecoderFactory(@NonNull DecoderFactory<? extends ImageRegionDecoder> decoderFactory) {
        if (decoderFactory == null) {
            throw new IllegalArgumentException("Decoder factory cannot be set to null");
        }
        this.regionDecoderFactory = decoderFactory;
    }

    public final void setScaleAndCenter(float f2, @Nullable PointF pointF) {
        this.anim = null;
        this.pendingScale = Float.valueOf(f2);
        this.sPendingCenter = pointF;
        this.sRequestedCenter = pointF;
        invalidate();
    }

    public final void setTileBackgroundColor(int i2) {
        if (Color.alpha(i2) == 0) {
            this.tileBgPaint = null;
        } else {
            Paint paint = new Paint();
            this.tileBgPaint = paint;
            paint.setStyle(Paint.Style.FILL);
            this.tileBgPaint.setColor(i2);
        }
        invalidate();
    }

    public final void setZoomEnabled(boolean z2) {
        this.zoomEnabled = z2;
    }

    @Nullable
    public final PointF sourceToViewCoord(PointF pointF) {
        return sourceToViewCoord(pointF.x, pointF.y, new PointF());
    }

    public void viewToFileRect(Rect rect, Rect rect2) {
        if (this.vTranslate == null || !this.readySent) {
            return;
        }
        rect2.set((int) viewToSourceX(rect.left), (int) viewToSourceY(rect.top), (int) viewToSourceX(rect.right), (int) viewToSourceY(rect.bottom));
        fileSRect(rect2, rect2);
        rect2.set(Math.max(0, rect2.left), Math.max(0, rect2.top), Math.min(this.sWidth, rect2.right), Math.min(this.sHeight, rect2.bottom));
        Rect rect3 = this.sRegion;
        if (rect3 != null) {
            rect2.offset(rect3.left, rect3.top);
        }
    }

    @Nullable
    public final PointF viewToSourceCoord(PointF pointF) {
        return viewToSourceCoord(pointF.x, pointF.y, new PointF());
    }

    public void visibleFileRect(Rect rect) {
        if (this.vTranslate == null || !this.readySent) {
            return;
        }
        rect.set(0, 0, getWidth(), getHeight());
        viewToFileRect(rect, rect);
    }

    public final void setImage(@NonNull ImageSource imageSource, ImageViewState imageViewState) {
        setImage(imageSource, null, imageViewState);
    }

    @Nullable
    public final PointF sourceToViewCoord(float f2, float f3) {
        return sourceToViewCoord(f2, f3, new PointF());
    }

    @Nullable
    public final PointF viewToSourceCoord(float f2, float f3) {
        return viewToSourceCoord(f2, f3, new PointF());
    }

    public final void setImage(@NonNull ImageSource imageSource, ImageSource imageSource2) {
        setImage(imageSource, imageSource2, null);
    }

    public void setMaxTileSize(int i2, int i3) {
        this.maxTileWidth = i2;
        this.maxTileHeight = i3;
    }

    @Nullable
    public final PointF sourceToViewCoord(PointF pointF, @NonNull PointF pointF2) {
        return sourceToViewCoord(pointF.x, pointF.y, pointF2);
    }

    @Nullable
    public final PointF viewToSourceCoord(PointF pointF, @NonNull PointF pointF2) {
        return viewToSourceCoord(pointF.x, pointF.y, pointF2);
    }

    public final class AnimationBuilder {
        private long duration;
        private int easing;
        private boolean interruptible;
        private OnAnimationEventListener listener;
        private int origin;
        private boolean panLimited;
        private final PointF targetSCenter;
        private final float targetScale;
        private final PointF vFocus;

        /* JADX INFO: Access modifiers changed from: private */
        @NonNull
        public AnimationBuilder withOrigin(int i2) {
            this.origin = i2;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @NonNull
        public AnimationBuilder withPanLimited(boolean z2) {
            this.panLimited = z2;
            return this;
        }

        public void start() {
            PointF pointFLimitedSCenter;
            if (SubsamplingScaleImageView.this.anim != null && SubsamplingScaleImageView.this.anim.listener != null) {
                try {
                    SubsamplingScaleImageView.this.anim.listener.onInterruptedByNewAnim();
                } catch (Exception e2) {
                    Log.w(SubsamplingScaleImageView.TAG, "Error thrown by animation listener", e2);
                }
            }
            int paddingLeft = SubsamplingScaleImageView.this.getPaddingLeft() + (((SubsamplingScaleImageView.this.getWidth() - SubsamplingScaleImageView.this.getPaddingRight()) - SubsamplingScaleImageView.this.getPaddingLeft()) / 2);
            int paddingTop = SubsamplingScaleImageView.this.getPaddingTop() + (((SubsamplingScaleImageView.this.getHeight() - SubsamplingScaleImageView.this.getPaddingBottom()) - SubsamplingScaleImageView.this.getPaddingTop()) / 2);
            float fLimitedScale = SubsamplingScaleImageView.this.limitedScale(this.targetScale);
            if (this.panLimited) {
                SubsamplingScaleImageView subsamplingScaleImageView = SubsamplingScaleImageView.this;
                PointF pointF = this.targetSCenter;
                pointFLimitedSCenter = subsamplingScaleImageView.limitedSCenter(pointF.x, pointF.y, fLimitedScale, new PointF());
            } else {
                pointFLimitedSCenter = this.targetSCenter;
            }
            SubsamplingScaleImageView.this.anim = new Anim();
            SubsamplingScaleImageView.this.anim.scaleStart = SubsamplingScaleImageView.this.scale;
            SubsamplingScaleImageView.this.anim.scaleEnd = fLimitedScale;
            SubsamplingScaleImageView.this.anim.time = System.currentTimeMillis();
            SubsamplingScaleImageView.this.anim.sCenterEndRequested = pointFLimitedSCenter;
            SubsamplingScaleImageView.this.anim.sCenterStart = SubsamplingScaleImageView.this.getCenter();
            SubsamplingScaleImageView.this.anim.sCenterEnd = pointFLimitedSCenter;
            SubsamplingScaleImageView.this.anim.vFocusStart = SubsamplingScaleImageView.this.sourceToViewCoord(pointFLimitedSCenter);
            SubsamplingScaleImageView.this.anim.vFocusEnd = new PointF(paddingLeft, paddingTop);
            SubsamplingScaleImageView.this.anim.duration = this.duration;
            SubsamplingScaleImageView.this.anim.interruptible = this.interruptible;
            SubsamplingScaleImageView.this.anim.easing = this.easing;
            SubsamplingScaleImageView.this.anim.origin = this.origin;
            SubsamplingScaleImageView.this.anim.time = System.currentTimeMillis();
            SubsamplingScaleImageView.this.anim.listener = this.listener;
            PointF pointF2 = this.vFocus;
            if (pointF2 != null) {
                float f2 = pointF2.x - (SubsamplingScaleImageView.this.anim.sCenterStart.x * fLimitedScale);
                float f3 = this.vFocus.y - (SubsamplingScaleImageView.this.anim.sCenterStart.y * fLimitedScale);
                ScaleAndTranslate scaleAndTranslate = new ScaleAndTranslate(fLimitedScale, new PointF(f2, f3));
                SubsamplingScaleImageView.this.fitToBounds(true, scaleAndTranslate);
                SubsamplingScaleImageView.this.anim.vFocusEnd = new PointF(this.vFocus.x + (scaleAndTranslate.vTranslate.x - f2), this.vFocus.y + (scaleAndTranslate.vTranslate.y - f3));
            }
            SubsamplingScaleImageView.this.invalidate();
        }

        @NonNull
        public AnimationBuilder withDuration(long j2) {
            this.duration = j2;
            return this;
        }

        @NonNull
        public AnimationBuilder withEasing(int i2) {
            if (SubsamplingScaleImageView.VALID_EASING_STYLES.contains(Integer.valueOf(i2))) {
                this.easing = i2;
                return this;
            }
            throw new IllegalArgumentException("Unknown easing type: " + i2);
        }

        @NonNull
        public AnimationBuilder withInterruptible(boolean z2) {
            this.interruptible = z2;
            return this;
        }

        @NonNull
        public AnimationBuilder withOnAnimationEventListener(OnAnimationEventListener onAnimationEventListener) {
            this.listener = onAnimationEventListener;
            return this;
        }

        private AnimationBuilder(PointF pointF) {
            this.duration = 500L;
            this.easing = 2;
            this.origin = 1;
            this.interruptible = true;
            this.panLimited = true;
            this.targetScale = SubsamplingScaleImageView.this.scale;
            this.targetSCenter = pointF;
            this.vFocus = null;
        }

        private AnimationBuilder(float f2) {
            this.duration = 500L;
            this.easing = 2;
            this.origin = 1;
            this.interruptible = true;
            this.panLimited = true;
            this.targetScale = f2;
            this.targetSCenter = SubsamplingScaleImageView.this.getCenter();
            this.vFocus = null;
        }

        private AnimationBuilder(float f2, PointF pointF) {
            this.duration = 500L;
            this.easing = 2;
            this.origin = 1;
            this.interruptible = true;
            this.panLimited = true;
            this.targetScale = f2;
            this.targetSCenter = pointF;
            this.vFocus = null;
        }

        private AnimationBuilder(float f2, PointF pointF, PointF pointF2) {
            this.duration = 500L;
            this.easing = 2;
            this.origin = 1;
            this.interruptible = true;
            this.panLimited = true;
            this.targetScale = f2;
            this.targetSCenter = pointF;
            this.vFocus = pointF2;
        }
    }

    public final void setImage(@NonNull ImageSource imageSource, ImageSource imageSource2, ImageViewState imageViewState) {
        if (imageSource != null) {
            reset(true);
            if (imageViewState != null) {
                restoreState(imageViewState);
            }
            if (imageSource2 != null) {
                if (imageSource.getBitmap() == null) {
                    if (imageSource.getSWidth() > 0 && imageSource.getSHeight() > 0) {
                        this.sWidth = imageSource.getSWidth();
                        this.sHeight = imageSource.getSHeight();
                        this.pRegion = imageSource2.getSRegion();
                        if (imageSource2.getBitmap() != null) {
                            this.bitmapIsCached = imageSource2.isCached();
                            onPreviewLoaded(imageSource2.getBitmap());
                        } else {
                            Uri uri = imageSource2.getUri();
                            if (uri == null && imageSource2.getResource() != null) {
                                uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + imageSource2.getResource());
                            }
                            execute(new BitmapLoadTask(this, getContext(), this.bitmapDecoderFactory, uri, true));
                        }
                    } else {
                        throw new IllegalArgumentException("Preview image cannot be used unless dimensions are provided for the main image");
                    }
                } else {
                    throw new IllegalArgumentException("Preview image cannot be used when a bitmap is provided for the main image");
                }
            }
            if (imageSource.getBitmap() != null && imageSource.getSRegion() != null) {
                onImageLoaded(Bitmap.createBitmap(imageSource.getBitmap(), imageSource.getSRegion().left, imageSource.getSRegion().top, imageSource.getSRegion().width(), imageSource.getSRegion().height()), 0, false);
                return;
            }
            if (imageSource.getBitmap() != null) {
                onImageLoaded(imageSource.getBitmap(), 0, imageSource.isCached());
                return;
            }
            this.sRegion = imageSource.getSRegion();
            Uri uri2 = imageSource.getUri();
            this.uri = uri2;
            if (uri2 == null && imageSource.getResource() != null) {
                this.uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + imageSource.getResource());
            }
            if (!imageSource.getTile() && this.sRegion == null) {
                execute(new BitmapLoadTask(this, getContext(), this.bitmapDecoderFactory, this.uri, false));
                return;
            } else {
                execute(new TilesInitTask(this, getContext(), this.regionDecoderFactory, this.uri));
                return;
            }
        }
        throw new NullPointerException("imageSource must not be null");
    }

    @Nullable
    public final PointF sourceToViewCoord(float f2, float f3, @NonNull PointF pointF) {
        if (this.vTranslate == null) {
            return null;
        }
        pointF.set(sourceToViewX(f2), sourceToViewY(f3));
        return pointF;
    }

    @Nullable
    public final PointF viewToSourceCoord(float f2, float f3, @NonNull PointF pointF) {
        if (this.vTranslate == null) {
            return null;
        }
        pointF.set(viewToSourceX(f2), viewToSourceY(f3));
        return pointF;
    }

    private void fitToBounds(boolean z2) {
        boolean z3;
        float f2 = 0.0f;
        if (this.vTranslate == null) {
            this.vTranslate = new PointF(0.0f, 0.0f);
            z3 = true;
        } else {
            z3 = false;
        }
        if (this.satTemp == null) {
            this.satTemp = new ScaleAndTranslate(f2, new PointF(0.0f, 0.0f));
        }
        this.satTemp.scale = this.scale;
        this.satTemp.vTranslate.set(this.vTranslate);
        fitToBounds(z2, this.satTemp);
        this.scale = this.satTemp.scale;
        this.vTranslate.set(this.satTemp.vTranslate);
        if (!z3 || this.minimumScaleType == 4) {
            return;
        }
        this.vTranslate.set(vTranslateForSCenter(sWidth() / 2, sHeight() / 2, this.scale));
    }

    public SubsamplingScaleImageView(Context context) {
        this(context, null);
    }
}
