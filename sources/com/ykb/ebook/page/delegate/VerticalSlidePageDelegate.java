package com.ykb.ebook.page.delegate;

import android.graphics.Canvas;
import com.ykb.ebook.page.PageDirection;
import com.ykb.ebook.weight.ReadView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lcom/ykb/ebook/page/delegate/VerticalSlidePageDelegate;", "Lcom/ykb/ebook/page/delegate/VerticalPageDelegate;", "readView", "Lcom/ykb/ebook/weight/ReadView;", "(Lcom/ykb/ebook/weight/ReadView;)V", "onAnimStart", "", "animationSpeed", "", "onAnimStop", "onDraw", "canvas", "Landroid/graphics/Canvas;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nVerticalSlidePageDelegate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VerticalSlidePageDelegate.kt\ncom/ykb/ebook/page/delegate/VerticalSlidePageDelegate\n+ 2 Canvas.kt\nandroidx/core/graphics/CanvasKt\n*L\n1#1,63:1\n42#2,13:64\n42#2,13:77\n42#2,13:90\n42#2,13:103\n*S KotlinDebug\n*F\n+ 1 VerticalSlidePageDelegate.kt\ncom/ykb/ebook/page/delegate/VerticalSlidePageDelegate\n*L\n42#1:64,13\n45#1:77,13\n49#1:90,13\n52#1:103,13\n*E\n"})
/* loaded from: classes7.dex */
public final class VerticalSlidePageDelegate extends VerticalPageDelegate {

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
    public VerticalSlidePageDelegate(@NotNull ReadView readView) {
        super(readView);
        Intrinsics.checkNotNullParameter(readView, "readView");
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onAnimStart(int animationSpeed) {
        float viewHeight;
        if (WhenMappings.$EnumSwitchMapping$0[getMDirection().ordinal()] != 1) {
            viewHeight = getIsCancel() ? -(getTouchY() - getStartY()) : getViewHeight() - (getTouchY() - getStartY());
        } else if (getIsCancel()) {
            float viewHeight2 = (getViewHeight() - getStartY()) + getTouchY();
            if (viewHeight2 > getViewHeight()) {
                viewHeight2 = getViewHeight();
            }
            viewHeight = getViewHeight() - viewHeight2;
        } else {
            viewHeight = -(getTouchY() + (getViewHeight() - getStartY()));
        }
        startScroll(0, (int) getTouchY(), 0, (int) viewHeight, animationSpeed);
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
        float touchY = getTouchY() - getStartY();
        PageDirection mDirection = getMDirection();
        PageDirection pageDirection = PageDirection.NEXT;
        if (mDirection != pageDirection || touchY <= 0.0f) {
            PageDirection mDirection2 = getMDirection();
            PageDirection pageDirection2 = PageDirection.PREV;
            if (mDirection2 != pageDirection2 || touchY >= 0.0f) {
                float viewHeight = touchY > 0.0f ? touchY - getViewHeight() : touchY + getViewHeight();
                if (getIsRunning()) {
                    if (getMDirection() == pageDirection2) {
                        iSave = canvas.save();
                        canvas.translate(0.0f, getViewHeight() + viewHeight);
                        try {
                            getCurRecorder().draw(canvas);
                            canvas.restoreToCount(iSave);
                            iSave = canvas.save();
                            canvas.translate(0.0f, viewHeight);
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
                        canvas.translate(0.0f, viewHeight);
                        try {
                            getNextRecorder().draw(canvas);
                            canvas.restoreToCount(iSave);
                            iSave = canvas.save();
                            canvas.translate(0.0f, viewHeight - getViewHeight());
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
