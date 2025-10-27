package pl.droidsonroids.gif;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/* loaded from: classes9.dex */
final class GifRenderingExecutor extends ScheduledThreadPoolExecutor {

    public static final class InstanceHolder {
        private static final GifRenderingExecutor INSTANCE = new GifRenderingExecutor();

        private InstanceHolder() {
        }
    }

    public static GifRenderingExecutor getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private GifRenderingExecutor() {
        super(1, new ThreadPoolExecutor.DiscardPolicy());
    }
}
