package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class MyExpendView extends LinearLayout {
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

        public ExpandCollapseAnimation(int startValue, int endValue) {
            this.startValue = 0;
            this.endValue = 0;
            setDuration(MyExpendView.this.duration);
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override // android.view.animation.Animation
        public void applyTransformation(float interpolatedTime, Transformation t2) {
            super.applyTransformation(interpolatedTime, t2);
            int i2 = this.endValue;
            int i3 = (int) (((i2 - r0) * interpolatedTime) + this.startValue);
            MyExpendView myExpendView = MyExpendView.this;
            myExpendView.id_source_textview.setMaxHeight(i3 - myExpendView.lastHeight);
            MyExpendView.this.getLayoutParams().height = i3;
            MyExpendView.this.requestLayout();
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
        void onClickStateChange(View v2);

        void onExpandStateChanged(boolean isExpanded);
    }

    public MyExpendView(Context context) {
        this(context, null);
    }

    private int getRealTextViewHeight(TextView textView) {
        return textView.getLayout().getLineTop(textView.getLineCount()) + textView.getCompoundPaddingBottom() + textView.getCompoundPaddingTop();
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(1);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextViewAttr);
        this.maxExpandLines = typedArrayObtainStyledAttributes.getInteger(1, 5);
        this.duration = typedArrayObtainStyledAttributes.getInteger(0, 500);
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$0(View view) {
        if (this.listener != null) {
            if (!this.is_del.equals("0")) {
                NewToast.showShort(getContext(), "评论已删除", 0).show();
            } else {
                if (ProjectApp.isSerachClick) {
                    return;
                }
                this.listener.onClickStateChange(view);
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
        expandCollapseAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.psychiatrygarden.widget.MyExpendView.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                MyExpendView.this.clearAnimation();
                MyExpendView myExpendView = MyExpendView.this;
                myExpendView.isAnimate = false;
                myExpendView.line_ask_arrow_child.setVisibility(8);
                MyExpendView myExpendView2 = MyExpendView.this;
                myExpendView2.collapsedHeight = myExpendView2.getMeasuredHeight();
                MyExpendView.this.postInvalidate();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                MyExpendView.this.isAnimate = true;
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
        this.id_source_textview.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.eb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16436c.lambda$onFinishInflate$0(view);
            }
        });
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rel_img);
        this.rel_img = relativeLayout;
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.fb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16485c.lambda$onFinishInflate$1(view);
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.line_ask_arrow_child);
        this.line_ask_arrow_child = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.gb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16515c.lambda$onFinishInflate$2(view);
            }
        });
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.isAnimate;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getVisibility() == 8 || !this.isChange) {
            return;
        }
        this.isChange = false;
        this.line_ask_arrow_child.setVisibility(8);
        this.id_source_textview.setMaxLines(Integer.MAX_VALUE);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.id_source_textview.getLineCount() <= 10) {
            TextView textView = this.id_source_textview;
            textView.setLines(textView.getLineCount());
            return;
        }
        this.realTextViewHeigt = getRealTextViewHeight(this.id_source_textview);
        if (this.isCollapsed) {
            this.id_source_textview.setLines(this.maxExpandLines);
            this.line_ask_arrow_child.setVisibility(0);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.isCollapsed) {
            this.id_source_textview.post(new Runnable() { // from class: com.psychiatrygarden.widget.db
                @Override // java.lang.Runnable
                public final void run() {
                    this.f16406c.lambda$onMeasure$3();
                }
            });
        }
    }

    public void setFromVideo(boolean fromVideo) {
        this.fromVideo = fromVideo;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public void setListener(OnExpandStateChangeListener listener) {
        this.listener = listener;
    }

    public void setOnClickListenter(OnClickListenter onClickListenter) {
        this.onClickListenter = onClickListenter;
    }

    public void setText(String text) {
        this.isChange = true;
        if (text == null || "".equals(text)) {
            return;
        }
        this.id_source_textview.setText(Html.fromHtml(text.replaceAll("\n", "<br/>")));
    }

    public MyExpendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.maxExpandLines = 0;
        this.duration = 0;
        this.isChange = false;
        this.realTextViewHeigt = 0;
        this.isCollapsed = true;
        this.collapsedHeight = 0;
        this.lastHeight = 0;
        this.isAnimate = false;
        this.is_del = "0";
        init(context, attrs);
    }

    public void setText(String text, boolean isCollapsed, String str) {
        if (TextUtils.isEmpty(str)) {
            this.rel_img.setVisibility(8);
            this.str = str;
        } else {
            ImageView imageView = (ImageView) this.rel_img.findViewById(R.id.img);
            TextView textView = (TextView) this.rel_img.findViewById(R.id.id_expand_textview);
            this.rel_img.setVisibility(0);
            if (str.contains("http")) {
                if (SkinManager.getCurrentSkinType(getContext()) != 0 && !this.fromVideo) {
                    imageView.setImageResource(R.drawable.pingluntupian_night);
                } else {
                    imageView.setImageResource(R.drawable.pingluntupian);
                }
                textView.setText("查看图片");
            } else {
                if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                    imageView.setImageResource(R.drawable.cat_video);
                } else {
                    imageView.setImageResource(R.drawable.cat_video);
                }
                if (this.fromVideo) {
                    imageView.setImageResource(R.drawable.cat_video_night);
                }
                textView.setText("查看视频");
                textView.setTextColor(Color.parseColor(!this.fromVideo ? "#C75959" : "#FFC100"));
            }
        }
        if (TextUtils.isEmpty(text)) {
            this.id_source_textview.setVisibility(8);
        } else {
            this.id_source_textview.setVisibility(0);
        }
        this.isCollapsed = isCollapsed;
        if (isCollapsed) {
            this.txt_str.setText("查看全部");
        }
        clearAnimation();
        setText(text);
        getLayoutParams().height = -2;
    }

    public void setText(String text, boolean isCollapsed) {
        if (TextUtils.isEmpty(text)) {
            setVisibility(8);
        } else {
            setVisibility(0);
        }
        this.isCollapsed = isCollapsed;
        if (isCollapsed) {
            this.txt_str.setText("查看全部");
        }
        clearAnimation();
        setText(text);
        getLayoutParams().height = -2;
    }
}
