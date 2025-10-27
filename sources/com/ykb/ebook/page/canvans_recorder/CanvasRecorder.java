package com.ykb.ebook.page.canvans_recorder;

import android.graphics.Canvas;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH&J\b\u0010\r\u001a\u00020\u000bH&J\b\u0010\u000e\u001a\u00020\u000bH&J\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0010H&J\b\u0010\u0012\u001a\u00020\u0010H&J\b\u0010\u0013\u001a\u00020\u000bH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005¨\u0006\u0014"}, d2 = {"Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;", "", "height", "", "getHeight", "()I", "width", "getWidth", "beginRecording", "Landroid/graphics/Canvas;", "draw", "", "canvas", "endRecording", "invalidate", "isDirty", "", "isLocked", "needRecord", "recycle", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public interface CanvasRecorder {
    @NotNull
    Canvas beginRecording(int width, int height);

    void draw(@NotNull Canvas canvas);

    void endRecording();

    int getHeight();

    int getWidth();

    void invalidate();

    boolean isDirty();

    boolean isLocked();

    boolean needRecord();

    void recycle();
}
