package com.easefun.polyv.livecommon.ui.window;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;

/* loaded from: classes3.dex */
public class PLVEmptyFragment extends PLVBaseFragment {
    private ViewActionListener viewActionListener;

    public interface ViewActionListener {
        void onViewCreated(View view);
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.plv_horizontal_linear_layout, (ViewGroup) null);
        this.view = viewInflate;
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewActionListener viewActionListener = this.viewActionListener;
        if (viewActionListener != null) {
            viewActionListener.onViewCreated(view);
        }
    }

    public void setViewActionListener(ViewActionListener listener) {
        this.viewActionListener = listener;
    }
}
