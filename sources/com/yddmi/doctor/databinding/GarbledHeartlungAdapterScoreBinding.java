package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class GarbledHeartlungAdapterScoreBinding implements ViewBinding {

    @NonNull
    private final TextView rootView;

    /* renamed from: tv, reason: collision with root package name */
    @NonNull
    public final TextView f25735tv;

    private GarbledHeartlungAdapterScoreBinding(@NonNull TextView textView, @NonNull TextView textView2) {
        this.rootView = textView;
        this.f25735tv = textView2;
    }

    @NonNull
    public static GarbledHeartlungAdapterScoreBinding bind(@NonNull View view) {
        if (view == null) {
            throw new NullPointerException("rootView");
        }
        TextView textView = (TextView) view;
        return new GarbledHeartlungAdapterScoreBinding(textView, textView);
    }

    @NonNull
    public static GarbledHeartlungAdapterScoreBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static GarbledHeartlungAdapterScoreBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.garbled_heartlung_adapter_score, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public TextView getRoot() {
        return this.rootView;
    }
}
