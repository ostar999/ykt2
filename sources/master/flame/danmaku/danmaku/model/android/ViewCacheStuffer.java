package master.flame.danmaku.danmaku.model.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.SparseArray;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.android.AndroidDisplayer;
import master.flame.danmaku.danmaku.model.android.ViewCacheStuffer.ViewHolder;

/* loaded from: classes8.dex */
public abstract class ViewCacheStuffer<VH extends ViewHolder> extends BaseCacheStuffer {
    public static final int CACHE_VIEW_TYPE = -3;
    public static final int DRAW_VIEW_TYPE = -3;
    public static final int INVALID_TYPE = -1;
    public static final int MEASURE_VIEW_TYPE = -2;
    private SparseArray<List<VH>> mViewHolderArray = new SparseArray<>();
    private final int mMaximumWidthPixels = -1;
    private final int mMaximumHeightPixels = -1;

    public static abstract class ViewHolder {
        protected final View itemView;

        public ViewHolder(View view) {
            if (view == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view;
        }

        public void draw(Canvas canvas, AndroidDisplayer.DisplayerConfig displayerConfig) {
            this.itemView.draw(canvas);
        }

        public int getMeasureHeight() {
            return this.itemView.getMeasuredHeight();
        }

        public int getMeasureWidth() {
            return this.itemView.getMeasuredWidth();
        }

        public void layout(int i2, int i3, int i4, int i5) {
            this.itemView.layout(i2, i3, i4, i5);
        }

        public void measure(int i2, int i3) {
            this.itemView.measure(i2, i3);
        }
    }

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void clearCaches() {
    }

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void drawDanmaku(BaseDanmaku baseDanmaku, Canvas canvas, float f2, float f3, boolean z2, AndroidDisplayer.DisplayerConfig displayerConfig) {
        VH vh;
        int itemViewType = getItemViewType(baseDanmaku.index, baseDanmaku);
        List<VH> list = this.mViewHolderArray.get(itemViewType);
        boolean z3 = true;
        if (list != null) {
            vh = list.get(z2 ? 1 : 2);
        } else {
            vh = null;
        }
        if (vh == null) {
            return;
        }
        displayerConfig.definePaintParams(z2);
        TextPaint paint = displayerConfig.getPaint(baseDanmaku, z2);
        displayerConfig.applyPaintConfig(baseDanmaku, paint, false);
        onBindViewHolder(itemViewType, vh, baseDanmaku, displayerConfig, paint);
        vh.measure(View.MeasureSpec.makeMeasureSpec(Math.round(baseDanmaku.paintWidth), 1073741824), View.MeasureSpec.makeMeasureSpec(Math.round(baseDanmaku.paintHeight), 1073741824));
        if (z2) {
            z3 = false;
        } else {
            canvas.save();
            canvas.translate(f2, f3);
        }
        if (baseDanmaku.underlineColor != 0) {
            Paint underlinePaint = displayerConfig.getUnderlinePaint(baseDanmaku);
            float f4 = (baseDanmaku.paintHeight + f3) - displayerConfig.UNDERLINE_HEIGHT;
            canvas.drawLine(f2, f4, f2 + baseDanmaku.paintWidth, f4, underlinePaint);
        }
        if (baseDanmaku.borderColor != 0) {
            canvas.drawRect(f2, f3, f2 + baseDanmaku.paintWidth, f3 + baseDanmaku.paintHeight, displayerConfig.getBorderPaint(baseDanmaku));
        }
        vh.layout(0, 0, (int) baseDanmaku.paintWidth, (int) baseDanmaku.paintHeight);
        vh.draw(canvas, displayerConfig);
        if (z3) {
            canvas.restore();
        }
    }

    public int getItemViewType(int i2, BaseDanmaku baseDanmaku) {
        return 0;
    }

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void measure(BaseDanmaku baseDanmaku, TextPaint textPaint, boolean z2) {
        int itemViewType = getItemViewType(baseDanmaku.index, baseDanmaku);
        List arrayList = this.mViewHolderArray.get(itemViewType);
        if (arrayList == null) {
            arrayList = new ArrayList();
            arrayList.add(onCreateViewHolder(itemViewType));
            arrayList.add(onCreateViewHolder(itemViewType));
            arrayList.add(onCreateViewHolder(itemViewType));
            this.mViewHolderArray.put(itemViewType, arrayList);
        }
        ViewHolder viewHolder = (ViewHolder) arrayList.get(0);
        onBindViewHolder(itemViewType, viewHolder, baseDanmaku, null, textPaint);
        viewHolder.measure(View.MeasureSpec.makeMeasureSpec(this.mMaximumWidthPixels, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(this.mMaximumHeightPixels, Integer.MIN_VALUE));
        viewHolder.layout(0, 0, viewHolder.getMeasureWidth(), viewHolder.getMeasureHeight());
        baseDanmaku.paintWidth = viewHolder.getMeasureWidth();
        baseDanmaku.paintHeight = viewHolder.getMeasureHeight();
    }

    public abstract void onBindViewHolder(int i2, VH vh, BaseDanmaku baseDanmaku, AndroidDisplayer.DisplayerConfig displayerConfig, TextPaint textPaint);

    public abstract VH onCreateViewHolder(int i2);

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void releaseResource(BaseDanmaku baseDanmaku) {
        super.releaseResource(baseDanmaku);
        baseDanmaku.tag = null;
    }
}
