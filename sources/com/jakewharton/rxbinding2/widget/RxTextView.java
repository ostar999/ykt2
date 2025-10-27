package com.jakewharton.rxbinding2.widget;

import android.widget.TextView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Functions;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/* loaded from: classes4.dex */
public final class RxTextView {
    private RxTextView() {
        throw new AssertionError("No instances.");
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<TextViewAfterTextChangeEvent> afterTextChangeEvents(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new TextViewAfterTextChangeEventObservable(textView);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<TextViewBeforeTextChangeEvent> beforeTextChangeEvents(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new TextViewBeforeTextChangeEventObservable(textView);
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> color(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.7
            @Override // io.reactivex.functions.Consumer
            public void accept(Integer num) throws Exception {
                textView.setTextColor(num.intValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Observable<TextViewEditorActionEvent> editorActionEvents(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return editorActionEvents(textView, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    public static Observable<Integer> editorActions(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return editorActions(textView, Functions.PREDICATE_ALWAYS_TRUE);
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> error(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.3
            @Override // io.reactivex.functions.Consumer
            public void accept(CharSequence charSequence) {
                textView.setError(charSequence);
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> errorRes(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Integer num) {
                TextView textView2 = textView;
                textView2.setError(textView2.getContext().getResources().getText(num.intValue()));
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> hint(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.5
            @Override // io.reactivex.functions.Consumer
            public void accept(CharSequence charSequence) {
                textView.setHint(charSequence);
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> hintRes(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Integer num) {
                textView.setHint(num.intValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> text(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.1
            @Override // io.reactivex.functions.Consumer
            public void accept(CharSequence charSequence) {
                textView.setText(charSequence);
            }
        };
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<TextViewTextChangeEvent> textChangeEvents(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new TextViewTextChangeEventObservable(textView);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<CharSequence> textChanges(@NonNull TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new TextViewTextObservable(textView);
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Integer> textRes(@NonNull final TextView textView) {
        Preconditions.checkNotNull(textView, "view == null");
        return new Consumer<Integer>() { // from class: com.jakewharton.rxbinding2.widget.RxTextView.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Integer num) {
                textView.setText(num.intValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Observable<TextViewEditorActionEvent> editorActionEvents(@NonNull TextView textView, @NonNull Predicate<? super TextViewEditorActionEvent> predicate) {
        Preconditions.checkNotNull(textView, "view == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new TextViewEditorActionEventObservable(textView, predicate);
    }

    @NonNull
    @CheckResult
    public static Observable<Integer> editorActions(@NonNull TextView textView, @NonNull Predicate<? super Integer> predicate) {
        Preconditions.checkNotNull(textView, "view == null");
        Preconditions.checkNotNull(predicate, "handled == null");
        return new TextViewEditorActionObservable(textView, predicate);
    }
}
