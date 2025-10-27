package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.psychiatrygarden.activity.purchase.beans.Tag;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class TagCloudLinkViewTag2 extends RelativeLayout {
    private static final String DEFAULT_DELETABLE_STRING = "×";
    private static final int DEFAULT_TEXT_SIZE = 14;
    private static final int HEIGHT_WC = -2;
    private static final int INNER_VIEW_PADDING = 5;
    private static final int LAYOUT_WIDTH_OFFSET = 5;
    private static final int TAG_LAYOUT_LEFT_MERGIN = 10;
    private static final int TAG_LAYOUT_TOP_MERGIN = 10;
    private int mDeletableTextColor;
    private float mDeletableTextSize;
    private OnTagDeleteListener mDeleteListener;
    private Display mDisplay;
    private int mHeight;
    private LayoutInflater mInflater;
    private boolean mInitialized;
    private boolean mIsDeletable;
    private OnTagSelectListener mSelectListener;
    private int mTagLayoutColor;
    private int mTagTextColor;
    private float mTagTextSize;
    public List<Tag> mTags;
    private ViewTreeObserver mViewTreeObserber;
    private int mWidth;
    private static final int DEFAULT_TAG_LAYOUT_COLOR = Color.parseColor("#00000000");
    private static final int DEFAULT_TAG_TEXT_COLOR = Color.parseColor("#f6f6f6");
    private static final int DEFAULT_DELETABLE_TEXT_COLOR = Color.parseColor("#1a1a1a");

    public interface OnTagDeleteListener {
        void onTagDeleted(Tag tag, int position);
    }

    public interface OnTagSelectListener {
        void onTagSelected(Tag tag, int position);
    }

    public TagCloudLinkViewTag2(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        this.mTags = new ArrayList();
        this.mInitialized = false;
        initialize(ctx, attrs, 0);
    }

    private void initialize(Context ctx, AttributeSet attrs, int defStyle) {
        this.mInflater = (LayoutInflater) ctx.getSystemService("layout_inflater");
        this.mDisplay = ((WindowManager) ctx.getSystemService("window")).getDefaultDisplay();
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        this.mViewTreeObserber = viewTreeObserver;
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.widget.TagCloudLinkViewTag2.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (TagCloudLinkViewTag2.this.mInitialized) {
                    return;
                }
                TagCloudLinkViewTag2.this.mInitialized = true;
                TagCloudLinkViewTag2.this.drawTags();
            }
        });
        TypedArray typedArrayObtainStyledAttributes = ctx.obtainStyledAttributes(attrs, R.styleable.TagCloudLinkView, defStyle, defStyle);
        this.mTagLayoutColor = typedArrayObtainStyledAttributes.getColor(3, DEFAULT_TAG_LAYOUT_COLOR);
        this.mTagTextSize = typedArrayObtainStyledAttributes.getDimension(5, 14.0f);
        this.mIsDeletable = typedArrayObtainStyledAttributes.getBoolean(2, false);
        this.mDeletableTextColor = typedArrayObtainStyledAttributes.getColor(0, DEFAULT_TAG_TEXT_COLOR);
        this.mDeletableTextSize = typedArrayObtainStyledAttributes.getDimension(1, DEFAULT_DELETABLE_TEXT_COLOR);
    }

    public void add(Tag tag) {
        this.mTags.add(tag);
    }

    public void drawTags() {
        if (this.mInitialized) {
            removeAllViews();
            float paddingLeft = getPaddingLeft() + getPaddingRight();
            int i2 = 1;
            int i3 = 1;
            final int i4 = 0;
            for (final Tag tag : this.mTags) {
                View viewInflate = this.mInflater.inflate(R.layout.tv_tag3, (ViewGroup) null);
                viewInflate.setId(i2);
                final TextView textView = (TextView) viewInflate.findViewById(R.id.tv_selector);
                textView.setText(tag.getText());
                textView.setSelected(tag.isIs_click());
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.TagCloudLinkViewTag2.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v2) {
                        textView.setSelected(true);
                        if (TagCloudLinkViewTag2.this.mSelectListener != null) {
                            TagCloudLinkViewTag2.this.mSelectListener.onTagSelected(tag, i4);
                        }
                    }
                });
                float fMeasureText = textView.getPaint().measureText(tag.getText()) + 10.0f;
                TextView textView2 = (TextView) viewInflate.findViewById(R.id.delete_txt);
                if (this.mIsDeletable) {
                    textView2.setVisibility(0);
                    textView2.setText(DEFAULT_DELETABLE_STRING);
                    textView2.setPadding(5, 5, 5, 5);
                    textView2.setTextColor(this.mDeletableTextColor);
                    textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.TagCloudLinkViewTag2.3
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v2) {
                            if (TagCloudLinkViewTag2.this.mDeleteListener != null) {
                                Tag tag2 = tag;
                                TagCloudLinkViewTag2.this.remove(i4);
                                TagCloudLinkViewTag2.this.mDeleteListener.onTagDeleted(tag2, i4);
                            }
                        }
                    });
                    fMeasureText += textView2.getPaint().measureText(DEFAULT_DELETABLE_STRING) + 10.0f;
                } else {
                    textView2.setVisibility(8);
                }
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams.setMargins(0, 0, 0, 0);
                if (this.mWidth <= paddingLeft + fMeasureText + 5.0f) {
                    layoutParams.addRule(3, i3);
                    layoutParams.topMargin = 10;
                    paddingLeft = getPaddingLeft() + getPaddingRight();
                    i3 = i2;
                } else {
                    layoutParams.addRule(3, i2 - 1);
                    if (i2 > 1) {
                        layoutParams.topMargin = 10;
                        paddingLeft += 10.0f;
                    }
                }
                paddingLeft += fMeasureText;
                addView(viewInflate, layoutParams);
                i2++;
                i4++;
            }
        }
    }

    public List<Tag> getTags() {
        return this.mTags;
    }

    public int height() {
        return this.mHeight;
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        this.mWidth = w2;
        this.mHeight = h2;
    }

    public void remove(int position) {
        this.mTags.remove(position);
        drawTags();
    }

    public void setOnTagDeleteListener(OnTagDeleteListener deleteListener) {
        this.mDeleteListener = deleteListener;
    }

    public void setOnTagSelectListener(OnTagSelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    public int width() {
        return this.mWidth;
    }

    public TagCloudLinkViewTag2(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        this.mTags = new ArrayList();
        this.mInitialized = false;
        initialize(ctx, attrs, defStyle);
    }
}
