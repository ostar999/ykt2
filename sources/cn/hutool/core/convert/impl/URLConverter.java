package cn.hutool.core.convert.impl;

import cn.hutool.core.convert.AbstractConverter;
import java.io.File;
import java.net.URI;
import java.net.URL;

/* loaded from: classes.dex */
public class URLConverter extends AbstractConverter<URL> {
    private static final long serialVersionUID = 1;

    @Override // cn.hutool.core.convert.AbstractConverter
    public URL convertInternal(Object obj) {
        try {
            return obj instanceof File ? ((File) obj).toURI().toURL() : obj instanceof URI ? ((URI) obj).toURL() : new URL(convertToStr(obj));
        } catch (Exception unused) {
            return null;
        }
    }
}
