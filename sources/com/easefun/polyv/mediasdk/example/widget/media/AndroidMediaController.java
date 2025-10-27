package com.easefun.polyv.mediasdk.example.widget.media;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.MediaController;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class AndroidMediaController extends MediaController implements IMediaController {
    private ActionBar mActionBar;
    private ArrayList<View> mShowOnceArray;

    public AndroidMediaController(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mShowOnceArray = new ArrayList<>();
        initView(context);
    }

    private void initView(Context context) {
    }

    @Override // android.widget.MediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void hide() {
        super.hide();
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.hide();
        }
        Iterator<View> it = this.mShowOnceArray.iterator();
        while (it.hasNext()) {
            it.next().setVisibility(8);
        }
        this.mShowOnceArray.clear();
    }

    public void setSupportActionBar(@Nullable ActionBar actionBar) {
        this.mActionBar = actionBar;
        if (isShowing()) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
    }

    @Override // android.widget.MediaController, com.easefun.polyv.mediasdk.example.widget.media.IMediaController
    public void show() {
        super.show();
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.show();
        }
    }

    public void showOnce(@NonNull View view) {
        this.mShowOnceArray.add(view);
        view.setVisibility(0);
        show();
    }

    public AndroidMediaController(Context context, boolean z2) {
        super(context, z2);
        this.mShowOnceArray = new ArrayList<>();
        initView(context);
    }

    public AndroidMediaController(Context context) {
        super(context);
        this.mShowOnceArray = new ArrayList<>();
        initView(context);
    }
}
