package core.renderer;

import android.os.Handler;
import android.os.Looper;
import android.view.TextureView;
import c.h;
import core.interfaces.DataReceiver;
import core.interfaces.ScreenShot;
import j.d;
import java.nio.ByteBuffer;
import org.wrtca.api.EglBase;
import org.wrtca.api.EglRenderer;
import org.wrtca.api.RendererCommon;
import org.wrtca.video.TextureViewRenderer;

/* loaded from: classes8.dex */
public class CoreTextureViewRenderer extends TextureViewRenderer implements RendererCommon.RendererEvents {
    private static final String TAG = "CoreTextureViewRenderer";
    public Handler mMainHandler;

    public CoreTextureViewRenderer(TextureView textureView) {
        super(textureView);
        this.mMainHandler = null;
        this.mMainHandler = new Handler(Looper.getMainLooper());
    }

    public int getScaleType() {
        return this.mScaleType;
    }

    public void init() {
        super.init(d.d().o(), this, null);
    }

    @Override // org.wrtca.video.TextureViewRenderer
    public void release() {
        super.release();
        if (this.mMainHandler != null) {
            this.mMainHandler = null;
        }
    }

    @Override // org.wrtca.video.TextureViewRenderer
    public void setScaleType(int i2) {
        if (i2 >= 0) {
            RendererCommon.ScalingType scalingType = RendererCommon.ScalingType.SCALE_ASPECT_FILL;
            if (i2 == 0) {
                scalingType = RendererCommon.ScalingType.SCALE_ASPECT_FIT;
            }
            this.mScaleType = i2;
            h.a(TAG, " renderview setScaleType" + i2);
            super.setScalingType(scalingType, scalingType);
        }
    }

    public void setScreenShotBack(ScreenShot screenShot) {
        if (screenShot != null) {
            addFrameListener(new EglRenderer.FrameListener(screenShot) { // from class: core.renderer.CoreTextureViewRenderer.1
                public ScreenShot mSceenShot;
                public final /* synthetic */ ScreenShot val$callBack;

                {
                    this.val$callBack = screenShot;
                    this.mSceenShot = screenShot;
                }

                @Override // org.wrtca.api.EglRenderer.FrameListener
                public void onFrame(ByteBuffer byteBuffer, int i2, int i3) {
                    this.mSceenShot.onReceiveRGBAData(byteBuffer, i2, i3);
                }
            }, 1.0f);
        }
    }

    @Override // org.wrtca.video.TextureViewRenderer
    public void init(EglBase.Context context, RendererCommon.RendererEvents rendererEvents, DataReceiver dataReceiver) {
        h.a(TAG, "init egl texture view ");
        super.init(context, this, dataReceiver);
        RendererCommon.ScalingType scalingType = RendererCommon.ScalingType.SCALE_ASPECT_FIT;
        super.setScalingType(scalingType, scalingType);
    }
}
