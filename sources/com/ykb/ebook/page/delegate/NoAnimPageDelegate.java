package com.ykb.ebook.page.delegate;

import android.graphics.Canvas;
import com.ykb.ebook.weight.ReadView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u0006H\u0016¨\u0006\u000e"}, d2 = {"Lcom/ykb/ebook/page/delegate/NoAnimPageDelegate;", "Lcom/ykb/ebook/page/delegate/HorizontalPageDelegate;", "readView", "Lcom/ykb/ebook/weight/ReadView;", "(Lcom/ykb/ebook/weight/ReadView;)V", "onAnimStart", "", "animationSpeed", "", "onAnimStop", "onDraw", "canvas", "Landroid/graphics/Canvas;", "setBitmap", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class NoAnimPageDelegate extends HorizontalPageDelegate {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoAnimPageDelegate(@NotNull ReadView readView) {
        super(readView);
        Intrinsics.checkNotNullParameter(readView, "readView");
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onAnimStart(int animationSpeed) throws SecurityException, NumberFormatException {
        if (!getIsCancel()) {
            getReadView().fillPage(getMDirection());
        }
        stopScroll();
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onAnimStop() {
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    @Override // com.ykb.ebook.page.delegate.HorizontalPageDelegate
    public void setBitmap() {
    }
}
