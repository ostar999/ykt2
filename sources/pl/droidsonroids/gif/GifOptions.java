package pl.droidsonroids.gif;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import okhttp3.internal.ws.WebSocketProtocol;

/* loaded from: classes9.dex */
public class GifOptions {
    boolean inIsOpaque;
    char inSampleSize;

    public GifOptions() {
        reset();
    }

    private void reset() {
        this.inSampleSize = (char) 1;
        this.inIsOpaque = false;
    }

    public void setFrom(@Nullable GifOptions gifOptions) {
        if (gifOptions == null) {
            reset();
        } else {
            this.inIsOpaque = gifOptions.inIsOpaque;
            this.inSampleSize = gifOptions.inSampleSize;
        }
    }

    public void setInIsOpaque(boolean z2) {
        this.inIsOpaque = z2;
    }

    public void setInSampleSize(@IntRange(from = 1, to = WebSocketProtocol.PAYLOAD_SHORT_MAX) int i2) {
        if (i2 < 1 || i2 > 65535) {
            this.inSampleSize = (char) 1;
        } else {
            this.inSampleSize = (char) i2;
        }
    }
}
