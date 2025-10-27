package com.google.android.exoplayer2;

import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public final class RendererConfiguration {
    public static final RendererConfiguration DEFAULT = new RendererConfiguration(false);
    public final boolean tunneling;

    public RendererConfiguration(boolean z2) {
        this.tunneling = z2;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && RendererConfiguration.class == obj.getClass() && this.tunneling == ((RendererConfiguration) obj).tunneling;
    }

    public int hashCode() {
        return !this.tunneling ? 1 : 0;
    }
}
