package com.ykb.ebook.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;

/* loaded from: classes8.dex */
public class MyExpendViews extends LinearLayout {
    int collapsedHeight;
    int duration;
    private boolean fromVideo;
    TextView id_source_textview;
    boolean isAnimate;
    boolean isChange;
    boolean isCollapsed;
    private String is_del;
    int lastHeight;
    LinearLayout line_ask_arrow_child;
    OnExpandStateChangeListener listener;
    int maxExpandLines;
    OnClickListenter onClickListenter;
    int realTextViewHeigt;
    RelativeLayout rel_img;
    private String str;
    TextView txt_str;

    public class ExpandCollapseAnimation extends Animation {
        int endValue;
        int startValue;

        public ExpandCollapseAnimation(int i2, int i3) {
            this.startValue = 0;
            this.endValue = 0;
            setDuration(MyExpendViews.this.duration);
            this.startValue = i2;
            this.endValue = i3;
        }

        @Override // android.view.animation.Animation
        public void applyTransformation(float f2, Transformation transformation) {
            super.applyTransformation(f2, transformation);
            int i2 = this.endValue;
            int i3 = (int) (((i2 - r0) * f2) + this.startValue);
            MyExpendViews myExpendViews = MyExpendViews.this;
            myExpendViews.id_source_textview.setMaxHeight(i3 - myExpendViews.lastHeight);
            MyExpendViews.this.getLayoutParams().height = i3;
            MyExpendViews.this.requestLayout();
        }

        @Override // android.view.animation.Animation
        public boolean willChangeBounds() {
            return true;
        }
    }

    public interface OnClickListenter {
        void OnClickChildListenter();
    }

    public interface OnExpandStateChangeListener {
        void onClickStateChange(View view);

        void onExpandStateChanged(boolean z2);
    }

    public MyExpendViews(Context context) {
        this(context, null);
    }

    private int getRealTextViewHeight(TextView textView) {
        return textView.getLayout().getLineTop(textView.getLineCount()) + textView.getCompoundPaddingBottom() + textView.getCompoundPaddingTop();
    }

    private void init(Context context, AttributeSet attributeSet) {
        setOrientation(1);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ExpandableTextViewsAttr);
        this.maxExpandLines = typedArrayObtainStyledAttributes.getInteger(R.styleable.ExpandableTextViewsAttr_maxExpandLines, 5);
        this.duration = typedArrayObtainStyledAttributes.getInteger(R.styleable.ExpandableTextViewsAttr_duration, 500);
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$0(View view) {
        if (this.listener != null) {
            if (this.is_del.equals("0")) {
                this.listener.onClickStateChange(view);
            } else {
                Toast.makeText(getContext(), "评论已删除", 0).show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$1(View view) {
        this.onClickListenter.OnClickChildListenter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$2(View view) {
        this.isCollapsed = !this.isCollapsed;
        OnExpandStateChangeListener onExpandStateChangeListener = this.listener;
        if (onExpandStateChangeListener != null) {
            onExpandStateChangeListener.onExpandStateChanged(false);
        }
        this.lastHeight = 0;
        ExpandCollapseAnimation expandCollapseAnimation = new ExpandCollapseAnimation(getHeight(), this.realTextViewHeigt + this.lastHeight);
        expandCollapseAnimation.setFillAfter(true);
        expandCollapseAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.ykb.ebook.weight.MyExpendViews.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                MyExpendViews.this.clearAnimation();
                MyExpendViews myExpendViews = MyExpendViews.this;
                myExpendViews.isAnimate = false;
                myExpendViews.line_ask_arrow_child.setVisibility(8);
                MyExpendViews myExpendViews2 = MyExpendViews.this;
                myExpendViews2.collapsedHeight = myExpendViews2.getMeasuredHeight();
                MyExpendViews.this.postInvalidate();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                MyExpendViews.this.isAnimate = true;
            }
        });
        clearAnimation();
        startAnimation(expandCollapseAnimation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMeasure$3() {
        this.lastHeight = getHeight() - this.id_source_textview.getHeight();
        this.collapsedHeight = getMeasuredHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMeasure$4(int i2, int i3, int i4) {
        if (this.id_source_textview.getLineCount() <= this.maxExpandLines) {
            TextView textView = this.id_source_textview;
            textView.setLines(textView.getLineCount());
            Log.e("line_count", "count2====>" + i2);
            return;
        }
        this.realTextViewHeigt = getRealTextViewHeight(this.id_source_textview);
        if (this.isCollapsed) {
            this.id_source_textview.setLines(this.maxExpandLines);
            this.line_ask_arrow_child.setVisibility(0);
        }
        super.onMeasure(i3, i4);
        if (this.isCollapsed) {
            this.id_source_textview.post(new Runnable() { // from class: com.ykb.ebook.weight.i
                @Override // java.lang.Runnable
                public final void run() {
                    this.f26491c.lambda$onMeasure$3();
                }
            });
        }
    }

    public String getIs_del() {
        return this.is_del;
    }

    public OnClickListenter getOnClickListenter() {
        return this.onClickListenter;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.id_source_textview = (TextView) findViewById(R.id.id_source_textview);
        this.txt_str = (TextView) findViewById(R.id.txt_str);
        this.id_source_textview.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f26493c.lambda$onFinishInflate$0(view);
            }
        });
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rel_img);
        this.rel_img = relativeLayout;
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f26495c.lambda$onFinishInflate$1(view);
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.line_ask_arrow_child);
        this.line_ask_arrow_child = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f26497c.lambda$onFinishInflate$2(view);
            }
        });
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.isAnimate;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(final int i2, final int i3) {
        super.onMeasure(i2, i3);
        if (getVisibility() == 8 || !this.isChange) {
            return;
        }
        this.isChange = false;
        this.line_ask_arrow_child.setVisibility(8);
        this.id_source_textview.setMaxLines(Integer.MAX_VALUE);
        final int lineCount = this.id_source_textview.getLineCount();
        Log.e("line_count", "count====>" + lineCount);
        super.onMeasure(i2, i3);
        postDelayed(new Runnable() { // from class: com.ykb.ebook.weight.m
            @Override // java.lang.Runnable
            public final void run() {
                this.f26499c.lambda$onMeasure$4(lineCount, i2, i3);
            }
        }, 100L);
    }

    public void setFromVideo(boolean z2) {
        this.fromVideo = z2;
    }

    public void setIs_del(String str) {
        this.is_del = str;
    }

    public void setListener(OnExpandStateChangeListener onExpandStateChangeListener) {
        this.listener = onExpandStateChangeListener;
    }

    public void setOnClickListenter(OnClickListenter onClickListenter) {
        this.onClickListenter = onClickListenter;
    }

    public void setText(String str) {
        this.isChange = true;
        if (str == null || "".equals(str)) {
            return;
        }
        this.id_source_textview.setText(Html.fromHtml(str.replaceAll("\n", "<br/>")));
    }

    public MyExpendViews(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.maxExpandLines = 0;
        this.duration = 0;
        this.isChange = false;
        this.realTextViewHeigt = 0;
        this.isCollapsed = true;
        this.collapsedHeight = 0;
        this.lastHeight = 0;
        this.isAnimate = false;
        this.is_del = "0";
        init(context, attributeSet);
    }

    public void setText(String str, boolean z2, String str2) {
        if (TextUtils.isEmpty(str2)) {
            this.rel_img.setVisibility(8);
            this.str = str2;
        } else {
            ImageView imageView = (ImageView) this.rel_img.findViewById(R.id.img);
            this.rel_img.setVisibility(0);
            if (ReadConfig.INSTANCE.getColorMode() == 2) {
                imageView.setImageResource(R.mipmap.pingluntupian_night);
            } else {
                imageView.setImageResource(R.mipmap.pingluntupian);
            }
        }
        if (TextUtils.isEmpty(str)) {
            this.id_source_textview.setVisibility(8);
        } else {
            this.id_source_textview.setVisibility(0);
        }
        this.isCollapsed = z2;
        if (z2) {
            this.txt_str.setText("查看全部");
        }
        clearAnimation();
        setText(str);
        getLayoutParams().height = -2;
    }

    public void setText(String str, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            setVisibility(8);
        } else {
            setVisibility(0);
        }
        this.isCollapsed = z2;
        if (z2) {
            this.txt_str.setText("查看全部");
        }
        clearAnimation();
        setText(str);
        getLayoutParams().height = -2;
    }
}
