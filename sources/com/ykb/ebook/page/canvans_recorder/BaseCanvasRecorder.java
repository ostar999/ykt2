package com.ykb.ebook.page.canvans_recorder;

import androidx.annotation.CallSuper;
import kotlin.Metadata;
import kotlin.jvm.JvmField;

@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0017J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\b\u001a\u00020\u0004H\u0016J\b\u0010\t\u001a\u00020\u0004H\u0016J\b\u0010\n\u001a\u00020\u0006H\u0017R\u0012\u0010\u0003\u001a\u00020\u00048\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/page/canvans_recorder/BaseCanvasRecorder;", "Lcom/ykb/ebook/page/canvans_recorder/CanvasRecorder;", "()V", "isDirty", "", "endRecording", "", "invalidate", "isLocked", "needRecord", "recycle", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public abstract class BaseCanvasRecorder implements CanvasRecorder {

    @JvmField
    protected boolean isDirty = true;

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    @CallSuper
    public void endRecording() {
        this.isDirty = false;
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public void invalidate() {
        this.isDirty = true;
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    /* renamed from: isDirty, reason: from getter */
    public boolean getIsDirty() {
        return this.isDirty;
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public boolean isLocked() {
        return false;
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    public boolean needRecord() {
        return getIsDirty() && !isLocked();
    }

    @Override // com.ykb.ebook.page.canvans_recorder.CanvasRecorder
    @CallSuper
    public void recycle() {
        this.isDirty = true;
    }
}
