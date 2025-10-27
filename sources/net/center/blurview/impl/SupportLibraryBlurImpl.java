package net.center.blurview.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/* loaded from: classes9.dex */
public class SupportLibraryBlurImpl implements BlurImpl {
    static Boolean DEBUG;
    private Allocation mBlurInput;
    private Allocation mBlurOutput;
    private ScriptIntrinsicBlur mBlurScript;
    private RenderScript mRenderScript;

    public static boolean isDebug(Context context) {
        if (DEBUG == null && context != null) {
            DEBUG = Boolean.valueOf((context.getApplicationInfo().flags & 2) != 0);
        }
        return DEBUG.equals(Boolean.TRUE);
    }

    @Override // net.center.blurview.impl.BlurImpl
    public void blur(Bitmap bitmap, Bitmap bitmap2) {
        this.mBlurInput.copyFrom(bitmap);
        this.mBlurScript.setInput(this.mBlurInput);
        this.mBlurScript.forEach(this.mBlurOutput);
        this.mBlurOutput.copyTo(bitmap2);
    }

    @Override // net.center.blurview.impl.BlurImpl
    public boolean prepare(Context context, Bitmap bitmap, float f2) {
        if (this.mRenderScript == null) {
            try {
                RenderScript renderScriptCreate = RenderScript.create(context);
                this.mRenderScript = renderScriptCreate;
                this.mBlurScript = ScriptIntrinsicBlur.create(renderScriptCreate, Element.U8_4(renderScriptCreate));
            } catch (RSRuntimeException e2) {
                if (isDebug(context)) {
                    throw e2;
                }
                release();
                return false;
            }
        }
        this.mBlurScript.setRadius(f2);
        Allocation allocationCreateFromBitmap = Allocation.createFromBitmap(this.mRenderScript, bitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
        this.mBlurInput = allocationCreateFromBitmap;
        this.mBlurOutput = Allocation.createTyped(this.mRenderScript, allocationCreateFromBitmap.getType());
        return true;
    }

    @Override // net.center.blurview.impl.BlurImpl
    public void release() {
        Allocation allocation = this.mBlurInput;
        if (allocation != null) {
            allocation.destroy();
            this.mBlurInput = null;
        }
        Allocation allocation2 = this.mBlurOutput;
        if (allocation2 != null) {
            allocation2.destroy();
            this.mBlurOutput = null;
        }
        ScriptIntrinsicBlur scriptIntrinsicBlur = this.mBlurScript;
        if (scriptIntrinsicBlur != null) {
            scriptIntrinsicBlur.destroy();
            this.mBlurScript = null;
        }
        RenderScript renderScript = this.mRenderScript;
        if (renderScript != null) {
            renderScript.destroy();
            this.mRenderScript = null;
        }
    }
}
