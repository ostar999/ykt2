package cn.webdemo.com.supporfragment;

import cn.webdemo.com.supporfragment.helper.ExceptionHandler;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class Fragmentation {
    public static final int BUBBLE = 2;
    static volatile Fragmentation INSTANCE = null;
    public static final int NONE = 0;
    public static final int SHAKE = 1;
    private boolean debug;
    private ExceptionHandler handler;
    private int mode;

    public static class FragmentationBuilder {
        private boolean debug;
        private ExceptionHandler handler;
        private int mode;

        public FragmentationBuilder debug(boolean z2) {
            this.debug = z2;
            return this;
        }

        public FragmentationBuilder handleException(ExceptionHandler exceptionHandler) {
            this.handler = exceptionHandler;
            return this;
        }

        public Fragmentation install() {
            Fragmentation fragmentation;
            synchronized (Fragmentation.class) {
                if (Fragmentation.INSTANCE != null) {
                    throw new RuntimeException("Default instance already exists. It may be only set once before it's used the first time to ensure consistent behavior.");
                }
                Fragmentation.INSTANCE = new Fragmentation(this);
                fragmentation = Fragmentation.INSTANCE;
            }
            return fragmentation;
        }

        public FragmentationBuilder stackViewMode(int i2) {
            this.mode = i2;
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StackViewMode {
    }

    public Fragmentation(FragmentationBuilder fragmentationBuilder) {
        this.mode = 2;
        boolean z2 = fragmentationBuilder.debug;
        this.debug = z2;
        if (z2) {
            this.mode = fragmentationBuilder.mode;
        } else {
            this.mode = 0;
        }
        this.handler = fragmentationBuilder.handler;
    }

    public static FragmentationBuilder builder() {
        return new FragmentationBuilder();
    }

    public static Fragmentation getDefault() {
        if (INSTANCE == null) {
            synchronized (Fragmentation.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Fragmentation(new FragmentationBuilder());
                }
            }
        }
        return INSTANCE;
    }

    public ExceptionHandler getHandler() {
        return this.handler;
    }

    public int getMode() {
        return this.mode;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean z2) {
        this.debug = z2;
    }

    public void setHandler(ExceptionHandler exceptionHandler) {
        this.handler = exceptionHandler;
    }

    public void setMode(int i2) {
        this.mode = i2;
    }
}
