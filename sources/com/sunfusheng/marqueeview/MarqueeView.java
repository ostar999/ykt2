package com.sunfusheng.marqueeview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.annotation.AnimRes;
import androidx.core.content.res.ResourcesCompat;
import com.catchpig.mvvm.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class MarqueeView<T> extends ViewFlipper {
    private static final int DIRECTION_BOTTOM_TO_TOP = 0;
    private static final int DIRECTION_LEFT_TO_RIGHT = 3;
    private static final int DIRECTION_RIGHT_TO_LEFT = 2;
    private static final int DIRECTION_TOP_TO_BOTTOM = 1;
    private static final int GRAVITY_CENTER = 1;
    private static final int GRAVITY_LEFT = 0;
    private static final int GRAVITY_RIGHT = 2;
    private int animDuration;
    private int direction;
    private int gravity;
    private boolean hasSetAnimDuration;
    private int inAnimResId;
    private int interval;
    private boolean isAnimStart;
    private List<T> messages;
    private OnItemClickListener onItemClickListener;
    private int outAnimResId;
    private int position;
    private boolean singleLine;
    private int textColor;
    private int textSize;
    private Typeface typeface;

    public interface OnItemClickListener {
        void onItemClick(int i2, TextView textView);
    }

    public MarqueeView(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TextView createTextView(T t2) {
        TextView textView = (TextView) getChildAt((getDisplayedChild() + 1) % 3);
        if (textView == null) {
            textView = new TextView(getContext());
            textView.setGravity(this.gravity | 16);
            textView.setTextColor(this.textColor);
            textView.setTextSize(this.textSize);
            textView.setIncludeFontPadding(true);
            textView.setSingleLine(this.singleLine);
            if (this.singleLine) {
                textView.setMaxLines(1);
                textView.setEllipsize(TextUtils.TruncateAt.END);
            }
            Typeface typeface = this.typeface;
            if (typeface != null) {
                textView.setTypeface(typeface);
            }
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.sunfusheng.marqueeview.MarqueeView.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (MarqueeView.this.onItemClickListener != null) {
                        MarqueeView.this.onItemClickListener.onItemClick(MarqueeView.this.getPosition(), (TextView) view);
                    }
                }
            });
        }
        textView.setText(t2 instanceof CharSequence ? (CharSequence) t2 : t2 instanceof IMarqueeItem ? ((IMarqueeItem) t2).marqueeMessage() : "");
        textView.setTag(Integer.valueOf(this.position));
        return textView;
    }

    private void init(Context context, AttributeSet attributeSet, int i2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MarqueeViewStyle, i2, 0);
        this.interval = typedArrayObtainStyledAttributes.getInteger(R.styleable.MarqueeViewStyle_mvInterval, this.interval);
        int i3 = R.styleable.MarqueeViewStyle_mvAnimDuration;
        this.hasSetAnimDuration = typedArrayObtainStyledAttributes.hasValue(i3);
        this.animDuration = typedArrayObtainStyledAttributes.getInteger(i3, this.animDuration);
        this.singleLine = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MarqueeViewStyle_mvSingleLine, false);
        int i4 = R.styleable.MarqueeViewStyle_mvTextSize;
        if (typedArrayObtainStyledAttributes.hasValue(i4)) {
            int dimension = (int) typedArrayObtainStyledAttributes.getDimension(i4, this.textSize);
            this.textSize = dimension;
            this.textSize = Utils.px2sp(context, dimension);
        }
        this.textColor = typedArrayObtainStyledAttributes.getColor(R.styleable.MarqueeViewStyle_mvTextColor, this.textColor);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MarqueeViewStyle_mvFont, 0);
        if (resourceId != 0) {
            this.typeface = ResourcesCompat.getFont(context, resourceId);
        }
        int i5 = typedArrayObtainStyledAttributes.getInt(R.styleable.MarqueeViewStyle_mvGravity, 0);
        if (i5 == 0) {
            this.gravity = 19;
        } else if (i5 == 1) {
            this.gravity = 17;
        } else if (i5 == 2) {
            this.gravity = 21;
        }
        int i6 = R.styleable.MarqueeViewStyle_mvDirection;
        if (typedArrayObtainStyledAttributes.hasValue(i6)) {
            int i7 = typedArrayObtainStyledAttributes.getInt(i6, this.direction);
            this.direction = i7;
            if (i7 == 0) {
                this.inAnimResId = R.anim.anim_bottom_in;
                this.outAnimResId = R.anim.anim_top_out;
            } else if (i7 == 1) {
                this.inAnimResId = R.anim.anim_top_in;
                this.outAnimResId = R.anim.anim_bottom_out;
            } else if (i7 == 2) {
                this.inAnimResId = R.anim.anim_right_in;
                this.outAnimResId = R.anim.anim_left_out;
            } else if (i7 == 3) {
                this.inAnimResId = R.anim.anim_left_in;
                this.outAnimResId = R.anim.anim_right_out;
            }
        } else {
            this.inAnimResId = R.anim.anim_bottom_in;
            this.outAnimResId = R.anim.anim_top_out;
        }
        typedArrayObtainStyledAttributes.recycle();
        setFlipInterval(this.interval);
    }

    private void postStart(@AnimRes final int i2, @AnimRes final int i3) {
        post(new Runnable() { // from class: com.sunfusheng.marqueeview.MarqueeView.2
            @Override // java.lang.Runnable
            public void run() throws Resources.NotFoundException {
                MarqueeView.this.start(i2, i3);
            }
        });
    }

    private void setInAndOutAnimation(@AnimRes int i2, @AnimRes int i3) throws Resources.NotFoundException {
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(getContext(), i2);
        if (this.hasSetAnimDuration) {
            animationLoadAnimation.setDuration(this.animDuration);
        }
        setInAnimation(animationLoadAnimation);
        Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(getContext(), i3);
        if (this.hasSetAnimDuration) {
            animationLoadAnimation2.setDuration(this.animDuration);
        }
        setOutAnimation(animationLoadAnimation2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void start(@AnimRes int i2, @AnimRes int i3) throws Resources.NotFoundException {
        removeAllViews();
        clearAnimation();
        List<T> list = this.messages;
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("The messages cannot be empty!");
        }
        this.position = 0;
        addView(createTextView(this.messages.get(0)));
        if (this.messages.size() > 1) {
            setInAndOutAnimation(i2, i3);
            startFlipping();
        }
        if (getInAnimation() != null) {
            getInAnimation().setAnimationListener(new Animation.AnimationListener() { // from class: com.sunfusheng.marqueeview.MarqueeView.3
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    MarqueeView.this.position++;
                    if (MarqueeView.this.position >= MarqueeView.this.messages.size()) {
                        MarqueeView.this.position = 0;
                    }
                    MarqueeView marqueeView = MarqueeView.this;
                    TextView textViewCreateTextView = marqueeView.createTextView(marqueeView.messages.get(MarqueeView.this.position));
                    if (textViewCreateTextView.getParent() == null) {
                        MarqueeView.this.addView(textViewCreateTextView);
                    }
                    MarqueeView.this.isAnimStart = false;
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                    if (MarqueeView.this.isAnimStart) {
                        animation.cancel();
                    }
                    MarqueeView.this.isAnimStart = true;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startWithFixedWidth(String str, @AnimRes int i2, @AnimRes int i3) {
        int length = str.length();
        int iPx2dip = Utils.px2dip(getContext(), getWidth());
        if (iPx2dip == 0) {
            throw new RuntimeException("Please set the width of MarqueeView !");
        }
        int i4 = iPx2dip / this.textSize;
        ArrayList arrayList = new ArrayList();
        if (length <= i4) {
            arrayList.add(str);
        } else {
            int i5 = 0;
            int i6 = (length / i4) + (length % i4 != 0 ? 1 : 0);
            while (i5 < i6) {
                int i7 = i5 * i4;
                i5++;
                int i8 = i5 * i4;
                if (i8 >= length) {
                    i8 = length;
                }
                arrayList.add(str.substring(i7, i8));
            }
        }
        if (this.messages == null) {
            this.messages = new ArrayList();
        }
        this.messages.clear();
        this.messages.addAll(arrayList);
        postStart(i2, i3);
    }

    public List<T> getMessages() {
        return this.messages;
    }

    public int getPosition() {
        return ((Integer) getCurrentView().getTag()).intValue();
    }

    public void setMessages(List<T> list) {
        this.messages = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public void startWithList(List<T> list) {
        startWithList(list, this.inAnimResId, this.outAnimResId);
    }

    public void startWithText(String str) {
        startWithText(str, this.inAnimResId, this.outAnimResId);
    }

    public MarqueeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.interval = 3000;
        this.hasSetAnimDuration = false;
        this.animDuration = 1000;
        this.textSize = 14;
        this.textColor = -16777216;
        this.singleLine = false;
        this.gravity = 19;
        this.direction = 0;
        this.inAnimResId = R.anim.anim_bottom_in;
        this.outAnimResId = R.anim.anim_top_out;
        this.messages = new ArrayList();
        this.isAnimStart = false;
        init(context, attributeSet, 0);
    }

    public void startWithList(List<T> list, @AnimRes int i2, @AnimRes int i3) {
        if (Utils.isEmpty(list)) {
            return;
        }
        setMessages(list);
        postStart(i2, i3);
    }

    public void startWithText(final String str, @AnimRes final int i2, @AnimRes final int i3) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.sunfusheng.marqueeview.MarqueeView.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                MarqueeView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                MarqueeView.this.startWithFixedWidth(str, i2, i3);
            }
        });
    }
}
