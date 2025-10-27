package org.bouncycastle.i18n;

import cn.hutool.core.text.StrPool;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;

/* loaded from: classes9.dex */
public class MissingEntryException extends RuntimeException {
    private String debugMsg;
    protected final String key;
    protected final ClassLoader loader;
    protected final Locale locale;
    protected final String resource;

    public MissingEntryException(String str, String str2, String str3, Locale locale, ClassLoader classLoader) {
        super(str);
        this.resource = str2;
        this.key = str3;
        this.locale = locale;
        this.loader = classLoader;
    }

    public MissingEntryException(String str, Throwable th, String str2, String str3, Locale locale, ClassLoader classLoader) {
        super(str, th);
        this.resource = str2;
        this.key = str3;
        this.locale = locale;
        this.loader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return this.loader;
    }

    public String getDebugMsg() {
        if (this.debugMsg == null) {
            this.debugMsg = "Can not find entry " + this.key + " in resource file " + this.resource + " for the locale " + this.locale + StrPool.DOT;
            ClassLoader classLoader = this.loader;
            if (classLoader instanceof URLClassLoader) {
                URL[] uRLs = ((URLClassLoader) classLoader).getURLs();
                this.debugMsg += " The following entries in the classpath were searched: ";
                for (int i2 = 0; i2 != uRLs.length; i2++) {
                    this.debugMsg += uRLs[i2] + " ";
                }
            }
        }
        return this.debugMsg;
    }

    public String getKey() {
        return this.key;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getResource() {
        return this.resource;
    }
}
