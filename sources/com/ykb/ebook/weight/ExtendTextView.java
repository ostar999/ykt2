package com.ykb.ebook.weight;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import java.lang.reflect.Field;
import kotlin.text.Typography;

/* loaded from: classes8.dex */
public class ExtendTextView extends AppCompatTextView {
    private static final String DEFAULT_CLOSE_SUFFIX = " 收起";
    private static final int DEFAULT_MAX_LINE = 3;
    private static final String DEFAULT_OPEN_SUFFIX = " 更多";
    public static final String ELLIPSIS_STRING = new String(new char[]{Typography.ellipsis});
    volatile boolean animating;
    private boolean hasAnimation;
    private int initWidth;
    boolean isClosed;
    private int mCLoseHeight;
    private CharSequenceToSpannableHandler mCharSequenceToSpannableHandler;
    private Animation mCloseAnim;
    private boolean mCloseInNewLine;
    private SpannableStringBuilder mCloseSpannableStr;
    private int mCloseSuffixColor;

    @Nullable
    private SpannableString mCloseSuffixSpan;
    private String mCloseSuffixStr;
    private boolean mExpandable;
    private int mMaxLines;
    private View.OnClickListener mOnClickListener;
    private Animation mOpenAnim;
    public OpenAndCloseCallback mOpenCloseCallback;
    private int mOpenHeight;
    private SpannableStringBuilder mOpenSpannableStr;
    private int mOpenSuffixColor;

    @Nullable
    private SpannableString mOpenSuffixSpan;
    private String mOpenSuffixStr;
    private CharSequence originalText;

    public interface CharSequenceToSpannableHandler {
        @NonNull
        SpannableStringBuilder charSequenceToSpannable(CharSequence charSequence);
    }

    public class ExpandCollapseAnimation extends Animation {
        private final int mEndHeight;
        private final int mStartHeight;
        private final View mTargetView;

        public ExpandCollapseAnimation(View view, int i2, int i3) {
            this.mTargetView = view;
            this.mStartHeight = i2;
            this.mEndHeight = i3;
            setDuration(400L);
        }

        @Override // android.view.animation.Animation
        public void applyTransformation(float f2, Transformation transformation) {
            this.mTargetView.setScrollY(0);
            ViewGroup.LayoutParams layoutParams = this.mTargetView.getLayoutParams();
            int i2 = this.mEndHeight;
            layoutParams.height = (int) (((i2 - r1) * f2) + this.mStartHeight);
            this.mTargetView.requestLayout();
        }
    }

    public interface OpenAndCloseCallback {
        void onClose();

        void onOpen();
    }

    public ExtendTextView(Context context) {
        this(context, null);
    }

    private SpannableStringBuilder charSequenceToSpannable(@NonNull CharSequence charSequence) {
        CharSequenceToSpannableHandler charSequenceToSpannableHandler = this.mCharSequenceToSpannableHandler;
        SpannableStringBuilder spannableStringBuilderCharSequenceToSpannable = charSequenceToSpannableHandler != null ? charSequenceToSpannableHandler.charSequenceToSpannable(charSequence) : null;
        return spannableStringBuilderCharSequenceToSpannable == null ? new SpannableStringBuilder(charSequence) : spannableStringBuilderCharSequenceToSpannable;
    }

    private void close() {
    }

    private Layout createStaticLayout(SpannableStringBuilder spannableStringBuilder) {
        StaticLayout.Builder builderObtain = StaticLayout.Builder.obtain(spannableStringBuilder, 0, spannableStringBuilder.length(), getPaint(), (this.initWidth - getPaddingLeft()) - getPaddingRight());
        builderObtain.setAlignment(Layout.Alignment.ALIGN_NORMAL);
        builderObtain.setIncludePad(getIncludeFontPadding());
        builderObtain.setLineSpacing(getLineSpacingExtra(), getLineSpacingMultiplier());
        return builderObtain.build();
    }

    private void executeCloseAnim() {
        if (this.mCloseAnim == null) {
            ExpandCollapseAnimation expandCollapseAnimation = new ExpandCollapseAnimation(this, this.mOpenHeight, this.mCLoseHeight);
            this.mCloseAnim = expandCollapseAnimation;
            expandCollapseAnimation.setFillAfter(true);
            this.mCloseAnim.setAnimationListener(new Animation.AnimationListener() { // from class: com.ykb.ebook.weight.ExtendTextView.3
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    ExtendTextView.this.animating = false;
                    ExtendTextView extendTextView = ExtendTextView.this;
                    ExtendTextView.super.setMaxLines(extendTextView.mMaxLines);
                    ExtendTextView extendTextView2 = ExtendTextView.this;
                    extendTextView2.setText(extendTextView2.mCloseSpannableStr);
                    ExtendTextView.this.getLayoutParams().height = ExtendTextView.this.mCLoseHeight;
                    ExtendTextView.this.requestLayout();
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }
            });
        }
        if (this.animating) {
            return;
        }
        this.animating = true;
        clearAnimation();
        startAnimation(this.mCloseAnim);
    }

    private void executeOpenAnim() {
        if (this.mOpenAnim == null) {
            ExpandCollapseAnimation expandCollapseAnimation = new ExpandCollapseAnimation(this, this.mCLoseHeight, this.mOpenHeight);
            this.mOpenAnim = expandCollapseAnimation;
            expandCollapseAnimation.setFillAfter(true);
            this.mOpenAnim.setAnimationListener(new Animation.AnimationListener() { // from class: com.ykb.ebook.weight.ExtendTextView.2
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    ExtendTextView.this.getLayoutParams().height = ExtendTextView.this.mOpenHeight;
                    ExtendTextView.this.requestLayout();
                    ExtendTextView.this.animating = false;
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                    ExtendTextView.super.setMaxLines(Integer.MAX_VALUE);
                    ExtendTextView extendTextView = ExtendTextView.this;
                    extendTextView.setText(extendTextView.mOpenSpannableStr);
                }
            });
        }
        if (this.animating) {
            return;
        }
        this.animating = true;
        clearAnimation();
        startAnimation(this.mOpenAnim);
    }

    private float getFloatField(String str, float f2) {
        if (TextUtils.isEmpty(str)) {
            return f2;
        }
        try {
            for (Field field : getClass().getDeclaredFields()) {
                if (TextUtils.equals(str, field.getName())) {
                    return field.getFloat(this);
                }
            }
            return f2;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return f2;
        }
    }

    private int hasEnCharCount(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < charSequence.length(); i3++) {
            char cCharAt = charSequence.charAt(i3);
            if (cCharAt >= ' ' && cCharAt <= '~') {
                i2++;
            }
        }
        return i2;
    }

    private void initialize() {
        int color = Color.parseColor("#145FB3");
        this.mCloseSuffixColor = color;
        this.mOpenSuffixColor = color;
        setMovementMethod(OverLinkMovementMethod.getInstance());
        setIncludeFontPadding(false);
        updateOpenSuffixSpan();
        updateCloseSuffixSpan();
    }

    private void open() {
        OpenAndCloseCallback openAndCloseCallback = this.mOpenCloseCallback;
        if (openAndCloseCallback != null) {
            openAndCloseCallback.onOpen();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchOpenClose() {
        open();
    }

    private void updateCloseSuffixSpan() {
        if (TextUtils.isEmpty(this.mCloseSuffixStr)) {
            this.mCloseSuffixSpan = null;
            return;
        }
        SpannableString spannableString = new SpannableString(this.mCloseSuffixStr);
        this.mCloseSuffixSpan = spannableString;
        spannableString.setSpan(new StyleSpan(1), 0, this.mCloseSuffixStr.length(), 33);
        if (this.mCloseInNewLine) {
            this.mCloseSuffixSpan.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), 0, 1, 33);
        }
        this.mCloseSuffixSpan.setSpan(new ClickableSpan() { // from class: com.ykb.ebook.weight.ExtendTextView.5
            @Override // android.text.style.ClickableSpan
            public void onClick(@NonNull View view) {
                ExtendTextView.this.switchOpenClose();
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(@NonNull TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(ExtendTextView.this.mCloseSuffixColor);
                textPaint.setUnderlineText(false);
            }
        }, 1, this.mCloseSuffixStr.length(), 33);
    }

    private void updateOpenSuffixSpan() {
        if (TextUtils.isEmpty(this.mOpenSuffixStr)) {
            this.mOpenSuffixSpan = null;
            return;
        }
        SpannableString spannableString = new SpannableString(this.mOpenSuffixStr);
        this.mOpenSuffixSpan = spannableString;
        spannableString.setSpan(new StyleSpan(0), 0, this.mOpenSuffixStr.length(), 33);
        this.mOpenSuffixSpan.setSpan(new ClickableSpan() { // from class: com.ykb.ebook.weight.ExtendTextView.4
            @Override // android.text.style.ClickableSpan
            public void onClick(@NonNull View view) {
                ExtendTextView.this.switchOpenClose();
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(@NonNull TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(ExtendTextView.this.mOpenSuffixColor);
                textPaint.setUnderlineText(false);
            }
        }, 0, this.mOpenSuffixStr.length(), 34);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void initWidth(int i2) {
        this.initWidth = i2;
    }

    public void setCharSequenceToSpannableHandler(CharSequenceToSpannableHandler charSequenceToSpannableHandler) {
        this.mCharSequenceToSpannableHandler = charSequenceToSpannableHandler;
    }

    public void setCloseInNewLine(boolean z2) {
        this.mCloseInNewLine = z2;
        updateCloseSuffixSpan();
    }

    public void setCloseSuffix(String str) {
        updateCloseSuffixSpan();
    }

    public void setCloseSuffixColor(@ColorInt int i2) {
        this.mCloseSuffixColor = i2;
        updateCloseSuffixSpan();
    }

    public void setHasAnimation(boolean z2) {
        this.hasAnimation = z2;
    }

    @Override // android.widget.TextView
    public void setMaxLines(int i2) {
        this.mMaxLines = i2;
        super.setMaxLines(i2);
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setOpenAndCloseCallback(OpenAndCloseCallback openAndCloseCallback) {
        this.mOpenCloseCallback = openAndCloseCallback;
    }

    public void setOpenSuffix(String str) {
        this.mOpenSuffixStr = str;
        updateOpenSuffixSpan();
    }

    public void setOpenSuffixColor(@ColorInt int i2) {
        this.mOpenSuffixColor = i2;
        updateOpenSuffixSpan();
    }

    public void setOriginalText(CharSequence charSequence) {
        int length;
        this.originalText = charSequence;
        this.mExpandable = false;
        this.mCloseSpannableStr = new SpannableStringBuilder();
        int i2 = this.mMaxLines;
        SpannableStringBuilder spannableStringBuilderCharSequenceToSpannable = charSequenceToSpannable(charSequence);
        this.mOpenSpannableStr = charSequenceToSpannable(charSequence);
        if (i2 != -1) {
            Layout layoutCreateStaticLayout = createStaticLayout(spannableStringBuilderCharSequenceToSpannable);
            boolean z2 = layoutCreateStaticLayout.getLineCount() > i2;
            this.mExpandable = z2;
            if (z2) {
                if (this.mCloseInNewLine) {
                    this.mOpenSpannableStr.append((CharSequence) "\n");
                }
                SpannableString spannableString = this.mCloseSuffixSpan;
                if (spannableString != null) {
                    this.mOpenSpannableStr.append((CharSequence) spannableString);
                }
                int lineEnd = layoutCreateStaticLayout.getLineEnd(i2 - 1);
                if (charSequence.length() <= lineEnd) {
                    this.mCloseSpannableStr = charSequenceToSpannable(charSequence);
                } else {
                    this.mCloseSpannableStr = charSequenceToSpannable(charSequence.subSequence(0, lineEnd));
                }
                SpannableStringBuilder spannableStringBuilderAppend = charSequenceToSpannable(this.mCloseSpannableStr).append((CharSequence) ELLIPSIS_STRING);
                SpannableString spannableString2 = this.mOpenSuffixSpan;
                if (spannableString2 != null) {
                    spannableStringBuilderAppend.append((CharSequence) spannableString2);
                }
                Layout layoutCreateStaticLayout2 = createStaticLayout(spannableStringBuilderAppend);
                while (layoutCreateStaticLayout2.getLineCount() > i2 && (length = this.mCloseSpannableStr.length() - 1) != -1) {
                    if (charSequence.length() <= length) {
                        this.mCloseSpannableStr = charSequenceToSpannable(charSequence);
                    } else {
                        this.mCloseSpannableStr = charSequenceToSpannable(charSequence.subSequence(0, length));
                    }
                    SpannableStringBuilder spannableStringBuilderAppend2 = charSequenceToSpannable(this.mCloseSpannableStr).append((CharSequence) ELLIPSIS_STRING);
                    SpannableString spannableString3 = this.mOpenSuffixSpan;
                    if (spannableString3 != null) {
                        spannableStringBuilderAppend2.append((CharSequence) spannableString3);
                    }
                    layoutCreateStaticLayout2 = createStaticLayout(spannableStringBuilderAppend2);
                }
                int length2 = this.mCloseSpannableStr.length() - this.mOpenSuffixSpan.length();
                if (length2 >= 0 && charSequence.length() > length2) {
                    int iHasEnCharCount = (hasEnCharCount(charSequence.subSequence(length2, this.mOpenSuffixSpan.length() + length2)) - hasEnCharCount(this.mOpenSuffixSpan)) + 1;
                    if (iHasEnCharCount > 0) {
                        length2 -= iHasEnCharCount;
                    }
                    this.mCloseSpannableStr = charSequenceToSpannable(charSequence.subSequence(0, length2));
                }
                this.mCLoseHeight = layoutCreateStaticLayout2.getHeight() + getPaddingTop() + getPaddingBottom();
                this.mCloseSpannableStr.append((CharSequence) ELLIPSIS_STRING);
                SpannableString spannableString4 = this.mOpenSuffixSpan;
                if (spannableString4 != null) {
                    this.mCloseSpannableStr.append((CharSequence) spannableString4);
                }
            }
        }
        boolean z3 = this.mExpandable;
        this.isClosed = z3;
        if (!z3) {
            setText(this.mOpenSpannableStr);
        } else {
            setText(this.mCloseSpannableStr);
            super.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.ExtendTextView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                }
            });
        }
    }

    public ExtendTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ExtendTextView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.animating = false;
        this.isClosed = false;
        this.mMaxLines = 3;
        this.initWidth = 0;
        this.hasAnimation = false;
        this.mOpenSuffixStr = DEFAULT_OPEN_SUFFIX;
        this.mCloseSuffixStr = DEFAULT_CLOSE_SUFFIX;
        initialize();
    }
}
