package com.psychiatrygarden.activity.online.fragment;

/* loaded from: classes5.dex */
public final /* synthetic */ class x0 implements Runnable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ SubChoiceQuestionFragment f13386c;

    public /* synthetic */ x0(SubChoiceQuestionFragment subChoiceQuestionFragment) {
        this.f13386c = subChoiceQuestionFragment;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f13386c.playAudio();
    }
}
