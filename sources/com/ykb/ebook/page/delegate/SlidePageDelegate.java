package com.ykb.ebook.page.delegate;

import android.graphics.Canvas;
import com.ykb.ebook.page.PageDirection;
import com.ykb.ebook.weight.ReadView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lcom/ykb/ebook/page/delegate/SlidePageDelegate;", "Lcom/ykb/ebook/page/delegate/HorizontalPageDelegate;", "readView", "Lcom/ykb/ebook/weight/ReadView;", "(Lcom/ykb/ebook/weight/ReadView;)V", "onAnimStart", "", "animationSpeed", "", "onAnimStop", "onDraw", "canvas", "Landroid/graphics/Canvas;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSlidePageDelegate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SlidePageDelegate.kt\ncom/ykb/ebook/page/delegate/SlidePageDelegate\n+ 2 Canvas.kt\nandroidx/core/graphics/CanvasKt\n*L\n1#1,68:1\n42#2,13:69\n42#2,13:82\n42#2,13:95\n42#2,13:108\n*S KotlinDebug\n*F\n+ 1 SlidePageDelegate.kt\ncom/ykb/ebook/page/delegate/SlidePageDelegate\n*L\n47#1:69,13\n50#1:82,13\n54#1:95,13\n57#1:108,13\n*E\n"})
/* loaded from: classes7.dex */
public final class SlidePageDelegate extends HorizontalPageDelegate {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PageDirection.values().length];
            try {
                iArr[PageDirection.NEXT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SlidePageDelegate(@NotNull ReadView readView) {
        super(readView);
        Intrinsics.checkNotNullParameter(readView, "readView");
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onAnimStart(int animationSpeed) {
        float viewWidth;
        if (WhenMappings.$EnumSwitchMapping$0[getMDirection().ordinal()] != 1) {
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
        float touchX = getTouchX() - getStartX();
        PageDirection mDirection = getMDirection();
        PageDirection pageDirection = PageDirection.NEXT;
        if (mDirection != pageDirection || touchX <= 0.0f) {
            PageDirection mDirection2 = getMDirection();
            PageDirection pageDirection2 = PageDirection.PREV;
            if (mDirection2 != pageDirection2 || touchX >= 0.0f) {
                float viewWidth = touchX > 0.0f ? touchX - getViewWidth() : touchX + getViewWidth();
                if (getIsRunning()) {
                    if (getMDirection() == pageDirection2) {
                        iSave = canvas.save();
                        canvas.translate(getViewWidth() + viewWidth, 0.0f);
                        try {
                            getCurRecorder().draw(canvas);
                            canvas.restoreToCount(iSave);
                            iSave = canvas.save();
                            canvas.translate(viewWidth, 0.0f);
                            try {
                                getPrevRecorder().draw(canvas);
                                return;
                            } finally {
                            }
                        } finally {
                        }
                    }
                    if (getMDirection() == pageDirection) {
                        iSave = canvas.save();
                        canvas.translate(viewWidth, 0.0f);
                        try {
                            getNextRecorder().draw(canvas);
                            canvas.restoreToCount(iSave);
                            iSave = canvas.save();
                            canvas.translate(viewWidth - getViewWidth(), 0.0f);
                            try {
                                getCurRecorder().draw(canvas);
                            } finally {
                            }
                        } finally {
                        }
                    }
                }
            }
        }
    }
}
