package com.petterp.floatingx.listener.provider;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
public interface IFxContextProvider {
    @NonNull
    View build(@Nullable Context context);
}
