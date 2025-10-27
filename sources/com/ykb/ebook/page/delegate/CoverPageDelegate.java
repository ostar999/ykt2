package com.ykb.ebook.page.delegate;

import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.page.PageDirection;
import com.ykb.ebook.weight.ReadView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\bH\u0016J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\u0012\u001a\u00020\bH\u0016J\u0018\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u000fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/ykb/ebook/page/delegate/CoverPageDelegate;", "Lcom/ykb/ebook/page/delegate/HorizontalPageDelegate;", "readView", "Lcom/ykb/ebook/weight/ReadView;", "(Lcom/ykb/ebook/weight/ReadView;)V", "shadowDrawableR", "Landroid/graphics/drawable/GradientDrawable;", "addShadow", "", "left", "", "canvas", "Landroid/graphics/Canvas;", "onAnimStart", "animationSpeed", "", "onAnimStop", "onDraw", "setBitmap", "setViewSize", "width", "height", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCoverPageDelegate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CoverPageDelegate.kt\ncom/ykb/ebook/page/delegate/CoverPageDelegate\n+ 2 Canvas.kt\nandroidx/core/graphics/CanvasKt\n*L\n1#1,121:1\n42#2,13:122\n195#2,8:135\n42#2,13:143\n42#2,13:156\n*S KotlinDebug\n*F\n+ 1 CoverPageDelegate.kt\ncom/ykb/ebook/page/delegate/CoverPageDelegate\n*L\n40#1:122,13\n50#1:135,8\n53#1:143,13\n82#1:156,13\n*E\n"})
/* loaded from: classes7.dex */
public final class CoverPageDelegate extends HorizontalPageDelegate {

    @NotNull
    private final GradientDrawable shadowDrawableR;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PageDirection.values().length];
            try {
                iArr[PageDirection.PREV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PageDirection.NEXT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CoverPageDelegate(@NotNull ReadView readView) {
        super(readView);
        Intrinsics.checkNotNullParameter(readView, "readView");
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{1712394513, 0});
        this.shadowDrawableR = gradientDrawable;
        gradientDrawable.setGradientType(0);
    }

    private final void addShadow(float left, Canvas canvas) {
        if (left == 0.0f) {
            return;
        }
        if (left < 0.0f) {
            left += getViewWidth();
        }
        int iSave = canvas.save();
        canvas.translate(left, 0.0f);
        try {
            this.shadowDrawableR.draw(canvas);
        } finally {
            canvas.restoreToCount(iSave);
        }
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onAnimStart(int animationSpeed) {
        float viewWidth;
        if (WhenMappings.$EnumSwitchMapping$0[getMDirection().ordinal()] != 2) {
            viewWidth = getIsCancel() ? -(getTouchX() - getStartX()) : getViewWidth() - (getTouchX() - getStartX());
        } else if (getIsCancel()) {
            float viewWidth2 = (getViewWidth() - getStartX()) + getTouchX();
            if (viewWidth2 > getViewWidth()) {
                viewWidth2 = getViewWidth();
            }
            viewWidth = getViewWidth() - viewWidth2;
        } else {
            viewWidth = -(getTouchX() + (getViewWidth() - getStartX()));
        }
        startScroll((int) getTouchX(), 0, (int) viewWidth, 0, animationSpeed);
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onAnimStop() throws SecurityException, NumberFormatException {
        if (getIsCancel()) {
            return;
        }
        getReadView().fillPage(getMDirection());
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onDraw(@NotNull Canvas canvas) {
        int iSave;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (getIsRunning()) {
            float touchX = getTouchX() - getStartX();
            PageDirection mDirection = getMDirection();
            PageDirection pageDirection = PageDirection.NEXT;
            if (mDirection != pageDirection || touchX <= 0.0f) {
                PageDirection mDirection2 = getMDirection();
                PageDirection pageDirection2 = PageDirection.PREV;
                if (mDirection2 != pageDirection2 || touchX >= 0.0f) {
                    float viewWidth = touchX > 0.0f ? touchX - getViewWidth() : getViewWidth() + touchX;
                    if (getMDirection() == pageDirection2) {
                        if (touchX > getViewWidth()) {
                            getPrevRecorder().draw(canvas);
                            return;
                        }
                        iSave = canvas.save();
                        canvas.translate(viewWidth, 0.0f);
                        try {
                            getPrevRecorder().draw(canvas);
                            canvas.restoreToCount(iSave);
                            addShadow(viewWidth, canvas);
                            return;
                        } finally {
                        }
                    }
                    if (getMDirection() == pageDirection) {
                        float width = getNextRecorder().getWidth();
                        float height = getNextRecorder().getHeight();
                        iSave = canvas.save();
                        canvas.clipRect(touchX + width, 0.0f, width, height);
                        try {
                            getNextRecorder().draw(canvas);
                            canvas.restoreToCount(iSave);
                            iSave = canvas.save();
                            canvas.translate(viewWidth - getViewWidth(), 0.0f);
                            try {
                                getCurRecorder().draw(canvas);
                                canvas.restoreToCount(iSave);
                                addShadow(viewWidth, canvas);
                            } finally {
                            }
                        } finally {
                        }
                    }
                }
            }
        }
    }

    @Override // com.ykb.ebook.page.delegate.HorizontalPageDelegate
    public void setBitmap() {
        int i2 = WhenMappings.$EnumSwitchMapping$0[getMDirection().ordinal()];
        if (i2 == 1) {
            ViewExtensionsKt.screenshot(getPrevPage(), getPrevRecorder());
        } else {
            if (i2 != 2) {
                return;
            }
            ViewExtensionsKt.screenshot(getNextPage(), getNextRecorder());
            ViewExtensionsKt.screenshot(getCurPage(), getCurRecorder());
        }
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void setViewSize(int width, int height) {
        super.setViewSize(width, height);
        this.shadowDrawableR.setBounds(0, 0, 30, getViewHeight());
    }
}
