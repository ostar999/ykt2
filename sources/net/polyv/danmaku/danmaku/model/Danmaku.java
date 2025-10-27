package net.polyv.danmaku.danmaku.model;

import net.polyv.danmaku.danmaku.util.DanmakuUtils;

/* loaded from: classes9.dex */
public class Danmaku extends BaseDanmaku {
    public Danmaku(CharSequence charSequence) {
        DanmakuUtils.fillText(this, charSequence);
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float getBottom() {
        return 0.0f;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float getLeft() {
        return 0.0f;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float[] getRectAtTime(IDisplayer iDisplayer, long j2) {
        return null;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float getRight() {
        return 0.0f;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public float getTop() {
        return 0.0f;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public int getType() {
        return 0;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public boolean isShown() {
        return false;
    }

    @Override // net.polyv.danmaku.danmaku.model.BaseDanmaku
    public void layout(IDisplayer iDisplayer, float f2, float f3) {
    }
}
