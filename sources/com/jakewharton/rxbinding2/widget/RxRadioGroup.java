package com.jakewharton.rxbinding2.widget;

import android.widget.RadioGroup;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public final class RxRadioGroup {
    private RxRadioGroup() {
        throw new AssertionError("No instances.");
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> checked(@NonNull final RadioGroup radioGroup) {
        Preconditions.checkNotNull(radioGroup, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxRadioGroup.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Integer num) {
                if (num.intValue() == -1) {
                    radioGroup.clearCheck();
                } else {
                    radioGroup.check(num.intValue());
                }
            }
        };
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<Integer> checkedChanges(@NonNull RadioGroup radioGroup) {
        Preconditions.checkNotNull(radioGroup, "view == null");
        return new RadioGroupCheckedChangeObservable(radioGroup);
    }
}
