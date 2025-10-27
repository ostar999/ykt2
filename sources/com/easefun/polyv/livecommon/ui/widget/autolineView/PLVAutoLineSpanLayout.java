package com.easefun.polyv.livecommon.ui.widget.autolineView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVAutoLineSpanLayout extends ViewGroup {
    List<PLVAutoLineStateModel> list;
    int span;

    public PLVAutoLineSpanLayout(Context context) {
        super(context);
        this.list = new ArrayList();
        this.span = 4;
    }

    private View createItemView(PLVAutoLineStateModel model) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
        if (model.isShow()) {
            layoutParams.width = getWidth() / this.span;
            linearLayout.setVisibility(0);
        } else {
            linearLayout.setVisibility(8);
        }
        linearLayout.setLayoutParams(layoutParams);
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ConvertUtils.dp2px(30.0f), ConvertUtils.dp2px(30.0f)));
        imageView.setImageResource(model.getImageSource());
        imageView.setSelected(model.isActive());
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        textView.setText(model.getName());
        textView.setSelected(model.isActive());
        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        return linearLayout;
    }

    public void addItem(PLVAutoLineStateModel model) {
        this.list.add(model);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        getWidth();
        for (int i2 = 0; i2 < this.list.size(); i2++) {
            addView(createItemView(this.list.get(i2)));
            getMeasuredWidth();
        }
    }

    public void setItemView(View view) {
    }

    public PLVAutoLineSpanLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.list = new ArrayList();
        this.span = 4;
    }

    public PLVAutoLineSpanLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.list = new ArrayList();
        this.span = 4;
    }
}
