package com.davemorrissey.labs.subscaleview.decoder;

import androidx.annotation.NonNull;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
public interface DecoderFactory<T> {
    @NonNull
    T make() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException;
}
