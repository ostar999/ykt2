package com.plv.linkmic.processor.b;

import android.view.SurfaceView;

/* loaded from: classes4.dex */
public class d {
    private String T;
    private SurfaceView U;
    private boolean V;
    private int renderMode = 1;
    private int streamType;

    public d(String str) {
        this.T = str;
    }

    public void a(String str) {
        this.T = str;
    }

    public void b(boolean z2) {
        this.V = z2;
    }

    public String f() {
        return this.T;
    }

    public SurfaceView g() {
        return this.U;
    }

    public int getRenderMode() {
        return this.renderMode;
    }

    public int getStreamType() {
        return this.streamType;
    }

    public boolean h() {
        return this.V;
    }

    public void setRenderMode(int i2) {
        this.renderMode = i2;
    }

    public void setStreamType(int i2) {
        this.streamType = i2;
    }

    public void a(SurfaceView surfaceView) {
        this.U = surfaceView;
    }
}
