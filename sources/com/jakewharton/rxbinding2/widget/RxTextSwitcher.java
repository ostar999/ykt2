package com.jakewharton.rxbinding2.widget;

import android.widget.TextSwitcher;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public final class RxTextSwitcher {
    private RxTextSwitcher() {
        throw new AssertionError("No instances.");
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> currentText(@NonNull final TextSwitcher textSwitcher) {
        Preconditions.checkNotNull(textSwitcher, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxTextSwitcher.2
            @Override // io.reactivex.functions.Consumer
            public void accept(CharSequence charSequence) {
                textSwitcher.setCurrentText(charSequence);
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> text(@NonNull final TextSwitcher textSwitcher) {
        Preconditions.checkNotNull(textSwitcher, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxTextSwitcher.1
            @Override // io.reactivex.functions.Consumer
            public void accept(CharSequence charSequence) {
                textSwitcher.setText(charSequence);
            }
        };
    }
}
