package com.opensource.svgaplayer;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.widget.ImageView;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.opensource.svgaplayer.drawer.SVGACanvasDrawer;
import com.opensource.svgaplayer.entities.SVGAAudioEntity;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010!\u001a\u00020\"J\u0012\u0010#\u001a\u00020\"2\b\u0010$\u001a\u0004\u0018\u00010%H\u0016J\b\u0010&\u001a\u00020\u000fH\u0016J\u0006\u0010'\u001a\u00020\"J\u0006\u0010(\u001a\u00020\"J\u0010\u0010)\u001a\u00020\"2\u0006\u0010*\u001a\u00020\u000fH\u0016J\u0012\u0010+\u001a\u00020\"2\b\u0010,\u001a\u0004\u0018\u00010-H\u0016J\u0006\u0010.\u001a\u00020\"R$\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR$\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 ¨\u0006/"}, d2 = {"Lcom/opensource/svgaplayer/SVGADrawable;", "Landroid/graphics/drawable/Drawable;", "videoItem", "Lcom/opensource/svgaplayer/SVGAVideoEntity;", "(Lcom/opensource/svgaplayer/SVGAVideoEntity;)V", "dynamicItem", "Lcom/opensource/svgaplayer/SVGADynamicEntity;", "(Lcom/opensource/svgaplayer/SVGAVideoEntity;Lcom/opensource/svgaplayer/SVGADynamicEntity;)V", "value", "", "cleared", "getCleared", "()Z", "setCleared$com_opensource_svgaplayer", "(Z)V", "", "currentFrame", "getCurrentFrame", "()I", "setCurrentFrame$com_opensource_svgaplayer", "(I)V", "drawer", "Lcom/opensource/svgaplayer/drawer/SVGACanvasDrawer;", "getDynamicItem", "()Lcom/opensource/svgaplayer/SVGADynamicEntity;", "scaleType", "Landroid/widget/ImageView$ScaleType;", "getScaleType", "()Landroid/widget/ImageView$ScaleType;", "setScaleType", "(Landroid/widget/ImageView$ScaleType;)V", "getVideoItem", "()Lcom/opensource/svgaplayer/SVGAVideoEntity;", PLVDocumentMarkToolType.CLEAR, "", "draw", "canvas", "Landroid/graphics/Canvas;", "getOpacity", "pause", "resume", "setAlpha", "alpha", "setColorFilter", "colorFilter", "Landroid/graphics/ColorFilter;", "stop", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGADrawable extends Drawable {
    private boolean cleared;
    private int currentFrame;
    private final SVGACanvasDrawer drawer;

    @NotNull
    private final SVGADynamicEntity dynamicItem;

    @NotNull
    private ImageView.ScaleType scaleType;

    @NotNull
    private final SVGAVideoEntity videoItem;

    public SVGADrawable(@NotNull SVGAVideoEntity videoItem, @NotNull SVGADynamicEntity dynamicItem) {
        Intrinsics.checkParameterIsNotNull(videoItem, "videoItem");
        Intrinsics.checkParameterIsNotNull(dynamicItem, "dynamicItem");
        this.videoItem = videoItem;
        this.dynamicItem = dynamicItem;
        this.cleared = true;
        this.scaleType = ImageView.ScaleType.MATRIX;
        this.drawer = new SVGACanvasDrawer(videoItem, dynamicItem);
    }

    public final void clear() {
        for (SVGAAudioEntity sVGAAudioEntity : this.videoItem.getAudioList$com_opensource_svgaplayer()) {
            Integer playID = sVGAAudioEntity.getPlayID();
            if (playID != null) {
                int iIntValue = playID.intValue();
                SVGASoundManager sVGASoundManager = SVGASoundManager.INSTANCE;
                if (sVGASoundManager.isInit$com_opensource_svgaplayer()) {
                    sVGASoundManager.stop$com_opensource_svgaplayer(iIntValue);
                } else {
                    SoundPool soundPool$com_opensource_svgaplayer = this.videoItem.getSoundPool();
                    if (soundPool$com_opensource_svgaplayer != null) {
                        soundPool$com_opensource_svgaplayer.stop(iIntValue);
                    }
                }
            }
            sVGAAudioEntity.setPlayID(null);
        }
        this.videoItem.clear();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@Nullable Canvas canvas) {
        if (this.cleared || canvas == null) {
            return;
        }
        this.drawer.drawFrame(canvas, this.currentFrame, this.scaleType);
    }

    public final boolean getCleared() {
        return this.cleared;
    }

    public final int getCurrentFrame() {
        return this.currentFrame;
    }

    @NotNull
    public final SVGADynamicEntity getDynamicItem() {
        return this.dynamicItem;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @NotNull
    public final ImageView.ScaleType getScaleType() {
        return this.scaleType;
    }

    @NotNull
    public final SVGAVideoEntity getVideoItem() {
        return this.videoItem;
    }

    public final void pause() {
        Iterator<T> it = this.videoItem.getAudioList$com_opensource_svgaplayer().iterator();
        while (it.hasNext()) {
            Integer playID = ((SVGAAudioEntity) it.next()).getPlayID();
            if (playID != null) {
                int iIntValue = playID.intValue();
                SVGASoundManager sVGASoundManager = SVGASoundManager.INSTANCE;
                if (sVGASoundManager.isInit$com_opensource_svgaplayer()) {
                    sVGASoundManager.pause$com_opensource_svgaplayer(iIntValue);
                } else {
                    SoundPool soundPool$com_opensource_svgaplayer = this.videoItem.getSoundPool();
                    if (soundPool$com_opensource_svgaplayer != null) {
                        soundPool$com_opensource_svgaplayer.pause(iIntValue);
                    }
                }
            }
        }
    }

    public final void resume() {
        Iterator<T> it = this.videoItem.getAudioList$com_opensource_svgaplayer().iterator();
        while (it.hasNext()) {
            Integer playID = ((SVGAAudioEntity) it.next()).getPlayID();
            if (playID != null) {
                int iIntValue = playID.intValue();
                SVGASoundManager sVGASoundManager = SVGASoundManager.INSTANCE;
                if (sVGASoundManager.isInit$com_opensource_svgaplayer()) {
                    sVGASoundManager.resume$com_opensource_svgaplayer(iIntValue);
                } else {
                    SoundPool soundPool$com_opensource_svgaplayer = this.videoItem.getSoundPool();
                    if (soundPool$com_opensource_svgaplayer != null) {
                        soundPool$com_opensource_svgaplayer.resume(iIntValue);
                    }
                }
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
    }

    public final void setCleared$com_opensource_svgaplayer(boolean z2) {
        if (this.cleared == z2) {
            return;
        }
        this.cleared = z2;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public final void setCurrentFrame$com_opensource_svgaplayer(int i2) {
        if (this.currentFrame == i2) {
            return;
        }
        this.currentFrame = i2;
        invalidateSelf();
    }

    public final void setScaleType(@NotNull ImageView.ScaleType scaleType) {
        Intrinsics.checkParameterIsNotNull(scaleType, "<set-?>");
        this.scaleType = scaleType;
    }

    public final void stop() {
        Iterator<T> it = this.videoItem.getAudioList$com_opensource_svgaplayer().iterator();
        while (it.hasNext()) {
            Integer playID = ((SVGAAudioEntity) it.next()).getPlayID();
            if (playID != null) {
                int iIntValue = playID.intValue();
                SVGASoundManager sVGASoundManager = SVGASoundManager.INSTANCE;
                if (sVGASoundManager.isInit$com_opensource_svgaplayer()) {
                    sVGASoundManager.stop$com_opensource_svgaplayer(iIntValue);
                } else {
                    SoundPool soundPool$com_opensource_svgaplayer = this.videoItem.getSoundPool();
                    if (soundPool$com_opensource_svgaplayer != null) {
                        soundPool$com_opensource_svgaplayer.stop(iIntValue);
                    }
                }
            }
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SVGADrawable(@NotNull SVGAVideoEntity videoItem) {
        this(videoItem, new SVGADynamicEntity());
        Intrinsics.checkParameterIsNotNull(videoItem, "videoItem");
    }
}
