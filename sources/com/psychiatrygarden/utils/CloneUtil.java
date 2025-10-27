package com.psychiatrygarden.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* loaded from: classes6.dex */
public class CloneUtil {
    private CloneUtil() {
        throw new AssertionError();
    }

    public static <T extends Serializable> T clone(T object) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new ObjectOutputStream(byteArrayOutputStream).writeObject(object);
        return (T) new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())).readObject();
    }
}
