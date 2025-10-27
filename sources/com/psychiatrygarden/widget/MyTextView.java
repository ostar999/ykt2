package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class MyTextView extends LinearLayout {
    TextView contentView;
    private int defaultLine;
    protected boolean isExpand;
    public OnStateChangeListener onStateChangeListener;
    TextView openView;

    public interface OnStateChangeListener {
        void onStateChange(boolean isExpand);
    }

    public MyTextView(Context context) {
        super(context);
        this.isExpand = false;
        this.defaultLine = 2;
        LayoutInflater.from(context).inflate(R.layout.text_layout, this);
        this.contentView = (TextView) findViewById(R.id.content_text);
        TextView textView = (TextView) findViewById(R.id.open_view);
        this.openView = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.MyTextView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                MyTextView myTextView = MyTextView.this;
                boolean z2 = !myTextView.isExpand;
                myTextView.isExpand = z2;
                OnStateChangeListener onStateChangeListener = myTextView.onStateChangeListener;
                if (onStateChangeListener != null) {
                    onStateChangeListener.onStateChange(z2);
                }
                MyTextView myTextView2 = MyTextView.this;
                if (!myTextView2.isExpand) {
                    myTextView2.contentView.setLines(myTextView2.defaultLine);
                    MyTextView.this.openView.setText("展开显示");
                } else {
                    TextView textView2 = myTextView2.contentView;
                    textView2.setLines(textView2.getLineCount());
                    MyTextView.this.openView.setText("收起");
                }
            }
        });
    }

    private int getLineNumber() {
        this.contentView.measure(View.MeasureSpec.makeMeasureSpec(((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getWidth(), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
        return this.contentView.getMeasuredHeight() / this.contentView.getLineHeight();
    }

    public void setIsExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            this.contentView.setLines(this.defaultLine);
            this.openView.setText("展开显示");
        } else {
            TextView textView = this.contentView;
            textView.setLines(textView.getLineCount());
            this.openView.setText("收起");
        }
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    public void setText(String str) {
        this.contentView.setText(str);
        int lineNumber = this.contentView.getLayout() == null ? getLineNumber() : this.contentView.getLineCount();
        int i2 = this.defaultLine;
        if (lineNumber <= i2) {
            this.openView.setVisibility(8);
        } else {
            this.contentView.setLines(i2);
            this.openView.setVisibility(0);
        }
    }
}
