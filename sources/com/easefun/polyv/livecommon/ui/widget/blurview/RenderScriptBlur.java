package com.easefun.polyv.livecommon.ui.widget.blurview;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* loaded from: classes3.dex */
public final class RenderScriptBlur implements BlurAlgorithm {
    private final ScriptIntrinsicBlur blurScript;
    private Allocation outAllocation;
    private final RenderScript renderScript;
    private int lastBitmapWidth = -1;
    private int lastBitmapHeight = -1;

    @RequiresApi(api = 17)
    public RenderScriptBlur(Context context) {
        RenderScript renderScriptCreate = RenderScript.create(context);
        this.renderScript = renderScriptCreate;
        this.blurScript = ScriptIntrinsicBlur.create(renderScriptCreate, Element.U8_4(renderScriptCreate));
    }

    private boolean canReuseAllocation(Bitmap bitmap) {
        return bitmap.getHeight() == this.lastBitmapHeight && bitmap.getWidth() == this.lastBitmapWidth;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurAlgorithm
    @RequiresApi(api = 17)
    public final Bitmap blur(Bitmap bitmap, float blurRadius) {
        Allocation allocationCreateFromBitmap = Allocation.createFromBitmap(this.renderScript, bitmap);
        if (!canReuseAllocation(bitmap)) {
            Allocation allocation = this.outAllocation;
            if (allocation != null) {
                allocation.destroy();
            }
            this.outAllocation = Allocation.createTyped(this.renderScript, allocationCreateFromBitmap.getType());
            this.lastBitmapWidth = bitmap.getWidth();
            this.lastBitmapHeight = bitmap.getHeight();
        }
        this.blurScript.setRadius(blurRadius);
        this.blurScript.setInput(allocationCreateFromBitmap);
        this.blurScript.forEach(this.outAllocation);
        this.outAllocation.copyTo(bitmap);
        allocationCreateFromBitmap.destroy();
        return bitmap;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurAlgorithm
    public boolean canModifyBitmap() {
        return true;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurAlgorithm
    public final void destroy() {
        this.blurScript.destroy();
        this.renderScript.destroy();
        Allocation allocation = this.outAllocation;
        if (allocation != null) {
            allocation.destroy();
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.blurview.BlurAlgorithm
    @NonNull
    public Bitmap.Config getSupportedBitmapConfig() {
        return Bitmap.Config.ARGB_8888;
    }
}
