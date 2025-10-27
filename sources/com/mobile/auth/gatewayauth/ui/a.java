package com.mobile.auth.gatewayauth.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private View f10276a;

    /* renamed from: b, reason: collision with root package name */
    private WeakReference<Context> f10277b;

    /* renamed from: c, reason: collision with root package name */
    private AbstractPnsViewDelegate f10278c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f10279d;

    public a(Context context, int i2, AbstractPnsViewDelegate abstractPnsViewDelegate) {
        this(LayoutInflater.from(context).inflate(i2, (ViewGroup) new FrameLayout(context), false), abstractPnsViewDelegate);
    }

    public a(View view, AbstractPnsViewDelegate abstractPnsViewDelegate) {
        this.f10279d = true;
        this.f10278c = abstractPnsViewDelegate;
        this.f10276a = view;
        this.f10277b = new WeakReference<>(view.getContext());
        AbstractPnsViewDelegate abstractPnsViewDelegate2 = this.f10278c;
        if (abstractPnsViewDelegate2 != null) {
            abstractPnsViewDelegate2.setPnsView(this);
            this.f10278c.onViewCreated(this.f10276a);
        }
    }

    public Context a() {
        Context context;
        try {
            WeakReference<Context> weakReference = this.f10277b;
            return (weakReference == null || (context = weakReference.get()) == null) ? this.f10276a.getContext() : context;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public View a(int i2) {
        try {
            return this.f10276a.findViewById(i2);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public View b() {
        try {
            return this.f10276a;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
