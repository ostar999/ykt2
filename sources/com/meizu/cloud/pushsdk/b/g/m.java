package com.meizu.cloud.pushsdk.b.g;

import java.io.IOException;
import java.io.InterruptedIOException;

/* loaded from: classes4.dex */
public class m {

    /* renamed from: a, reason: collision with root package name */
    public static final m f9208a = new m() { // from class: com.meizu.cloud.pushsdk.b.g.m.1
        @Override // com.meizu.cloud.pushsdk.b.g.m
        public void a() throws IOException {
        }
    };

    /* renamed from: b, reason: collision with root package name */
    private boolean f9209b;

    /* renamed from: c, reason: collision with root package name */
    private long f9210c;

    public void a() throws IOException {
        if (Thread.interrupted()) {
            throw new InterruptedIOException("thread interrupted");
        }
        if (this.f9209b && this.f9210c - System.nanoTime() <= 0) {
            throw new InterruptedIOException("deadline reached");
        }
    }
}
