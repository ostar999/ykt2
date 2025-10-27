package com.plv.rtc.urtc.view;

import android.content.Context;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import com.plv.rtc.urtc.R;
import com.plv.rtc.urtc.listener.URTCOnFrameListener;
import com.ucloudrtclib.sdkengine.define.UCloudRtcRenderTextureView;
import com.ucloudrtclib.sdkengine.define.UCloudRtcRenderView;
import java.nio.ByteBuffer;
import org.wrtca.api.EglRenderer;

/* loaded from: classes5.dex */
public class URTCRendererViewWrapper {
    private View renderView;
    private Object renderer;

    public static class NoOpViewAdapter extends RenderViewAdapter {
        private NoOpViewAdapter() {
            super();
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void addFrameListener(URTCOnFrameListener uRTCOnFrameListener, float f2) {
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void init() {
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void release() {
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void setMirror(boolean z2) {
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void setMirrorOnlyRemote(boolean z2) {
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void setZOrderMediaOverlay(boolean z2) {
        }
    }

    public static abstract class RenderViewAdapter {
        private RenderViewAdapter() {
        }

        public static RenderViewAdapter of(View view) {
            return view instanceof SurfaceView ? new SurfaceViewAdapter((SurfaceView) view) : view instanceof TextureView ? new TextureViewAdapter((TextureView) view) : new NoOpViewAdapter();
        }

        public abstract void addFrameListener(URTCOnFrameListener uRTCOnFrameListener, float f2);

        public abstract void init();

        public abstract void release();

        public abstract void setMirror(boolean z2);

        public abstract void setMirrorOnlyRemote(boolean z2);

        public abstract void setZOrderMediaOverlay(boolean z2);
    }

    public static class SurfaceViewAdapter extends RenderViewAdapter {
        private UCloudRtcRenderView renderer;
        private SurfaceView view;

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void addFrameListener(final URTCOnFrameListener uRTCOnFrameListener, float f2) {
            this.renderer.addFrameListener(new EglRenderer.FrameListener() { // from class: com.plv.rtc.urtc.view.URTCRendererViewWrapper.SurfaceViewAdapter.1
                @Override // org.wrtca.api.EglRenderer.FrameListener
                public void onFrame(ByteBuffer byteBuffer, int i2, int i3) {
                    URTCOnFrameListener uRTCOnFrameListener2 = uRTCOnFrameListener;
                    if (uRTCOnFrameListener2 != null) {
                        uRTCOnFrameListener2.onFrame(byteBuffer, i2, i3);
                    }
                }
            }, f2);
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void init() {
            this.renderer.init();
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void release() {
            this.renderer.release();
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void setMirror(boolean z2) {
            this.renderer.setMirror(z2);
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void setMirrorOnlyRemote(boolean z2) {
            this.renderer.setMirrorOnlyRemote(z2);
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void setZOrderMediaOverlay(boolean z2) {
            this.view.setZOrderMediaOverlay(z2);
        }

        private SurfaceViewAdapter(SurfaceView surfaceView) {
            super();
            this.view = surfaceView;
            this.renderer = (UCloudRtcRenderView) URTCRendererViewWrapper.getRendererView(surfaceView).renderer;
        }
    }

    public static class TextureViewAdapter extends RenderViewAdapter {
        private UCloudRtcRenderTextureView renderer;
        private TextureView view;

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void addFrameListener(final URTCOnFrameListener uRTCOnFrameListener, float f2) {
            this.renderer.addFrameListener(new EglRenderer.FrameListener() { // from class: com.plv.rtc.urtc.view.URTCRendererViewWrapper.TextureViewAdapter.1
                @Override // org.wrtca.api.EglRenderer.FrameListener
                public void onFrame(ByteBuffer byteBuffer, int i2, int i3) {
                    URTCOnFrameListener uRTCOnFrameListener2 = uRTCOnFrameListener;
                    if (uRTCOnFrameListener2 != null) {
                        uRTCOnFrameListener2.onFrame(byteBuffer, i2, i3);
                    }
                }
            }, f2);
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void init() {
            this.renderer.init();
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void release() {
            this.renderer.release();
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void setMirror(boolean z2) {
            this.renderer.setMirror(z2);
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void setMirrorOnlyRemote(boolean z2) {
            this.renderer.setMirrorOnlyRemote(z2);
        }

        @Override // com.plv.rtc.urtc.view.URTCRendererViewWrapper.RenderViewAdapter
        public void setZOrderMediaOverlay(boolean z2) {
        }

        private TextureViewAdapter(TextureView textureView) {
            super();
            this.view = textureView;
            this.renderer = (UCloudRtcRenderTextureView) URTCRendererViewWrapper.getRendererView(textureView).renderer;
        }
    }

    public URTCRendererViewWrapper(Context context) {
        this(context, false);
    }

    public void addFrameListener(URTCOnFrameListener uRTCOnFrameListener, float f2) {
        RenderViewAdapter.of(this.renderView).addFrameListener(uRTCOnFrameListener, f2);
    }

    public Object getRenderer() {
        return this.renderer;
    }

    public View getRendererView() {
        return this.renderView;
    }

    public SurfaceView getSurfaceView() {
        return (SurfaceView) getRendererView();
    }

    public Object getTag() {
        return this.renderView.getTag();
    }

    public TextureView getTextureView() {
        return (TextureView) getRendererView();
    }

    public void init() {
        RenderViewAdapter.of(this.renderView).init();
    }

    public void release() {
        RenderViewAdapter.of(this.renderView).release();
    }

    public void setMirror(boolean z2) {
        RenderViewAdapter.of(this.renderView).setMirror(z2);
    }

    public void setMirrorOnlyRemote(boolean z2) {
        RenderViewAdapter.of(this.renderView).setMirrorOnlyRemote(z2);
    }

    public void setZOrderMediaOverlay(boolean z2) {
        RenderViewAdapter.of(this.renderView).setZOrderMediaOverlay(z2);
    }

    public URTCRendererViewWrapper(Context context, boolean z2) {
        if (z2) {
            TextureView textureView = new TextureView(context);
            this.renderView = textureView;
            this.renderer = new UCloudRtcRenderTextureView(textureView);
        } else {
            UCloudRtcRenderView uCloudRtcRenderView = new UCloudRtcRenderView(context);
            this.renderView = uCloudRtcRenderView;
            this.renderer = uCloudRtcRenderView;
        }
        this.renderView.setTag(R.id.tag_render_view, this);
    }

    public static URTCRendererViewWrapper getRendererView(SurfaceView surfaceView) {
        if (surfaceView == null) {
            return null;
        }
        Object tag = surfaceView.getTag(R.id.tag_render_view);
        if (tag instanceof URTCRendererViewWrapper) {
            return (URTCRendererViewWrapper) tag;
        }
        return null;
    }

    public static URTCRendererViewWrapper getRendererView(TextureView textureView) {
        if (textureView == null) {
            return null;
        }
        Object tag = textureView.getTag(R.id.tag_render_view);
        if (tag instanceof URTCRendererViewWrapper) {
            return (URTCRendererViewWrapper) tag;
        }
        return null;
    }
}
