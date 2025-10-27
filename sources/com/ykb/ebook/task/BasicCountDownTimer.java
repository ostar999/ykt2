package com.ykb.ebook.task;

import android.os.CountDownTimer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\u0013\u001a\u00020\bH\u0016J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0003H\u0016R\"\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR(\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\b\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0016"}, d2 = {"Lcom/ykb/ebook/task/BasicCountDownTimer;", "Landroid/os/CountDownTimer;", "millisInFuture", "", "countDownInterval", "(JJ)V", "onFinishListener", "Lkotlin/Function0;", "", "getOnFinishListener", "()Lkotlin/jvm/functions/Function0;", "setOnFinishListener", "(Lkotlin/jvm/functions/Function0;)V", "onTickListener", "Lkotlin/Function1;", "getOnTickListener", "()Lkotlin/jvm/functions/Function1;", "setOnTickListener", "(Lkotlin/jvm/functions/Function1;)V", "onFinish", "onTick", "millisUntilFinished", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class BasicCountDownTimer extends CountDownTimer {

    @Nullable
    private Function0<Unit> onFinishListener;

    @Nullable
    private Function1<? super Long, Unit> onTickListener;

    public BasicCountDownTimer(long j2, long j3) {
        super(j2, j3);
    }

    @Nullable
    public final Function0<Unit> getOnFinishListener() {
        return this.onFinishListener;
    }

    @Nullable
    public final Function1<Long, Unit> getOnTickListener() {
        return this.onTickListener;
    }

    @Override // android.os.CountDownTimer
    public void onFinish() {
        Function0<Unit> function0 = this.onFinishListener;
        if (function0 != null) {
            function0.invoke();
        }
    }

    @Override // android.os.CountDownTimer
    public void onTick(long millisUntilFinished) {
        Function1<? super Long, Unit> function1 = this.onTickListener;
        if (function1 != null) {
            function1.invoke(Long.valueOf(millisUntilFinished));
        }
    }

    public final void setOnFinishListener(@Nullable Function0<Unit> function0) {
        this.onFinishListener = function0;
    }

    public final void setOnTickListener(@Nullable Function1<? super Long, Unit> function1) {
        this.onTickListener = function1;
    }
}
