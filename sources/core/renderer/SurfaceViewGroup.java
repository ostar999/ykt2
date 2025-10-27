package core.renderer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import c.l;
import core.interfaces.DataReceiver;
import core.interfaces.ScreenShot;
import core.renderer.CoreSurfaceViewRenderer;
import j.d;
import java.nio.ByteBuffer;
import org.wrtca.api.EglRenderer;

/* loaded from: classes8.dex */
public class SurfaceViewGroup extends FrameLayout {
    private static final String TAG = "==SurfaceViewGroup==";
    public Context mContext;
    public DataReceiver mDataReceiver;
    public int[] mIconIds;
    public CoreSurfaceViewRenderer.PositionChanged mPositionChangedListener;
    public RemoteOpTrigger mRemotTrigger;
    public LinearLayout mRemoteOp;
    public CoreSurfaceViewRenderer mSurface;
    public ImageView muteAudio;
    public ImageView muteVideo;

    public interface RemoteOpTrigger {
        void onRemoteAudio(View view, SurfaceViewGroup surfaceViewGroup);

        void onRemoteVideo(View view, SurfaceViewGroup surfaceViewGroup);
    }

    public SurfaceViewGroup(Context context) {
        super(context);
        this.mPositionChangedListener = new CoreSurfaceViewRenderer.PositionChanged() { // from class: core.renderer.SurfaceViewGroup.1
            @Override // core.renderer.CoreSurfaceViewRenderer.PositionChanged
            public void onPositionChanged(int i2) {
                SurfaceViewGroup.this.adjustRemoteOpBottom(i2);
            }
        };
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustRemoteOpBottom(int i2) {
        LinearLayout linearLayout = this.mRemoteOp;
        if (linearLayout != null) {
            ((FrameLayout.LayoutParams) linearLayout.getLayoutParams()).bottomMargin = i2;
        }
    }

    private void initRemotePanel(boolean z2, int[] iArr) {
        LinearLayout linearLayout = new LinearLayout(this.mContext);
        this.mRemoteOp = linearLayout;
        linearLayout.setOrientation(0);
        if (z2) {
            this.mRemoteOp.setVisibility(4);
        } else {
            this.mRemoteOp.setVisibility(0);
        }
        int iA = l.a(this.mContext, 20.0f);
        ImageView imageView = new ImageView(this.mContext);
        this.muteVideo = imageView;
        if (iArr != null && iArr.length >= 2) {
            imageView.setId(iArr[0]);
        }
        int iA2 = l.a(this.mContext, 3.0f);
        this.muteVideo.setPadding(iA2, iA2, iA2, iA2);
        this.muteVideo.setBackgroundResource(this.mIconIds[4]);
        this.muteVideo.setImageResource(this.mIconIds[0]);
        this.muteVideo.setOnClickListener(new View.OnClickListener() { // from class: core.renderer.SurfaceViewGroup.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SurfaceViewGroup surfaceViewGroup = SurfaceViewGroup.this;
                RemoteOpTrigger remoteOpTrigger = surfaceViewGroup.mRemotTrigger;
                if (remoteOpTrigger != null) {
                    remoteOpTrigger.onRemoteVideo(surfaceViewGroup.muteVideo, surfaceViewGroup);
                }
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(iA, iA);
        layoutParams.setMargins(0, 0, l.a(this.mContext, 10.0f), 0);
        this.mRemoteOp.addView(this.muteVideo, layoutParams);
        ImageView imageView2 = new ImageView(this.mContext);
        this.muteAudio = imageView2;
        if (iArr != null && iArr.length >= 2) {
            imageView2.setId(iArr[1]);
        }
        this.muteAudio.setPadding(iA2, iA2, iA2, iA2);
        this.muteAudio.setBackgroundResource(this.mIconIds[4]);
        this.muteAudio.setImageResource(this.mIconIds[1]);
        this.muteAudio.setOnClickListener(new View.OnClickListener() { // from class: core.renderer.SurfaceViewGroup.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SurfaceViewGroup surfaceViewGroup = SurfaceViewGroup.this;
                RemoteOpTrigger remoteOpTrigger = surfaceViewGroup.mRemotTrigger;
                if (remoteOpTrigger != null) {
                    remoteOpTrigger.onRemoteAudio(surfaceViewGroup.muteAudio, surfaceViewGroup);
                }
            }
        });
        this.mRemoteOp.addView(this.muteAudio, new LinearLayout.LayoutParams(iA, iA));
    }

    public CoreSurfaceViewRenderer getSurfaceView() {
        return this.mSurface;
    }

    public void init(boolean z2, CoreSurfaceViewRenderer coreSurfaceViewRenderer) {
        if (coreSurfaceViewRenderer == null) {
            this.mSurface = new CoreSurfaceViewRenderer(this.mContext);
        } else {
            this.mSurface = coreSurfaceViewRenderer;
        }
        this.mSurface.setPositionChangedListener(this.mPositionChangedListener);
        this.mSurface.init(d.d().o(), null, this.mDataReceiver);
        this.mSurface.setEnableHardwareScaler(true);
        this.mSurface.setNeedFullScreen(z2);
        setBackgroundColor(-16777216);
        addView(this.mSurface, new FrameLayout.LayoutParams(-1, -1));
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.mSurface != null) {
            refresh();
        }
    }

    public void refresh() {
        CoreSurfaceViewRenderer coreSurfaceViewRenderer = this.mSurface;
        if (coreSurfaceViewRenderer != null) {
            coreSurfaceViewRenderer.clearImage();
        }
    }

    public void refreshRemoteAudio(boolean z2) {
        if (z2) {
            this.muteAudio.setImageResource(this.mIconIds[3]);
        } else {
            this.muteAudio.setImageResource(this.mIconIds[1]);
        }
    }

    public void refreshRemoteOp(int i2) {
        LinearLayout linearLayout = this.mRemoteOp;
        if (linearLayout != null) {
            linearLayout.setVisibility(i2);
        }
    }

    public void refreshRemoteVideo(boolean z2) {
        if (z2) {
            this.muteVideo.setImageResource(this.mIconIds[2]);
        } else {
            this.muteVideo.setImageResource(this.mIconIds[0]);
        }
    }

    public void release() {
        CoreSurfaceViewRenderer coreSurfaceViewRenderer = this.mSurface;
        if (coreSurfaceViewRenderer != null) {
            coreSurfaceViewRenderer.release();
            this.mSurface = null;
        }
    }

    public void setFrameCallBack(DataReceiver dataReceiver) {
        if (dataReceiver != null) {
            this.mDataReceiver = dataReceiver;
        }
    }

    public void setMirror(boolean z2) {
        CoreSurfaceViewRenderer coreSurfaceViewRenderer = this.mSurface;
        if (coreSurfaceViewRenderer != null) {
            coreSurfaceViewRenderer.setMirror(z2);
        }
    }

    public void setMirrorOnlyRemote(boolean z2) {
        CoreSurfaceViewRenderer coreSurfaceViewRenderer = this.mSurface;
        if (coreSurfaceViewRenderer != null) {
            coreSurfaceViewRenderer.setMirrorOnlyRemote(z2);
        }
    }

    public void setScalingType(int i2) {
        CoreSurfaceViewRenderer coreSurfaceViewRenderer = this.mSurface;
        if (coreSurfaceViewRenderer != null) {
            coreSurfaceViewRenderer.setScaleType(i2);
        }
    }

    public void setScreenShotBack(ScreenShot screenShot) {
        if (screenShot == null || this.mSurface == null) {
            return;
        }
        this.mSurface.addFrameListener(new EglRenderer.FrameListener(screenShot) { // from class: core.renderer.SurfaceViewGroup.4
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

    public void setZOrderMediaOverlay(boolean z2) {
        this.mSurface.setZOrderMediaOverlay(z2);
    }

    public SurfaceViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPositionChangedListener = new CoreSurfaceViewRenderer.PositionChanged() { // from class: core.renderer.SurfaceViewGroup.1
            @Override // core.renderer.CoreSurfaceViewRenderer.PositionChanged
            public void onPositionChanged(int i2) {
                SurfaceViewGroup.this.adjustRemoteOpBottom(i2);
            }
        };
        this.mContext = context;
    }

    public void init(boolean z2, int[] iArr, RemoteOpTrigger remoteOpTrigger, int[] iArr2, CoreSurfaceViewRenderer coreSurfaceViewRenderer) {
        if (coreSurfaceViewRenderer == null) {
            this.mSurface = new CoreSurfaceViewRenderer(this.mContext);
        } else {
            this.mSurface = coreSurfaceViewRenderer;
        }
        this.mSurface.setPositionChangedListener(this.mPositionChangedListener);
        this.mSurface.init(d.d().o(), null, this.mDataReceiver);
        this.mSurface.setEnableHardwareScaler(true);
        this.mSurface.setNeedFullScreen(z2);
        setBackgroundColor(-16777216);
        addView(this.mSurface, new FrameLayout.LayoutParams(-1, -1));
        this.mIconIds = iArr;
        this.mRemotTrigger = remoteOpTrigger;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 85;
        initRemotePanel(z2, iArr2);
        addView(this.mRemoteOp, layoutParams);
    }
}
