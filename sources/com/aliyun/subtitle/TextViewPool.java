package com.aliyun.subtitle;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class TextViewPool {
    private Context mContext;
    private List<TextView> idelTextViewList = new ArrayList();
    private List<TextView> busyTextViewList = new ArrayList();

    public TextViewPool(Context context) {
        this.mContext = context;
    }

    public TextView obtain() {
        TextView textView = this.idelTextViewList.isEmpty() ? new TextView(this.mContext) : this.idelTextViewList.get(0);
        this.busyTextViewList.add(textView);
        return textView;
    }

    public void recycle(TextView textView) {
        if (textView == null) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) textView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(textView);
        }
        this.busyTextViewList.remove(textView);
        this.idelTextViewList.add(textView);
    }
}
