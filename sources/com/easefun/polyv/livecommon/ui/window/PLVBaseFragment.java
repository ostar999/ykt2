package com.easefun.polyv.livecommon.ui.window;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.LinkedList;
import java.util.Queue;

/* loaded from: classes3.dex */
public abstract class PLVBaseFragment extends Fragment {
    protected View view;
    protected Handler handler = new Handler(Looper.getMainLooper());
    private boolean afterOnActivityCreated = false;
    private final Queue<Runnable> pendingTaskOnActivityCreated = new LinkedList();

    public final <T extends View> T findViewById(int i2) {
        return (T) this.view.findViewById(i2);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.handler.post(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.window.PLVBaseFragment.1
            @Override // java.lang.Runnable
            public void run() {
                PLVBaseFragment.this.afterOnActivityCreated = true;
                while (!PLVBaseFragment.this.pendingTaskOnActivityCreated.isEmpty()) {
                    ((Runnable) PLVBaseFragment.this.pendingTaskOnActivityCreated.poll()).run();
                }
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacksAndMessages(null);
    }

    public void runAfterOnActivityCreated(@NonNull final Runnable runnable) {
        if (this.afterOnActivityCreated) {
            runnable.run();
        } else {
            this.pendingTaskOnActivityCreated.add(runnable);
        }
    }
}
