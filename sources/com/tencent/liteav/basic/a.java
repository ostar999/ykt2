package com.tencent.liteav.basic;

import com.tencent.liteav.TXLiteAVExternalDecoderFactoryInterface;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static TXLiteAVExternalDecoderFactoryInterface f18326a;

    public static synchronized boolean a() {
        return f18326a != null;
    }

    public static synchronized TXLiteAVExternalDecoderFactoryInterface b() {
        return f18326a;
    }

    public static synchronized void a(TXLiteAVExternalDecoderFactoryInterface tXLiteAVExternalDecoderFactoryInterface) {
        f18326a = tXLiteAVExternalDecoderFactoryInterface;
    }
}
