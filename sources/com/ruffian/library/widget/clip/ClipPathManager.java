package com.ruffian.library.widget.clip;

import android.graphics.Path;

/* loaded from: classes6.dex */
public class ClipPathManager {
    protected final Path path = new Path();
    private ClipPathCreator createClipPath = null;

    public interface ClipPathCreator {
        Path createClipPath(int i2, int i3);
    }

    public Path getClipPath() {
        return this.path;
    }

    public void setClipPathCreator(ClipPathCreator clipPathCreator) {
        this.createClipPath = clipPathCreator;
    }

    public void setupClipLayout(int i2, int i3) {
        this.path.reset();
        ClipPathCreator clipPathCreator = this.createClipPath;
        Path pathCreateClipPath = clipPathCreator != null ? clipPathCreator.createClipPath(i2, i3) : null;
        if (pathCreateClipPath != null) {
            this.path.set(pathCreateClipPath);
        }
    }
}
